package com.example.sigara_stok.tekel_stocks;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TekelStocksActivity extends AppCompatActivity {

    private int countTekel2000MaviKisa = 0, countTekel2000MaviUzun = 0;
    private int countTekel2000KirmiziKisa = 0, countTekel2000KirmiziUzun = 0;
    private int countTekel2001MaviKisa = 0, countTekel2001MaviUzun = 0;
    private int countTekel2001KirmiziKisa = 0, countTekel2001KirmiziUzun = 0;
    private TextView tv_2000_kisa_mavi, tv_2000_uzun_mavi;
    private TextView tv_2000_kirmizi_kisa, tv_2000_kirmizi_uzun;
    private TextView tv_2001_mavi_kisa, tv_2001_mavi_uzun;
    private TextView tv_2001_kirmizi_kisa, tv_2001_kirmizi_uzun;

    FirebaseFirestore db;

    final static String documentNameTekel2000MaviKisa = "BAT_Tekel_2000_Mavi_Kisa", documentNameTekel2000MaviUzun = "BAT_Tekel_2000_Mavi_Uzun";
    final static String documentNameTekel2000KirmiziKisa = "BAT_Tekel_2000_kirmizi_Kisa", documentNameTekel2000KirmiziUzun = "BAT_Tekel_2000_kirmizi_Uzun";
    final static String documentNameTekel2001MaviKisa = "BAT_Tekel_2001_Mavi_Kisa", documentNameTekel2001MaviUzun = "BAT_Tekel_2001_Mavi_Uzun";
    final static String documentNameTekel2001KirmiziKisa = "BAT_Tekel_2001_kirmizi_Kisa", documentNameTekel2001KirmiziUzun = "BAT_Tekel_2001_kirmizi_Uzun";

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekel_stocks);

        Button reset_all_tekel_btn = findViewById(R.id.reset_all_tekel_btn);

        Button mavi_2000_kisa_azalt_btn = findViewById(R.id.mavi_2000_kisa_azalt_btn);
        Button mavi_2000_kisa_arttir_btn = findViewById(R.id.mavi_2000_kisa_arttir_btn);
        Button mavi_2000_uzun_azalt_btn = findViewById(R.id.mavi_2000_uzun_azalt_btn);
        Button mavi_2000_uzun_arttir_btn = findViewById(R.id.mavi_2000_uzun_arttir_btn);
        Button kirmizi_2000_kisa_azalt_btn = findViewById(R.id.kirmizi_2000_kisa_azalt_btn);
        Button kirmizi_2000_kisa_arttir_btn = findViewById(R.id.kirmizi_2000_kisa_arttir_btn);
        Button kirmizi_2000_uzun_azalt_btn = findViewById(R.id.kirmizi_2000_uzun_azalt_btn);
        Button kirmizi_2000_uzun_arttir_btn = findViewById(R.id.kirmizi_2000_uzun_arttir_btn);
        Button mavi_2001_kisa_azalt_btn = findViewById(R.id.mavi_2001_kisa_azalt_btn);
        Button mavi_2001_kisa_arttir_btn = findViewById(R.id.mavi_2001_kisa_arttir_btn);
        Button mavi_2001_uzun_azalt_btn = findViewById(R.id.mavi_2001_uzun_azalt_btn);
        Button mavi_2001_uzun_arttir_btn = findViewById(R.id.mavi_2001_uzun_arttir_btn);
        Button kirmizi_2001_kisa_azalt_btn = findViewById(R.id.kirmizi_2001_kisa_azalt_btn);
        Button kirmizi_2001_kisa_arttir_btn = findViewById(R.id.kirmizi_2001_kisa_arttir_btn);
        Button kirmizi_2001_uzun_azalt_btn = findViewById(R.id.kirmizi_2001_uzun_azalt_btn);
        Button kirmizi_2001_uzun_arttir_btn = findViewById(R.id.kirmizi_2001_uzun_arttir_btn);


        setTekelButtonClickListeners(mavi_2000_kisa_azalt_btn, mavi_2000_kisa_arttir_btn, documentNameTekel2000MaviKisa);
        setTekelButtonClickListeners(mavi_2000_uzun_azalt_btn, mavi_2000_uzun_arttir_btn, documentNameTekel2000MaviUzun);
        setTekelButtonClickListeners(kirmizi_2000_kisa_azalt_btn, kirmizi_2000_kisa_arttir_btn, documentNameTekel2000KirmiziKisa);
        setTekelButtonClickListeners(kirmizi_2000_uzun_azalt_btn, kirmizi_2000_uzun_arttir_btn, documentNameTekel2000KirmiziUzun);
        setTekelButtonClickListeners(mavi_2001_kisa_azalt_btn, mavi_2001_kisa_arttir_btn, documentNameTekel2001MaviKisa);
        setTekelButtonClickListeners(mavi_2001_uzun_azalt_btn, mavi_2001_uzun_arttir_btn, documentNameTekel2001MaviUzun);
        setTekelButtonClickListeners(kirmizi_2001_kisa_azalt_btn, kirmizi_2001_kisa_arttir_btn, documentNameTekel2001KirmiziKisa);
        setTekelButtonClickListeners(kirmizi_2001_uzun_azalt_btn, kirmizi_2001_uzun_arttir_btn, documentNameTekel2001KirmiziUzun);


        tv_2000_kisa_mavi = findViewById(R.id.tv_2000_kisa_mavi);
        tv_2000_uzun_mavi = findViewById(R.id.tv_2000_uzun_mavi);
        tv_2000_kirmizi_kisa = findViewById(R.id.tv_2000_kirmizi_kisa);
        tv_2000_kirmizi_uzun = findViewById(R.id.tv_2000_kirmizi_uzun);
        tv_2001_mavi_kisa = findViewById(R.id.tv_2001_mavi_kisa);
        tv_2001_mavi_uzun = findViewById(R.id.tv_2001_mavi_uzun);
        tv_2001_kirmizi_kisa = findViewById(R.id.tv_2001_kirmizi_kisa);
        tv_2001_kirmizi_uzun = findViewById(R.id.tv_2001_kirmizi_uzun);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_tekel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void setTekelButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countTekel2000MaviKisa = 0;
        countTekel2000MaviUzun = 0;
        countTekel2000KirmiziKisa = 0;
        countTekel2000KirmiziUzun = 0;
        countTekel2001MaviKisa = 0;
        countTekel2001MaviUzun = 0;
        countTekel2001KirmiziKisa = 0;
        countTekel2001KirmiziUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameTekel2000MaviKisa, countTekel2000MaviKisa);
        updateTextView(documentNameTekel2000MaviUzun, countTekel2000MaviUzun);
        updateTextView(documentNameTekel2000KirmiziKisa, countTekel2000KirmiziKisa);
        updateTextView(documentNameTekel2000KirmiziUzun, countTekel2000KirmiziUzun);
        updateTextView(documentNameTekel2001MaviKisa, countTekel2001MaviKisa);
        updateTextView(documentNameTekel2001MaviUzun, countTekel2001MaviUzun);
        updateTextView(documentNameTekel2001KirmiziKisa, countTekel2001KirmiziKisa);
        updateTextView(documentNameTekel2001KirmiziUzun, countTekel2001KirmiziUzun);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameTekel2000MaviKisa, 0);
        firestoreCount(documentNameTekel2000MaviUzun, 0);
        firestoreCount(documentNameTekel2000KirmiziKisa, 0);
        firestoreCount(documentNameTekel2000KirmiziUzun, 0);
        firestoreCount(documentNameTekel2001MaviKisa, 0);
        firestoreCount(documentNameTekel2001MaviUzun, 0);
        firestoreCount(documentNameTekel2001KirmiziKisa, 0);
        firestoreCount(documentNameTekel2001KirmiziUzun, 0);
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
        readFirestoreForDocument(documentNameTekel2000MaviKisa);
        readFirestoreForDocument(documentNameTekel2000MaviUzun);

        readFirestoreForDocument(documentNameTekel2000KirmiziKisa);
        readFirestoreForDocument(documentNameTekel2000KirmiziUzun);

        readFirestoreForDocument(documentNameTekel2001MaviKisa);
        readFirestoreForDocument(documentNameTekel2001MaviUzun);

        readFirestoreForDocument(documentNameTekel2001KirmiziKisa);
        readFirestoreForDocument(documentNameTekel2001KirmiziUzun);
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
        if (documentName.equals(documentNameTekel2000MaviKisa)) {
            tv_2000_kisa_mavi.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2000MaviUzun)) {
            tv_2000_uzun_mavi.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2000KirmiziKisa)) {
            tv_2000_kirmizi_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2000KirmiziUzun)) {
            tv_2000_kirmizi_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2001MaviKisa)) {
            tv_2001_mavi_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2001MaviUzun)) {
            tv_2001_mavi_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2001KirmiziKisa)) {
            tv_2001_kirmizi_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTekel2001KirmiziUzun)) {
            tv_2001_kirmizi_uzun.setText(String.valueOf(count));
        }

    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameTekel2000MaviKisa)) {
            return countTekel2000MaviKisa;
        } else if (documentName.equals(documentNameTekel2000MaviUzun)) {
            return countTekel2000MaviUzun;
        } else if(documentName.equals(documentNameTekel2000KirmiziKisa)) {
            return countTekel2000KirmiziKisa;
        } else if(documentName.equals(documentNameTekel2000KirmiziUzun)) {
            return countTekel2000KirmiziUzun;
        } else if(documentName.equals(documentNameTekel2001MaviKisa)) {
            return countTekel2001MaviKisa;
        } else if(documentName.equals(documentNameTekel2001MaviUzun)) {
            return countTekel2001MaviUzun;
        } else if(documentName.equals(documentNameTekel2001KirmiziKisa)) {
            return countTekel2001KirmiziKisa;
        } else if(documentName.equals(documentNameTekel2001KirmiziUzun)) {
            return countTekel2001KirmiziUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameTekel2000MaviKisa)) {
            countTekel2000MaviKisa = count;
        } else if (documentName.equals(documentNameTekel2000MaviUzun)) {
            countTekel2000MaviUzun = count;
        } else if (documentName.equals(documentNameTekel2000KirmiziKisa)) {
            countTekel2000KirmiziKisa = count;
        } else if (documentName.equals(documentNameTekel2000KirmiziUzun)) {
            countTekel2000KirmiziUzun = count;
        } else if (documentName.equals(documentNameTekel2001MaviKisa)) {
            countTekel2001MaviKisa = count;
        } else if (documentName.equals(documentNameTekel2001MaviUzun)) {
            countTekel2001MaviUzun = count;
        } else if (documentName.equals(documentNameTekel2001KirmiziKisa)) {
            countTekel2001KirmiziKisa = count;
        } else if (documentName.equals(documentNameTekel2001KirmiziUzun)) {
            countTekel2001KirmiziUzun = count;
        }
    }
}