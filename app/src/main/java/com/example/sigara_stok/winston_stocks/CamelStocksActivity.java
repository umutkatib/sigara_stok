package com.example.sigara_stok.winston_stocks;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CamelStocksActivity extends AppCompatActivity {
    private int countCamelYellowKisa, countCamelYellowUzun, countCamelYellowSoftKisa;
    private int  countCamelWhite, countCamelBrown, countCamelBlack, countCamelDeepBlue;
    private int countCamelSlenderBlue, countCamelSlenderGrey;
    private TextView tv_yellow_kisa, tv_yellow_uzun;
    private TextView tv_yellow_soft_kisa, tv_white;
    private TextView tv_black, tv_deep_blue;
    private TextView tv_brown, tv_slender_blue, tv_slender_grey;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameCamelYellowKisa = "JTI_Camel_Yellow_Kisa", documentNameCamelYellowUzun = "JTI_Camel_Yellow_Uzun";
    final static String documentNameCamelYellowSoftKisa = "JTI_Camel_Yellow_Soft_Kisa", documentNameCamelWhite = "JTI_Camel_White";
    final static String documentNameCamelBlack = "JTI_Camel_Black", documentNameCamelBrown = "JTI_Camel_Brown";
    final static String documentNameCamelDeepBlue = "JTI_Camel_Deep_Blue", documentNameCamelSlenderBlue = "JTI_Camel_Slender_Blue", documentNameCamelSlenderGrey = "JTI_Camel_Slender_Grey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camel_stocks);

        Button reset_all_camel_all_btn = findViewById(R.id.reset_all_camel_all_btn);

        Button camel_yellow_kisa_azalt_btn = findViewById(R.id.camel_yellow_kisa_azalt_btn);
        Button camel_yellow_kisa_arttir_btn = findViewById(R.id.camel_yellow_kisa_arttir_btn);
        Button camel_yellow_uzun_azalt_btn = findViewById(R.id.camel_yellow_uzun_azalt_btn);
        Button camel_yellow_uzun_arttir_btn = findViewById(R.id.camel_yellow_uzun_arttir_btn);
        Button camel_yellow_soft_azalt_btn = findViewById(R.id.camel_yellow_soft_azalt_btn);
        Button camel_yellow_soft_arttir_btn = findViewById(R.id.camel_yellow_soft_arttir_btn);
        Button camel_white_azalt_btn = findViewById(R.id.camel_white_azalt_btn);
        Button camel_white_arttir_btn = findViewById(R.id.camel_white_arttir_btn);
        Button camel_black_azalt_btn = findViewById(R.id.camel_black_azalt_btn);
        Button camel_black_arttir_btn = findViewById(R.id.camel_black_arttir_btn);
        Button camel_deep_blue_azalt_btn = findViewById(R.id.camel_deep_blue_azalt_btn);
        Button camel_deep_blue_arttir_btn = findViewById(R.id.camel_deep_blue_arttir_btn);
        Button camel_brown_azalt_btn = findViewById(R.id.camel_brown_azalt_btn);
        Button camel_brown_arttir_btn = findViewById(R.id.camel_brown_arttir_btn);
        Button camel_slender_blue_azalt_btn = findViewById(R.id.camel_slender_blue_azalt_btn);
        Button camel_slender_blue_arttir_btn = findViewById(R.id.camel_slender_blue_arttir_btn);
        Button camel_slender_grey_azalt_btn = findViewById(R.id.camel_slender_grey_azalt_btn);
        Button camel_slender_grey_arttir_btn = findViewById(R.id.camel_slender_grey_arttir_btn);

        // Set click listeners for Camel buttons
        setCamelButtonClickListeners(camel_yellow_kisa_azalt_btn, camel_yellow_kisa_arttir_btn, documentNameCamelYellowKisa);
        setCamelButtonClickListeners(camel_yellow_uzun_azalt_btn, camel_yellow_uzun_arttir_btn, documentNameCamelYellowUzun);
        setCamelButtonClickListeners(camel_yellow_soft_azalt_btn, camel_yellow_soft_arttir_btn, documentNameCamelYellowSoftKisa);
        setCamelButtonClickListeners(camel_white_azalt_btn, camel_white_arttir_btn, documentNameCamelWhite);
        setCamelButtonClickListeners(camel_black_azalt_btn, camel_black_arttir_btn, documentNameCamelBlack);
        setCamelButtonClickListeners(camel_deep_blue_azalt_btn, camel_deep_blue_arttir_btn, documentNameCamelDeepBlue);
        setCamelButtonClickListeners(camel_brown_azalt_btn, camel_brown_arttir_btn, documentNameCamelBrown);
        setCamelButtonClickListeners(camel_slender_blue_azalt_btn, camel_slender_blue_arttir_btn, documentNameCamelSlenderBlue);
        setCamelButtonClickListeners(camel_slender_grey_azalt_btn, camel_slender_grey_arttir_btn, documentNameCamelSlenderGrey);


        tv_yellow_kisa = findViewById(R.id.tv_yellow_kisa);
        tv_yellow_uzun = findViewById(R.id.tv_yellow_uzun);
        tv_yellow_soft_kisa = findViewById(R.id.tv_yellow_soft_kisa);

        tv_white = findViewById(R.id.tv_white);
        tv_black = findViewById(R.id.tv_black);
        tv_deep_blue = findViewById(R.id.tv_deep_blue);
        tv_brown = findViewById(R.id.tv_brown);
        tv_slender_blue = findViewById(R.id.tv_slender_blue);
        tv_slender_grey = findViewById(R.id.tv_slender_grey);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_camel_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void setCamelButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countCamelYellowKisa = 0;
        countCamelYellowUzun = 0;
        countCamelYellowSoftKisa = 0;
        countCamelWhite = 0;
        countCamelBrown = 0;
        countCamelBlack = 0;
        countCamelDeepBlue = 0;
        countCamelSlenderBlue = 0;
        countCamelSlenderGrey = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameCamelYellowKisa, countCamelYellowKisa);
        updateTextView(documentNameCamelYellowUzun, countCamelYellowUzun);
        updateTextView(documentNameCamelYellowSoftKisa, countCamelYellowSoftKisa);
        updateTextView(documentNameCamelWhite, countCamelWhite);
        updateTextView(documentNameCamelBlack, countCamelBrown);
        updateTextView(documentNameCamelBrown, countCamelBlack);
        updateTextView(documentNameCamelDeepBlue, countCamelDeepBlue);
        updateTextView(documentNameCamelSlenderBlue, countCamelSlenderBlue);
        updateTextView(documentNameCamelSlenderGrey, countCamelSlenderGrey);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameCamelYellowKisa, 0);
        firestoreCount(documentNameCamelYellowUzun, 0);
        firestoreCount(documentNameCamelYellowSoftKisa, 0);
        firestoreCount(documentNameCamelWhite, 0);
        firestoreCount(documentNameCamelBlack, 0);
        firestoreCount(documentNameCamelBrown, 0);
        firestoreCount(documentNameCamelDeepBlue, 0);
        firestoreCount(documentNameCamelSlenderBlue, 0);
        firestoreCount(documentNameCamelSlenderGrey, 0);
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
        readFirestoreForDocument(documentNameCamelYellowKisa);
        readFirestoreForDocument(documentNameCamelYellowUzun);
        readFirestoreForDocument(documentNameCamelYellowSoftKisa);
        readFirestoreForDocument(documentNameCamelWhite);
        readFirestoreForDocument(documentNameCamelBlack);
        readFirestoreForDocument(documentNameCamelBrown);
        readFirestoreForDocument(documentNameCamelDeepBlue);
        readFirestoreForDocument(documentNameCamelSlenderBlue);
        readFirestoreForDocument(documentNameCamelSlenderGrey);
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
                        Long stockLong = documentSnapshot.getLong("stock");
                        int stockValue = (stockLong != null) ? stockLong.intValue() : 0;

                        // Sayfa açıldığında mevcut değerlerini göster
                        updateTextView(documentName, stockValue);

                        // Ayrıca, yerel count değerlerini güncelle
                        setCount(documentName, stockValue);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_SHORT).show();
                });
    }


    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameCamelYellowKisa)) {
            tv_yellow_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowUzun)) {
            tv_yellow_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowSoftKisa)) {
            tv_yellow_soft_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelWhite)) {
            tv_white.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBlack)) {
            tv_black.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBrown)) {
            tv_deep_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            tv_brown.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            tv_slender_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            tv_slender_grey.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameCamelYellowKisa)) {
            return countCamelYellowKisa;
        } else if (documentName.equals(documentNameCamelYellowUzun)) {
            return countCamelYellowUzun;
        } else if(documentName.equals(documentNameCamelYellowSoftKisa)) {
            return countCamelYellowSoftKisa;
        } else if(documentName.equals(documentNameCamelWhite)) {
            return countCamelWhite;
        } else if(documentName.equals(documentNameCamelBlack)) {
            return countCamelBrown;
        } else if(documentName.equals(documentNameCamelBrown)) {
            return countCamelBlack;
        } else if(documentName.equals(documentNameCamelDeepBlue)) {
            return countCamelDeepBlue;
        } else if(documentName.equals(documentNameCamelSlenderBlue)) {
            return countCamelSlenderBlue;
        } else if(documentName.equals(documentNameCamelSlenderGrey)) {
            return countCamelSlenderGrey;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameCamelYellowKisa)) {
            countCamelYellowKisa = count;
        } else if (documentName.equals(documentNameCamelYellowUzun)) {
            countCamelYellowUzun = count;
        } else if (documentName.equals(documentNameCamelYellowSoftKisa)) {
            countCamelYellowSoftKisa = count;
        } else if (documentName.equals(documentNameCamelWhite)) {
            countCamelWhite = count;
        } else if (documentName.equals(documentNameCamelBlack)) {
            countCamelBrown = count;
        } else if (documentName.equals(documentNameCamelBrown)) {
            countCamelBlack = count;
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            countCamelDeepBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            countCamelSlenderBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            countCamelSlenderGrey = count;
        }
    }
}