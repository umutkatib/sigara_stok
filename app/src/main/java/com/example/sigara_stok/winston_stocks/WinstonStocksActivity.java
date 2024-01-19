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

public class WinstonStocksActivity extends AppCompatActivity {

    private int countSlenderKisa = 0, countSlenderUzun = 0;
    private int countBlueKisa = 0, countBlueUzun = 0;
    private int countRedKisa = 0, countRedUzun = 0;
    private int countSlimBlue = 0, countSlimGrey = 0;
    private TextView tv_winston_slender_kisa, tv_winston_slender_uzun;
    private TextView tv_winston_blue_kisa, tv_winston_blue_uzun;
    private TextView tv_winston_red_kisa, tv_winston_red_uzun;
    private TextView tv_winston_slim_blue, tv_winston_slim_grey;

    private FirebaseFirestore db;
    FirebaseAuth auth;

    String documentNameSlenderKisa = "JTI_Slender_Blue_Kisa", documentNameSlenderUzun = "JTI_Slender_Blue_Uzun";
    String getDocumentNameBlueKisa = "JTI_Blue_Kisa", getDocumentNameBlueUzun = "JTI_Blue_Uzun";
    String getDocumentNameRedKisa = "JTI_Red_Kisa", getDocumentNameRedUzun = "JTI_Red_Uzun";
    String getDocumentNameSlimBlue = "JTI_Slims_Blue", getDocumentNameSlimGrey = "JTI_Slims_Grey";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winston_stocks);

        Button resetAllButton = findViewById(R.id.reset_all_btn);

        Button slender_kisa_azalt_btn = findViewById(R.id.slender_kisa_azalt_btn);
        Button slender_kisa_arttir_btn = findViewById(R.id.tekel_2000_kirmizi_uzun_arttir_btn);
        Button slender_uzun_azalt_btn = findViewById(R.id.tekel_2000_kirmizi_kisa_azalt_btn);
        Button slender_uzun_arttir_btn = findViewById(R.id.slender_uzun_arttir_btn);

        Button blue_kisa_azalt_btn = findViewById(R.id.blue_kisa_azalt_btn);
        Button blue_kisa_arttir_btn = findViewById(R.id.blue_kisa_arttir_btn);
        Button blue_uzun_azalt_btn = findViewById(R.id.blue_uzun_azalt_btn);
        Button blue_uzun_arttır_btn = findViewById(R.id.blue_uzun_arttır_btn);

        Button red_kisa_azalt_btn = findViewById(R.id.red_kisa_azalt_btn);
        Button red_kisa_arttir_btn = findViewById(R.id.red_kisa_arttir_btn);
        Button red_uzun_azalt_btn = findViewById(R.id.red_uzun_azalt_btn);
        Button red_uzun_arttir_btn = findViewById(R.id.red_uzun_arttir_btn);

        Button slim_blue_azalt_btn = findViewById(R.id.slim_blue_azalt_btn);
        Button slim_blue_arttir_btn = findViewById(R.id.slim_blue_arttir_btn);
        Button slim_grey_azalt_btn = findViewById(R.id.slim_grey_azalt_btn);
        Button slim_grey_arttir_btn = findViewById(R.id.slim_grey_arttir_btn);

        tv_winston_slender_kisa = findViewById(R.id.tv_winston_slender_kisa);
        tv_winston_slender_uzun = findViewById(R.id.tv_tekel_2001_mavi_uzun);

        tv_winston_blue_kisa = findViewById(R.id.tv_winston_blue_kisa);
        tv_winston_blue_uzun = findViewById(R.id.tv_winston_blue_uzun);

        tv_winston_red_kisa = findViewById(R.id.tv_winston_red_kisa);
        tv_winston_red_uzun = findViewById(R.id.tv_winston_red_uzun);

        tv_winston_slim_blue = findViewById(R.id.tv_winston_slim_blue);
        tv_winston_slim_grey = findViewById(R.id.tv_winston_slim_grey);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();


        resetAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        slender_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameSlenderKisa);
            }
        });

        slender_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameSlenderKisa);
            }
        });

        //////////////////////

        slender_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameSlenderUzun);
            }
        });

        slender_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameSlenderUzun);
            }
        });

        //////////////////////

        blue_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameBlueKisa);
            }
        });

        blue_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameBlueKisa);
            }
        });

        //////////////////////


        blue_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameBlueUzun);
            }
        });


        blue_uzun_arttır_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameBlueUzun);
            }
        });

        //////////////////////

        red_kisa_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameRedKisa);
            }
        });


        red_kisa_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameRedKisa);
            }
        });

        //////////////////////

        red_uzun_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameRedUzun);
            }
        });


        red_uzun_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameRedUzun);
            }
        });

        //////////////////////

        slim_blue_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameSlimBlue);
            }
        });


        slim_blue_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameSlimBlue);
            }
        });

        //////////////////////

        slim_grey_azalt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(getDocumentNameSlimGrey);
            }
        });


        slim_grey_arttir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(getDocumentNameSlimGrey);
            }
        });

    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countSlenderKisa = 0;
        countSlenderUzun = 0;
        countBlueKisa = 0;
        countBlueUzun = 0;
        countRedKisa = 0;
        countRedUzun = 0;
        countSlimBlue = 0;
        countSlimGrey = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameSlenderKisa, countSlenderKisa);
        updateTextView(documentNameSlenderUzun, countSlenderUzun);
        updateTextView(getDocumentNameBlueKisa, countBlueKisa);
        updateTextView(getDocumentNameBlueUzun, countBlueUzun);
        updateTextView(getDocumentNameRedKisa, countRedKisa);
        updateTextView(getDocumentNameRedUzun, countRedUzun);
        updateTextView(getDocumentNameSlimBlue, countSlimBlue);
        updateTextView(getDocumentNameSlimGrey, countSlimGrey);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameSlenderKisa, 0);
        updateFirestore(documentNameSlenderUzun, 0);
        updateFirestore(getDocumentNameBlueKisa, 0);
        updateFirestore(getDocumentNameBlueUzun, 0);
        updateFirestore(getDocumentNameRedKisa, 0);
        updateFirestore(getDocumentNameRedUzun, 0);
        updateFirestore(getDocumentNameSlimBlue, 0);
        updateFirestore(getDocumentNameSlimGrey, 0);
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

            FirebaseUser currentUser = auth.getCurrentUser();
            String userEmail = currentUser.getEmail();
            String[] parts = userEmail.split("@");
            String userName = parts[0];

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
        readFirestoreForDocument(documentNameSlenderKisa);
        readFirestoreForDocument(documentNameSlenderUzun);

        readFirestoreForDocument(getDocumentNameBlueKisa);
        readFirestoreForDocument(getDocumentNameBlueUzun);

        readFirestoreForDocument(getDocumentNameRedKisa);
        readFirestoreForDocument(getDocumentNameRedUzun);

        readFirestoreForDocument(getDocumentNameSlimBlue);
        readFirestoreForDocument(getDocumentNameSlimGrey);
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
        if (documentName.equals(documentNameSlenderKisa)) {
            tv_winston_slender_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameSlenderUzun)) {
            tv_winston_slender_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameBlueKisa)) {
            tv_winston_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameBlueUzun)) {
            tv_winston_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameRedKisa)) {
            tv_winston_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameRedUzun)) {
            tv_winston_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameSlimBlue)) {
            tv_winston_slim_blue.setText(String.valueOf(count));
        } else if (documentName.equals(getDocumentNameSlimGrey)) {
            tv_winston_slim_grey.setText(String.valueOf(count));
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
        }
    }
}
