package com.example.sigara_stok.winston_stocks;

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

public class LDStocksActivity extends AppCompatActivity {
    private int countLdMaviKisa = 0, countLdMaviUzun = 0, countLdRedKisa = 0;
    private int countLdRedUzun = 0, countLdSlim = 0;
    private EditText et_blue_kisa, et_blue_uzun, et_red_kisa, et_red_uzun, et_ld_slim;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameLdBlueKisa = "JTI_Ld_Blue_Kisa", documentNameLdBlueUzun = "JTI_Ld_Blue_Uzun";
    final static String documentNameLdRedKisa = "JTI_Ld_Red_Kisa", documentNameLdRedUzun = "JTI_Ld_Red_Uzun";
    final static String documentNameLdSlim = "JTI_Ld_Slim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldstocks);

        Button reset_all_ld_all_btn = findViewById(R.id.reset_all_ld_all_btn);

        Button ld_blue_kisa_azalt = findViewById(R.id.ld_blue_kisa_azalt);
        Button ld_blue_kisa_arttir = findViewById(R.id.ld_blue_kisa_arttir);
        Button ld_blue_uzun_azalt = findViewById(R.id.ld_blue_uzun_azalt);
        Button ld_blue_uzun_arttir = findViewById(R.id.ld_blue_uzun_arttir);
        Button ld_red_kisa_azalt = findViewById(R.id.ld_red_kisa_azalt);
        Button ld_red_kisa_arttir = findViewById(R.id.ld_red_kisa_arttir);
        Button ld_red_uzun_azalt = findViewById(R.id.ld_red_uzun_azalt);
        Button ld_red_uzun_arttir = findViewById(R.id.ld_red_uzun_arttir);
        Button ld_slim_azalt = findViewById(R.id.ld_slim_azalt);
        Button ld_slim_arttir = findViewById(R.id.ld_slim_arttir);

        Button updateValuesButton = findViewById(R.id.ldGuncelle);


        setLdButtonClickListeners(ld_blue_kisa_azalt, ld_blue_kisa_arttir, documentNameLdBlueKisa);
        setLdButtonClickListeners(ld_blue_uzun_azalt, ld_blue_uzun_arttir, documentNameLdBlueUzun);
        setLdButtonClickListeners(ld_red_kisa_azalt, ld_red_kisa_arttir, documentNameLdRedKisa);
        setLdButtonClickListeners(ld_red_uzun_azalt, ld_red_uzun_arttir, documentNameLdRedUzun);
        setLdButtonClickListeners(ld_slim_azalt, ld_slim_arttir, documentNameLdSlim);

        et_blue_kisa = findViewById(R.id.et_blue_kisa);
        et_blue_uzun = findViewById(R.id.et_blue_uzun);
        et_red_kisa = findViewById(R.id.et_red_kisa);
        et_red_uzun = findViewById(R.id.et_red_uzun);
        et_ld_slim = findViewById(R.id.et_ld_slim);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();


        updateValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockDialog();
            }
        });
        reset_all_ld_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countLdMaviKisa = Integer.parseInt(et_blue_kisa.getText().toString());
        countLdMaviUzun = Integer.parseInt(et_blue_uzun.getText().toString());
        countLdRedKisa = Integer.parseInt(et_red_kisa.getText().toString());
        countLdRedUzun = Integer.parseInt(et_red_uzun.getText().toString());
        countLdSlim = Integer.parseInt(et_ld_slim.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameLdBlueKisa, countLdMaviKisa);
        updateTextView(documentNameLdBlueUzun, countLdMaviUzun);
        updateTextView(documentNameLdRedKisa, countLdRedKisa);
        updateTextView(documentNameLdRedUzun, countLdRedUzun);
        updateTextView(documentNameLdSlim, countLdSlim);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameLdBlueKisa, countLdMaviKisa);
        firestoreCount(documentNameLdBlueUzun, countLdMaviUzun);
        firestoreCount(documentNameLdRedKisa, countLdRedKisa);
        firestoreCount(documentNameLdRedUzun, countLdRedUzun);
        firestoreCount(documentNameLdSlim, countLdSlim);
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

    private void discardStockCount() {
        et_blue_kisa.setText(String.valueOf(countLdMaviKisa));
        et_blue_uzun.setText(String.valueOf(countLdMaviUzun));
        et_red_kisa.setText(String.valueOf(countLdRedKisa));
        et_red_uzun.setText(String.valueOf(countLdRedUzun));
        et_ld_slim.setText(String.valueOf(countLdSlim));
    }

    private void setLdButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countLdMaviKisa = 0;
        countLdMaviUzun = 0;
        countLdRedKisa = 0;
        countLdRedUzun = 0;
        countLdSlim = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameLdBlueKisa, countLdMaviKisa);
        updateTextView(documentNameLdBlueUzun, countLdMaviUzun);
        updateTextView(documentNameLdRedKisa, countLdRedKisa);
        updateTextView(documentNameLdRedUzun, countLdRedUzun);
        updateTextView(documentNameLdSlim, countLdSlim);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameLdBlueKisa, 0);
        firestoreCount(documentNameLdBlueUzun, 0);
        firestoreCount(documentNameLdRedKisa, 0);
        firestoreCount(documentNameLdRedUzun, 0);
        firestoreCount(documentNameLdSlim, 0);
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
        readFirestoreForDocument(documentNameLdBlueKisa);
        readFirestoreForDocument(documentNameLdBlueUzun);

        readFirestoreForDocument(documentNameLdRedKisa);
        readFirestoreForDocument(documentNameLdRedUzun);

        readFirestoreForDocument(documentNameLdSlim);
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
        if (documentName.equals(documentNameLdBlueKisa)) {
            et_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            et_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdRedKisa)) {
            et_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdRedUzun)) {
            et_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdSlim)) {
            et_ld_slim.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameLdBlueKisa)) {
            return countLdMaviKisa;
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            return countLdMaviUzun;
        } else if(documentName.equals(documentNameLdRedKisa)) {
            return countLdRedKisa;
        } else if(documentName.equals(documentNameLdRedUzun)) {
            return countLdRedUzun;
        } else if(documentName.equals(documentNameLdSlim)) {
            return countLdSlim;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameLdBlueKisa)) {
            countLdMaviKisa = count;
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            countLdMaviUzun = count;
        } else if (documentName.equals(documentNameLdRedKisa)) {
            countLdRedKisa = count;
        } else if (documentName.equals(documentNameLdRedUzun)) {
            countLdRedUzun = count;
        } else if (documentName.equals(documentNameLdSlim)) {
            countLdSlim = count;
        }
    }
}