package com.kdc.chatapp_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    private Button signOut, accept;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount googleSignInAccount;
    private MaterialEditText new_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        signOut = findViewById(R.id.sign_out);
        new_name = findViewById(R.id.new_name);
        accept = findViewById(R.id.accept);

        mAuth = FirebaseAuth.getInstance();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignInAccount = GoogleSignIn.getLastSignedInAccount(MainActivity2.this);

                if (googleSignInAccount != null) {
                    GoogleSignIn.getClient(
                            getApplicationContext(),
                            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                    ).signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(MainActivity2.this, StartActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    mAuth.signOut();
                    Intent intent = new Intent(MainActivity2.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }



            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = new_name.getText().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getCurrentUser().getUid());

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("username", newName);

                reference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity2.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
