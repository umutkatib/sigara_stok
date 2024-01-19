package com.example.sigara_stok.winston_stocks;

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

public class LDStocksActivity extends AppCompatActivity {




    private int countLdMaviKisa = 0, countLdMaviUzun = 0, countLdRedKisa = 0;
    private int countLdRedUzun = 0, countLdSlim = 0;
    private int countCamelSlenderBlue = 0, countCamelSlenderGrey = 0;
    private TextView tv_blue_kisa, tv_blue_uzun;
    private TextView tv_ld_red_kisa, tv_ld_red_uzun;
    private TextView tv_ld_slim, tv_deep_blue;
    private TextView tv_brown, tv_slender_blue, tv_slender_grey;

    private FirebaseFirestore db;
    FirebaseAuth auth;


    String documentNameLdBlueKisa = "JTI_Ld_Blue_Kisa", documentNameLdBlueUzun = "JTI_Ld_Blue_Uzun";
    String documentNameLdRedKisa = "JTI_Ld_Red_Kisa", documentNameLdRedUzun = "JTI_Ld_Red_Uzun";
    String documentNameLdSlim = "JTI_Ld_Slim";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldstocks);

        Button reset_all_ld_all_btn = findViewById(R.id.reset_all_ld_all_btn);

        Button ld_blue_kisa_azalt = findViewById(R.id.ld_blue_kisa_azalt);
        Button ld_blue_kisa_arttir = findViewById(R.id.ld_blue_kisa_arttir);
        Button ld_blue_uzun_azalt = findViewById(R.id.ld_blue_uzun_azalt);
        Button ld_blue_uzun_arttir = findViewById(R.id.ld_blue_uzun_arttir);

        Button ld_red_kisa_azalt = findViewById(R.id.ld_red_kisa_azalt);
        Button ld_red_kisa_arttir = findViewById(R.id.ld_red_kisa_arttir);
        Button ld_red_uzun_azalt = findViewById(R.id.ld_red_uzun_azalt);
        Button ld_red_uzun_arttir = findViewById(R.id.ld_red_uzun_arttir);

        Button ld_slim_azalt = findViewById(R.id.ld_slim_azalt);
        Button ld_slim_arttir = findViewById(R.id.ld_slim_arttir);


        tv_blue_kisa = findViewById(R.id.tv_blue_kisa);
        tv_blue_uzun = findViewById(R.id.tv_blue_uzun);
        tv_ld_red_kisa = findViewById(R.id.tv_ld_red_kisa);
        tv_ld_red_uzun = findViewById(R.id.tv_ld_red_uzun);
        tv_ld_slim = findViewById(R.id.tv_ld_slim);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_ld_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        ld_blue_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameLdBlueKisa);
            }
        });

        ld_blue_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameLdBlueKisa);
            }
        });

        //////////////////////

        ld_blue_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameLdBlueUzun);
            }
        });

        ld_blue_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameLdBlueUzun);
            }
        });

        //////////////////////

        ld_red_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameLdRedKisa);
            }
        });

        ld_red_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameLdRedKisa);
            }
        });

        //////////////////////


        ld_red_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameLdRedUzun);
            }
        });


        ld_red_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameLdRedUzun);
            }
        });

        //////////////////////

        ld_slim_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameLdSlim);
            }
        });


        ld_slim_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameLdSlim);
            }
        });
    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countLdMaviKisa = 0;
        countLdMaviUzun = 0;
        countLdRedKisa = 0;
        countLdRedUzun = 0;
        countLdSlim = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameLdBlueKisa, countLdMaviKisa);
        updateTextView(documentNameLdBlueUzun, countLdMaviUzun);
        updateTextView(documentNameLdRedKisa, countLdRedKisa);
        updateTextView(documentNameLdRedUzun, countLdRedUzun);
        updateTextView(documentNameLdSlim, countLdSlim);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameLdBlueKisa, 0);
        updateFirestore(documentNameLdBlueUzun, 0);
        updateFirestore(documentNameLdRedKisa, 0);
        updateFirestore(documentNameLdRedUzun, 0);
        updateFirestore(documentNameLdSlim, 0);
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
        readFirestoreForDocument(documentNameLdBlueKisa);
        readFirestoreForDocument(documentNameLdBlueUzun);

        readFirestoreForDocument(documentNameLdRedKisa);
        readFirestoreForDocument(documentNameLdRedUzun);

        readFirestoreForDocument(documentNameLdSlim);
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
        if (documentName.equals(documentNameLdBlueKisa)) {
            tv_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            tv_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdRedKisa)) {
            tv_ld_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdRedUzun)) {
            tv_ld_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameLdSlim)) {
            tv_ld_slim.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameLdBlueKisa)) {
            return countLdMaviKisa;
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            return countLdMaviUzun;
        } else if(documentName.equals(documentNameLdRedKisa)) {
            return countLdRedKisa;
        } else if(documentName.equals(documentNameLdRedUzun)) {
            return countLdRedUzun;
        } else if(documentName.equals(documentNameLdSlim)) {
            return countLdSlim;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameLdBlueKisa)) {
            countLdMaviKisa = count;
        } else if (documentName.equals(documentNameLdBlueUzun)) {
            countLdMaviUzun = count;
        } else if (documentName.equals(documentNameLdRedKisa)) {
            countLdRedKisa = count;
        } else if (documentName.equals(documentNameLdRedUzun)) {
            countLdRedUzun = count;
        } else if (documentName.equals(documentNameLdSlim)) {
            countLdSlim = count;
        }
    }
}