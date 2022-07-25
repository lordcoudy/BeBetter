package com.milord.bebetter;

import static com.milord.bebetter.MainActivity.blessCount;
import static com.milord.bebetter.MainActivity.curseCount;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Increase number of curseless days by 1 each day
        blessCount++;
        DatabaseHelper DH = new DatabaseHelper(context);
        Curse cursebase = new Curse(curseCount, blessCount);
        DH.updateCurse(cursebase);
    }
}