package com.example.sigara_stok.tekel_stocks;


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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RothmansStocksActivity extends AppCompatActivity {

    private int countRothmansBlueKisa = 0, countRothmansBlueUzun = 0;
    private int countRothmansDrangeBlackKisa = 0, countRothmansDrangeBlackUzun = 0;
    private int countRothmansDrangeBlueKisa = 0, countRothmansDrangeBlueUzun = 0;
    private TextView tv_rothmans_kisa, tv_rothmans_uzun;
    private TextView tv_drange_black_kisa, tv_drange_black_uzun;
    TextView tv_rothmans_drange_blue_kisa, tv_rothmans_drange_blue_arttir;


    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameRothamnsBlueKisa = "BAT_Rothmans_Kisa_Blue", documentNameRothamnsBlueUzun = "BAT_Rothmans_Uzun_Blue";
    final static String documentNameRothmansDrangeBlackKisa = "BAT_Rothmans_Drange_Kisa_Black", documentNameRothmansDrangeBlackUzun = "BAT_Rothmans_Drange_Uzun_Black";
    final static String documentNameRothmansDrangeBlueKisa = "BAT_Rothmans_Drange_Kisa_Blue", documentNameRothmansDrangeBlueUzun = "BAT_Rothmans_Drange_Uzun_Blue";

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


        setRothmansButtonClickListeners(rothmans_kisa_azalt_btn, rothmans_kisa_arttir_btn, documentNameRothamnsBlueKisa);
        setRothmansButtonClickListeners(rothmans_uzun_azalt_btn, rothmans_uzun_arttir_btn, documentNameRothamnsBlueUzun);
        setRothmansButtonClickListeners(drange_black_kisa_azalt_btn, drange_black_kisa_arttir_btn, documentNameRothmansDrangeBlackKisa);
        setRothmansButtonClickListeners(drange_black_uzun_azalt_btn, drange_black_uzun_arttir_btn, documentNameRothmansDrangeBlackUzun);
        setRothmansButtonClickListeners(rothmans_drange_blue_kisa_azalt_btn, rothmans_drange_black_kisa_arttir_btn, documentNameRothmansDrangeBlueKisa);
        setRothmansButtonClickListeners(rothmans_drange_blue_uzun_azalt_btn, rothmans_drange_blue_uzun_arttir_btn, documentNameRothmansDrangeBlueUzun);

        tv_rothmans_kisa = findViewById(R.id.tv_rothmans_kisa);
        tv_rothmans_uzun = findViewById(R.id.tv_rothmans_uzun);
        tv_drange_black_kisa = findViewById(R.id.tv_drange_black_kisa);
        tv_drange_black_uzun = findViewById(R.id.tv_drange_black_uzun);
        tv_rothmans_drange_blue_kisa = findViewById(R.id.tv_rothmans_drange_blue_kisa);
        tv_rothmans_drange_blue_arttir = findViewById(R.id.tv_rothmans_drange_blue_arttir);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        btn_reset_all_kent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
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


        // Tüm TextView'ları sıfırla
        updateTextView(documentNameRothamnsBlueKisa, countRothmansBlueKisa);
        updateTextView(documentNameRothamnsBlueUzun, countRothmansBlueUzun);
        updateTextView(documentNameRothmansDrangeBlackKisa, countRothmansDrangeBlackKisa);
        updateTextView(documentNameRothmansDrangeBlackUzun, countRothmansDrangeBlackUzun);
        updateTextView(documentNameRothmansDrangeBlueKisa, countRothmansDrangeBlueKisa);
        updateTextView(documentNameRothmansDrangeBlueUzun, countRothmansDrangeBlueUzun);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameRothamnsBlueKisa, 0);
        firestoreCount(documentNameRothamnsBlueUzun, 0);
        firestoreCount(documentNameRothmansDrangeBlackKisa, 0);
        firestoreCount(documentNameRothmansDrangeBlackUzun, 0);
        firestoreCount(documentNameRothmansDrangeBlueKisa, 0);
        firestoreCount(documentNameRothmansDrangeBlueUzun, 0);
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
                    }
                });
    }


    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameRothamnsBlueKisa)) {
            tv_rothmans_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothamnsBlueUzun)) {
            tv_rothmans_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackKisa)) {
            tv_drange_black_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlackUzun)) {
            tv_drange_black_uzun.setText(String.valueOf(count));
        }else if (documentName.equals(documentNameRothmansDrangeBlueKisa)) {
            tv_rothmans_drange_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameRothmansDrangeBlueUzun)) {
            tv_rothmans_drange_blue_arttir.setText(String.valueOf(count));
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
        }
    }
}