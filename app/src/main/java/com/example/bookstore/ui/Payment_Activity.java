package com.example.bookstore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookstore.BasicActivity;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment_Activity extends BasicActivity {
    private RadioGroup RG;
    private RadioButton delivery,pick_up;
    private Button Buy_delivery_button;
    private FirebaseDatabase Database,db;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference12,DatabaseReference23,DatabaseReference_breakdownk;
    private String snum = loginActivity.id_Snum;
    private String name = loginActivity.id_name;
    private Context context;
    private int i;

    @Override
    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.buy_book_activity);

        RG = (RadioGroup)findViewById(R.id.delivery_pick_up_Group);
        delivery=findViewById(R.id.delivery);
        pick_up=findViewById(R.id.pick_up);
        Buy_delivery_button=findViewById(R.id.Buy_delivery_button);

        Database = FirebaseDatabase.getInstance();//db연결
        db=FirebaseDatabase.getInstance();
        DatabaseReference12 = Database.getReference("shopping").child(snum);//db테이블 연결
        DatabaseReference23= Database.getReference("shopping").child(snum);//db테이블 연결

        DatabaseReference =Database.getReference();


        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.delivery){
                    FragmentTransaction delivery_transaction = getSupportFragmentManager().beginTransaction();
                    Delivery delivery = new Delivery();
                    delivery_transaction.replace(R.id.Frame,delivery);
                    delivery_transaction.commit();

                }else if(checkedId==R.id.pick_up){
                    FragmentTransaction Pick_Up_transaction = getSupportFragmentManager().beginTransaction();
                    Pick_Up Pcik_up = new Pick_Up();
                    Pick_Up_transaction.replace(R.id.Frame,Pcik_up);
                    Pick_Up_transaction.commit();

                }
            }
        });

    }
    public void payment_completed_go(View view){
        DatabaseReference23.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snpapshot1) {
                //현재시간 구
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
                String formatDate = sdfNow.format(date);
                //

                //Database.getReference("buy_list").child(formatDate).child(snum).child(String.valueOf(i)).toString();


                for(DataSnapshot snapshot1 : snpapshot1.getChildren()) {//반복문으로 데이터 list 추출


                     //int int_the_number = Integer.parseInt(the_number);

                    Map<String, Object> mHashmap = new HashMap<>();
                    mHashmap.put(snapshot1.child("author").getKey(), snapshot1.child("author").getValue().toString());
                    mHashmap.put(snapshot1.child("image").getKey(), snapshot1.child("image").getValue().toString());
                    mHashmap.put(snapshot1.child("price").getKey(), snapshot1.child("price").getValue().toString());
                    mHashmap.put(snapshot1.child("publisher").getKey(), snapshot1.child("publisher").getValue().toString());
                    mHashmap.put(snapshot1.child("shopping_S_number").getKey(), snapshot1.child("shopping_S_number").getValue().toString());
                    mHashmap.put(snapshot1.child("the_number").getKey(), snapshot1.child("the_number").getValue().toString());
                    mHashmap.put(snapshot1.child("title").getKey(), snapshot1.child("title").getValue().toString());
                    mHashmap.put("buy_date",formatDate);
                    mHashmap.put("buy_order","준비중");
                    mHashmap.put("buy_name",name);






                     DatabaseReference12 = Database.getReference("buy_list").child(formatDate).child(snum).child(String.valueOf(i));
                     i=i+1;
                     DatabaseReference12.setValue(mHashmap);
                     DatabaseReference23.removeValue();






                }

                Intent activity_intent = new Intent(Payment_Activity.this, purchase_completed.class);
                startActivity(activity_intent);
                finish();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}
