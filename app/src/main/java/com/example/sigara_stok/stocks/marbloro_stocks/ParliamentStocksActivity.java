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

public class ParliamentStocksActivity extends AppCompatActivity {
    private int countParliamentKisa = 0, countParliamentUzun = 0, countParliamentMidnight = 0, countParliamentReserve = 0, countParliamentSlims = 0, countParliamentAquaBlue = 0;
    private EditText et_parliament_kisa, et_parliament_uzun, et_parliament_midnight, et_parliament_reserve, et_parliament_slims, et_parliament_aqua_blue;
    FirebaseFirestore db;
    FirebaseAuth auth;
    final static String documentNameParliamentKisa = "PM_Parliament_Kisa", documentNameParliamentUzun = "PM_Parliament_Uzun", documentNameParliamentMidnight = "PM_Parliament_Midnight_Blue", documentNameParliamentReserve = "PM_Parliament_Reserve", documentNameParliamentSlims = "PM_Parliament_Slims", documentNameParliamentAquaBlue = "PM_Parliament_Aqua_Blue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parliament_stocks);

        Button parliament_reset_all = findViewById(R.id.parliament_reset_all);
        Button parliament_kisa_azalt = findViewById(R.id.parliament_kisa_azalt);
        Button parliament_kisa_arttir = findViewById(R.id.parliament_kisa_arttir);
        Button parliament_uzun_azalt = findViewById(R.id.parliament_uzun_azalt);
        Button parliament_uzun_arttir = findViewById(R.id.parliament_uzun_arttir);
        Button parliament_midnight_azalt = findViewById(R.id.parliament_midnight_azalt);
        Button parliament_midnight_arttir = findViewById(R.id.parliament_midnight_arttir);
        Button parliament_reserve_azalt = findViewById(R.id.parliament_reserve_azalt);
        Button parliament_reserve_arttir = findViewById(R.id.parliament_reserve_arttir);
        Button parliament_slims_azalt = findViewById(R.id.parliament_slims_azalt);
        Button parliament_slims_arttir = findViewById(R.id.parliament_slims_arttir);
        Button parliament_aqua_azalt = findViewById(R.id.parliament_aqua_azalt);
        Button parliament_aqua_arttir = findViewById(R.id.parliament_aqua_arttir);

        Button updateValuesButton = findViewById(R.id.parliamentGuncelle);

        setParliamentButtonClickListeners(parliament_kisa_azalt, parliament_kisa_arttir, documentNameParliamentKisa);
        setParliamentButtonClickListeners(parliament_uzun_azalt, parliament_uzun_arttir, documentNameParliamentUzun);
        setParliamentButtonClickListeners(parliament_midnight_azalt, parliament_midnight_arttir, documentNameParliamentMidnight);
        setParliamentButtonClickListeners(parliament_reserve_azalt, parliament_reserve_arttir, documentNameParliamentReserve);
        setParliamentButtonClickListeners(parliament_slims_azalt, parliament_slims_arttir, documentNameParliamentSlims);
        setParliamentButtonClickListeners(parliament_aqua_azalt, parliament_aqua_arttir, documentNameParliamentAquaBlue);


        et_parliament_kisa = findViewById(R.id.et_parliament_kisa);
        et_parliament_uzun = findViewById(R.id.et_parliament_uzun);
        et_parliament_midnight = findViewById(R.id.et_parliament_midnight);
        et_parliament_reserve = findViewById(R.id.et_parliament_reserve);
        et_parliament_slims = findViewById(R.id.et_parliament_slims);
        et_parliament_aqua_blue = findViewById(R.id.et_parliament_aqua_blue);

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

        parliament_reset_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void setParliamentButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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

    private void discardStockCount() {
        et_parliament_kisa.setText(String.valueOf(countParliamentKisa));
        et_parliament_uzun.setText(String.valueOf(countParliamentUzun));

        et_parliament_midnight.setText(String.valueOf(countParliamentMidnight));
        et_parliament_reserve.setText(String.valueOf(countParliamentReserve));

        et_parliament_slims.setText(String.valueOf(countParliamentSlims));
        et_parliament_aqua_blue.setText(String.valueOf(countParliamentAquaBlue));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countParliamentKisa = Integer.parseInt(et_parliament_kisa.getText().toString());
        countParliamentUzun = Integer.parseInt(et_parliament_uzun.getText().toString());

        countParliamentMidnight = Integer.parseInt(et_parliament_midnight.getText().toString());
        countParliamentReserve = Integer.parseInt(et_parliament_reserve.getText().toString());

        countParliamentSlims = Integer.parseInt(et_parliament_slims.getText().toString());
        countParliamentAquaBlue = Integer.parseInt(et_parliament_aqua_blue.getText().toString());


        // TextView'ları güncelle
        updateTextView(documentNameParliamentKisa, countParliamentKisa);
        updateTextView(documentNameParliamentUzun, countParliamentUzun);

        updateTextView(documentNameParliamentMidnight, countParliamentMidnight);
        updateTextView(documentNameParliamentReserve, countParliamentReserve);

        updateTextView(documentNameParliamentSlims, countParliamentSlims);
        updateTextView(documentNameParliamentAquaBlue, countParliamentAquaBlue);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameParliamentKisa, countParliamentKisa);
        firestoreCount(documentNameParliamentUzun, countParliamentUzun);

        firestoreCount(documentNameParliamentMidnight, countParliamentMidnight);
        firestoreCount(documentNameParliamentReserve, countParliamentReserve);

        firestoreCount(documentNameParliamentSlims, countParliamentSlims);
        firestoreCount(documentNameParliamentAquaBlue, countParliamentAquaBlue);
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

    private void resetCounts() {
        countParliamentKisa = 0;
        countParliamentUzun = 0;
        countParliamentMidnight = 0;
        countParliamentReserve = 0;
        countParliamentSlims = 0;
        countParliamentAquaBlue = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameParliamentKisa, countParliamentKisa);
        updateTextView(documentNameParliamentUzun, countParliamentUzun);
        updateTextView(documentNameParliamentMidnight, countParliamentMidnight);
        updateTextView(documentNameParliamentReserve, countParliamentReserve);
        updateTextView(documentNameParliamentSlims, countParliamentSlims);
        updateTextView(documentNameParliamentAquaBlue, countParliamentAquaBlue);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameParliamentKisa, 0);
        firestoreCount(documentNameParliamentUzun, 0);
        firestoreCount(documentNameParliamentMidnight, 0);
        firestoreCount(documentNameParliamentReserve, 0);
        firestoreCount(documentNameParliamentSlims, 0);
        firestoreCount(documentNameParliamentAquaBlue, 0);
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
        readFirestoreForDocument(documentNameParliamentKisa);
        readFirestoreForDocument(documentNameParliamentUzun);

        readFirestoreForDocument(documentNameParliamentMidnight);
        readFirestoreForDocument(documentNameParliamentReserve);

        readFirestoreForDocument(documentNameParliamentSlims);
        readFirestoreForDocument(documentNameParliamentAquaBlue);
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
        if (documentName.equals(documentNameParliamentKisa)) {
            et_parliament_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentUzun)) {
            et_parliament_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentMidnight)) {
            et_parliament_midnight.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentReserve)) {
            et_parliament_reserve.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentSlims)) {
            et_parliament_slims.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentAquaBlue)) {
            et_parliament_aqua_blue.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameParliamentKisa)) {
            return countParliamentKisa;
        } else if (documentName.equals(documentNameParliamentUzun)) {
            return countParliamentUzun;
        } else if(documentName.equals(documentNameParliamentMidnight)) {
            return countParliamentMidnight;
        } else if(documentName.equals(documentNameParliamentReserve)) {
            return countParliamentReserve;
        } else if(documentName.equals(documentNameParliamentSlims)) {
            return countParliamentSlims;
        } else if(documentName.equals(documentNameParliamentAquaBlue)) {
            return countParliamentAquaBlue;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameParliamentKisa)) {
            countParliamentKisa = count;
        } else if (documentName.equals(documentNameParliamentUzun)) {
            countParliamentUzun = count;
        } else if (documentName.equals(documentNameParliamentMidnight)) {
            countParliamentMidnight = count;
        } else if (documentName.equals(documentNameParliamentReserve)) {
            countParliamentReserve = count;
        } else if (documentName.equals(documentNameParliamentSlims)) {
            countParliamentSlims = count;
        } else if (documentName.equals(documentNameParliamentAquaBlue)) {
            countParliamentAquaBlue = count;
        }
    }
}