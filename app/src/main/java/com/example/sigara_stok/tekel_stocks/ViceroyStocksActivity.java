package com.example.sigara_stok.tekel_stocks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sigara_stok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViceroyStocksActivity extends AppCompatActivity {


    private int countViceroyNavyKisa = 0, countViceroyNavyUzun = 0;
    private int countViceroyRedKisa = 0, countViceroyRedUzun = 0;
    private TextView tv_navy_kisa, tv_navy_uzun;
    private TextView tv_red_kisa, tv_red_uzun;

    private FirebaseFirestore db;
    FirebaseAuth auth;

    String documentNameRothamnsBlueKisa = "BAT_Viceroy_Kisa_Navy", documentNameRothamnsBlueUzun = "BAT_Viceroy_Uzun_Navy";
    String documentNameRothmansDrangeBlackKisa = "BAT_Viceroy_Kisa_Red", documentNameRothmansDrangeBlackUzun = "BAT_Viceroy_Uzun_Red";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viceroy_stocks);

        Button btn_reset_viceroy_all = findViewById(R.id.btn_reset_viceroy_all);

        Button viceroy_nav_kisa_azalt = findViewById(R.id.viceroy_nav_kisa_azalt);
        Button viceroy_nav_kisa_arttir = findViewById(R.id.viceroy_nav_kisa_arttir);
        Button viceroy_nav_uzun_azalt = findViewById(R.id.viceroy_nav_uzun_azalt);
        Button viceroy_nav_uzun_arttir = findViewById(R.id.viceroy_nav_uzun_arttir);

        Button viceroy_red_kisa_azalt = findViewById(R.id.viceroy_red_kisa_azalt);
        Button viceroy_red_kisa_arttir = findViewById(R.id.viceroy_red_kisa_arttir);
        Button viceroy_red_uzun_azalt = findViewById(R.id.viceroy_red_uzun_azalt);
        Button viceroy_red_uzun_arttir = findViewById(R.id.viceroy_red_uzun_arttir);


        tv_navy_kisa = findViewById(R.id.tv_navy_kisa);
        tv_navy_uzun = findViewById(R.id.tv_navy_uzun);

        tv_red_kisa = findViewById(R.id.tv_red_kisa);
        tv_red_uzun = findViewById(R.id.tv_red_uzun);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        btn_reset_viceroy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        viceroy_nav_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameRothamnsBlueKisa);
            }
        });

        viceroy_nav_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameRothamnsBlueKisa);
            }
        });

        //////////////////////

        viceroy_nav_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameRothamnsBlueUzun);
            }
        });

        viceroy_nav_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameRothamnsBlueUzun);
            }
        });

        //////////////////////

        viceroy_red_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameRothmansDrangeBlackKisa);
            }
        });

        viceroy_red_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameRothmansDrangeBlackKisa);
            }
        });

        //////////////////////


        viceroy_red_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameRothmansDrangeBlackUzun);
            }
        });


        viceroy_red_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameRothmansDrangeBlackUzun);
            }
        });

    }

    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countViceroyNavyKisa = 0;
        countViceroyNavyUzun = 0;
        countViceroyRedKisa = 0;
        countViceroyRedUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameRothamnsBlueKisa, countViceroyNavyKisa);
        updateTextView(documentNameRothamnsBlueUzun, countViceroyNavyUzun);
        updateTextView(documentNameRothmansDrangeBlackKisa, countViceroyRedKisa);
        updateTextView(documentNameRothmansDrangeBlackUzun, countViceroyRedUzun);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameRothamnsBlueKisa, 0);
        updateFirestore(documentNameRothamnsBlueUzun, 0);
        updateFirestore(documentNameRothmansDrangeBlackKisa, 0);
        updateFirestore(documentNameRothmansDrangeBlackUzun, 0);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sıfırlama Onayı");
        builder.setMessage("Tüm stok verisini sıfırlamak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı evet derse tüm verileri sıfırla
                resetAllCounts();
                Toast.makeText(getApplicationContext(), "Tüm stok verisi sıfırlandı.", Toast.LENGTH_SHORT).show();
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

    private void incrementCount(String documentName) {
        // Mevcut değeri arttır ve güncelle
        updateFirestore(documentName, getCount(documentName) + 1);
    }

    private void decrementCount(String documentName) {
        // Eğer mevcut değer 0'dan büyükse azalt ve güncelle
        if (getCount(documentName) > 0) {
            updateFirestore(documentName, getCount(documentName) - 1);
        }
    }


    private void updateFirestore(String documentName, int count) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            FirebaseUser currentUser = auth.getCurrentUser();
            String userEmail = currentUser.getEmail();
            String[] parts = userEmail.split("@");
            String userName = parts[0];

            String userCollectionName = userName + "_market_" + uid;
            CollectionReference userCollectionRef = db.collection(userCollectionName);

            // Belgeyi ekleyin
            Map<String, Object> userData = new HashMap<>();
            userData.put("stock", count);

            userCollectionRef.document(documentName)
                    .set(userData)
                    .addOnSuccessListener(aVoid -> {
                        // Yerel count değerini ve TextView'i güncelle
                        setCount(documentName, count);
                        updateTextView(documentName, count);
                        Log.d("TAG333", documentName + " belgesi başarıyla oluşturuldu ve güncellendi.");
                    })
                    .addOnFailureListener(e -> {
                        Log.w("TAG444", documentName + " belgesi oluşturulurken hata oluştu", e);
                        Toast.makeText(getApplicationContext(), "Belge oluşturma hatası", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Stok Güncelleme Başarısız..", Toast.LENGTH_SHORT).show();
        }
    }




    private void readFirestore() {
        // Her iki belgeyi de oku
        readFirestoreForDocument(documentNameRothamnsBlueKisa);
        readFirestoreForDocument(documentNameRothamnsBlueUzun);

        readFirestoreForDocument(documentNameRothmansDrangeBlackKisa);
        readFirestoreForDocument(documentNameRothmansDrangeBlackUzun);
    }

    private void readFirestoreForDocument(String documentName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userEmail = currentUser.getEmail();
        String[] parts = userEmail.split("@");
        String userName = parts[0];


        // Koleksiyon adını belirleyin
        String userCollectionName = userName + "_market_" + uid;

        db.collection(userCollectionName).document(documentName)
                .addSnapshotListener(this, (documentSnapshot, error) -> {
                    if (documentSnapshot != null && documentSnapshot.exists() && !documentName.isEmpty()) {
                        int stockValue = documentSnapshot.getLong("stock").intValue();
                        // Sayfa açıldığında mevcut değeri göster
                        updateTextView(documentName, stockValue);
                        // Ayrıca, yerel count değerini güncelle
                        setCount(documentName, stockValue);
                    }
                });
    }


    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            tv_navy_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            tv_navy_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            tv_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            tv_red_uzun.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            return countViceroyNavyKisa;
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            return countViceroyNavyUzun;
        } else if(documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            return countViceroyRedKisa;
        } else if(documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            return countViceroyRedUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            countViceroyNavyKisa = count;
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            countViceroyNavyUzun = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            countViceroyRedKisa = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            countViceroyRedUzun = count;
        }
    }
}