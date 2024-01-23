package com.example.sigara_stok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sigara_stok.R;
import com.example.sigara_stok.tekel_stocks.KentStocksActivity;
import com.example.sigara_stok.tekel_stocks.RothmansStocksActivity;
import com.example.sigara_stok.tekel_stocks.TekelStocksActivity;
import com.example.sigara_stok.tekel_stocks.ViceroyStocksActivity;

public class TekelActivity extends AppCompatActivity {

    Button kent_intent_btn, rothmans_intent_btn, tekel_intent_btn, viceroy_intent_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekel);

        setTekelButtons(R.id.kent_intent_btn, KentStocksActivity.class);
        setTekelButtons(R.id.rothmans_intent_btn, RothmansStocksActivity.class);
        setTekelButtons(R.id.tekel_intent_btn, TekelStocksActivity.class);
        setTekelButtons(R.id.viceroy_intent_btn, ViceroyStocksActivity.class);
    }

    private void setTekelButtons(int buttonId, final Class<?> cls) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TekelActivity.this, cls));
            }
        });
    }
}