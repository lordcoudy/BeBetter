package com.milord.bebetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Content values for INSERT into DB
    private final ContentValues curseRow = new ContentValues();
    private final ContentValues blessRow = new ContentValues();
    private final ContentValues dateRow = new ContentValues();

    private int curseCount = 0;
    private int blessCount = 0;
    private int snacks = 0;
    private int pushups = 0;
    private int goods = 0;
    private int tmpCurse = 0;

    private String currentDate = "";
    private final String tmpDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS curseDB (curse INT, bless INT)"
        );
        Cursor myCursor =
                myDB.rawQuery("select curse, bless from curseDB", null);
        while(myCursor.moveToNext()) {
            curseCount = myCursor.getInt(0);
            blessCount = myCursor.getInt(1);
        }
        myCursor.close();

        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS timeDB (currentdate CHAR(50))"
        );

        Cursor dateCursor =
                myDB.rawQuery("select currentdate from timeDB", null);
        while(dateCursor.moveToNext()) {
            currentDate = dateCursor.getString(0);
        }
        dateCursor.close();
        myDB.close();

        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;
        goods = blessCount/50;

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

        if (!tmpDate.equals(currentDate))
        {
            blessCount--;
            currentDate = tmpDate;
        }

        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;

        SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS curseDB (curse INT, bless INT)"
        );
        curseRow.put("curse", curseCount);
        curseRow.put("bless", blessCount);
        myDB.insert("curseDB", null, curseRow);
        myDB.insert("curseDB", null, blessRow);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS timeDB (currentdate CHAR(50))"
        );
        dateRow.put("currentdate", currentDate);
        myDB.insert("timeDB", null, dateRow);
        myDB.close();

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

        SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS curseDB (curse INT, bless INT)"
        );
        curseRow.put("curse", curseCount);
        curseRow.put("bless", blessCount);
        myDB.insert("curseDB", null, curseRow);
        myDB.insert("curseDB", null, blessRow);
        myDB.close();

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

        SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS curseDB (curse INT, bless INT)"
        );
        curseRow.put("curse", curseCount);
        curseRow.put("bless", blessCount);
        myDB.insert("curseDB", null, curseRow);
        myDB.insert("curseDB", null, blessRow);
        myDB.close();

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

        SQLiteDatabase myDB =
                openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS curseDB (curse INT, bless INT)"
        );
        curseRow.put("curse", curseCount);
        curseRow.put("bless", blessCount);
        myDB.insert("curseDB", null, curseRow);
        myDB.insert("curseDB", null, blessRow);
        myDB.close();

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
}

