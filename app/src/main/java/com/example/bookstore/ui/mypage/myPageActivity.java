package com.example.bookstore.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.BasicActivity;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.example.bookstore.shoppingActivity;
import com.example.bookstore.ui.book.MyShoppingBook;
import com.example.bookstore.ui.book.book;
import com.example.bookstore.ui.book.shoppingbook;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myPageActivity extends BasicActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<mypage_item> arrayList;
    private String dataKey;

    //private AppBarConfiguration mAppBarConfiguration;
    //private BackPressCloseHandler backPressCloseHandler;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference1;
    private FirebaseDatabase Database;
    @Override
    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.mypage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //backPressCloseHandler = new BackPressCloseHandler(this);
        final String snum = loginActivity.id_Snum;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Button mypage_used_button = findViewById(R.id.mypage_used_button);
        Button mypage_service = findViewById(R.id.mypage_service);
        Button information = findViewById(R.id.information);



        mypage_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mypage_used_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Database = FirebaseDatabase.getInstance();//db??????
        DatabaseReference = Database.getReference();//db????????? ??????


        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);*/


        recyclerView = findViewById(R.id.mypage_recy); //??????
        recyclerView.setHasFixedSize(true); //?????????????????? ?????? ?????? ??????
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user ????????? ?????? ????????? ?????????(??????????????????)
        DatabaseReference.child("buy_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();//?????? ??????????????? ?????????
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    dataKey =snapshot1.getKey();

                    DatabaseReference.child("buy_list").child(dataKey).child(snum).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                mypage_item mypage_item = snapshot1.getValue(mypage_item.class);//??????????????? book????????? ???????????? ?????????.
                                arrayList.add(mypage_item);//?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????
                            }
                            adapter.notifyDataSetChanged();//????????? ?????? ??? ????????????
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
               /*DatabaseReference.child("buy_list").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //?????????????????? ????????????????????? ???????????? ???????????? ???
                        arrayList.clear();//?????? ??????????????? ?????????
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//??????????????? ????????? list ??????

                            shoppingbook Book = snapshot.getValue(shoppingbook.class);//??????????????? book????????? ???????????? ?????????.
                            arrayList.add(Book);//?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????


                        }



                        adapter.notifyDataSetChanged();//????????? ?????? ??? ????????????
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("shoppingActivity e", String.valueOf(databaseError.toException())); //????????? ??????
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter = new buy_list_book(arrayList,this);

        recyclerView.setAdapter(adapter);//????????????????????? ????????? ??????

        /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_used, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();*/

    //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

}

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingButton2:
                shopping();
                break;
            case R.id.myPageButton2:
                mypage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void shopping() {
        Intent intent = new Intent(myPageActivity.this, shoppingActivity.class);
        startActivity(intent);
    }

    public void mypage() {
        Intent intent = new Intent(myPageActivity.this, myPageActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
