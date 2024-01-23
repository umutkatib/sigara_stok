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
import com.example.sigara_stok.tekel_stocks.KentStocksActivity;
import com.example.sigara_stok.tekel_stocks.RothmansStocksActivity;
import com.example.sigara_stok.tekel_stocks.TekelStocksActivity;
import com.example.sigara_stok.tekel_stocks.ViceroyStocksActivity;

public class MarlboroActivity extends AppCompatActivity {

    Button marlboro, parliament, muratti, lark, lm, chesterfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marlboro);

        setMarlboroButtons(R.id.marlboro_intent, MarlboroStocksActivity.class);
        setMarlboroButtons(R.id.parliament_intent, ParliamentStocksActivity.class);
        setMarlboroButtons(R.id.muratti_intent, MurattiStocksActivity.class);
        setMarlboroButtons(R.id.lark_intent, LarkStocksActivity.class);
        setMarlboroButtons(R.id.lm_intent, LMStocksActivity.class);
        setMarlboroButtons(R.id.chesterfield_intent, ChesterfieldStocksActivity.class);
    }
    private void setMarlboroButtons(int buttonId, final Class<?> cls) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarlboroActivity.this, cls));
            }
        });
    }
}