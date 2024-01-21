package com.example.sigara_stok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sigara_stok.R;
import com.example.sigara_stok.stocks.marbloro_stocks.ChesterfieldStocksActivity;
import com.example.sigara_stok.stocks.marbloro_stocks.LMStocksActivity;
import com.example.sigara_stok.stocks.marbloro_stocks.LarkStocksActivity;
import com.example.sigara_stok.stocks.marbloro_stocks.MarlboroStocksActivity;
import com.example.sigara_stok.stocks.marbloro_stocks.MurattiStocksActivity;
import com.example.sigara_stok.stocks.marbloro_stocks.ParliamentStocksActivity;

public class MarlboroActivity extends AppCompatActivity {

    Button marlboro, parliament, muratti, lark, lm, chesterfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marlboro);


        marlboro = findViewById(R.id.marlboro_intent);
        parliament = findViewById(R.id.parliament_intent);
        muratti = findViewById(R.id.muratti_intent);
        lark = findViewById(R.id.lark_intent);
        lm = findViewById(R.id.lm_intent);
        chesterfield = findViewById(R.id.chesterfield_intent);

        marlboro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MarlboroStocksActivity.class);
                startActivity(i);
            }
        });

        parliament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ParliamentStocksActivity.class);
                startActivity(i);
            }
        });

        muratti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MurattiStocksActivity.class);
                startActivity(i);
            }
        });

        lark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LarkStocksActivity.class);
                startActivity(i);
            }
        });

        lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LMStocksActivity.class);
                startActivity(i);
            }
        });

        chesterfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChesterfieldStocksActivity.class);
                startActivity(i);
            }
        });
    }
}