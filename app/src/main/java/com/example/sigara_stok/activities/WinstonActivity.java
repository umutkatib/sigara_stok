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

        setWinstonButtons(R.id.winston_intent_btn, WinstonStocksActivity.class);
        setWinstonButtons(R.id.camel_intent_btn, CamelStocksActivity.class);
        setWinstonButtons(R.id.montecarlo_intent_btn, MonteCarloStocksActivity.class);
        setWinstonButtons(R.id.ld_intent_btn, LDStocksActivity.class);
    }

    private void setWinstonButtons(int buttonId, final Class<?> cls) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinstonActivity.this, cls));
            }
        });
    }
}