package com.example.sigara_stok.stocks.marbloro_stocks;


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

public class LMStocksActivity extends AppCompatActivity {
    private int countLmKisa, countLmUzun, countTotalStock;
    private EditText et_lm_kisa, et_lm_uzun;
    private TextView totalSumTextView;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameLmKisa = "PM_LM_Kisa", documentNameLmUzun = "PM_LM_Uzun", documentTotalStocks= "Total_LM_Stocks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lmstocks);

        Button reset_all_lm = findViewById(R.id.reset_all_lm);
        Button lm_kisa_azalt = findViewById(R.id.lm_kisa_azalt);
        Button lm_kisa_arttir = findViewById(R.id.lm_kisa_arttir);
        Button lm_uzun_azalt = findViewById(R.id.lm_uzun_azalt);
        Button lm_uzun_arttir = findViewById(R.id.lm_uzun_arttir);

        Button updateValuesButton = findViewById(R.id.lmGuncelle);

        setLmButtonClickListeners(lm_kisa_azalt, lm_kisa_arttir, documentNameLmKisa);
        setLmButtonClickListeners(lm_uzun_azalt, lm_uzun_arttir, documentNameLmUzun);


        totalSumTextView = findViewById(R.id.tv_lm_total);
        et_lm_kisa = findViewById(R.id.et_lm_kisa);
        et_lm_uzun = findViewById(R.id.et_lm_uzun);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        updateValuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStockDialog();
            }
        });

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private int parseEditTextValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;  // Set a default value or handle the error as needed
        }
    }

    private void updateTotalSum() {
        countLmKisa = parseEditTextValue(et_lm_kisa);
        countLmUzun = parseEditTextValue(et_lm_uzun);

        countTotalStock = countLmKisa + countLmUzun;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }

    private void discardStockCount() {
        et_lm_kisa.setText(String.valueOf(countLmKisa));
        et_lm_uzun.setText(String.valueOf(countLmUzun));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countLmKisa = Integer.parseInt(et_lm_kisa.getText().toString());
        countLmUzun = Integer.parseInt(et_lm_uzun.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameLmKisa, countLmKisa);
        updateTextView(documentNameLmUzun, countLmUzun);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameLmKisa, countLmKisa);
        firestoreCount(documentNameLmUzun, countLmUzun);
        firestoreCount(documentTotalStocks, countTotalStock);
    }

    private void updateStockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stok Güncelle");
        builder.setMessage("Stok verisini güncellemek istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTotalSum();
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

    private void setLmButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countLmKisa = 0;
        countLmUzun = 0;
        countTotalStock = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameLmKisa, countLmKisa);
        updateTextView(documentNameLmUzun, countLmUzun);
        updateTextView(documentTotalStocks, countTotalStock);
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameLmKisa, 0);
        firestoreCount(documentNameLmUzun, 0);
        firestoreCount(documentTotalStocks, 0);
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
                    updateTotalSum();
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
                    updateTotalSum();
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
        readFirestoreForDocument(documentNameLmKisa);
        readFirestoreForDocument(documentNameLmUzun);
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
                        updateTotalSum();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameLmKisa)) {
            et_lm_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLmUzun)) {
            et_lm_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameLmKisa)) {
            return countLmKisa;
        } else if (documentName.equals(documentNameLmUzun)) {
            return countLmUzun;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameLmKisa)) {
            countLmKisa = count;
        } else if (documentName.equals(documentNameLmUzun)) {
            countLmUzun = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}