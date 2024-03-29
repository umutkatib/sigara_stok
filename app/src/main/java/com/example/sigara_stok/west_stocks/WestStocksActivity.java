package com.example.sigara_stok.west_stocks;

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

public class WestStocksActivity extends AppCompatActivity {
    private int countWestNavyKisa, countWestGreyKisa, countTotalStock;
    private EditText navy_kisa, grey_kisa;
    private TextView totalSumTextView;

    FirebaseFirestore db;
    FirebaseAuth auth;
    String documentNameWestNavyKisa = "WEST_Navy", documentNameWestGrey = "WEST_Grey", documentTotalStocks= "Total_West_Stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_west);

        Button reset_all_west = findViewById(R.id.reset_all_west);

        Button west_navy_azalt = findViewById(R.id.west_navy_azalt);
        Button west_navy_arttir = findViewById(R.id.west_navy_arttir);
        Button west_grey_azalt = findViewById(R.id.west_grey_azalt);
        Button west_grey_arttir = findViewById(R.id.west_grey_arttir);


        Button updateValuesButton = findViewById(R.id.guncelle);

        totalSumTextView = findViewById(R.id.tv_west_total);
        navy_kisa = findViewById(R.id.navy_kisa);
        grey_kisa = findViewById(R.id.grey_kisa);

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

        reset_all_west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });


        ////////////////////

        west_navy_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameWestNavyKisa);
            }
        });

        west_navy_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameWestNavyKisa);
            }
        });

        //////////////////////

        west_grey_arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(documentNameWestGrey);
            }
        });

        west_grey_azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementCount(documentNameWestGrey);
            }
        });


    }

    private void updateTotalSum() {
        try {
            countWestNavyKisa = Integer.parseInt(navy_kisa.getText().toString());
        } catch (NumberFormatException e) {
            countWestNavyKisa = 0;  // Set a default value or handle the error as needed
        }

        try {
            countWestGreyKisa = Integer.parseInt(grey_kisa.getText().toString());
        } catch (NumberFormatException e) {
            countWestGreyKisa = 0;  // Set a default value or handle the error as needed
        }

        countTotalStock = countWestNavyKisa + countWestGreyKisa;
        totalSumTextView.setText(String.valueOf(countTotalStock));
    }



    private void discardStockCount() {
        navy_kisa.setText(String.valueOf(countWestNavyKisa));
        grey_kisa.setText(String.valueOf(countWestGreyKisa));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countWestNavyKisa = Integer.parseInt(navy_kisa.getText().toString());
        countWestGreyKisa = Integer.parseInt(grey_kisa.getText().toString());

        // TextView'ları güncelle
        updateTextView(documentNameWestNavyKisa, countWestNavyKisa);
        updateTextView(documentNameWestGrey, countWestGreyKisa);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameWestNavyKisa, countWestNavyKisa);
        firestoreCount(documentNameWestGrey, countWestGreyKisa);

        // Update the Firestore document for documentTotalStocks
        firestoreCount(documentTotalStocks, countTotalStock);
    }



    private void resetCounts() {
        countWestNavyKisa = 0;
        countWestGreyKisa = 0;
        countTotalStock = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameWestNavyKisa, countWestNavyKisa);
        updateTextView(documentNameWestGrey, countWestGreyKisa);
        updateTextView(documentTotalStocks, countTotalStock);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameWestNavyKisa, 0);
        firestoreCount(documentNameWestGrey, 0);
        firestoreCount(documentTotalStocks, 0);
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

    private void updateStockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stok Güncelle");
        builder.setMessage("Stok verisini güncellemek istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTotalSum();
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

    private void incrementCount(String documentName) {
        // Mevcut değeri arttır ve güncelle
        firestoreCount(documentName, getCount(documentName) + 1);

    }

    private void decrementCount(String documentName) {
        // Eğer mevcut değer 0'dan büyükse azalt ve güncelle
        int currentCount = getCount(documentName);
        if (currentCount > 0) {
            firestoreCount(documentName, currentCount - 1);

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
                    updateTotalSum();
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
                    updateTotalSum();
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
        readFirestoreForDocument(documentNameWestNavyKisa);
        readFirestoreForDocument(documentNameWestGrey);
        readFirestoreForDocument(documentTotalStocks);
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
                        updateTotalSum();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Hata!", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateTextView(String documentName, int count) {
        // Belirli bir belgeye ait TextView'i güncelle
        if (documentName.equals(documentNameWestNavyKisa)) {
            navy_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameWestGrey)) {
            grey_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentTotalStocks)) {
            totalSumTextView.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameWestNavyKisa)) {
            return countWestNavyKisa;
        } else if (documentName.equals(documentNameWestGrey)) {
            return countWestGreyKisa;
        } else if (documentName.equals(documentTotalStocks)) {
            return countTotalStock;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameWestNavyKisa)) {
            countWestNavyKisa = count;
        } else if (documentName.equals(documentNameWestGrey)) {
            countWestGreyKisa = count;
        } else if (documentName.equals(documentTotalStocks)) {
            countTotalStock = count;
        }
    }
}