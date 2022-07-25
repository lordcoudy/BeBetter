package com.milord.bebetter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database info
    public static final String CURSE_TABLE = "curseDB";
    public static final String TIME_TABLE = "timeDB";
    public static final String CURSE_TABLE_CURSES = "curse";
    public static final String CURSE_TABLE_BLESSES = "bless";
    public static final String TIME_TABLE_CURRENT = "currentdate";
    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CURSES_QUERY = "CREATE TABLE IF NOT EXISTS " + CURSE_TABLE + "(" + CURSE_TABLE_CURSES + "INT," + CURSE_TABLE_BLESSES + "INT);";
        final String TIME_QUERY = "CREATE TABLE IF NOT EXISTS " + TIME_TABLE + "(" + TIME_TABLE_CURRENT + "TEXT);";
        db.execSQL(CURSES_QUERY);
        db.execSQL(TIME_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String CURSES_DELETE_QUERY = "DROP TABLE IF EXISTS " + CURSE_TABLE;
        final String TIME_DELETE_QUERY = "DROP TABLE IF EXISTS " + TIME_TABLE;
        db.execSQL(CURSES_DELETE_QUERY);
        db.execSQL(TIME_DELETE_QUERY);
        onCreate(db);
        DATABASE_VERSION++;
    }

    void updateCurse(Curse curse) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CURSE_TABLE_CURSES, curse.GetCurse()); // Curses
        values.put(CURSE_TABLE_BLESSES, curse.GetBless()); // Blesses

        // Inserting Row
        db.insert(CURSE_TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    Curse getCurse(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery("select " + CURSE_TABLE_CURSES + ", " + CURSE_TABLE_BLESSES + " from " + CURSE_TABLE, null);

        if (cursor != null)
            cursor.moveToLast();

        Curse curse = new Curse(cursor.getInt(0),
                cursor.getInt(1));

        return curse;
    }

    void updateTime(Timetable timetable) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TIME_TABLE_CURRENT, timetable.GetDate()); // Curses

        // Inserting Row
        db.insert(TIME_TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    Timetable getTime(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery("select " + TIME_TABLE_CURRENT + " from " + TIME_TABLE, null);

        if (cursor != null)
            cursor.moveToLast();

        Timetable timetable = new Timetable(cursor.getString(0));

        return timetable;
    }

}
