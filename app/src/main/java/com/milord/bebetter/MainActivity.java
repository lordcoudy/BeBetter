package com.milord.bebetter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Content values for INSERT into DB
    private final ContentValues curseRow = new ContentValues();
    private final ContentValues blessRow = new ContentValues();
    private final ContentValues dateRow = new ContentValues();

    // Base values for app
    protected static int curseCount = 0;
    protected static int blessCount = 0;
    private int snacks = 0;
    private int pushups = 0;
    private int goods = 0;
    private int tmpCurse = 0;
    private String currentDate = "";
    private final String tmpDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    private PendingIntent pendingIntent;

    public DatabaseHelper DH = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        }
        startAt2359();  // Run void to set up Alarm at 23:59

        // Get info from database
        curseCount = DH.getCurse().GetCurse();
        blessCount = DH.getCurse().GetBless();
        currentDate = DH.getTime().GetDate();

        // Additional values
        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;
        goods = blessCount/50;

        // Views
        final TextView curse = this.findViewById(R.id.curseTW);
        final TextView curseAmount = this.findViewById(R.id.curseTW2);
        final TextView curseless = this.findViewById(R.id.curselessTW);
        final TextView curselessAmount = this.findViewById(R.id.curselessTW2);

        curse.setText(getString(R.string.cursesBegin) + currentDate + ":\n" + Settings.penaltyBig + " " + getString(R.string.snacksToBuy) + snacks + "\n" + Settings.penalty + " " + getString(R.string.PushUps) + pushups);
        curseAmount.setText(curseCount + "");

        curseless.setText(getString(R.string.blessBegin) + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }

    public void AddCurse (View view)
    {
        curseCount++;

        // Taking care of auto decreasing
        if (!tmpDate.equals(currentDate))
        {
            blessCount--;
            currentDate = tmpDate;
        }

        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;

        // Updating database
        Curse cursebase = new Curse(curseCount, blessCount);
        DH.updateCurse(cursebase);
        Timetable timetable = new Timetable(currentDate);
        DH.updateTime(timetable);

        // Views
        final TextView curse = this.findViewById(R.id.curseTW);
        final TextView curseAmount = this.findViewById(R.id.curseTW2);
        final Button curseButton = this.findViewById(R.id.addCurseButton);
        curseButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        curse.setText(getString(R.string.cursesBegin) + currentDate + ":\n" + Settings.penaltyBig + " " + getString(R.string.snacksToBuy) + snacks + "\n" + Settings.penalty + " " + getString(R.string.PushUps) + pushups);
        curseAmount.setText(curseCount + "");

    }

    public void SubCurse (View view)
    {
        curseCount -= (curseCount > 0 ? 1 : 0);

        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;

        Curse cursebase = new Curse(curseCount, blessCount);
        DH.updateCurse(cursebase);

        final TextView curse = this.findViewById(R.id.curseTW);
        final TextView curseAmount = this.findViewById(R.id.curseTW2);
        final Button curselessButton = this.findViewById(R.id.subCurseButton);
        curselessButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        curse.setText(getString(R.string.cursesBegin) + currentDate + ":\n" + Settings.penaltyBig + " " + getString(R.string.snacksToBuy) + snacks + "\n" + Settings.penalty + " " + getString(R.string.PushUps) + pushups);
        curseAmount.setText(curseCount + "");
    }

    public void AddCurseless (View view)
    {
        blessCount++;
        goods = blessCount/50;

        Curse cursebase = new Curse(curseCount, blessCount);
        DH.updateCurse(cursebase);

        final TextView curseless = this.findViewById(R.id.curselessTW);
        final TextView curselessAmount = this.findViewById(R.id.curselessTW2);
        final Button blessButton = this.findViewById(R.id.addCurselessButton);
        blessButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        curseless.setText(getString(R.string.blessBegin) + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }

    public void SubCurseless (View view)
    {
        blessCount -= (blessCount > 0 ? 1 : 0);
        goods = blessCount/50;

        Curse cursebase = new Curse(curseCount, blessCount);
        DH.updateCurse(cursebase);

        final TextView curseless = this.findViewById(R.id.curselessTW);
        final TextView curselessAmount = this.findViewById(R.id.curselessTW2);
        final Button blesslessButton = this.findViewById(R.id.subCurselessButton);
        blesslessButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        curseless.setText(getString(R.string.blessBegin) + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }

    public void goToSettings (View view)
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void startAt2359() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60 * 24;

        /* Set the alarm to start at 23:59 */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);

        /* Repeating on every 24 hours interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);

    }

}

