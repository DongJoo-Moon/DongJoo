package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class loginActivity extends BasicActivity {
    public static String id_name,id_Snum;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    EditText EditText_id;
    EditText EditText_password;
    Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_id = (EditText)findViewById(R.id.EditText_id);
        EditText_password = (EditText)findViewById(R.id.EditText_password);

        loginButton = (Button) findViewById(R.id.loginButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("user");


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String id = EditText_id.getText().toString();
                final String pw = EditText_password.getText().toString();
                databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        if(dataSnapshot.getChildrenCount() > 0 ){
                            String fbPw = dataSnapshot.child("password").getValue().toString();
                            String fbname = dataSnapshot.child("name").getValue().toString(); //firebase에서 name의 Value값을 get하고 string 시킴.

                            if(fbPw.equals(pw)){
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);

                                id_name = fbname;
                                id_Snum = id;
                                intent.putExtra("name", id_name);
                                finish();

                                Toast.makeText(loginActivity.this,fbname+"님 어서오세요.",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }else{
                                Toast.makeText(loginActivity.this,"Wrong PassWord",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(loginActivity.this,"Nothing",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
