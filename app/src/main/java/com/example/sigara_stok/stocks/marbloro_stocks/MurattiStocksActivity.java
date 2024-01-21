package com.example.sigara_stok.stocks.marbloro_stocks;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MurattiStocksActivity extends AppCompatActivity {

    private int countLarkKisa = 0, countLarkUzun = 0;
    private TextView tv_rosso, tv_blue_line;

    private FirebaseFirestore db;
    FirebaseAuth auth;


    String documentNameMCDarkBlueKisa = "PM_Muratti_Rosso", documentNameMCDarkBlueUzun = "PM_Muratti_Blu_Line";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muratti_stocks);

        Button reset_all_muratti = findViewById(R.id.reset_all_muratti);

        Button rosso_azalt = findViewById(R.id.rosso_azalt);
        Button rosso_arttir = findViewById(R.id.rosso_arttir);
        Button blue_line_azalt = findViewById(R.id.blue_line_azalt);
        Button blue_line_arttir = findViewById(R.id.blue_line_arttir);

        tv_rosso = findViewById(R.id.tv_rosso);
        tv_blue_line = findViewById(R.id.tv_blue_line);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_muratti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        rosso_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkBlueKisa);
            }
        });

        rosso_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkBlueKisa);
            }
        });

        //////////////////////

        blue_line_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkBlueUzun);
            }
        });

        blue_line_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkBlueUzun);
            }
        });


    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countLarkKisa = 0;
        countLarkUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameMCDarkBlueKisa, countLarkKisa);
        updateTextView(documentNameMCDarkBlueUzun, countLarkUzun);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameMCDarkBlueKisa, 0);
        updateFirestore(documentNameMCDarkBlueUzun, 0);
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
        updateFirestore(documentName, getCount(documentName) + 1);
    }

    private void decrementCount(String documentName) {
        // Eğer mevcut değer 0'dan büyükse azalt ve güncelle
        if (getCount(documentName) > 0) {
            updateFirestore(documentName, getCount(documentName) - 1);
        }
    }

    private void updateFirestore(String documentName, int count) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            FirebaseUser currentUser = auth.getCurrentUser();
            String userEmail = currentUser.getEmail();
            String[] parts = userEmail.split("@");
            String userName = parts[0];

            // Koleksiyon adını belirleyin
            String userCollectionName = userName + "_market_" + uid;
            CollectionReference userCollectionRef = db.collection(userCollectionName);

            // Yeni belge eklemek için haritaları oluşturun
            Map<String, Object> userData = new HashMap<>();
            userData.put("stock", 0);

            userCollectionRef.document(documentName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Belge mevcut, güncelle
                                    userCollectionRef.document(documentName)
                                            .update("stock", count)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    setCount(documentName, count);
                                                    // TextView'i güncelle
                                                    updateTextView(documentName, count);
                                                    Log.d("TAG333", documentName + " belgesi başarıyla güncellendi.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG444", documentName + " belgesi güncellenirken hata oluştu", e);
                                                }
                                            });
                                } else {
                                    // Belge mevcut değil, yeni belge ekle
                                    userCollectionRef.document(documentName)
                                            .set(userData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    setCount(documentName, count);
                                                    // TextView'i güncelle
                                                    updateTextView(documentName, count);
                                                    Log.d("TAG333", documentName + " belgesi başarıyla eklendi.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG444", documentName + " belgesi eklenirken hata oluştu", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d("TAG555", "Belge varlık durumu kontrolü başarısız oldu.", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Stok Güncelleme Başarısız..", Toast.LENGTH_SHORT).show();
        }
    }


    private void readFirestore() {
        // Her iki belgeyi de oku
        readFirestoreForDocument(documentNameMCDarkBlueKisa);
        readFirestoreForDocument(documentNameMCDarkBlueUzun);

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
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            tv_rosso.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            tv_blue_line.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            return countLarkKisa;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            return countLarkUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            countLarkKisa = count;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            countLarkUzun = count;
        }
    }
}