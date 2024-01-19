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

public class CamelStocksActivity extends AppCompatActivity {



    private int countCamelYellowKisa = 0, countCamelYellowUzun = 0, countCamelYellowSoftKisa = 0;
    private int  countCamelWhite = 0, countCamelBrown = 0, countCamelBlack = 0, countCamelDeepBlue = 0;
    private int countCamelSlenderBlue = 0, countCamelSlenderGrey = 0;
    private TextView tv_yellow_kisa, tv_yellow_uzun;
    private TextView tv_yellow_soft_kisa, tv_white;
    private TextView tv_black, tv_deep_blue;
    private TextView tv_brown, tv_slender_blue, tv_slender_grey;

    private FirebaseFirestore db;
    FirebaseAuth auth;


    String documentNameCamelYellowKisa = "JTI_Camel_Yellow_Kisa", documentNameCamelYellowUzun = "JTI_Camel_Yellow_Uzun";
    String documentNameCamelYellowSoftKisa = "JTI_Camel_Yellow_Soft_Kisa", documentNameCamelWhite = "JTI_Camel_White";
    String documentNameCamelBlack = "JTI_Camel_Black", documentNameCamelBrown = "JTI_Camel_Brown";
    String documentNameCamelDeepBlue = "JTI_Camel_Deep_Blue", documentNameCamelSlenderBlue = "JTI_Camel_Slender_Blue", documentNameCamelSlenderGrey = "JTI_Camel_Slender_Grey";



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

        tv_yellow_kisa = findViewById(R.id.tv_yellow_kisa);
        tv_yellow_uzun = findViewById(R.id.tv_yellow_uzun);
        tv_yellow_soft_kisa = findViewById(R.id.tv_yellow_soft_kisa);

        tv_white = findViewById(R.id.tv_white);
        tv_black = findViewById(R.id.tv_black);
        tv_deep_blue = findViewById(R.id.tv_deep_blue);
        tv_brown = findViewById(R.id.tv_brown);
        tv_slender_blue = findViewById(R.id.tv_slender_blue);
        tv_slender_grey = findViewById(R.id.tv_slender_grey);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_camel_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        camel_yellow_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelYellowKisa);
            }
        });

        camel_yellow_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelYellowKisa);
            }
        });

        //////////////////////

        camel_yellow_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelYellowUzun);
            }
        });

        camel_yellow_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelYellowUzun);
            }
        });

        //////////////////////

        camel_yellow_soft_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelYellowSoftKisa);
            }
        });

        camel_yellow_soft_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelYellowSoftKisa);
            }
        });

        //////////////////////


        camel_white_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelWhite);
            }
        });


        camel_white_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelWhite);
            }
        });

        //////////////////////

        camel_black_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelBlack);
            }
        });


        camel_black_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelBlack);
            }
        });

        //////////////////////

        camel_deep_blue_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelBrown);
            }
        });


        camel_deep_blue_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelBrown);
            }
        });

        //////////////////////

        camel_brown_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelDeepBlue);
            }
        });


        camel_brown_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelDeepBlue);
            }
        });

        //////////////////////

        camel_slender_blue_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelSlenderBlue);
            }
        });


        camel_slender_blue_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelSlenderBlue);
            }
        });

        camel_slender_grey_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameCamelSlenderGrey);
            }
        });


        camel_slender_grey_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameCamelSlenderGrey);
            }
        });
    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countCamelYellowKisa = 0;
        countCamelYellowUzun = 0;
        countCamelYellowSoftKisa = 0;
        countCamelWhite = 0;
        countCamelBrown = 0;
        countCamelBlack = 0;
        countCamelDeepBlue = 0;
        countCamelSlenderBlue = 0;
        countCamelSlenderGrey = 0;

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
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameCamelYellowKisa, 0);
        updateFirestore(documentNameCamelYellowUzun, 0);
        updateFirestore(documentNameCamelYellowSoftKisa, 0);
        updateFirestore(documentNameCamelWhite, 0);
        updateFirestore(documentNameCamelBlack, 0);
        updateFirestore(documentNameCamelBrown, 0);
        updateFirestore(documentNameCamelDeepBlue, 0);
        updateFirestore(documentNameCamelSlenderBlue, 0);
        updateFirestore(documentNameCamelSlenderGrey, 0);
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
        readFirestoreForDocument(documentNameCamelYellowKisa);
        readFirestoreForDocument(documentNameCamelYellowUzun);

        readFirestoreForDocument(documentNameCamelYellowSoftKisa);
        readFirestoreForDocument(documentNameCamelWhite);

        readFirestoreForDocument(documentNameCamelBlack);
        readFirestoreForDocument(documentNameCamelBrown);

        readFirestoreForDocument(documentNameCamelDeepBlue);
        readFirestoreForDocument(documentNameCamelSlenderBlue);
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
        if (documentName.equals(documentNameCamelYellowKisa)) {
            tv_yellow_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowUzun)) {
            tv_yellow_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelYellowSoftKisa)) {
            tv_yellow_soft_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelWhite)) {
            tv_white.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBlack)) {
            tv_black.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelBrown)) {
            tv_deep_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            tv_brown.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            tv_slender_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            tv_slender_grey.setText(String.valueOf(count));
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
        } else if(documentName.equals(documentNameCamelBlack)) {
            return countCamelBrown;
        } else if(documentName.equals(documentNameCamelBrown)) {
            return countCamelBlack;
        } else if(documentName.equals(documentNameCamelDeepBlue)) {
            return countCamelDeepBlue;
        } else if(documentName.equals(documentNameCamelSlenderBlue)) {
            return countCamelSlenderBlue;
        } else if(documentName.equals(documentNameCamelSlenderGrey)) {
            return countCamelSlenderGrey;
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
        } else if (documentName.equals(documentNameCamelBlack)) {
            countCamelBrown = count;
        } else if (documentName.equals(documentNameCamelBrown)) {
            countCamelBlack = count;
        } else if (documentName.equals(documentNameCamelDeepBlue)) {
            countCamelDeepBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderBlue)) {
            countCamelSlenderBlue = count;
        } else if (documentName.equals(documentNameCamelSlenderGrey)) {
            countCamelSlenderGrey = count;
        }
    }
}