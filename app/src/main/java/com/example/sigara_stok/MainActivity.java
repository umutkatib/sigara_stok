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
import com.example.sigara_stok.activities.LoginActivity;
import com.example.sigara_stok.activities.MarlboroActivity;
import com.example.sigara_stok.activities.TekelActivity;
import com.example.sigara_stok.activities.WinstonActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button marlboro, tekel, winston, hd, west, cikisYap;
    TextView userName;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        marlboro = findViewById(R.id.btn_marlbora);
        tekel = findViewById(R.id.btn_tekel);
        winston = findViewById(R.id.btn_winston);
        cikisYap = findViewById(R.id.btn_cikis_yap);
        userName = findViewById(R.id.userName);
        hd = findViewById(R.id.btn_hd);
        west = findViewById(R.id.btn_west);

        marlboro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MarlboroActivity.class);
                startActivity(i);
            }
        });

        tekel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TekelActivity.class);
                startActivity(i);
            }
        });

        winston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WinstonActivity.class);
                startActivity(i);
            }
        });

        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WinstonActivity.class);
                startActivity(i);
            }
        });

        west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WinstonActivity.class);
                startActivity(i);
            }
        });

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
            userName.setText(userName2);
        }
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Çıkış Onayı");
        builder.setMessage("Çıkmak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı hayır derse hiçbir şey yapma
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
