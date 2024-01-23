package com.example.sigara_stok.winston_stocks;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MonteCarloStocksActivity extends AppCompatActivity {
    private int countMCDarkBlueKisa = 0, countMCDarkBlueUzun = 0, countMCDarkRedKisa = 0;
    private int countMCDarkRedUzun = 0, countMCSlenderDarkBlue = 0;
    private TextView tv_mc_dark_blue_kisa, tv_mc_dark_blue_uzun;
    private TextView tv_mc_dark_red_kisa, tv_mc_dark_red_uzun;
    private TextView tv_mc_slender;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameMCDarkBlueKisa = "JTI_MC_Dark_Blue_Kisa", documentNameMCDarkBlueUzun = "JTI_MC_Dark_Blue_Uzun";
    final static String documentNameMCDarkRedKisa = "JTI_MC_Dark_Red_Kisa", documentNameMCDarkRedUzun = "JTI_MC_Dark_Red_Uzun";
    final static String documentNameMCSlenderDarkBlue = "JTI_MC_Slender_Dark";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monte_carlo_stocks);

        Button reset_all_mc_btn = findViewById(R.id.reset_all_mc_btn);

        Button mc_dark_blue_kisa_azalt = findViewById(R.id.mc_dark_blue_kisa_azalt);
        Button mc_dark_blue_kisa_arttir = findViewById(R.id.mc_dark_blue_kisa_arttir);
        Button mc_dark_blue_uzun_azalt = findViewById(R.id.mc_dark_blue_uzun_azalt);
        Button mc_dark_blue_uzun_arttir = findViewById(R.id.mc_dark_blue_uzun_arttir);
        Button mc_dark_red_kisa_azalt = findViewById(R.id.mc_dark_red_kisa_azalt);
        Button mc_dark_red_kisa_arttir = findViewById(R.id.mc_dark_red_kisa_arttir);
        Button mc_dark_red_uzun_azalt = findViewById(R.id.mc_dark_red_uzun_azalt);
        Button mc_dark_red_uzun_arttir = findViewById(R.id.mc_dark_red_uzun_arttir);
        Button mc_slender_blue_azalt = findViewById(R.id.mc_slender_blue_azalt);
        Button mc_slender_blue_arttir = findViewById(R.id.mc_slender_blue_arttir);

        setMonteCarloButtonClickListeners(mc_dark_blue_kisa_azalt, mc_dark_blue_kisa_arttir, documentNameMCDarkBlueKisa);
        setMonteCarloButtonClickListeners(mc_dark_blue_uzun_azalt, mc_dark_blue_uzun_arttir, documentNameMCDarkBlueUzun);
        setMonteCarloButtonClickListeners(mc_dark_red_kisa_azalt, mc_dark_red_kisa_arttir, documentNameMCDarkRedKisa);
        setMonteCarloButtonClickListeners(mc_dark_red_uzun_azalt, mc_dark_red_uzun_arttir, documentNameMCDarkRedUzun);
        setMonteCarloButtonClickListeners(mc_slender_blue_azalt, mc_slender_blue_arttir, documentNameMCSlenderDarkBlue);

        tv_mc_dark_blue_kisa = findViewById(R.id.tv_mc_dark_blue_kisa);
        tv_mc_dark_blue_uzun = findViewById(R.id.tv_mc_dark_blue_uzun);
        tv_mc_dark_red_kisa = findViewById(R.id.tv_mc_dark_red_kisa);
        tv_mc_dark_red_uzun = findViewById(R.id.tv_mc_dark_red_uzun);
        tv_mc_slender = findViewById(R.id.tv_mc_slender);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_mc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void setMonteCarloButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentName);
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentName);
            }
        });
    }

    private void resetCounts() {
        countMCDarkBlueKisa = 0;
        countMCDarkBlueUzun = 0;
        countMCDarkRedKisa = 0;
        countMCDarkRedUzun = 0;
        countMCSlenderDarkBlue = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameMCDarkBlueKisa, countMCDarkBlueKisa);
        updateTextView(documentNameMCDarkBlueUzun, countMCDarkBlueUzun);
        updateTextView(documentNameMCDarkRedKisa, countMCDarkRedKisa);
        updateTextView(documentNameMCDarkRedUzun, countMCDarkRedUzun);
        updateTextView(documentNameMCSlenderDarkBlue, countMCSlenderDarkBlue);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameMCDarkBlueKisa, 0);
        firestoreCount(documentNameMCDarkBlueUzun, 0);
        firestoreCount(documentNameMCDarkRedKisa, 0);
        firestoreCount(documentNameMCDarkRedUzun, 0);
        firestoreCount(documentNameMCSlenderDarkBlue, 0);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sıfırlama Onayı");
        builder.setMessage("Tüm stok verisini sıfırlamak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı evet derse tüm verileri sıfırla
                resetCounts();
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
        firestoreCount(documentName, getCount(documentName) + 1);
    }

    private void decrementCount(String documentName) {
        // Eğer mevcut değer 0'dan büyükse azalt ve güncelle
        if (getCount(documentName) > 0) {
            firestoreCount(documentName, getCount(documentName) - 1);
        }
    }


    private void firestoreCount(String documentName, int count) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userCollectionName = getUserCollectionName(uid);

            CollectionReference userCollectionRef = db.collection(userCollectionName);
            DocumentReference documentReference = userCollectionRef.document(documentName);

            documentReference.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Document exists, update count
                            updateFirestore(documentReference, count);
                        } else {
                            // Document doesn't exist, create new document
                            createFirestoreDocument(documentReference, count);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Firestore Operation Failed!", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Firestore Operation Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFirestore(DocumentReference documentReference, int count) {
        documentReference.update("stock", count)
                .addOnSuccessListener(aVoid -> {
                    setCount(documentReference.getId(), count);
                    updateTextView(documentReference.getId(), count);
                    Log.d("TAG333", documentReference.getId() + " document successfully updated.");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG444", documentReference.getId() + " document update failed.", e);
                });
    }


    private void createFirestoreDocument(DocumentReference documentReference, int count) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("stock", count);

        documentReference.set(userData)
                .addOnSuccessListener(aVoid -> {
                    setCount(documentReference.getId(), count);
                    updateTextView(documentReference.getId(), count);
                    Log.d("TAG333", documentReference.getId() + " document successfully created.");
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG444", documentReference.getId() + " document creation failed.", e);
                });
    }

    private String getUserCollectionName(String uid) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = currentUser.getEmail();
        String[] parts = userEmail.split("@");
        String userName = parts[0];
        return userName + "_market_" + uid;
    }


    private void readFirestore() {
        // Her iki belgeyi de oku
        readFirestoreForDocument(documentNameMCDarkBlueKisa);
        readFirestoreForDocument(documentNameMCDarkBlueUzun);

        readFirestoreForDocument(documentNameMCDarkRedKisa);
        readFirestoreForDocument(documentNameMCDarkRedUzun);

        readFirestoreForDocument(documentNameMCSlenderDarkBlue);
    }

    private void readFirestoreForDocument(String documentName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        // Koleksiyon adını belirleyin
        String userCollectionName = getUserCollectionName(uid);

        db.collection(userCollectionName).document(documentName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        int stockValue = documentSnapshot.getLong("stock").intValue();
                        // Sayfa açıldığında mevcut değeri göster
                        updateTextView(documentName, stockValue);
                        // Ayrıca, yerel count değerini güncelle
                        setCount(documentName, stockValue);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            tv_mc_dark_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            tv_mc_dark_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkRedKisa)) {
            tv_mc_dark_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkRedUzun)) {
            tv_mc_dark_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCSlenderDarkBlue)) {
            tv_mc_slender.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            return countMCDarkBlueKisa;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            return countMCDarkBlueUzun;
        } else if(documentName.equals(documentNameMCDarkRedKisa)) {
            return countMCDarkRedKisa;
        } else if(documentName.equals(documentNameMCDarkRedUzun)) {
            return countMCDarkRedUzun;
        } else if(documentName.equals(documentNameMCSlenderDarkBlue)) {
            return countMCSlenderDarkBlue;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            countMCDarkBlueKisa = count;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            countMCDarkBlueUzun = count;
        } else if (documentName.equals(documentNameMCDarkRedKisa)) {
            countMCDarkRedKisa = count;
        } else if (documentName.equals(documentNameMCDarkRedUzun)) {
            countMCDarkRedUzun = count;
        } else if (documentName.equals(documentNameMCSlenderDarkBlue)) {
            countMCSlenderDarkBlue = count;
        }
    }
}