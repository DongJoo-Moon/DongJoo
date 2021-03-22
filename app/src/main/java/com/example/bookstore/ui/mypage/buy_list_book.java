package com.example.bookstore.ui.mypage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class buy_list_book extends RecyclerView.Adapter<buy_list_book.CustomViewHolder>{
    private ArrayList<mypage_item> arrayList;
    private Context context;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference, DatabaseReference1;
    private RecyclerView.Adapter adapter;
    String title, price, author, publisher, image, s_number,order,date,the_number,name;
    int position1,int_sum_number;
    String sum_number;
    public buy_list_book(ArrayList<mypage_item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 리스너 객체 참조를 저장하는 변수
    private buy_list_book.OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(buy_list_book.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public buy_list_book.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_list_item, parent, false);
        buy_list_book.CustomViewHolder holder = new buy_list_book.CustomViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final buy_list_book.CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getimage())
                .into(holder.buy_image);
        holder.buy_title.setText(arrayList.get(position).gettitle());
        holder.buy_price.setText(arrayList.get(position).getprice());
        holder.buy_author.setText(arrayList.get(position).getauthor());

        holder.buy_publisher.setText(arrayList.get(position).getpublisher());
        holder.buy_snumber.setText(arrayList.get(position).getshopping_S_number());
        holder.buy_the_number.setText(arrayList.get(position).getthe_number());
        holder.buy_date.setText(arrayList.get(position).getbuy_date());

        holder.buy_order.setText(arrayList.get(position).getbuy_order());
        holder.buy_name.setText(arrayList.get(position).getbuy_name());

        final String snum = loginActivity.id_Snum;


        /*
        Database = FirebaseDatabase.getInstance(); //db연결
        DatabaseReference1 = Database.getReference().child("shopping");

        sum_number = holder.buy_the_number.getText().toString();
        int_sum_number = Integer.parseInt(sum_number);
        String holder_price = holder.buy_price.getText().toString();

        int holder_price1 = Integer.parseInt(holder_price.replaceAll(",",""));
        int_sum_number = int_sum_number*holder_price1;
        Intent Sum_number_intent = new Intent();
        Sum_number_intent.putExtra("값",int_sum_number);
        */


    }
    @Override
    public int getItemCount () {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0); //참이면 ? 뒤 실행 거짓이면 :뒤 실행
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView buy_image;
        TextView buy_title;
        TextView buy_price;
        TextView buy_author;
        TextView buy_publisher;
        TextView buy_the_number;
        TextView buy_snumber;
        TextView buy_date;
        TextView buy_order;
        TextView buy_name;

        private com.example.bookstore.ui.book.book book;

        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);

            this.buy_image = itemView.findViewById(R.id.buy_image_text);
            this.buy_title = itemView.findViewById(R.id.buy_title_text);
            this.buy_price = itemView.findViewById(R.id.buy_price_text);
            this.buy_author = itemView.findViewById(R.id.buy_author_text);
            this.buy_publisher = itemView.findViewById(R.id.buy_publisher_text);
            this.buy_the_number=itemView.findViewById(R.id.buy_the_number);
            this.buy_snumber = itemView.findViewById(R.id.buy_snumber_text);
            this.buy_date = itemView.findViewById(R.id.buy_date);
            this.buy_order = itemView.findViewById(R.id.buy_order);
            this.buy_name = itemView.findViewById(R.id.buy_name);
        }
    }

    public buy_list_book(String title, String price, String author, String publisher, String image,String s_num,String order, String date , String the_number,String name){
        this.title = title;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.image = image;
        this.s_number=s_num;
        this.order =order;
        this.date = date;
        this.the_number = the_number;
        this.name=name;
    }
}