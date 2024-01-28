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

public class RothmansStocksActivity extends AppCompatActivity {
    private int countRothmansBlueKisa, countRothmansBlueUzun, countRothmansDrangeBlackKisa, countRothmansDrangeBlackUzun, countRothmansDrangeBlueKisa, countRothmansDrangeBlueUzun, countTotalStock;
    private EditText et_rothmans_kisa, et_rothmans_uzun, et_rothmans_drange__black_kisa, et_rothmans_drange__black_uzun, et_rothmans_drange__blue_kisa, et_rothmans_drange__blue_uzun;
    private TextView totalSumTextView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameRothamnsBlueKisa = "BAT_Rothmans_Kisa_Blue", documentNameRothamnsBlueUzun = "BAT_Rothmans_Uzun_Blue", documentNameRothmansDrangeBlackKisa = "BAT_Rothmans_Drange_Kisa_Black", documentNameRothmansDrangeBlackUzun = "BAT_Rothmans_Drange_Uzun_Black", documentNameRothmansDrangeBlueKisa = "BAT_Rothmans_Drange_Kisa_Blue", documentNameRothmansDrangeBlueUzun = "BAT_Rothmans_Drange_Uzun_Blue", documentTotalStocks= "Total_Rothmans_Stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rothmans_stocks);

        Button btn_reset_all_kent = findViewById(R.id.btn_reset_all_rothmans);

        Button rothmans_kisa_azalt_btn = findViewById(R.id.rothmans_kisa_azalt_btn);
        Button rothmans_kisa_arttir_btn = findViewById(R.id.rothmans_kisa_arttir_btn);
        Button rothmans_uzun_azalt_btn = findViewById(R.id.rothmans_uzun_azalt_btn);
        Button rothmans_uzun_arttir_btn = findViewById(R.id.rothmans_uzun_arttir_btn);
        Button drange_black_kisa_azalt_btn = findViewById(R.id.drange_black_kisa_azalt_btn);
        Button drange_black_kisa_arttir_btn = findViewById(R.id.drange_black_kisa_arttir_btn);
        Button drange_black_uzun_azalt_btn = findViewById(R.id.drange_black_uzun_azalt_btn);
        Button drange_black_uzun_arttir_btn = findViewById(R.id.drange_black_uzun_arttir_btn);
        Button rothmans_drange_blue_kisa_azalt_btn = findViewById(R.id.rothmans_drange_blue_kisa_azalt_btn);
        Button rothmans_drange_black_kisa_arttir_btn = findViewById(R.id.rothmans_drange_black_kisa_arttir_btn);
        Button rothmans_drange_blue_uzun_azalt_btn = findViewById(R.id.rothmans_drange_blue_uzun_azalt_btn);
        Button rothmans_drange_blue_uzun_arttir_btn = findViewById(R.id.rothmans_drange_blue_uzun_arttir_btn);

        Button updateValuesButton = findViewById(R.id.rothmansGuncelle);

        setRothmansButtonClickListeners(rothmans_kisa_azalt_btn, rothmans_kisa_arttir_btn, documentNameRothamnsBlueKisa);
        setRothmansButtonClickListeners(rothmans_uzun_azalt_btn, rothmans_uzun_arttir_btn, documentNameRothamnsBlueUzun);
        setRothmansButtonClickListeners(drange_black_kisa_azalt_btn, drange_black_kisa_arttir_btn, documentNameRothmansDrangeBlackKisa);
        setRothmansButtonClickListeners(drange_black_uzun_azalt_btn, drange_black_uzun_arttir_btn, documentNameRothmansDrangeBlackUzun);
        setRothmansButtonClickListeners(rothmans_drange_blue_kisa_azalt_btn, rothmans_drange_black_kisa_arttir_btn, documentNameRothmansDrangeBlueKisa);
        setRothmansButtonClickListeners(rothmans_drange_blue_uzun_azalt_btn, rothmans_drange_blue_uzun_arttir_btn, documentNameRothmansDrangeBlueUzun);


        totalSumTextView = findViewById(R.id.tv_rothmans_total);


        et_rothmans_kisa = findViewById(R.id.et_rothmans_kisa);
        et_rothmans_uzun = findViewById(R.id.et_rothmans_uzun);
        et_rothmans_drange__black_kisa = findViewById(R.id.et_rothmans_drange__black_kisa);
        et_rothmans_drange__black_uzun = findViewById(R.id.et_rothmans_drange__black_uzun);
        et_rothmans_drange__blue_kisa = findViewById(R.id.et_rothmans_drange__blue_kisa);
        et_rothmans_drange__blue_uzun = findViewById(R.id.et_rothmans_drange__blue_uzun);

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

        btn_reset_all_kent.setOnClickListener(new View.OnClickListener() {
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
        countRothmansBlueKisa = parseEditTextValue(et_rothmans_kisa);
        countRothmansBlueUzun = parseEditTextValue(et_rothmans_uzun);
        countRothmansDrangeBlackKisa = parseEditTextValue(et_rothmans_drange__black_kisa);
        countRothmansDrangeBlackUzun = parseEditTextValue(et_rothmans_drange__black_uzun);
        countRothmansDrangeBlueKisa = parseEditTextValue(et_rothmans_drange__blue_kisa);
        countRothmansDrangeBlueUzun = parseEditTextValue(et_rothmans_drange__blue_uzun);

        countTotalStock = countRothmansBlueKisa + countRothmansBlueUzun+ countRothmansDrangeBlackKisa+ countRothmansDrangeBlackUzun+ countRothmansDrangeBlueKisa+ countRothmansDrangeBlueUzun;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }

    private void discardStockCount() {
        et_rothmans_kisa.setText(String.valueOf(countRothmansBlueKisa));
        et_rothmans_uzun.setText(String.valueOf(countRothmansBlueUzun));

        et_rothmans_drange__black_kisa.setText(String.valueOf(countRothmansDrangeBlackKisa));
        et_rothmans_drange__black_uzun.setText(String.valueOf(countRothmansDrangeBlackUzun));

        et_rothmans_drange__blue_kisa.setText(String.valueOf(countRothmansDrangeBlueKisa));
        et_rothmans_drange__blue_uzun.setText(String.valueOf(countRothmansDrangeBlueUzun));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countRothmansBlueKisa = Integer.parseInt(et_rothmans_kisa.getText().toString());
        countRothmansBlueUzun = Integer.parseInt(et_rothmans_uzun.getText().toString());
        countRothmansDrangeBlackKisa = Integer.parseInt(et_rothmans_drange__black_kisa.getText().toString());
        countRothmansDrangeBlackUzun = Integer.parseInt(et_rothmans_drange__black_uzun.getText().toString());
        countRothmansDrangeBlueKisa = Integer.parseInt(et_rothmans_drange__blue_kisa.getText().toString());
        countRothmansDrangeBlueUzun = Integer.parseInt(et_rothmans_drange__blue_uzun.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameRothamnsBlueKisa, countRothmansBlueKisa);
        updateTextView(documentNameRothamnsBlueUzun, countRothmansBlueUzun);
        updateTextView(documentNameRothmansDrangeBlackKisa, countRothmansDrangeBlackKisa);
        updateTextView(documentNameRothmansDrangeBlackUzun, countRothmansDrangeBlackUzun);
        updateTextView(documentNameRothmansDrangeBlueKisa, countRothmansDrangeBlueKisa);
        updateTextView(documentNameRothmansDrangeBlueUzun, countRothmansDrangeBlueUzun);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameRothamnsBlueKisa, countRothmansBlueKisa);
        firestoreCount(documentNameRothamnsBlueUzun, countRothmansBlueUzun);
        firestoreCount(documentNameRothmansDrangeBlackKisa, countRothmansDrangeBlackKisa);
        firestoreCount(documentNameRothmansDrangeBlackUzun, countRothmansDrangeBlackUzun);
        firestoreCount(documentNameRothmansDrangeBlueKisa, countRothmansDrangeBlueKisa);
        firestoreCount(documentNameRothmansDrangeBlueUzun, countRothmansDrangeBlueUzun);
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

    private void setRothmansButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countRothmansBlueKisa = 0;
        countRothmansBlueUzun = 0;
        countRothmansDrangeBlackKisa = 0;
        countRothmansDrangeBlackUzun = 0;
        countRothmansDrangeBlueKisa = 0;
        countRothmansDrangeBlueUzun = 0;
        countTotalStock = 0;



        // Tüm TextView'ları sıfırla
        updateTextView(documentNameRothamnsBlueKisa, countRothmansBlueKisa);
        updateTextView(documentNameRothamnsBlueUzun, countRothmansBlueUzun);
        updateTextView(documentNameRothmansDrangeBlackKisa, countRothmansDrangeBlackKisa);
        updateTextView(documentNameRothmansDrangeBlackUzun, countRothmansDrangeBlackUzun);
        updateTextView(documentNameRothmansDrangeBlueKisa, countRothmansDrangeBlueKisa);
        updateTextView(documentNameRothmansDrangeBlueUzun, countRothmansDrangeBlueUzun);
        updateTextView(documentTotalStocks, countTotalStock);


        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameRothamnsBlueKisa, 0);
        firestoreCount(documentNameRothamnsBlueUzun, 0);
        firestoreCount(documentNameRothmansDrangeBlackKisa, 0);
        firestoreCount(documentNameRothmansDrangeBlackUzun, 0);
        firestoreCount(documentNameRothmansDrangeBlueKisa, 0);
        firestoreCount(documentNameRothmansDrangeBlueUzun, 0);
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
        readFirestoreForDocument(documentNameRothamnsBlueKisa);
        readFirestoreForDocument(documentNameRothamnsBlueUzun);

        readFirestoreForDocument(documentNameRothmansDrangeBlackKisa);
        readFirestoreForDocument(documentNameRothmansDrangeBlackUzun);

        readFirestoreForDocument(documentNameRothmansDrangeBlueKisa);
        readFirestoreForDocument(documentNameRothmansDrangeBlueUzun);
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
                        updateTotalSum();
                    }
                });
    }


    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            et_rothmans_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            et_rothmans_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            et_rothmans_drange__black_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            et_rothmans_drange__black_uzun.setText(String.valueOf(count));
        }else if (documentName.equals(documentNameRothmansDrangeBlueKisa)) {
            et_rothmans_drange__blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlueUzun)) {
            et_rothmans_drange__blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            return countRothmansBlueKisa;
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            return countRothmansBlueUzun;
        } else if(documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            return countRothmansDrangeBlackKisa;
        } else if(documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            return countRothmansDrangeBlackUzun;
        } else if(documentName.equals(documentNameRothmansDrangeBlueKisa)) {
            return countRothmansDrangeBlueKisa;
        } else if(documentName.equals(documentNameRothmansDrangeBlueUzun)) {
            return countRothmansDrangeBlueUzun;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            countRothmansBlueKisa = count;
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            countRothmansBlueUzun = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            countRothmansDrangeBlackKisa = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            countRothmansDrangeBlackUzun = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlueKisa)) {
            countRothmansDrangeBlueKisa = count;
        } else if (documentName.equals(documentNameRothmansDrangeBlueUzun)) {
            countRothmansDrangeBlueUzun = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}