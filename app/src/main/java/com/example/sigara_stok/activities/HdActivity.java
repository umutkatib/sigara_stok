package com.example.sigara_stok.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sigara_stok.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class HdActivity extends AppCompatActivity {

    private int countHDBlueKisa = 0, countHDBlueUzun = 0, countHDSlimBlue = 0, countHDWhiteLine = 0, countTorosBlueKisa = 0, countTorosBlueUzun = 0, countTorosRedKisa = 0, countTorosRedUzun = 0;
    private EditText et_hd_kisa, et_hd_uzun, et_hd_slim, et_hd_white_line, et_toros_blue_kisa, et_toros_blue_uzun, et_toros_red_kisa, et_toros_red_uzun;
    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameHDBlueKisa = "HD_Blue_Kisa", documentNameHDBlueUzun = "HD_Blue_Uzun", documentNameHDSlimBlue = "HD_Slim_Blue", documentNameHDWhiteLine = "HD_White_Line", documentNameTorosBlueKisa = "HD_Toros_Blue_Kisa", documentNameTorosBlueUzun = "HD_Toros_Blue_Uzun", documentNameTorosRedKisa = "HD_Toros_Red_Kisa", documentNameTorosRedUzun = "HD_Toros_Red_Uzun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd);

        Button reset_all_hd = findViewById(R.id.reset_all_hd);

        Button hd_kisa_azalt = findViewById(R.id.hd_kisa_azalt);
        Button hd_kisa_arttir = findViewById(R.id.hd_kisa_arttir);
        Button hd_uzun_azalt = findViewById(R.id.hd_uzun_azalt);
        Button hd_uzun_arttir = findViewById(R.id.hd_uzun_arttir);
        Button slim_blue_azalt = findViewById(R.id.slim_blue_azalt);
        Button slim_blue_arttir = findViewById(R.id.slim_blue_arttir);

        Button white_line_azalt = findViewById(R.id.white_line_azalt);
        Button white_line_arttir = findViewById(R.id.white_line_arttir);

        Button toros_blue_kisa_azalt = findViewById(R.id.toros_blue_kisa_azalt);
        Button toros_blue_kisa_arttir = findViewById(R.id.toros_blue_kisa_arttir);
        Button toros_blue_uzun_azalt = findViewById(R.id.toros_blue_uzun_azalt);
        Button toros_blue_uzun_arttir = findViewById(R.id.toros_blue_uzun_arttir);
        Button toros_red_kisa_azalt = findViewById(R.id.toros_red_kisa_azalt);
        Button toros_red_kisa_arttir = findViewById(R.id.toros_red_kisa_arttir);
        Button toros_red_uzun_azalt = findViewById(R.id.toros_red_uzun_azalt);
        Button toros_red_uzun_arttir = findViewById(R.id.toros_red_uzun_arttir);

        Button updateValuesButton = findViewById(R.id.hdGuncelle);


        setHdButtonClickListeners(hd_kisa_azalt, hd_kisa_arttir, documentNameHDBlueKisa);
        setHdButtonClickListeners(hd_uzun_azalt, hd_uzun_arttir, documentNameHDBlueUzun);
        setHdButtonClickListeners(slim_blue_azalt, slim_blue_arttir, documentNameHDSlimBlue);
        setHdButtonClickListeners(white_line_azalt, white_line_arttir, documentNameHDWhiteLine);
        setHdButtonClickListeners(toros_blue_kisa_azalt, toros_blue_kisa_arttir, documentNameTorosBlueKisa);
        setHdButtonClickListeners(toros_blue_uzun_azalt, toros_blue_uzun_arttir, documentNameTorosBlueUzun);
        setHdButtonClickListeners(toros_red_kisa_azalt, toros_red_kisa_arttir, documentNameTorosRedKisa);
        setHdButtonClickListeners(toros_red_uzun_azalt, toros_red_uzun_arttir, documentNameTorosRedUzun);


        et_hd_kisa = findViewById(R.id.et_hd_kisa);
        et_hd_uzun = findViewById(R.id.et_hd_uzun);
        et_hd_slim = findViewById(R.id.et_hd_slim);
        et_hd_white_line = findViewById(R.id.et_hd_white_line);

        et_toros_blue_kisa = findViewById(R.id.et_toros_blue_kisa);
        et_toros_blue_uzun = findViewById(R.id.et_toros_blue_uzun);
        et_toros_red_kisa = findViewById(R.id.et_toros_red_kisa);
        et_toros_red_uzun = findViewById(R.id.et_toros_red_uzun);

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

        reset_all_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void setHdButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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

    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countHDBlueKisa = Integer.parseInt(et_hd_kisa.getText().toString());
        countHDBlueUzun = Integer.parseInt(et_hd_uzun.getText().toString());
        countHDSlimBlue = Integer.parseInt(et_hd_slim.getText().toString());
        countHDWhiteLine = Integer.parseInt(et_hd_white_line.getText().toString());
        countTorosBlueKisa = Integer.parseInt(et_toros_blue_kisa.getText().toString());
        countTorosBlueUzun = Integer.parseInt(et_toros_blue_uzun.getText().toString());
        countTorosRedKisa = Integer.parseInt(et_toros_red_kisa.getText().toString());
        countTorosRedUzun = Integer.parseInt(et_toros_red_uzun.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameHDBlueKisa, countHDBlueKisa);
        updateTextView(documentNameHDBlueUzun, countHDBlueUzun);
        updateTextView(documentNameHDSlimBlue, countHDSlimBlue);
        updateTextView(documentNameHDWhiteLine, countHDWhiteLine);
        updateTextView(documentNameTorosBlueKisa, countTorosBlueKisa);
        updateTextView(documentNameTorosBlueUzun, countTorosBlueUzun);
        updateTextView(documentNameTorosRedKisa, countTorosRedKisa);
        updateTextView(documentNameTorosRedUzun, countTorosRedUzun);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameHDBlueKisa, countHDBlueKisa);
        firestoreCount(documentNameHDBlueUzun, countHDBlueUzun);
        firestoreCount(documentNameHDSlimBlue, countHDSlimBlue);
        firestoreCount(documentNameHDWhiteLine, countHDWhiteLine);
        firestoreCount(documentNameTorosBlueKisa, countTorosBlueKisa);
        firestoreCount(documentNameTorosBlueUzun, countTorosBlueUzun);
        firestoreCount(documentNameTorosRedKisa, countTorosRedKisa);
        firestoreCount(documentNameTorosRedUzun, countTorosRedUzun);
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
        et_hd_kisa.setText(String.valueOf(countHDBlueKisa));
        et_hd_uzun.setText(String.valueOf(countHDBlueUzun));
        et_hd_slim.setText(String.valueOf(countHDSlimBlue));
        et_hd_white_line.setText(String.valueOf(countHDWhiteLine));
        et_toros_blue_kisa.setText(String.valueOf(countTorosBlueKisa));
        et_toros_blue_uzun.setText(String.valueOf(countTorosBlueUzun));
        et_toros_red_kisa.setText(String.valueOf(countTorosRedKisa));
        et_toros_red_uzun.setText(String.valueOf(countTorosRedUzun));
    }

    private String getUserCollectionName(String uid) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = currentUser.getEmail();
        String[] parts = userEmail.split("@");
        String userName = parts[0];
        return userName + "_market_" + uid;
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


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countHDBlueKisa = 0;
        countHDBlueUzun = 0;
        countHDSlimBlue = 0;
        countHDWhiteLine = 0;
        countTorosBlueKisa = 0;
        countTorosBlueUzun = 0;
        countTorosRedKisa = 0;
        countTorosRedUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameHDBlueKisa, countHDBlueKisa);
        updateTextView(documentNameHDBlueUzun, countHDBlueUzun);
        updateTextView(documentNameHDSlimBlue, countHDSlimBlue);
        updateTextView(documentNameHDWhiteLine, countHDWhiteLine);

        updateTextView(documentNameTorosBlueKisa, countTorosBlueKisa);
        updateTextView(documentNameTorosBlueUzun, countTorosBlueUzun);
        updateTextView(documentNameTorosRedKisa, countTorosRedKisa);
        updateTextView(documentNameTorosRedUzun, countTorosRedUzun);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameHDBlueKisa, 0);
        firestoreCount(documentNameHDBlueUzun, 0);
        firestoreCount(documentNameHDSlimBlue, 0);
        firestoreCount(documentNameHDWhiteLine, 0);

        firestoreCount(documentNameTorosBlueKisa, 0);
        firestoreCount(documentNameTorosBlueUzun, 0);
        firestoreCount(documentNameTorosRedKisa, 0);
        firestoreCount(documentNameTorosRedUzun, 0);
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


    private void readFirestore() {
        // Her iki belgeyi de oku
        readFirestoreForDocument(documentNameHDBlueKisa);
        readFirestoreForDocument(documentNameHDBlueUzun);

        readFirestoreForDocument(documentNameHDSlimBlue);
        readFirestoreForDocument(documentNameHDWhiteLine);

        readFirestoreForDocument(documentNameTorosBlueKisa);
        readFirestoreForDocument(documentNameTorosBlueUzun);

        readFirestoreForDocument(documentNameTorosRedKisa);
        readFirestoreForDocument(documentNameTorosRedUzun);

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
        if (documentName.equals(documentNameHDBlueKisa)) {
            et_hd_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameHDBlueUzun)) {
            et_hd_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameHDSlimBlue)) {
            et_hd_slim.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameHDWhiteLine)) {
            et_hd_white_line.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            et_toros_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosBlueUzun)) {
            et_toros_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosRedKisa)) {
            et_toros_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosRedUzun)) {
            et_toros_red_uzun.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameHDBlueKisa)) {
            return countHDBlueKisa;
        } else if (documentName.equals(documentNameHDBlueUzun)) {
            return countHDBlueUzun;
        } else if(documentName.equals(documentNameHDSlimBlue)) {
            return countHDSlimBlue;
        } else if(documentName.equals(documentNameHDWhiteLine)) {
            return countHDWhiteLine;
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            return countTorosBlueKisa;
        } else if(documentName.equals(documentNameTorosBlueUzun)) {
            return countTorosBlueUzun;
        } else if(documentName.equals(documentNameTorosRedKisa)) {
            return countTorosRedKisa;
        } else if(documentName.equals(documentNameTorosRedUzun)) {
            return countTorosRedUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameHDBlueKisa)) {
            countHDBlueKisa = count;
        } else if (documentName.equals(documentNameHDBlueUzun)) {
            countHDBlueUzun = count;
        } else if (documentName.equals(documentNameHDSlimBlue)) {
            countHDSlimBlue = count;
        } else if (documentName.equals(documentNameHDWhiteLine)) {
            countHDWhiteLine = count;
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            countTorosBlueKisa = count;
        } else if (documentName.equals(documentNameTorosBlueUzun)) {
            countTorosBlueUzun = count;
        } else if (documentName.equals(documentNameTorosRedKisa)) {
            countTorosRedKisa = count;
        } else if (documentName.equals(documentNameTorosRedUzun)) {
            countTorosRedUzun = count;
        }
    }
}