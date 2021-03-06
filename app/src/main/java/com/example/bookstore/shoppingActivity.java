package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.ui.Payment_Activity;
import com.example.bookstore.ui.book.MyShoppingBook;
import com.example.bookstore.ui.book.shoppingbook;
import com.example.bookstore.ui.mypage.myPageActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class shoppingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<shoppingbook> arrayList;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference1;
    private EditText the_number;
    private TextView sum_number;
    private String String_price,String_the_number,String_number,String_Price_Num_sum;
    private int int_price,int_the_number,int_sum=0;
    private int int_Price_Num_sum=0;
    private Button buy_button;

    Context context;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.shopping);
        String snum = loginActivity.id_Snum;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Database = FirebaseDatabase.getInstance();//db??????
        DatabaseReference = Database.getReference();//db????????? ??????


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_used, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();
        recyclerView = findViewById(R.id.recyclerView); //??????

        sum_number = findViewById(R.id.sum_number);
        buy_button= (Button) findViewById(R.id.buy_button);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????

                Intent intent = new Intent(shoppingActivity.this, Payment_Activity.class);
                startActivity(intent);
            }
        });







        recyclerView.setHasFixedSize(true); //?????????????????? ?????? ?????? ??????
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user ????????? ?????? ????????? ?????????(??????????????????)



        //Intent get_sum_number_Intet = getIntent();
        //int int_sum_number = get_sum_number_Intet.getIntExtra("???",0);
        //String String_sum_number = String.valueOf(int_sum_number);
       // DatabaseReference1=Database.getReference("shopping/the_number");

        DatabaseReference.child("shopping/"+snum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //?????????????????? ????????????????????? ???????????? ???????????? ???
                arrayList.clear();//?????? ??????????????? ?????????
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//??????????????? ????????? list ??????

                        shoppingbook Book = snapshot.getValue(shoppingbook.class);//??????????????? book????????? ???????????? ?????????.
                        arrayList.add(Book);//?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????

                        String_price = snapshot.child("price").getValue().toString().replace(",","");
                        String_the_number=snapshot.child("the_number").getValue().toString();
                        int_price =Integer.parseInt(String_price);
                        int_the_number=Integer.parseInt(String_the_number);
                        int_sum = int_price*int_the_number;
                        int_Price_Num_sum = int_Price_Num_sum +int_sum;
                        DecimalFormat formatter = new DecimalFormat("###,###");
                        //String_Price_Num_sum =  Integer.toString(int_Price_Num_sum);

                        sum_number.setText(formatter.format(int_Price_Num_sum));




                }
                int_Price_Num_sum= 0;


                adapter.notifyDataSetChanged();//????????? ?????? ??? ????????????
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("shoppingActivity e", String.valueOf(databaseError.toException())); //????????? ??????
            }
        });

        adapter = new MyShoppingBook(arrayList,this);

        recyclerView.setAdapter(adapter);//????????????????????? ????????? ??????
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
        Intent intent = new Intent(shoppingActivity.this, shoppingActivity.class);
        startActivity(intent);
    }

    public void mypage() {
        Intent intent = new Intent(shoppingActivity.this, myPageActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(shoppingActivity.this,R.id.Main_frame);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
