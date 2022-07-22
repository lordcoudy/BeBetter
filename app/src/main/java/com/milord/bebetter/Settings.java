package com.milord.bebetter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class Settings extends AppCompatActivity {
    public static String penalty = "Push-ups";
    public static String penaltyBig = "Snacks";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }

    public void Save (View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        final TextInputEditText penaltyInput = this.findViewById(R.id.curseInput);
        final TextInputEditText penaltyBigInput = this.findViewById(R.id.blessInput);
        if (!penaltyInput.getText().toString().isEmpty())
        {
            penalty = penaltyInput.getText().toString();
        }
        if (!penaltyBigInput.getText().toString().isEmpty())
        {
            penaltyBig = penaltyBigInput.getText().toString();
        }
        startActivity(intent);
    }
}
