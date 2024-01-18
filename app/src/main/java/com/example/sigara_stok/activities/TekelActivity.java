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

        kent_intent_btn = findViewById(R.id.kent_intent_btn);
        rothmans_intent_btn = findViewById(R.id.rothmans_intent_btn);
        tekel_intent_btn = findViewById(R.id.tekel_intent_btn);
        viceroy_intent_btn = findViewById(R.id.viceroy_intent_btn);

        kent_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TekelActivity.this, KentStocksActivity.class);
                startActivity(i);
            }
        });

        rothmans_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TekelActivity.this, RothmansStocksActivity.class);
                startActivity(i);
            }
        });

        tekel_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TekelActivity.this, TekelStocksActivity.class);
                startActivity(i);
            }
        });

        viceroy_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TekelActivity.this, ViceroyStocksActivity.class);
                startActivity(i);
            }
        });
    }
}