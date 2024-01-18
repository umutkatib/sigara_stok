package com.example.sigara_stok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sigara_stok.R;
import com.example.sigara_stok.winston_stocks.CamelStocksActivity;
import com.example.sigara_stok.winston_stocks.LDStocksActivity;
import com.example.sigara_stok.winston_stocks.MonteCarloStocksActivity;
import com.example.sigara_stok.winston_stocks.WinstonStocksActivity;

public class WinstonActivity extends AppCompatActivity {

    Button winston_intent_btn, camel_intent_btn, montecarlo_intent_btn, ld_intent_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winston);

        winston_intent_btn = findViewById(R.id.winston_intent_btn);
        camel_intent_btn = findViewById(R.id.camel_intent_btn);
        montecarlo_intent_btn = findViewById(R.id.montecarlo_intent_btn);
        ld_intent_btn = findViewById(R.id.ld_intent_btn);

        winston_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WinstonActivity.this, WinstonStocksActivity.class);
                startActivity(i);
            }
        });

        camel_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WinstonActivity.this, CamelStocksActivity.class);
                startActivity(i);
            }
        });

        montecarlo_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WinstonActivity.this, MonteCarloStocksActivity.class);
                startActivity(i);
            }
        });

        ld_intent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WinstonActivity.this, LDStocksActivity.class);
                startActivity(i);
            }
        });
    }
}