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

public class ParliamentStocksActivity extends AppCompatActivity {

    private int countParliamentKisa = 0, countParliamentUzun = 0, countParliamentMidnight = 0;
    private int countParliamentReserve = 0, countParliamentSlims = 0, countParliamentAquaBlue = 0;
    private TextView tv_parliament_kisa, tv_parliament_uzun;
    private TextView tv_parliament_midnight, tv_parliament_reserve;
    private TextView tv_parliament_slims, tv_parliament_aqua_blue;

    private FirebaseFirestore db;
    FirebaseAuth auth;


    String documentNameParliamentKisa = "PM_Parliament_Kisa", documentNameParliamentUzun = "PM_Parliament_Uzun";
    String documentNameParliamentMidnight = "PM_Parliament_Midnight_Blue", documentNameParliamentReserve = "PM_Parliament_Reserve";
    String documentNameParliamentSlims = "PM_Parliament_Slims", documentNameParliamentAquaBlue = "PM_Parliament_Aqua_Blue";


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

        tv_parliament_kisa = findViewById(R.id.tv_parliament_kisa);
        tv_parliament_uzun = findViewById(R.id.tv_parliament_uzun);
        tv_parliament_midnight = findViewById(R.id.tv_parliament_midnight);
        tv_parliament_reserve = findViewById(R.id.tv_parliament_reserve);
        tv_parliament_slims = findViewById(R.id.tv_parliament_slims);
        tv_parliament_aqua_blue = findViewById(R.id.tv_parliament_aqua_blue);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        parliament_reset_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        parliament_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentKisa);
            }
        });

        parliament_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentKisa);
            }
        });

        //////////////////////

        parliament_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentUzun);
            }
        });

        parliament_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentUzun);
            }
        });

        //////////////////////

        parliament_midnight_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentMidnight);
            }
        });

        parliament_midnight_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentMidnight);
            }
        });

        //////////////////////


        parliament_reserve_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentReserve);
            }
        });


        parliament_reserve_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentReserve);
            }
        });

        //////////////////////

        parliament_slims_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentSlims);
            }
        });


        parliament_slims_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentSlims);
            }
        });

        //////////////////////

        parliament_aqua_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameParliamentAquaBlue);
            }
        });


        parliament_aqua_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameParliamentAquaBlue);
            }
        });

    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
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
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameParliamentKisa, 0);
        updateFirestore(documentNameParliamentUzun, 0);
        updateFirestore(documentNameParliamentMidnight, 0);
        updateFirestore(documentNameParliamentReserve, 0);
        updateFirestore(documentNameParliamentSlims, 0);
        updateFirestore(documentNameParliamentAquaBlue, 0);
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
        if (documentName.equals(documentNameParliamentKisa)) {
            tv_parliament_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentUzun)) {
            tv_parliament_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentMidnight)) {
            tv_parliament_midnight.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentReserve)) {
            tv_parliament_reserve.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentSlims)) {
            tv_parliament_slims.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameParliamentAquaBlue)) {
            tv_parliament_aqua_blue.setText(String.valueOf(count));
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