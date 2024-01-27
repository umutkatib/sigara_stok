package com.example.sigara_stok.tekel_stocks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ViceroyStocksActivity extends AppCompatActivity {
    private int countViceroyNavyKisa = 0, countViceroyNavyUzun = 0, countViceroyRedKisa = 0, countViceroyRedUzun = 0;
    private EditText et_navy_kisa, et_navy_uzun, et_nav_red_kisa, et_nav_red_uzun;
    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameViceroyNavyKisa = "BAT_Viceroy_Kisa_Navy", documentNameViceroyNavyUzun = "BAT_Viceroy_Uzun_Navy", documentNameViceroyRedKisa = "BAT_Viceroy_Kisa_Red", documentNameViceroyRedUzun = "BAT_Viceroy_Uzun_Red";

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

        Button updateValuesButton = findViewById(R.id.viceroyGuncelle);

        setViceroyButtonClickListeners(viceroy_nav_kisa_azalt, viceroy_nav_kisa_arttir, documentNameViceroyNavyKisa);
        setViceroyButtonClickListeners(viceroy_nav_uzun_azalt, viceroy_nav_uzun_arttir, documentNameViceroyNavyUzun);
        setViceroyButtonClickListeners(viceroy_red_kisa_azalt, viceroy_red_kisa_arttir, documentNameViceroyRedKisa);
        setViceroyButtonClickListeners(viceroy_red_uzun_azalt, viceroy_red_uzun_arttir, documentNameViceroyRedUzun);


        et_navy_kisa = findViewById(R.id.et_navy_kisa);
        et_navy_uzun = findViewById(R.id.et_navy_uzun);

        et_nav_red_kisa = findViewById(R.id.et_nav_red_kisa);
        et_nav_red_uzun = findViewById(R.id.et_nav_red_uzun);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        updateValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockDialog();
            }
        });

        btn_reset_viceroy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }


    private void discardStockCount() {
        et_navy_kisa.setText(String.valueOf(countViceroyNavyKisa));
        et_navy_uzun.setText(String.valueOf(countViceroyNavyUzun));

        et_nav_red_kisa.setText(String.valueOf(countViceroyRedKisa));
        et_nav_red_uzun.setText(String.valueOf(countViceroyRedUzun));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countViceroyNavyKisa = Integer.parseInt(et_navy_kisa.getText().toString());
        countViceroyNavyUzun = Integer.parseInt(et_navy_uzun.getText().toString());

        countViceroyRedKisa = Integer.parseInt(et_nav_red_kisa.getText().toString());
        countViceroyRedUzun = Integer.parseInt(et_nav_red_uzun.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameViceroyNavyKisa, countViceroyNavyKisa);
        updateTextView(documentNameViceroyNavyUzun, countViceroyNavyUzun);
        updateTextView(documentNameViceroyRedKisa, countViceroyRedKisa);
        updateTextView(documentNameViceroyRedUzun, countViceroyRedUzun);


        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameViceroyNavyKisa, countViceroyNavyKisa);
        firestoreCount(documentNameViceroyNavyUzun, countViceroyNavyUzun);
        firestoreCount(documentNameViceroyRedKisa, countViceroyRedKisa);
        firestoreCount(documentNameViceroyRedUzun, countViceroyRedUzun);
    }

    private void updateStockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stok Güncelle");
        builder.setMessage("Stok verisini güncellemek istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateEditTextValues();
                Toast.makeText(getApplicationContext(), "Stok Başarıyla Güncellendi", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                discardStockCount();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void setViceroyButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countViceroyNavyKisa = 0;
        countViceroyNavyUzun = 0;
        countViceroyRedKisa = 0;
        countViceroyRedUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameViceroyNavyKisa, countViceroyNavyKisa);
        updateTextView(documentNameViceroyNavyUzun, countViceroyNavyUzun);
        updateTextView(documentNameViceroyRedKisa, countViceroyRedKisa);
        updateTextView(documentNameViceroyRedUzun, countViceroyRedUzun);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameViceroyNavyKisa, 0);
        firestoreCount(documentNameViceroyNavyUzun, 0);
        firestoreCount(documentNameViceroyRedKisa, 0);
        firestoreCount(documentNameViceroyRedUzun, 0);
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
        readFirestoreForDocument(documentNameViceroyNavyKisa);
        readFirestoreForDocument(documentNameViceroyNavyUzun);

        readFirestoreForDocument(documentNameViceroyRedKisa);
        readFirestoreForDocument(documentNameViceroyRedUzun);
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
        if (documentName.equals(documentNameViceroyNavyKisa)) {
            et_navy_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameViceroyNavyUzun)) {
            et_navy_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameViceroyRedKisa)) {
            et_nav_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameViceroyRedUzun)) {
            et_nav_red_uzun.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameViceroyNavyKisa)) {
            return countViceroyNavyKisa;
        } else if (documentName.equals(documentNameViceroyNavyUzun)) {
            return countViceroyNavyUzun;
        } else if(documentName.equals(documentNameViceroyRedKisa)) {
            return countViceroyRedKisa;
        } else if(documentName.equals(documentNameViceroyRedUzun)) {
            return countViceroyRedUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameViceroyNavyKisa)) {
            countViceroyNavyKisa = count;
        } else if (documentName.equals(documentNameViceroyNavyUzun)) {
            countViceroyNavyUzun = count;
        } else if (documentName.equals(documentNameViceroyRedKisa)) {
            countViceroyRedKisa = count;
        } else if (documentName.equals(documentNameViceroyRedUzun)) {
            countViceroyRedUzun = count;
        }
    }
}