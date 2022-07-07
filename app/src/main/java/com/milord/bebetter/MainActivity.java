package com.milord.bebetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final ContentValues curseRow = new ContentValues();
    private final ContentValues blessRow = new ContentValues();

    private int curseCount = 0;
    private int blessCount = 0;
    private int snacks = 0;
    private int pushups = 0;
    private int goods = 0;
    int tmpCurse = 0;
    private final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    private final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

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
        myDB.close();

        snacks = curseCount/5;
        tmpCurse = curseCount - snacks;
        pushups = tmpCurse*20;
        goods = blessCount/50;


        final TextView curse = this.findViewById(R.id.curseTW);
        final TextView curseAmount = this.findViewById(R.id.curseTW2);
        final TextView curseless = this.findViewById(R.id.curselessTW);
        final TextView curselessAmount = this.findViewById(R.id.curselessTW2);

        curse.setText(getString(R.string.cursesBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.snacksToBuy) + snacks + getString(R.string.PushUps) + pushups);
        curseAmount.setText(curseCount + "");

        curseless.setText(getString(R.string.blessBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }


    public void AddCurse (View view)
    {
        curseCount++;

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

        curse.setText(getString(R.string.cursesBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.snacksToBuy) + snacks + getString(R.string.PushUps) + pushups);
        curseAmount.setText(curseCount + "");
    }

    public void SubCurse (View view)
    {
        curseCount--;

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

        curse.setText(getString(R.string.cursesBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.snacksToBuy) + snacks + getString(R.string.PushUps) + pushups);
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

        curseless.setText(getString(R.string.blessBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }

    public void SubCurseless (View view)
    {
        blessCount--;
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

        curseless.setText(getString(R.string.blessBegin) + currentTime + getString(R.string.of) + currentDate + getString(R.string.Goods) + goods);
        curselessAmount.setText(blessCount + "");
    }
}