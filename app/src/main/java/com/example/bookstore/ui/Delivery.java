package com.example.bookstore.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.example.bookstore.ui.book.MyShoppingBook;
import com.example.bookstore.ui.book.shoppingbook;
import com.example.bookstore.ui.engineering_college.computer;
import com.example.bookstore.ui.used.used;
import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Delivery extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<shoppingbook> arrayList;
    private FirebaseDatabase Database,db;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference1,DatabaseReference2;
    private String snum = loginActivity.id_Snum;
   private View view;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.book_delivery,container,false);




        Database = FirebaseDatabase.getInstance();//db연결
        db=FirebaseDatabase.getInstance();
        DatabaseReference1 = Database.getReference("shopping").child(snum);//db테이블 연결
        DatabaseReference2= Database.getReference("shopping").child(snum);//db테이블 연결
        DatabaseReference =Database.getReference();

        recyclerView = view.findViewById(R.id.delivery_recylcerView); //연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user 객체를 담은 어레이 리스트(어댑터쪽으로)
        DatabaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열리스트 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추출

                    shoppingbook Book = snapshot.getValue(shoppingbook.class);//만들어둿던 book객체에 데이터를 담는다.
                    arrayList.add(Book);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비

                }

                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("deliveryActivity e", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter = new MyShoppingBook(arrayList,getContext());

        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결




    return view;
    }
}
