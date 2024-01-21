package com.example.sigara_stok.activities;

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

public class HdActivity extends AppCompatActivity {

    private int countHDBlueKisa = 0, countHDBlueUzun = 0, countHDSlimBlue = 0;
    private int countHDWhiteLine = 0, countTorosBlueKisa = 0, countTorosBlueUzun = 0, countTorosRedKisa = 0, countTorosRedUzun = 0;
    private TextView tv_hd_blue_kisa, tv_hd_blue_uzun;
    private TextView tv_hd_slim_blue, tv_hd_white_line;
    private TextView tv_toros_kisa_blue, tv_toros_uzun_blue;
    private TextView tv_toros_kisa_red, tv_toros_uzun_red;

    private FirebaseFirestore db;
    FirebaseAuth auth;


    String documentNameMCDarkBlueKisa = "HD_Blue_Kisa", documentNameMCDarkBlueUzun = "HD_Blue_Uzun";
    String documentNameMCDarkRedKisa = "HD_Slim_Blue", documentNameMCDarkRedUzun = "HD_White_Line";
    String documentNameTorosBlueKisa = "HD_Toros_Blue_Kisa", documentNameTorosBlueUzun = "HD_Toros_Blue_Uzun";
    String documentNameTorosRedKisa = "HD_Toros_Red_Kisa", documentNameTorosRedUzun = "HD_Toros_Red_Uzun";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd);

        Button reset_all_hd = findViewById(R.id.reset_all_hd);

        Button hd_kisa_azalt = findViewById(R.id.hd_kisa_azalt);
        Button hd_kisa_arttir = findViewById(R.id.hd_kisa_arttir);
        Button hd_uzun_azalt = findViewById(R.id.hd_uzun_azalt);
        Button hd_uzun_arttir = findViewById(R.id.hd_uzun_arttir);
        Button slim_blue_azalt = findViewById(R.id.slim_blue_azalt);
        Button slim_blue_arttir = findViewById(R.id.slim_blue_arttir);

        Button white_line_azalt = findViewById(R.id.white_line_azalt);
        Button white_line_arttir = findViewById(R.id.white_line_arttir);

        Button toros_blue_kisa_azalt = findViewById(R.id.toros_blue_kisa_azalt);
        Button toros_blue_kisa_arttir = findViewById(R.id.toros_blue_kisa_arttir);
        Button toros_blue_uzun_azalt = findViewById(R.id.toros_blue_uzun_azalt);
        Button toros_blue_uzun_arttir = findViewById(R.id.toros_blue_uzun_arttir);
        Button toros_red_kisa_azalt = findViewById(R.id.toros_red_kisa_azalt);
        Button toros_red_kisa_arttir = findViewById(R.id.toros_red_kisa_arttir);
        Button toros_red_uzun_azalt = findViewById(R.id.toros_red_uzun_azalt);
        Button toros_red_uzun_arttir = findViewById(R.id.toros_red_uzun_arttir);

        tv_hd_blue_kisa = findViewById(R.id.tv_hd_blue_kisa);
        tv_hd_blue_uzun = findViewById(R.id.tv_hd_blue_uzun);
        tv_hd_slim_blue = findViewById(R.id.tv_hd_slim_blue);


        tv_hd_white_line = findViewById(R.id.tv_hd_white_line);

        tv_toros_kisa_blue = findViewById(R.id.tv_totros_blue_kisa);
        tv_toros_uzun_blue = findViewById(R.id.tv_totros_blue_uzun);
        tv_toros_kisa_red = findViewById(R.id.tv_totros_red_kisa);
        tv_toros_uzun_red = findViewById(R.id.tv_totros_red_uzun);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Sayfa açıldığında veriyi çekip göster
        readFirestore();

        reset_all_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        hd_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkBlueKisa);
            }
        });

        hd_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkBlueKisa);
            }
        });

        //////////////////////

        hd_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkBlueUzun);
            }
        });

        hd_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkBlueUzun);
            }
        });

        //////////////////////

        slim_blue_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkRedKisa);
            }
        });

        slim_blue_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkRedKisa);
            }
        });

        //////////////////////


        white_line_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameMCDarkRedUzun);
            }
        });


        white_line_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameMCDarkRedUzun);
            }
        });

        //////////////////////


        toros_blue_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameTorosBlueKisa);
            }
        });


        toros_blue_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameTorosBlueKisa);
            }
        });

        //////////////////////


        toros_blue_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameTorosBlueUzun);
            }
        });


        toros_blue_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameTorosBlueUzun);
            }
        });

        //////////////////////


        toros_red_uzun_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameTorosRedUzun);
            }
        });

        toros_red_uzun_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameTorosRedUzun);
            }
        });

        //////////////////////


        toros_red_kisa_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameTorosRedKisa);
            }
        });


        toros_red_kisa_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameTorosRedKisa);
            }
        });

    }


    private void resetAllCounts() {
        // Tüm yerel ve Firestore count değerlerini sıfırla
        resetLocalCounts();
        resetFirestoreCounts();
    }

    private void resetLocalCounts() {
        countHDBlueKisa = 0;
        countHDBlueUzun = 0;
        countHDSlimBlue = 0;
        countHDWhiteLine = 0;
        countTorosBlueKisa = 0;
        countTorosBlueUzun = 0;
        countTorosRedKisa = 0;
        countTorosRedUzun = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameMCDarkBlueKisa, countHDBlueKisa);
        updateTextView(documentNameMCDarkBlueUzun, countHDBlueUzun);
        updateTextView(documentNameMCDarkRedKisa, countHDSlimBlue);
        updateTextView(documentNameMCDarkRedUzun, countHDWhiteLine);

        updateTextView(documentNameTorosBlueKisa, countTorosBlueKisa);
        updateTextView(documentNameTorosBlueUzun, countTorosBlueUzun);
        updateTextView(documentNameTorosRedKisa, countTorosRedKisa);
        updateTextView(documentNameTorosRedUzun, countTorosRedUzun);
    }

    private void resetFirestoreCounts() {
        // Firestore'daki tüm belgelerin stock değerini sıfırla
        updateFirestore(documentNameMCDarkBlueKisa, 0);
        updateFirestore(documentNameMCDarkBlueUzun, 0);
        updateFirestore(documentNameMCDarkRedKisa, 0);
        updateFirestore(documentNameMCDarkRedUzun, 0);

        updateFirestore(documentNameTorosBlueKisa, 0);
        updateFirestore(documentNameTorosBlueUzun, 0);
        updateFirestore(documentNameTorosRedKisa, 0);
        updateFirestore(documentNameTorosRedUzun, 0);
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

        readFirestoreForDocument(documentNameMCDarkRedKisa);
        readFirestoreForDocument(documentNameMCDarkRedUzun);

        readFirestoreForDocument(documentNameTorosBlueKisa);
        readFirestoreForDocument(documentNameTorosBlueUzun);

        readFirestoreForDocument(documentNameTorosRedKisa);
        readFirestoreForDocument(documentNameTorosRedUzun);

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
            tv_hd_blue_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            tv_hd_blue_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkRedKisa)) {
            tv_hd_slim_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMCDarkRedUzun)) {
            tv_hd_white_line.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            tv_toros_kisa_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosBlueUzun)) {
            tv_toros_uzun_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosRedKisa)) {
            tv_toros_kisa_red.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameTorosRedUzun)) {
            tv_toros_uzun_red.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            return countHDBlueKisa;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            return countHDBlueUzun;
        } else if(documentName.equals(documentNameMCDarkRedKisa)) {
            return countHDSlimBlue;
        } else if(documentName.equals(documentNameMCDarkRedUzun)) {
            return countHDWhiteLine;
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            return countTorosBlueKisa;
        } else if(documentName.equals(documentNameTorosBlueUzun)) {
            return countTorosBlueUzun;
        } else if(documentName.equals(documentNameTorosRedKisa)) {
            return countTorosRedKisa;
        } else if(documentName.equals(documentNameTorosRedUzun)) {
            return countTorosRedUzun;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameMCDarkBlueKisa)) {
            countHDBlueKisa = count;
        } else if (documentName.equals(documentNameMCDarkBlueUzun)) {
            countHDBlueUzun = count;
        } else if (documentName.equals(documentNameMCDarkRedKisa)) {
            countHDSlimBlue = count;
        } else if (documentName.equals(documentNameMCDarkRedUzun)) {
            countHDWhiteLine = count;
        } else if (documentName.equals(documentNameTorosBlueKisa)) {
            countTorosBlueKisa = count;
        } else if (documentName.equals(documentNameTorosBlueUzun)) {
            countTorosBlueUzun = count;
        } else if (documentName.equals(documentNameTorosRedKisa)) {
            countTorosRedKisa = count;
        } else if (documentName.equals(documentNameTorosRedUzun)) {
            countTorosRedUzun = count;
        }
    }
}