package com.example.bookstore.ui.used;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import static com.example.bookstore.ui.used.MyUsed.CustomViewHolder.used_profile;


public class MyUsed extends RecyclerView.Adapter<MyUsed.CustomViewHolder>  {
    private ArrayList<used> arrayList;
    private Context context;
    private String holder_tit;
    private int position1;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference;
    Dictionary dict;
    com.example.bookstore.ui.used.MyCustomDialog MyCustomDialog;
    com.example.bookstore.ui.used.Post_corrected Post_corrected;
    Activity activity;
    String Title1,Content1,Id,Price,Snum,Date,profile,number,sales;





    public MyUsed(ArrayList<used> arrayList, Context context, Activity activity) {
        this.arrayList = arrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.used_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getprofile())
                .into(used_profile);
        holder.used_title.setText(arrayList.get(position).gettitle());
        holder.used_content.setText(arrayList.get(position).getcontent());
        holder.used_id.setText(arrayList.get(position).getid());
        holder.used_price.setText(arrayList.get(position).getprice());
        holder.used_date.setText(arrayList.get(position).getdate());
        holder.used_snum.setText(arrayList.get(position).getsnum());
        holder.used_number.setText(arrayList.get(position).getused_number());
        holder.used_sale.setText(arrayList.get(position).getsale());
        //==2020.08.14
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1 = position;
                //Toast.makeText(activity, "포지션 값 : "+position, Toast.LENGTH_SHORT).show();

                Title1 = holder.used_title.getText().toString();
                Content1 = holder.used_content.getText().toString();
                Id = holder.used_id.getText().toString();
                Price = holder.used_price.getText().toString();
                Snum = holder.used_snum.getText().toString();
                Date = holder.used_date.getText().toString();
                profile =arrayList.get(position).getprofile();
                number = holder.used_number.getText().toString();
                sales= holder.used_sale.getText().toString();

                /*Intent intent = new Intent();
                intent.putExtra("IMAGE1",arrayList.get(position).getprofile());*/

               //Toast.makeText(context,,Toast.LENGTH_SHORT).show();


               MyCustomDialog = new MyCustomDialog(activity,chat_Button_Listener,corrected_button_Listener,close_button_Listener,Delete_button_Listener
                ,Title1,Content1,Id,Price,Snum,Date,profile,sales);

                MyCustomDialog.setCancelable(true);
                MyCustomDialog.getWindow().setGravity(Gravity.CENTER);

                MyCustomDialog.show();




            }
        });
        //==
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);//참이면 ? 뒤 실행 거짓이면 :뒤 실행
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public static ImageView used_profile;
        TextView used_title;
        TextView used_content;
        TextView used_id;
        TextView used_price;
        TextView used_date;
        TextView used_snum;
        TextView used_number;
        TextView used_sale;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.used_profile = itemView.findViewById(R.id.used_profile_text);
            this.used_title = itemView.findViewById(R.id.used_title_text);
            this.used_content = itemView.findViewById(R.id.used_context_text);
            this.used_id = itemView.findViewById(R.id.used_user_text);
            this.used_price = itemView.findViewById(R.id.used_price_text);
            this.used_date = itemView.findViewById(R.id.used_YY_text);
            this.used_snum = itemView.findViewById(R.id.used_snum_text);
            this.used_number = itemView.findViewById(R.id.Item_number);
            this.used_sale = itemView.findViewById(R.id.sale_item);

        }
    }


    Map<String, Object> map = new HashMap<String, Object>();
    private String str_roon;
    private DatabaseReference reference = FirebaseDatabase.getInstance().
            getReference().getRoot();
    String str_name = loginActivity.id_name;

    final View.OnClickListener chat_Button_Listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            str_roon = number;
            map.put(str_roon, "");
            String str_roon2 = reference.child(str_roon).toString();

            Toast.makeText(activity, str_roon+" 방에 입장했습니다.", Toast.LENGTH_SHORT).show();
            MyCustomDialog.dismiss();


            if(str_roon.equals(str_roon2)){ reference.updateChildren(map);}
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("room_name", str_roon);
            intent.putExtra("user_name", str_name);
           intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
           v.getContext().startActivity(intent);



        }
    };

    View.OnClickListener corrected_button_Listener= new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            final String snum = loginActivity.id_Snum;
            final String fbname = loginActivity.id_name;



            DatabaseReference corrected_ref =FirebaseDatabase.getInstance().getReference("used");
            corrected_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        //Toast.makeText(activity,   "used_number"+snapshot1.child("used_number").getValue().toString()+"id : "+snapshot1.child("id").getValue().toString(), Toast.LENGTH_SHORT).show();
                        if(snapshot1.child("used_number").getValue().toString().equals(number)){
                            if (snapshot1.child("id").getValue().toString().equals(fbname)) {
                                Toast.makeText(activity,   "수정 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                                MyCustomDialog.dismiss();

                                Post_corrected = new Post_corrected(activity, Title1, Content1, Id, Price, Snum, Date, profile, number);

                                Post_corrected.setCancelable(true);
                                Post_corrected.getWindow().setGravity(Gravity.CENTER);

                                Post_corrected.show();
                                break;
                            }
                            else {
                                Toast.makeText(activity, "작성자만 수정할수있습니다.", Toast.LENGTH_SHORT).show();
                                MyCustomDialog.dismiss();
                                break;
                            }
                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }
    };
    View.OnClickListener close_button_Listener= new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Toast.makeText(activity, "닫기 버튼을 눌렸습니다.", Toast.LENGTH_SHORT).show();
            MyCustomDialog.dismiss();



        }
    };
    View.OnClickListener Delete_button_Listener= new View.OnClickListener(){
        final String snum = loginActivity.id_Snum;
        final String fbname = loginActivity.id_name;

        @Override
        public void onClick(View v) {
            Toast.makeText(activity, "삭제 버튼을 눌렸습니다.", Toast.LENGTH_SHORT).show();

            final DatabaseReference remove_ref =FirebaseDatabase.getInstance().getReference();

            remove_ref.child("used").addListenerForSingleValueEvent(new ValueEventListener() { // 게시물 삭제
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapShot : snapshot.getChildren()){
                        if(snapShot.child("used_number").getValue().toString().equals(number)){
                            if (snapShot.child("id").getValue().toString().equals(fbname)) {

                                String now_number =snapShot.getKey();
                                if(now_number.equals(number)) {
                                        snapShot.getRef().removeValue();
                                        remove_ref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot snapShot : snapshot.getChildren()){

                                                    String now_number =snapShot.getKey();
                                                    if(now_number.equals(number)) {
                                                        snapShot.getRef().removeValue();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                }
                            }
                            else{
                                Toast.makeText(activity, "작성자만 삭제할수있습니다.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                    MyCustomDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
           /* remove_ref.addListenerForSingleValueEvent(new ValueEventListener() { //채팅방 삭제
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapShot : snapshot.getChildren()){

                        String now_number =snapShot.getKey();
                        if(now_number.equals(number)) {
                            snapShot.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            MyCustomDialog.dismiss();*/



        }
    };

}
