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




        Database = FirebaseDatabase.getInstance();//db연결
        DatabaseReference = Database.getReference();//db테이블 연결


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_used, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();
        recyclerView = findViewById(R.id.recyclerView); //연결

        sum_number = findViewById(R.id.sum_number);
        buy_button= (Button) findViewById(R.id.buy_button);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구매버튼

                Intent intent = new Intent(shoppingActivity.this, Payment_Activity.class);
                startActivity(intent);
            }
        });







        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user 객체를 담은 어레이 리스트(어댑터쪽으로)



        //Intent get_sum_number_Intet = getIntent();
        //int int_sum_number = get_sum_number_Intet.getIntExtra("값",0);
        //String String_sum_number = String.valueOf(int_sum_number);
       // DatabaseReference1=Database.getReference("shopping/the_number");

        DatabaseReference.child("shopping/"+snum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열리스트 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추출

                        shoppingbook Book = snapshot.getValue(shoppingbook.class);//만들어둿던 book객체에 데이터를 담는다.
                        arrayList.add(Book);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비

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


                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("shoppingActivity e", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter = new MyShoppingBook(arrayList,this);

        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결
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
