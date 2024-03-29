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

public class CamelStocksActivity extends AppCompatActivity {
    private int countCamelYellowKisa, countCamelYellowUzun, countCamelYellowSoftKisa, countCamelWhite, countCamelBrown, countCamelBlack, countCamelDeepBlue, countCamelSlenderBlue, countCamelSlenderGrey, countTotalStock;
    private EditText et_camel_kisa, et_camel_uzun, et_camel_soft, et_camel_white, et_camel_black, et_camel_deep_blue, et_camel_brown, et_camel_slender_blue, et_camel_slender_grey;
    private TextView totalSumTextView;

    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameCamelYellowKisa = "JTI_Camel_Yellow_Kisa", documentNameCamelYellowUzun = "JTI_Camel_Yellow_Uzun", documentNameCamelYellowSoftKisa = "JTI_Camel_Yellow_Soft_Kisa", documentNameCamelWhite = "JTI_Camel_White", documentNameCamelBlack = "JTI_Camel_Black", documentNameCamelBrown = "JTI_Camel_Brown", documentNameCamelDeepBlue = "JTI_Camel_Deep_Blue", documentNameCamelSlenderBlue = "JTI_Camel_Slender_Blue", documentNameCamelSlenderGrey = "JTI_Camel_Slender_Grey", documentTotalStocks= "Total_Camel_Stocks";

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

        Button updateValuesButton = findViewById(R.id.camelGuncelle);

        totalSumTextView = findViewById(R.id.tv_camel_total);


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

        et_camel_kisa = findViewById(R.id.et_camel_kisa);
        et_camel_uzun = findViewById(R.id.et_camel_uzun);
        et_camel_soft = findViewById(R.id.et_camel_soft);
        et_camel_white = findViewById(R.id.et_camel_white);
        et_camel_black = findViewById(R.id.et_camel_black);
        et_camel_deep_blue = findViewById(R.id.et_camel_deep_blue);
        et_camel_brown = findViewById(R.id.et_camel_brown);
        et_camel_slender_blue = findViewById(R.id.et_camel_slender_blue);
        et_camel_slender_grey = findViewById(R.id.et_camel_slender_grey);

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

        reset_all_camel_all_btn.setOnClickListener(new View.OnClickListener() {
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
        countCamelYellowKisa = parseEditTextValue(et_camel_kisa);
        countCamelYellowUzun = parseEditTextValue(et_camel_uzun);
        countCamelYellowSoftKisa = parseEditTextValue(et_camel_soft);
        countCamelWhite = parseEditTextValue(et_camel_white);
        countCamelBlack = parseEditTextValue(et_camel_black);
        countCamelDeepBlue = parseEditTextValue(et_camel_deep_blue);
        countCamelBrown = parseEditTextValue(et_camel_brown);
        countCamelSlenderBlue = parseEditTextValue(et_camel_slender_blue);
        countCamelSlenderGrey = parseEditTextValue(et_camel_slender_grey);

        countTotalStock = countCamelYellowKisa + countCamelYellowUzun+ countCamelYellowSoftKisa+ countCamelWhite+ countCamelBlack+ countCamelDeepBlue+ countCamelBrown + countCamelSlenderBlue+  countCamelSlenderGrey;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }

    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countCamelYellowKisa = Integer.parseInt(et_camel_kisa.getText().toString());
        countCamelYellowUzun = Integer.parseInt(et_camel_uzun.getText().toString());
        countCamelYellowSoftKisa = Integer.parseInt(et_camel_soft.getText().toString());
        countCamelWhite = Integer.parseInt(et_camel_white.getText().toString());
        countCamelBlack = Integer.parseInt(et_camel_black.getText().toString());
        countCamelDeepBlue = Integer.parseInt(et_camel_deep_blue.getText().toString());
        countCamelBrown = Integer.parseInt(et_camel_brown.getText().toString());
        countCamelSlenderBlue = Integer.parseInt(et_camel_slender_blue.getText().toString());
        countCamelSlenderGrey = Integer.parseInt(et_camel_slender_grey.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameCamelYellowKisa, countCamelYellowKisa);
        updateTextView(documentNameCamelYellowUzun, countCamelYellowUzun);
        updateTextView(documentNameCamelYellowSoftKisa, countCamelYellowSoftKisa);
        updateTextView(documentNameCamelWhite, countCamelWhite);
        updateTextView(documentNameCamelBlack, countCamelBlack);
        updateTextView(documentNameCamelDeepBlue, countCamelDeepBlue);
        updateTextView(documentNameCamelBrown, countCamelBrown);
        updateTextView(documentNameCamelSlenderBlue, countCamelSlenderBlue);
        updateTextView(documentNameCamelSlenderGrey, countCamelSlenderGrey);
        updateTextView(documentTotalStocks, countTotalStock);


        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameCamelYellowKisa, countCamelYellowKisa);
        firestoreCount(documentNameCamelYellowUzun, countCamelYellowUzun);
        firestoreCount(documentNameCamelYellowSoftKisa, countCamelYellowSoftKisa);
        firestoreCount(documentNameCamelWhite, countCamelWhite);
        firestoreCount(documentNameCamelBlack, countCamelBlack);
        firestoreCount(documentNameCamelDeepBlue, countCamelDeepBlue);
        firestoreCount(documentNameCamelBrown, countCamelBrown);
        firestoreCount(documentNameCamelSlenderBlue, countCamelSlenderBlue);
        firestoreCount(documentNameCamelSlenderGrey, countCamelSlenderGrey);
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

    private void discardStockCount() {
        et_camel_kisa.setText(String.valueOf(countCamelYellowKisa));
        et_camel_uzun.setText(String.valueOf(countCamelYellowUzun));
        et_camel_soft.setText(String.valueOf(countCamelYellowSoftKisa));
        et_camel_white.setText(String.valueOf(countCamelWhite));
        et_camel_black.setText(String.valueOf(countCamelBlack));
        et_camel_deep_blue.setText(String.valueOf(countCamelDeepBlue));
        et_camel_brown.setText(String.valueOf(countCamelBrown));
        et_camel_slender_blue.setText(String.valueOf(countCamelSlenderBlue));
        et_camel_slender_grey.setText(String.valueOf(countCamelSlenderGrey));
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
        countTotalStock = 0;


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
        updateTextView(documentTotalStocks, countTotalStock);


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
        firestoreCount(documentTotalStocks, 0);

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
                        updateTotalSum();

                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_SHORT).show();
                });
    }


    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameCamelYellowKisa)) {
            et_camel_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowUzun)) {
            et_camel_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowSoftKisa)) {
            et_camel_soft.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelWhite)) {
            et_camel_white.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBlack)) {
            et_camel_black.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            et_camel_deep_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBrown)) {
            et_camel_brown.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            et_camel_slender_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            et_camel_slender_grey.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
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
        } else if(documentName.equals(documentNameCamelBrown)) {
            return countCamelBrown;
        } else if(documentName.equals(documentNameCamelBlack)) {
            return countCamelBlack;
        } else if(documentName.equals(documentNameCamelDeepBlue)) {
            return countCamelDeepBlue;
        } else if(documentName.equals(documentNameCamelSlenderBlue)) {
            return countCamelSlenderBlue;
        } else if(documentName.equals(documentNameCamelSlenderGrey)) {
            return countCamelSlenderGrey;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
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
        } else if (documentName.equals(documentNameCamelBrown)) {
            countCamelBrown = count;
        } else if (documentName.equals(documentNameCamelBlack)) {
            countCamelBlack = count;
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            countCamelDeepBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            countCamelSlenderBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            countCamelSlenderGrey = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}