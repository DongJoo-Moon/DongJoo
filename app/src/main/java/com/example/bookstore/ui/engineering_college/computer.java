package com.example.bookstore.ui.engineering_college;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.example.bookstore.ui.book.MyBook;
import com.example.bookstore.ui.book.book;
import com.example.bookstore.ui.book.searchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class computer extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<book> arrayList;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference1;


    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer);


        recyclerView = findViewById(R.id.recyclerView); //연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user 객체를 담은 어레이 리스트(어댑터쪽으로)

        Database = FirebaseDatabase.getInstance();//db연결
        DatabaseReference = Database.getReference("computer_book");//db테이블 연결
        DatabaseReference1 = Database.getReference("user");
        DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    arrayList.clear();//기존 배열리스트 초기화
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문으로 데이터 list 추출
                        book Book = snapshot.getValue(book.class);//만들어둿던 book객체에 데이터를 담는다.
                        arrayList.add(Book);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        //Toast.makeText(computer.this,  "Value:"+snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db가져오던중 에러 발생
                Log.e("computer_book", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter = new MyBook(arrayList,this);


        /*((MyBook) adapter).setOnItemClickListener(new MyBook.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                // TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.
              //Toast.makeText(computer.this, position+"click", Toast.LENGTH_SHORT).show();  버튼 위치 찾기.

              if(position == 0) {
                  switch (v.getId()) {
                      case R.id.Purchasebtn:
                          DatabaseReference.child("이산수학").addListenerForSingleValueEvent(new ValueEventListener() {

                              @Override
                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                  arrayList.clear();
                                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추
                                      String stock = dataSnapshot.child("stock").getValue().toString();
                                      int int_stock = Integer.parseInt(stock);
                                      if(int_stock==0){//솔드아웃 구문
                                          Sold_out_dialog();
                                          break;
                                      }
                                      int_stock = int_stock-1;
                                      stock = String.valueOf(int_stock);
                                      DatabaseReference.child("이산수학").child("stock").setValue(stock);
                                      Toast.makeText(computer.this,  "stock = "+stock, Toast.LENGTH_SHORT).show();
                                  }

                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {
                                  //
                              }

                          });
                          break;


                  }
              }
              else if(position ==1){
                  switch (v.getId()) {
                      case R.id.Purchasebtn:
                          DatabaseReference.child("자바프로그래밍").addListenerForSingleValueEvent(new ValueEventListener() {
                              @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                              @Override
                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                  arrayList.clear();

                                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추

                                      String stock = dataSnapshot.child("stock").getValue().toString();
                                      int int_stock = Integer.parseInt(stock);
                                      if(int_stock==0){//솔드아웃 구문
                                          Sold_out_dialog();
                                          break;
                                      }

                                      int_stock = int_stock-1;
                                      stock = String.valueOf(int_stock);
                                      DatabaseReference.child("자바프로그래밍").child("stock").setValue(stock);
                                      Toast.makeText(computer.this,  "stock = "+stock, Toast.LENGTH_SHORT).show();
                                  }

                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {
                                  //
                              }

                          });
                  }
              }

            }
        }) ;*/
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결

    }
    public void Sold_out_dialog() {

        AlertDialog.Builder oDialog = new AlertDialog.Builder(computer.this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        String strHtml =
                "<b><font color='#ff0000'>Sold Out</font></b> 제품 입니다.<br/>";
        Spanned oHtml;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            oHtml = Html.fromHtml(strHtml);
        } else {
            oHtml = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_LEGACY);
        }

        oDialog.setTitle("")
                .setMessage(oHtml)
                .setPositiveButton("ok", null)
                .setCancelable(false)
                .show();

    }
}


