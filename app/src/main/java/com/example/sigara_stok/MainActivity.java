package com.example.sigara_stok;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sigara_stok.R;
import com.example.sigara_stok.activities.HdActivity;
import com.example.sigara_stok.activities.LoginActivity;
import com.example.sigara_stok.activities.MarlboroActivity;
import com.example.sigara_stok.activities.TekelActivity;
import com.example.sigara_stok.activities.WestActivity;
import com.example.sigara_stok.activities.WinstonActivity;
import com.example.sigara_stok.tekel_stocks.KentStocksActivity;
import com.example.sigara_stok.tekel_stocks.RothmansStocksActivity;
import com.example.sigara_stok.tekel_stocks.TekelStocksActivity;
import com.example.sigara_stok.tekel_stocks.ViceroyStocksActivity;
import com.example.sigara_stok.west_stocks.WestStocksActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button cikisYap;
    TextView userName;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        cikisYap = findViewById(R.id.btn_cikis_yap);
        userName = findViewById(R.id.userName);

        setMainButtons(R.id.btn_marlbora, MarlboroActivity.class);
        setMainButtons(R.id.btn_tekel, TekelActivity.class);
        setMainButtons(R.id.btn_winston, WinstonActivity.class);
        setMainButtons(R.id.btn_hd, HdActivity.class);
        setMainButtons(R.id.btn_west, WestActivity.class);

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        // Kullanıcı giriş yapmışsa e-posta adresini yazdır
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            String[] parts = userEmail.split("@");
            String userName2 = parts[0];
            userName2.toUpperCase();
            userName.setText(userName2);
        }
    }

    private void setMainButtons(int buttonId, final Class<?> cls) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, cls));
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ÇIKIŞ ONAYI");
        builder.setMessage("Çıkmak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
