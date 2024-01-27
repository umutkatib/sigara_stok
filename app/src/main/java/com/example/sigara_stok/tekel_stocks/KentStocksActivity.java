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

public class KentStocksActivity extends AppCompatActivity {
    private int countKentDrangeBlueKisa, countKentDrangeBlueUzun, countKentDrangeGreyKisa, countKentDrangeGreyUzun, countKentDarkBlueKisa, countKentDarkBlueUzun, countKentBlueKisa, countKentSwitchKisa, countTotalStock;
    private EditText et_drange_blue_kisa, et_drange_blue_uzun, et_drange_grey_kisa, et_drange_grey_uzun, et_dark_blue_kisa, et_dark_blue_uzun, et_kent_blue_kisa, et_switch_kisa;
    private TextView totalSumTextView;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameKentDrangeBlueKisa = "BAT_Kent_Drange_Blue_Kisa", documentNameKentDrangeBlueUzun = "BAT_Kent_Drange_Blue_Uzun", documentNameKentDrangeGreyKisa = "BAT_Kent_Drange_Grey_Kisa", documentNameKentDrangeGreyUzun = "BAT_Kent_Drange_Grey_Uzun", documentNameKentDarkBlueKisa = "BAT_Kent_Dark_Blue_Kisa", documentNameKentDarkBlueUzun = "BAT_Kent_Dark_Blue_Uzun", documentNameKentBlueKisa = "BAT_Kent_Blue_Kisa", documentNameKentSwitchKisa = "BAT_Kent_Switch_Kisa", documentTotalStocks= "Total_Kent_Stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kent_stocks);

        Button btn_reset_all_kent = findViewById(R.id.btn_reset_all_kent);

        Button drange_blue_kisa_azalt_btn = findViewById(R.id.drange_blue_kisa_azalt_btn);
        Button drange_blue_kisa_arttir_btn = findViewById(R.id.drange_blue_kisa_arttir_btn);
        Button drange_blue_uzun_azalt_btn = findViewById(R.id.drange_blue_uzun_azalt_btn);
        Button drange_blue_uzun_arttir_btn = findViewById(R.id.drange_blue_uzun_arttir_btn);
        Button drange_grey_kisa_azalt_btn = findViewById(R.id.drange_grey_kisa_azalt_btn);
        Button drange_grey_kisa_arttir_btn = findViewById(R.id.drange_grey_kisa_arttir_btn);
        Button drange_grey_uzun_azalt_btn = findViewById(R.id.drange_grey_uzun_azalt_btn);
        Button drange_grey_uzun_arttir_btn = findViewById(R.id.drange_grey_uzun_arttir_btn);
        Button kent_dark_blue_kisa_azalt_btn = findViewById(R.id.kent_dark_blue_kisa_azalt_btn);
        Button kent_dark_blue_kisa_arttir_btn = findViewById(R.id.kent_dark_blue_kisa_arttir_btn);
        Button kent_dark_blue_uzun_azalt_btn = findViewById(R.id.kent_dark_blue_uzun_azalt_btn);
        Button kent_dark_blue_uzun_arttir_btn = findViewById(R.id.kent_dark_blue_uzun_arttir_btn);
        Button kent_blue_azalt_btn = findViewById(R.id.kent_blue_azalt_btn);
        Button kent_blue_arttir_btn = findViewById(R.id.kent_blue_arttir_btn);
        Button kent_switch_azalt_btn = findViewById(R.id.kent_switch_azalt_btn);
        Button kent_switch_arttir_btn = findViewById(R.id.kent_switch_arttir_btn);


        Button updateValuesButton = findViewById(R.id.kentGuncelle);

        setLdButtonClickListeners(drange_blue_kisa_azalt_btn, drange_blue_kisa_arttir_btn, documentNameKentDrangeBlueKisa);
        setLdButtonClickListeners(drange_blue_uzun_azalt_btn, drange_blue_uzun_arttir_btn, documentNameKentDrangeBlueUzun);
        setLdButtonClickListeners(drange_grey_kisa_azalt_btn, drange_grey_kisa_arttir_btn, documentNameKentDrangeGreyKisa);
        setLdButtonClickListeners(drange_grey_uzun_azalt_btn, drange_grey_uzun_arttir_btn, documentNameKentDrangeGreyUzun);
        setLdButtonClickListeners(kent_dark_blue_kisa_azalt_btn, kent_dark_blue_kisa_arttir_btn, documentNameKentDarkBlueKisa);
        setLdButtonClickListeners(kent_dark_blue_uzun_azalt_btn, kent_dark_blue_uzun_arttir_btn, documentNameKentDarkBlueUzun);
        setLdButtonClickListeners(kent_blue_azalt_btn, kent_blue_arttir_btn, documentNameKentBlueKisa);
        setLdButtonClickListeners(kent_switch_azalt_btn, kent_switch_arttir_btn, documentNameKentSwitchKisa);


        totalSumTextView = findViewById(R.id.tv_kent_total);

        et_drange_blue_kisa = findViewById(R.id.et_drange_blue_kisa);
        et_drange_blue_uzun = findViewById(R.id.et_drange_blue_uzun);

        et_drange_grey_kisa = findViewById(R.id.et_drange_grey_kisa);
        et_drange_grey_uzun = findViewById(R.id.et_drange_grey_uzun);

        et_dark_blue_kisa = findViewById(R.id.et_dark_blue_kisa);
        et_dark_blue_uzun = findViewById(R.id.et_dark_blue_uzun);

        et_kent_blue_kisa = findViewById(R.id.et_kent_blue_kisa);
        et_switch_kisa = findViewById(R.id.et_switch_kisa);

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
        countKentDrangeBlueKisa = parseEditTextValue(et_drange_blue_kisa);
        countKentDrangeBlueUzun = parseEditTextValue(et_drange_blue_uzun);

        countKentDrangeGreyKisa = parseEditTextValue(et_drange_grey_kisa);
        countKentDrangeGreyKisa = parseEditTextValue(et_drange_grey_uzun);

        countKentDarkBlueKisa = parseEditTextValue(et_dark_blue_kisa);
        countKentDarkBlueUzun = parseEditTextValue(et_dark_blue_uzun);

        countKentBlueKisa = parseEditTextValue(et_kent_blue_kisa);
        countKentSwitchKisa = parseEditTextValue(et_switch_kisa);


        countTotalStock = countKentDrangeBlueKisa + countKentDrangeBlueUzun+ countKentDrangeGreyKisa+ countKentDrangeGreyKisa+ countKentDarkBlueKisa+ countKentDarkBlueUzun+ countKentBlueKisa +countKentSwitchKisa;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }


    private void discardStockCount() {
        et_drange_blue_kisa.setText(String.valueOf(countKentDrangeBlueKisa));
        et_drange_blue_uzun.setText(String.valueOf(countKentDrangeBlueUzun));

        et_drange_grey_kisa.setText(String.valueOf(countKentDrangeGreyKisa));
        et_drange_grey_uzun.setText(String.valueOf(countKentDrangeGreyUzun));

        et_dark_blue_kisa.setText(String.valueOf(countKentDarkBlueKisa));
        et_dark_blue_uzun.setText(String.valueOf(countKentDarkBlueUzun));

        et_kent_blue_kisa.setText(String.valueOf(countKentBlueKisa));
        et_switch_kisa.setText(String.valueOf(countKentSwitchKisa));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countKentDrangeBlueKisa = Integer.parseInt(et_drange_blue_kisa.getText().toString());
        countKentDrangeBlueUzun = Integer.parseInt(et_drange_blue_uzun.getText().toString());
        countKentDrangeGreyKisa = Integer.parseInt(et_drange_grey_kisa.getText().toString());
        countKentDrangeGreyUzun = Integer.parseInt(et_drange_grey_uzun.getText().toString());
        countKentDarkBlueKisa = Integer.parseInt(et_dark_blue_kisa.getText().toString());
        countKentDarkBlueUzun = Integer.parseInt(et_dark_blue_uzun.getText().toString());
        countKentBlueKisa = Integer.parseInt(et_kent_blue_kisa.getText().toString());
        countKentSwitchKisa = Integer.parseInt(et_switch_kisa.getText().toString());


        // TextView'ları güncelle
        updateTextView(documentNameKentDrangeBlueKisa, countKentDrangeBlueKisa);
        updateTextView(documentNameKentDrangeBlueUzun, countKentDrangeBlueUzun);
        updateTextView(documentNameKentDrangeGreyKisa, countKentDrangeGreyKisa);
        updateTextView(documentNameKentDrangeGreyUzun, countKentDrangeGreyUzun);
        updateTextView(documentNameKentDarkBlueKisa, countKentDarkBlueKisa);
        updateTextView(documentNameKentDarkBlueUzun, countKentDarkBlueUzun);
        updateTextView(documentNameKentBlueKisa, countKentBlueKisa);
        updateTextView(documentNameKentSwitchKisa, countKentSwitchKisa);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameKentDrangeBlueKisa, countKentDrangeBlueKisa);
        firestoreCount(documentNameKentDrangeBlueUzun, countKentDrangeBlueUzun);

        firestoreCount(documentNameKentDrangeGreyKisa, countKentDrangeGreyKisa);
        firestoreCount(documentNameKentDrangeGreyUzun, countKentDrangeGreyUzun);

        firestoreCount(documentNameKentDarkBlueKisa, countKentDarkBlueKisa);
        firestoreCount(documentNameKentDarkBlueUzun, countKentDarkBlueUzun);

        firestoreCount(documentNameKentBlueKisa, countKentBlueKisa);
        firestoreCount(documentNameKentSwitchKisa, countKentSwitchKisa);

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
        countKentDrangeBlueKisa = 0;
        countKentDrangeBlueUzun = 0;
        countKentDrangeGreyKisa = 0;
        countKentDrangeGreyUzun = 0;
        countKentDarkBlueKisa = 0;
        countKentDarkBlueUzun = 0;
        countKentBlueKisa = 0;
        countKentSwitchKisa = 0;
        countTotalStock = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameKentDrangeBlueKisa, countKentDrangeBlueKisa);
        updateTextView(documentNameKentDrangeBlueUzun, countKentDrangeBlueUzun);
        updateTextView(documentNameKentDrangeGreyKisa, countKentDrangeGreyKisa);
        updateTextView(documentNameKentDrangeGreyUzun, countKentDrangeGreyUzun);
        updateTextView(documentNameKentDarkBlueKisa, countKentDarkBlueKisa);
        updateTextView(documentNameKentDarkBlueUzun, countKentDarkBlueUzun);
        updateTextView(documentNameKentBlueKisa, countKentBlueKisa);
        updateTextView(documentNameKentSwitchKisa, countKentSwitchKisa);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameKentDrangeBlueKisa, 0);
        firestoreCount(documentNameKentDrangeBlueUzun, 0);
        firestoreCount(documentNameKentDrangeGreyKisa, 0);
        firestoreCount(documentNameKentDrangeGreyUzun, 0);
        firestoreCount(documentNameKentDarkBlueKisa, 0);
        firestoreCount(documentNameKentDarkBlueUzun, 0);
        firestoreCount(documentNameKentBlueKisa, 0);
        firestoreCount(documentNameKentSwitchKisa, 0);
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
        int currentCount = getCount(documentName);
        if (currentCount > 0) {
            firestoreCount(documentName, currentCount - 1);

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
        readFirestoreForDocument(documentNameKentDrangeBlueKisa);
        readFirestoreForDocument(documentNameKentDrangeBlueUzun);
        readFirestoreForDocument(documentNameKentDrangeGreyKisa);
        readFirestoreForDocument(documentNameKentDrangeGreyUzun);
        readFirestoreForDocument(documentNameKentDarkBlueKisa);
        readFirestoreForDocument(documentNameKentDarkBlueUzun);
        readFirestoreForDocument(documentNameKentBlueKisa);
        readFirestoreForDocument(documentNameKentSwitchKisa);
        readFirestoreForDocument(documentTotalStocks);
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
        if (documentName.equals(documentNameKentDrangeBlueKisa)) {
            et_drange_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeBlueUzun)) {
            et_drange_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeGreyKisa)) {
            et_drange_grey_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeGreyUzun)) {
            et_drange_grey_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDarkBlueKisa)) {
            et_dark_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDarkBlueUzun)) {
            et_dark_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentBlueKisa)) {
            et_kent_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentSwitchKisa)) {
            et_switch_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameKentDrangeBlueKisa)) {
            return countKentDrangeBlueKisa;
        } else if (documentName.equals(documentNameKentDrangeBlueUzun)) {
            return countKentDrangeBlueUzun;
        } else if(documentName.equals(documentNameKentDrangeGreyKisa)) {
            return countKentDrangeGreyKisa;
        } else if(documentName.equals(documentNameKentDrangeGreyUzun)) {
            return countKentDrangeGreyUzun;
        } else if(documentName.equals(documentNameKentDarkBlueKisa)) {
            return countKentDarkBlueKisa;
        } else if(documentName.equals(documentNameKentDarkBlueUzun)) {
            return countKentDarkBlueUzun;
        } else if(documentName.equals(documentNameKentBlueKisa)) {
            return countKentBlueKisa;
        } else if(documentName.equals(documentNameKentSwitchKisa)) {
            return countKentSwitchKisa;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameKentDrangeBlueKisa)) {
            countKentDrangeBlueKisa = count;
        } else if (documentName.equals(documentNameKentDrangeBlueUzun)) {
            countKentDrangeBlueUzun = count;
        } else if (documentName.equals(documentNameKentDrangeGreyKisa)) {
            countKentDrangeGreyKisa = count;
        } else if (documentName.equals(documentNameKentDrangeGreyUzun)) {
            countKentDrangeGreyUzun = count;
        } else if (documentName.equals(documentNameKentDarkBlueKisa)) {
            countKentDarkBlueKisa = count;
        } else if (documentName.equals(documentNameKentDarkBlueUzun)) {
            countKentDarkBlueUzun = count;
        } else if (documentName.equals(documentNameKentBlueKisa)) {
            countKentBlueKisa = count;
        } else if (documentName.equals(documentNameKentSwitchKisa)) {
            countKentSwitchKisa = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}