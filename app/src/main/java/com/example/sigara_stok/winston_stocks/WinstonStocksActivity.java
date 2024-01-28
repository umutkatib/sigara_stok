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

public class WinstonStocksActivity extends AppCompatActivity {
    private int countSlenderKisa, countSlenderUzun, countBlueKisa, countBlueUzun, countRedKisa, countRedUzun, countSlimBlue, countSlimGrey, countSlenderGrey, countGrey, countDarkBlueKisa, countDarkBlueUzun, countTotalStock;
    private EditText et_slender_blue_kisa, et_slender_blue_uzun, et_winston_blue_kisa, et_winston_blue_uzun, et_winston_red_kisa, et_winston_red_uzun, et_winston_dark_uzun, et_winston_dark_kisa, et_winston_slim_blue, et_winston_slim_grey, et_slender_grey_kisa, et_winston_grey;
    private TextView totalSumTextView;

    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameSlenderKisa = "JTI_Slender_Blue_Kisa", documentNameSlenderUzun = "JTI_Slender_Blue_Uzun", getDocumentNameBlueKisa = "JTI_Blue_Kisa", getDocumentNameBlueUzun = "JTI_Blue_Uzun", getDocumentNameRedKisa = "JTI_Red_Kisa", getDocumentNameRedUzun = "JTI_Red_Uzun", getDocumentNameSlimBlue = "JTI_Slims_Blue", getDocumentNameSlimGrey = "JTI_Slims_Grey", getDocumentNameSlenderGreyKisa = "JTI_Slender_Grey", getDocumentNameWinsonGrey= "JTI_Winston_Grey", getDocumentNameDarkBlueKisa = "JTI_Winston_Dark_Blue_Kisa", getDocumentNameDarkBlueUzun= "JTI_Winston_Dark_Blue_Uzun", documentTotalStocks= "Total_Winston_Stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winston_stocks);

        Button resetAllButton = findViewById(R.id.reset_all_btn);

        Button slender_kisa_azalt_btn = findViewById(R.id.slender_kisa_azalt_btn);
        Button slender_kisa_arttir_btn = findViewById(R.id.tekel_2000_kirmizi_uzun_arttir_btn);
        Button slender_uzun_azalt_btn = findViewById(R.id.tekel_2000_kirmizi_kisa_azalt_btn);
        Button slender_uzun_arttir_btn = findViewById(R.id.slender_uzun_arttir_btn);
        Button slender_grey_kisa_azalt_btn = findViewById(R.id.slender_grey_kisa_azalt_btn);
        Button slender_grey_kisa_arttir_btn = findViewById(R.id.slender_grey_kisa_arttir_btn);
        Button blue_kisa_azalt_btn = findViewById(R.id.blue_kisa_azalt_btn);
        Button blue_kisa_arttir_btn = findViewById(R.id.blue_kisa_arttir_btn);
        Button blue_uzun_azalt_btn = findViewById(R.id.blue_uzun_azalt_btn);
        Button blue_uzun_arttır_btn = findViewById(R.id.blue_uzun_arttır_btn);
        Button wins_grey_azalt = findViewById(R.id.wins_grey_azalt);
        Button wins_grey_arttir = findViewById(R.id.wins_grey_arttir);
        Button red_kisa_azalt_btn = findViewById(R.id.red_kisa_azalt_btn);
        Button red_kisa_arttir_btn = findViewById(R.id.red_kisa_arttir_btn);
        Button red_uzun_azalt_btn = findViewById(R.id.red_uzun_azalt_btn);
        Button red_uzun_arttir_btn = findViewById(R.id.red_uzun_arttir_btn);
        Button slim_blue_azalt_btn = findViewById(R.id.slim_blue_azalt_btn);
        Button slim_blue_arttir_btn = findViewById(R.id.slim_blue_arttir_btn);
        Button slim_grey_azalt_btn = findViewById(R.id.slim_grey_azalt_btn);
        Button slim_grey_arttir_btn = findViewById(R.id.slim_grey_arttir_btn);
        Button dark_blue_kisa_azalt_btn = findViewById(R.id.dark_blue_kisa_azalt_btn);
        Button dark_blue_kisa_arttir_btn = findViewById(R.id.dark_blue_kisa_arttir_btn);
        Button dark_blue_uzun_azalt_btn = findViewById(R.id.dark_blue_uzun_azalt_btn);
        Button dark_blue_uzun_arttir_btn = findViewById(R.id.dark_blue_uzun_arttir_btn);

        Button updateValuesButton = findViewById(R.id.winstonGuncelle);

        totalSumTextView = findViewById(R.id.tv_winston_total);



        setCamelButtonClickListeners(slender_kisa_azalt_btn, slender_kisa_arttir_btn, documentNameSlenderKisa);
        setCamelButtonClickListeners(slender_uzun_azalt_btn, slender_uzun_arttir_btn, documentNameSlenderUzun);
        setCamelButtonClickListeners(slender_grey_kisa_azalt_btn, slender_grey_kisa_arttir_btn, getDocumentNameSlenderGreyKisa);
        setCamelButtonClickListeners(blue_kisa_azalt_btn, blue_kisa_arttir_btn, getDocumentNameBlueKisa);
        setCamelButtonClickListeners(blue_uzun_azalt_btn, blue_uzun_arttır_btn, getDocumentNameBlueUzun);
        setCamelButtonClickListeners(wins_grey_azalt, wins_grey_arttir, getDocumentNameWinsonGrey);
        setCamelButtonClickListeners(red_kisa_azalt_btn, red_kisa_arttir_btn, getDocumentNameRedKisa);
        setCamelButtonClickListeners(red_uzun_azalt_btn, red_uzun_arttir_btn, getDocumentNameRedUzun);
        setCamelButtonClickListeners(slim_blue_azalt_btn, slim_blue_arttir_btn, getDocumentNameSlimBlue);
        setCamelButtonClickListeners(slim_grey_azalt_btn, slim_grey_arttir_btn, getDocumentNameSlimGrey);
        setCamelButtonClickListeners(dark_blue_kisa_azalt_btn, dark_blue_kisa_arttir_btn, getDocumentNameDarkBlueKisa);
        setCamelButtonClickListeners(dark_blue_uzun_azalt_btn, dark_blue_uzun_arttir_btn, getDocumentNameDarkBlueUzun);


        et_slender_blue_kisa = findViewById(R.id.et_slender_blue_kisa);
        et_slender_blue_uzun = findViewById(R.id.et_slender_blue_uzun);
        et_slender_grey_kisa = findViewById(R.id.et_slender_grey_kisa);
        et_winston_grey = findViewById(R.id.et_winston_grey);
        et_winston_blue_kisa = findViewById(R.id.et_winston_blue_kisa);
        et_winston_blue_uzun = findViewById(R.id.et_winston_blue_uzun);
        et_winston_dark_kisa = findViewById(R.id.et_winston_dark_kisa);
        et_winston_dark_uzun = findViewById(R.id.et_winston_dark_uzun);
        et_winston_red_kisa = findViewById(R.id.et_winston_red_kisa);
        et_winston_red_uzun = findViewById(R.id.et_winston_red_uzun);
        et_winston_slim_blue = findViewById(R.id.et_winston_slim_blue);
        et_winston_slim_grey = findViewById(R.id.et_winston_slim_grey);

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

        resetAllButton.setOnClickListener(new View.OnClickListener() {
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
        countSlenderKisa = parseEditTextValue(et_slender_blue_kisa);
        countSlenderUzun = parseEditTextValue(et_slender_blue_uzun);
        countSlenderGrey = parseEditTextValue(et_slender_grey_kisa);
        countBlueKisa = parseEditTextValue(et_winston_blue_kisa);
        countBlueUzun = parseEditTextValue(et_winston_blue_uzun);
        countGrey = parseEditTextValue(et_winston_grey);
        countDarkBlueKisa = parseEditTextValue(et_winston_dark_kisa);
        countDarkBlueUzun = parseEditTextValue(et_winston_dark_uzun);
        countRedKisa = parseEditTextValue(et_winston_red_kisa);
        countRedUzun = parseEditTextValue(et_winston_red_uzun);
        countSlimBlue = parseEditTextValue(et_winston_slim_blue);
        countSlimGrey = parseEditTextValue(et_winston_slim_grey);

        countTotalStock = countSlenderKisa + countSlenderUzun+ countSlenderGrey+ countBlueKisa+ countBlueUzun+ countGrey+ countDarkBlueKisa+ countDarkBlueUzun+ countRedKisa+ countRedUzun+ countSlimBlue+ countSlimGrey;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }

    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countSlenderKisa = Integer.parseInt(et_slender_blue_kisa.getText().toString());
        countSlenderUzun = Integer.parseInt(et_slender_blue_uzun.getText().toString());
        countBlueKisa = Integer.parseInt(et_winston_blue_kisa.getText().toString());
        countBlueUzun = Integer.parseInt(et_winston_blue_uzun.getText().toString());
        countRedKisa = Integer.parseInt(et_winston_red_kisa.getText().toString());
        countRedUzun = Integer.parseInt(et_winston_red_uzun.getText().toString());
        countSlimBlue = Integer.parseInt(et_winston_slim_blue.getText().toString());
        countSlimGrey = Integer.parseInt(et_winston_slim_grey.getText().toString());
        countSlenderGrey = Integer.parseInt(et_slender_grey_kisa.getText().toString());

        countGrey = Integer.parseInt(et_winston_grey.getText().toString());
        countDarkBlueKisa = Integer.parseInt(et_winston_dark_kisa.getText().toString());
        countDarkBlueUzun = Integer.parseInt(et_winston_dark_uzun.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameSlenderKisa, countSlenderKisa);
        updateTextView(documentNameSlenderUzun, countSlenderUzun);
        updateTextView(getDocumentNameBlueKisa, countBlueKisa);
        updateTextView(getDocumentNameBlueUzun, countBlueUzun);
        updateTextView(getDocumentNameRedKisa, countRedKisa);
        updateTextView(getDocumentNameRedUzun, countRedUzun);
        updateTextView(getDocumentNameSlimBlue, countSlimBlue);
        updateTextView(getDocumentNameSlimGrey, countSlimGrey);
        updateTextView(getDocumentNameSlenderGreyKisa, countSlenderGrey);
        updateTextView(getDocumentNameWinsonGrey, countGrey);
        updateTextView(getDocumentNameDarkBlueKisa, countDarkBlueKisa);
        updateTextView(getDocumentNameDarkBlueUzun, countDarkBlueUzun);
        updateTextView(documentTotalStocks, countTotalStock);


        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameSlenderKisa, countSlenderKisa);
        firestoreCount(documentNameSlenderUzun, countSlenderUzun);
        firestoreCount(getDocumentNameBlueKisa, countBlueKisa);
        firestoreCount(getDocumentNameBlueUzun, countBlueUzun);
        firestoreCount(getDocumentNameRedKisa, countRedKisa);
        firestoreCount(getDocumentNameRedUzun, countRedUzun);
        firestoreCount(getDocumentNameSlimBlue, countSlimBlue);
        firestoreCount(getDocumentNameSlimGrey, countSlimGrey);
        firestoreCount(getDocumentNameSlenderGreyKisa, countSlenderGrey);
        firestoreCount(getDocumentNameWinsonGrey, countGrey);
        firestoreCount(getDocumentNameDarkBlueKisa, countDarkBlueKisa);
        firestoreCount(getDocumentNameDarkBlueUzun, countDarkBlueUzun);
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
        et_slender_blue_kisa.setText(String.valueOf(countSlenderKisa));
        et_slender_blue_uzun.setText(String.valueOf(countSlenderUzun));
        et_winston_blue_kisa.setText(String.valueOf(countBlueKisa));
        et_winston_blue_uzun.setText(String.valueOf(countBlueUzun));
        et_winston_red_kisa.setText(String.valueOf(countRedKisa));
        et_winston_red_uzun.setText(String.valueOf(countRedUzun));
        et_winston_slim_blue.setText(String.valueOf(countSlimBlue));
        et_winston_slim_grey.setText(String.valueOf(countSlimGrey));
        et_winston_grey.setText(String.valueOf(countGrey));
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
        countSlenderKisa = 0;
        countSlenderUzun = 0;
        countBlueKisa = 0;
        countBlueUzun = 0;
        countRedKisa = 0;
        countRedUzun = 0;
        countSlimBlue = 0;
        countSlimGrey = 0;
        countSlenderGrey = 0;
        countGrey = 0;
        countDarkBlueKisa = 0;
        countDarkBlueUzun = 0;
        countTotalStock = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameSlenderKisa, countSlenderKisa);
        updateTextView(documentNameSlenderUzun, countSlenderUzun);
        updateTextView(getDocumentNameBlueKisa, countBlueKisa);
        updateTextView(getDocumentNameBlueUzun, countBlueUzun);
        updateTextView(getDocumentNameRedKisa, countRedKisa);
        updateTextView(getDocumentNameRedUzun, countRedUzun);
        updateTextView(getDocumentNameSlimBlue, countSlimBlue);
        updateTextView(getDocumentNameSlimGrey, countSlimGrey);
        updateTextView(getDocumentNameSlenderGreyKisa, countSlenderGrey);
        updateTextView(getDocumentNameWinsonGrey, countGrey);
        updateTextView(getDocumentNameDarkBlueKisa, countDarkBlueKisa);
        updateTextView(getDocumentNameDarkBlueUzun, countDarkBlueUzun);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameSlenderKisa, 0);
        firestoreCount(documentNameSlenderUzun, 0);
        firestoreCount(getDocumentNameBlueKisa, 0);
        firestoreCount(getDocumentNameBlueUzun, 0);
        firestoreCount(getDocumentNameRedKisa, 0);
        firestoreCount(getDocumentNameRedUzun, 0);
        firestoreCount(getDocumentNameSlimBlue, 0);
        firestoreCount(getDocumentNameSlimGrey, 0);
        firestoreCount(getDocumentNameSlenderGreyKisa, 0);
        firestoreCount(getDocumentNameWinsonGrey, 0);
        firestoreCount(getDocumentNameDarkBlueKisa, 0);
        firestoreCount(getDocumentNameDarkBlueUzun, 0);
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
        readFirestoreForDocument(documentNameSlenderKisa);
        readFirestoreForDocument(documentNameSlenderUzun);
        readFirestoreForDocument(getDocumentNameBlueKisa);
        readFirestoreForDocument(getDocumentNameBlueUzun);
        readFirestoreForDocument(getDocumentNameRedKisa);
        readFirestoreForDocument(getDocumentNameRedUzun);
        readFirestoreForDocument(getDocumentNameSlimBlue);
        readFirestoreForDocument(getDocumentNameSlimGrey);
        readFirestoreForDocument(getDocumentNameSlenderGreyKisa);
        readFirestoreForDocument(getDocumentNameWinsonGrey);
        readFirestoreForDocument(getDocumentNameDarkBlueKisa);
        readFirestoreForDocument(getDocumentNameDarkBlueUzun);
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
        if (documentName.equals(documentNameSlenderKisa)) {
            et_slender_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameSlenderUzun)) {
            et_slender_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameBlueKisa)) {
            et_winston_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameBlueUzun)) {
            et_winston_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameRedKisa)) {
            et_winston_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameRedUzun)) {
            et_winston_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameSlimBlue)) {
            et_winston_slim_blue.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameSlimGrey)) {
            et_winston_slim_grey.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameSlenderGreyKisa)) {
            et_slender_grey_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameWinsonGrey)) {
            et_winston_grey.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameDarkBlueKisa)) {
            et_winston_dark_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameDarkBlueUzun)) {
            et_winston_dark_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
        }

    }


    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameSlenderKisa)) {
            return countSlenderKisa;
        } else if (documentName.equals(documentNameSlenderUzun)) {
            return countSlenderUzun;
        } else if(documentName.equals(getDocumentNameBlueKisa)) {
            return countBlueKisa;
        } else if(documentName.equals(getDocumentNameBlueUzun)) {
            return countBlueUzun;
        } else if(documentName.equals(getDocumentNameRedKisa)) {
            return countRedKisa;
        } else if(documentName.equals(getDocumentNameRedUzun)) {
            return countRedUzun;
        } else if(documentName.equals(getDocumentNameSlimBlue)) {
            return countSlimBlue;
        } else if(documentName.equals(getDocumentNameSlimGrey)) {
            return countSlimGrey;
        } else if(documentName.equals(getDocumentNameSlenderGreyKisa)) {
            return countSlenderGrey;
        } else if(documentName.equals(getDocumentNameWinsonGrey)) {
            return countGrey;
        } else if(documentName.equals(getDocumentNameDarkBlueKisa)) {
            return countDarkBlueKisa;
        } else if(documentName.equals(getDocumentNameDarkBlueUzun)) {
            return countDarkBlueUzun;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
        }
        return 0;
    }


    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameSlenderKisa)) {
            countSlenderKisa = count;
        } else if (documentName.equals(documentNameSlenderUzun)) {
            countSlenderUzun = count;
        } else if (documentName.equals(getDocumentNameBlueKisa)) {
            countBlueKisa = count;
        } else if (documentName.equals(getDocumentNameBlueUzun)) {
            countBlueUzun = count;
        } else if (documentName.equals(getDocumentNameRedKisa)) {
            countRedKisa = count;
        } else if (documentName.equals(getDocumentNameRedUzun)) {
            countRedUzun = count;
        } else if (documentName.equals(getDocumentNameSlimBlue)) {
            countSlimBlue = count;
        } else if (documentName.equals(getDocumentNameSlimGrey)) {
            countSlimGrey = count;
        } else if (documentName.equals(getDocumentNameSlenderGreyKisa)) {
            countSlenderGrey = count;
        } else if (documentName.equals(getDocumentNameWinsonGrey)) {
            countGrey = count;
        } else if (documentName.equals(getDocumentNameDarkBlueKisa)) {
            countDarkBlueKisa = count;
        } else if (documentName.equals(getDocumentNameDarkBlueUzun)) {
            countDarkBlueUzun = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}
