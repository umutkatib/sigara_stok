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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class KentStocksActivity extends AppCompatActivity {


    private int countKentDrangeBlueKisa = 0, countKentDrangeBlueUzun = 0;
    private int countKentDrangeGreyKisa = 0, countKentDrangeGreyUzun = 0;
    private int countKentDarkBlueKisa = 0, countKentDarkBlueUzun = 0;
    private int countKentBlueKisa = 0, countKentSwitchKisa = 0;
    private TextView tv_drange_blue_kisa, tv_drange_blue_uzun;
    private TextView tv_drange_grey_kisa, tv_drange_grey_uzun;
    private TextView tv_kent_dark_blue_kisa, tv_kent_dark_blue_uzun;
    private TextView tv_kent_blue_kisa, tv_kent_switch_kisa;

    private FirebaseFirestore db;

    String documentNameKentDrangeBlueKisa = "kent_drange_blue_kisa", documentNameKentDrangeBlueUzun = "kent_drange_blue_uzun";
    String documentNameKentDrangeGreyKisa = "kent_drange_grey_kisa", documentNameKentDrangeGreyUzun = "kent_drange_grey_uzun";
    String documentNameKentDarkBlueKisa = "kent_dark_blue_kisa", documentNameKentDarkBlueUzun = "kent_dark_blue_uzun";
    String documentNameKentBlueKisa = "kent_blue_kisa", documentNameKentSwitchKisa = "kent_switch_kisa";



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

        tv_drange_blue_kisa = findViewById(R.id.tv_drange_blue_kisa);
        tv_drange_blue_uzun = findViewById(R.id.tv_drange_blue_uzun);

        tv_drange_grey_kisa = findViewById(R.id.tv_drange_grey_kisa);
        tv_drange_grey_uzun = findViewById(R.id.tv_drange_grey_uzun);

        tv_kent_dark_blue_kisa = findViewById(R.id.tv_kent_dark_blue_kisa);
        tv_kent_dark_blue_uzun = findViewById(R.id.tv_kent_dark_blue_uzun);

        tv_kent_blue_kisa = findViewById(R.id.tv_kent_blue_kisa);
        tv_kent_switch_kisa = findViewById(R.id.tv_kent_switch_kisa);

        db = FirebaseFirestore.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        btn_reset_all_kent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        drange_blue_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDrangeBlueKisa);
            }
        });

        drange_blue_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDrangeBlueKisa);
            }
        });

        //////////////////////

        drange_blue_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDrangeBlueUzun);
            }
        });

        drange_blue_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDrangeBlueUzun);
            }
        });

        //////////////////////

        drange_grey_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDrangeGreyKisa);
            }
        });

        drange_grey_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDrangeGreyKisa);
            }
        });

        //////////////////////


        drange_grey_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDrangeGreyUzun);
            }
        });


        drange_grey_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDrangeGreyUzun);
            }
        });

        //////////////////////

        kent_dark_blue_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDarkBlueKisa);
            }
        });


        kent_dark_blue_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDarkBlueKisa);
            }
        });

        //////////////////////

        kent_dark_blue_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentDarkBlueUzun);
            }
        });


        kent_dark_blue_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentDarkBlueUzun);
            }
        });

        //////////////////////

        kent_blue_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentBlueKisa);
            }
        });


        kent_blue_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentBlueKisa);
            }
        });

        //////////////////////

        kent_switch_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameKentSwitchKisa);
            }
        });


        kent_switch_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameKentSwitchKisa);
            }
        });

    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countKentDrangeBlueKisa = 0;
        countKentDrangeBlueUzun = 0;
        countKentDrangeGreyKisa = 0;
        countKentDrangeGreyUzun = 0;
        countKentDarkBlueKisa = 0;
        countKentDarkBlueUzun = 0;
        countKentBlueKisa = 0;
        countKentSwitchKisa = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameKentDrangeBlueKisa, countKentDrangeBlueKisa);
        updateTextView(documentNameKentDrangeBlueUzun, countKentDrangeBlueUzun);
        updateTextView(documentNameKentDrangeGreyKisa, countKentDrangeGreyKisa);
        updateTextView(documentNameKentDrangeGreyUzun, countKentDrangeGreyUzun);
        updateTextView(documentNameKentDarkBlueKisa, countKentDarkBlueKisa);
        updateTextView(documentNameKentDarkBlueUzun, countKentDarkBlueUzun);
        updateTextView(documentNameKentBlueKisa, countKentBlueKisa);
        updateTextView(documentNameKentSwitchKisa, countKentSwitchKisa);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameKentDrangeBlueKisa, 0);
        updateFirestore(documentNameKentDrangeBlueUzun, 0);
        updateFirestore(documentNameKentDrangeGreyKisa, 0);
        updateFirestore(documentNameKentDrangeGreyUzun, 0);
        updateFirestore(documentNameKentDarkBlueKisa, 0);
        updateFirestore(documentNameKentDarkBlueUzun, 0);
        updateFirestore(documentNameKentBlueKisa, 0);
        updateFirestore(documentNameKentSwitchKisa, 0);
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

            // Koleksiyon adını belirleyin
            String userCollectionName = "market_" + uid;
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
        readFirestoreForDocument(documentNameKentDrangeBlueKisa);
        readFirestoreForDocument(documentNameKentDrangeBlueUzun);

        readFirestoreForDocument(documentNameKentDrangeGreyKisa);
        readFirestoreForDocument(documentNameKentDrangeGreyUzun);

        readFirestoreForDocument(documentNameKentDarkBlueKisa);
        readFirestoreForDocument(documentNameKentDarkBlueUzun);

        readFirestoreForDocument(documentNameKentBlueKisa);
        readFirestoreForDocument(documentNameKentSwitchKisa);
    }

    private void readFirestoreForDocument(String documentName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        // Koleksiyon adını belirleyin
        String userCollectionName = "market_" + uid;
        CollectionReference userCollectionRef = db.collection(userCollectionName);

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
        if (documentName.equals(documentNameKentDrangeBlueKisa)) {
            tv_drange_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeBlueUzun)) {
            tv_drange_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeGreyKisa)) {
            tv_drange_grey_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDrangeGreyUzun)) {
            tv_drange_grey_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDarkBlueKisa)) {
            tv_kent_dark_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentDarkBlueUzun)) {
            tv_kent_dark_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentBlueKisa)) {
            tv_kent_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameKentSwitchKisa)) {
            tv_kent_switch_kisa.setText(String.valueOf(count));
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
        }
    }
}