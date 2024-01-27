package com.example.sigara_stok.stocks.marbloro_stocks;


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

public class MarlboroStocksActivity extends AppCompatActivity {

    private int countMBRedKisa = 0, countMBRedUzun = 0, countMBTouch = 0;
    private int countMBTouchGrey = 0, countMBTouchBlue = 0, countMBTouchWhite = 0, countMBEdge = 0;
    private int countMBEdgeSky = 0, countMBEdgeBlue = 0;
    private EditText et_mb_red_kisa, et_mb_red_uzun;
    private TextView et_mb_touch, et_mb_touch_grey;
    private TextView et_mb_touch_white, et_mb_touch_blue;
    private TextView et_mb_edge, et_mb_edge_blue, et_mb_edge_sky;
    FirebaseFirestore db;
    FirebaseAuth auth;

    final static String documentNameMBRedKisa = "PM_Marlboro_Red_Kisa", documentNameMBRedUzun = "PM_Marlboro_Red_Uzun";
    final static String documentNameMBTouch = "PM_Marlboro_Touch", documentNameMBTouchGrey = "PM_Marlboro_Touch_Grey";
    final static String documentNameMBTouchBlue = "PM_Marlboro_Touch_Blue", documentNameMBTouchWhite = "PM_Marlboro_Touch_White";
    final static String documentNameMBEdge = "PM_Marlboro_Edge", documentNameMBEdgeSky = "PM_Marlboro_Edge_Sky", documentNameMBEdgeBlue = "PM_Marlboro_Edge_Blue";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marlboro_stocks);

        Button reset_all_marl_all_btn = findViewById(R.id.reset_all_marl_all_btn);

        Button malb_red_kisa_azalt = findViewById(R.id.malb_red_kisa_azalt);
        Button malb_red_kisa_arttir = findViewById(R.id.malb_red_kisa_arttir);
        Button malb_red_uzun_azalt = findViewById(R.id.malb_red_uzun_azalt);
        Button malb_red_uzun_arttir = findViewById(R.id.malb_red_uzun_arttir);
        Button malb_touch_azalt = findViewById(R.id.malb_touch_azalt);
        Button malb_touch_arttir = findViewById(R.id.malb_touch_arttir);
        Button malb_touch_grey_azalt = findViewById(R.id.malb_touch_grey_azalt);
        Button malb_touch_grey_arttir = findViewById(R.id.malb_touch_grey_arttir);
        Button malb_touch_white_azalt = findViewById(R.id.malb_touch_white_azalt);
        Button malb_touch_white_arttir = findViewById(R.id.malb_touch_white_arttir);
        Button malb_touch_blue_azalt = findViewById(R.id.malb_touch_blue_azalt);
        Button malb_touch_blue_arttir = findViewById(R.id.malb_touch_blue_arttir);
        Button malb_edge_azalt = findViewById(R.id.malb_edge_azalt);
        Button malb_edge_arttir = findViewById(R.id.malb_edge_arttir);
        Button malb_edge_blue_azalt = findViewById(R.id.malb_edge_blue_azalt);
        Button malb_edge_blue_arttir = findViewById(R.id.malb_edge_blue_arttir);
        Button malb_edge_sky_azalt = findViewById(R.id.malb_edge_sky_azalt);
        Button malb_edge_sky_arttir = findViewById(R.id.malb_edge_sky_arttir);

        Button updateValuesButton = findViewById(R.id.marlboroGuncelle);

        setMarlboroButtonClickListeners(malb_red_kisa_azalt, malb_red_kisa_arttir, documentNameMBRedKisa);
        setMarlboroButtonClickListeners(malb_red_uzun_azalt, malb_red_uzun_arttir, documentNameMBRedUzun);
        setMarlboroButtonClickListeners(malb_touch_azalt, malb_touch_arttir, documentNameMBTouch);
        setMarlboroButtonClickListeners(malb_touch_grey_azalt, malb_touch_grey_arttir, documentNameMBTouchGrey);
        setMarlboroButtonClickListeners(malb_touch_blue_azalt, malb_touch_blue_arttir, documentNameMBTouchBlue);
        setMarlboroButtonClickListeners(malb_touch_white_azalt, malb_touch_white_arttir, documentNameMBTouchWhite);
        setMarlboroButtonClickListeners(malb_edge_azalt, malb_edge_arttir, documentNameMBEdge);
        setMarlboroButtonClickListeners(malb_edge_blue_azalt, malb_edge_blue_arttir, documentNameMBEdgeBlue);
        setMarlboroButtonClickListeners(malb_edge_sky_azalt, malb_edge_sky_arttir, documentNameMBEdgeSky);


        et_mb_red_kisa = findViewById(R.id.et_mb_red_kisa);
        et_mb_red_uzun = findViewById(R.id.et_mb_red_uzun);
        et_mb_touch = findViewById(R.id.et_mb_touch);
        et_mb_touch_grey = findViewById(R.id.et_mb_touch_grey);
        et_mb_touch_white = findViewById(R.id.et_mb_touch_white);
        et_mb_touch_blue = findViewById(R.id.et_mb_touch_blue);
        et_mb_edge = findViewById(R.id.et_mb_edge);
        et_mb_edge_blue = findViewById(R.id.et_mb_edge_blue);
        et_mb_edge_sky = findViewById(R.id.et_mb_edge_sky);

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

        reset_all_marl_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void discardStockCount() {
        et_mb_red_kisa.setText(String.valueOf(countMBRedKisa));
        et_mb_red_uzun.setText(String.valueOf(countMBRedUzun));

        et_mb_touch.setText(String.valueOf(countMBTouch));
        et_mb_touch_grey.setText(String.valueOf(countMBTouchGrey));

        et_mb_touch_blue.setText(String.valueOf(countMBTouchBlue));
        et_mb_touch_white.setText(String.valueOf(countMBTouchWhite));

        et_mb_edge.setText(String.valueOf(countMBEdge));
        et_mb_edge_blue.setText(String.valueOf(countMBEdgeBlue));
        et_mb_edge_sky.setText(String.valueOf(countMBEdgeSky));
    }


    private void updateEditTextValues() {
        // EditText değerlerini al ve ilgili değişkenlere at
        countMBRedKisa = Integer.parseInt(et_mb_red_kisa.getText().toString());
        countMBRedUzun = Integer.parseInt(et_mb_red_uzun.getText().toString());

        countMBTouch = Integer.parseInt(et_mb_touch.getText().toString());
        countMBTouchGrey = Integer.parseInt(et_mb_touch_grey.getText().toString());

        countMBTouchBlue = Integer.parseInt(et_mb_touch_blue.getText().toString());
        countMBTouchWhite = Integer.parseInt(et_mb_touch_white.getText().toString());

        countMBEdge = Integer.parseInt(et_mb_edge.getText().toString());
        countMBEdgeBlue = Integer.parseInt(et_mb_edge_blue.getText().toString());
        countMBEdgeSky = Integer.parseInt(et_mb_edge_sky.getText().toString());


        // TextView'ları güncelle
        updateTextView(documentNameMBRedKisa, countMBRedKisa);
        updateTextView(documentNameMBRedUzun, countMBRedUzun);
        updateTextView(documentNameMBTouch, countMBTouch);
        updateTextView(documentNameMBTouchGrey, countMBTouchGrey);
        updateTextView(documentNameMBTouchBlue, countMBTouchBlue);
        updateTextView(documentNameMBTouchWhite, countMBTouchWhite);
        updateTextView(documentNameMBEdge, countMBEdge);
        updateTextView(documentNameMBEdgeBlue, countMBEdgeBlue);
        updateTextView(documentNameMBEdgeSky, countMBEdgeSky);

        // Firestore'daki belgeleri güncelle
        firestoreCount(documentNameMBRedKisa, countMBRedKisa);
        firestoreCount(documentNameMBRedUzun, countMBRedUzun);
        firestoreCount(documentNameMBTouch, countMBTouch);
        firestoreCount(documentNameMBTouchGrey, countMBTouchGrey);
        firestoreCount(documentNameMBTouchBlue, countMBTouchBlue);
        firestoreCount(documentNameMBTouchWhite, countMBTouchWhite);
        firestoreCount(documentNameMBEdge, countMBEdge);
        firestoreCount(documentNameMBEdgeBlue, countMBEdgeBlue);
        firestoreCount(documentNameMBEdgeSky, countMBEdgeSky);
    }

    private void updateStockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stok Güncelle");
        builder.setMessage("Stok verisini güncellemek istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    private void setMarlboroButtonClickListeners(Button decrementButton, Button incrementButton, String documentName) {
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
        countMBRedKisa = 0;
        countMBRedUzun = 0;
        countMBTouch = 0;
        countMBTouchGrey = 0;
        countMBTouchBlue = 0;
        countMBTouchWhite = 0;
        countMBEdge = 0;
        countMBEdgeSky = 0;
        countMBEdgeBlue = 0;

        // Tüm TextView'ları sıfırla
        updateTextView(documentNameMBRedKisa, countMBRedKisa);
        updateTextView(documentNameMBRedUzun, countMBRedUzun);
        updateTextView(documentNameMBTouch, countMBTouch);
        updateTextView(documentNameMBTouchGrey, countMBTouchGrey);
        updateTextView(documentNameMBTouchBlue, countMBTouchBlue);
        updateTextView(documentNameMBTouchWhite, countMBTouchWhite);
        updateTextView(documentNameMBEdge, countMBEdge);
        updateTextView(documentNameMBEdgeSky, countMBEdgeSky);
        updateTextView(documentNameMBEdgeBlue, countMBEdgeBlue);

        // Firestore'daki tüm belgelerin stock değerini sıfırla
        firestoreCount(documentNameMBRedKisa, 0);
        firestoreCount(documentNameMBRedUzun, 0);
        firestoreCount(documentNameMBTouch, 0);
        firestoreCount(documentNameMBTouchGrey, 0);
        firestoreCount(documentNameMBTouchBlue, 0);
        firestoreCount(documentNameMBTouchWhite, 0);
        firestoreCount(documentNameMBEdge, 0);
        firestoreCount(documentNameMBEdgeSky, 0);
        firestoreCount(documentNameMBEdgeBlue, 0);
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
        readFirestoreForDocument(documentNameMBRedKisa);
        readFirestoreForDocument(documentNameMBRedUzun);
        readFirestoreForDocument(documentNameMBTouch);
        readFirestoreForDocument(documentNameMBTouchGrey);
        readFirestoreForDocument(documentNameMBTouchBlue);
        readFirestoreForDocument(documentNameMBTouchWhite);
        readFirestoreForDocument(documentNameMBEdge);
        readFirestoreForDocument(documentNameMBEdgeSky);
        readFirestoreForDocument(documentNameMBEdgeBlue);
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
        if (documentName.equals(documentNameMBRedKisa)) {
            et_mb_red_kisa.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBRedUzun)) {
            et_mb_red_uzun.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBTouch)) {
            et_mb_touch.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBTouchGrey)) {
            et_mb_touch_grey.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBTouchWhite)) {
            et_mb_touch_white.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBTouchBlue)) {
            et_mb_touch_blue.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBEdge)) {
            et_mb_edge.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBEdgeSky)) {
            et_mb_edge_sky.setText(String.valueOf(count));
        } else if (documentName.equals(documentNameMBEdgeBlue)) {
            et_mb_edge_blue.setText(String.valueOf(count));
        }
    }

    private int getCount(String documentName) {
        // Belirli bir belgeye ait yerel count değerini döndür
        if (documentName.equals(documentNameMBRedKisa)) {
            return countMBRedKisa;
        } else if (documentName.equals(documentNameMBRedUzun)) {
            return countMBRedUzun;
        } else if(documentName.equals(documentNameMBTouch)) {
            return countMBTouch;
        } else if(documentName.equals(documentNameMBTouchGrey)) {
            return countMBTouchGrey;
        } else if(documentName.equals(documentNameMBTouchBlue)) {
            return countMBTouchBlue;
        } else if(documentName.equals(documentNameMBTouchWhite)) {
            return countMBTouchWhite;
        } else if(documentName.equals(documentNameMBEdge)) {
            return countMBEdge;
        } else if(documentName.equals(documentNameMBEdgeSky)) {
            return countMBEdgeSky;
        } else if(documentName.equals(documentNameMBEdgeBlue)) {
            return countMBEdgeBlue;
        }
        return 0;
    }

    private void setCount(String documentName, int count) {
        // Belirli bir belgeye ait yerel count değerini güncelle
        if (documentName.equals(documentNameMBRedKisa)) {
            countMBRedKisa = count;
        } else if (documentName.equals(documentNameMBRedUzun)) {
            countMBRedUzun = count;
        } else if (documentName.equals(documentNameMBTouch)) {
            countMBTouch = count;
        } else if (documentName.equals(documentNameMBTouchGrey)) {
            countMBTouchGrey = count;
        } else if (documentName.equals(documentNameMBTouchBlue)) {
            countMBTouchBlue = count;
        } else if (documentName.equals(documentNameMBTouchWhite)) {
            countMBTouchWhite = count;
        } else if (documentName.equals(documentNameMBEdge)) {
            countMBEdge = count;
        } else if (documentName.equals(documentNameMBEdgeSky)) {
            countMBEdgeSky = count;
        } else if (documentName.equals(documentNameMBEdgeBlue)) {
            countMBEdgeBlue = count;
        }
    }
}