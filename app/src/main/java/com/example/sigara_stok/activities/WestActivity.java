package com.example.sigara_stok.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sigara_stok.R;
import com.example.sigara_stok.tekel_stocks.KentStocksActivity;
import com.example.sigara_stok.tekel_stocks.RothmansStocksActivity;
import com.example.sigara_stok.west_stocks.PoloStocksActivity;
import com.example.sigara_stok.west_stocks.WestStocksActivity;

public class WestActivity extends AppCompatActivity {

    Button west, polo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west_stocks);

        setWestButtons(R.id.west_intent, WestStocksActivity.class);
        setWestButtons(R.id.polo_intent, PoloStocksActivity.class);
    }

    private void setWestButtons(int buttonId, final Class<?> cls) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WestActivity.this, cls));
            }
        });
    }

}