package com.example.bookstore.ui.used;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.BasicActivity;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mun_gwa_used_activity extends BasicActivity implements View.OnClickListener {
    private ChildEventListener mChild;
    final static loginActivity loginactivity = new loginActivity();
    private static String id_name = loginActivity.id_name;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<used> arrayList;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference;
    private com.example.bookstore.ui.used.MyCustomDialog MyCustomDialog;

  //  private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mun_gwa_used);

        ImageButton add_button = (ImageButton) findViewById(R.id.add_Post_Button);
        ImageButton Refresh_button2 = (ImageButton) findViewById(R.id.Refresh_Post_Button2);
        add_button.setOnClickListener(this);



        recyclerView = findViewById(R.id.recyclerView); //연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//user 객체를 담은 어레이 리스트(어댑터쪽으로)

        Database = FirebaseDatabase.getInstance();//db연결
        DatabaseReference = Database.getReference("used");//db테이블 연결
        DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();//기존 배열리스트 초기화
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문으로 데이터 list 추출
                        used Used = snapshot.getValue(used.class);//만들어둿던 used객체에 데이터를 담는다.
                        arrayList.add(Used);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    }
                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db가져오던중 에러 발생
                Log.e("Mun_gwa_used_activity", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter = new MyUsed(arrayList,this,this);
        recyclerView.setAdapter(adapter);//리사이클러뷰에 어댑터 연결



        Refresh_button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                        arrayList.clear();//기존 배열리스트 초기화
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문으로 데이터 list 추출
                            used Used = snapshot.getValue(used.class);//만들어둿던 used객체에 데이터를 담는다.
                            arrayList.add(Used);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        }
                        adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //db가져오던중 에러 발생
                        Log.e("Mun_gwa_used_activity", String.valueOf(databaseError.toException())); //에러문 출력
                    }
                });
            }
        });
    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddPostActivity.class);
       // intent.putExtra("com.example.terry.basicintentsample.MESSAGE",getId_name);//intent할때 fbname의 값도 intent "com.example.terry.basicintentsample.MESSAGE"은 주소같은것.
        startActivity(intent);

    }





}
