package com.example.sigara_stok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sigara_stok.R;
import com.example.sigara_stok.tekel_stocks.KentStocksActivity;
import com.example.sigara_stok.west_stocks.PoloStocksActivity;
import com.example.sigara_stok.west_stocks.WestStocksActivity;

public class WestActivity extends AppCompatActivity {

    Button west, polo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west_stocks);

        west = findViewById(R.id.west_intent);
        polo = findViewById(R.id.polo_intent);

        west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WestStocksActivity.class);
                startActivity(i);
            }
        });

        polo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PoloStocksActivity.class);
                startActivity(i);
            }
        });
    }

}