package com.example.bookstore.ui.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyShoppingBook extends RecyclerView.Adapter<MyShoppingBook.CustomViewHolder> {
    private ArrayList<shoppingbook> arrayList;
    private Context context;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference, DatabaseReference1;
    private RecyclerView.Adapter adapter;
    String title, price, author, publisher, stock, image, book1,s_num;
    int position1,int_sum_number;
    String sum_number;
    public MyShoppingBook(ArrayList<shoppingbook> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyShoppingBook.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingbook_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyShoppingBook.CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getimage())
                .into(holder.book_image);
        holder.book_title.setText(arrayList.get(position).gettitle());
        holder.book_price.setText(arrayList.get(position).getprice());
        holder.book_author.setText(arrayList.get(position).getauthor());
        holder.book_publisher.setText(arrayList.get(position).getpublisher());
        holder.s_num.setText(arrayList.get(position).getshopping_S_number());
        holder.the_number.setText(arrayList.get(position).getthe_number());
        final String snum = loginActivity.id_Snum;



        Database = FirebaseDatabase.getInstance(); //db연결
        DatabaseReference1 = Database.getReference().child("shopping");

        sum_number = holder.the_number.getText().toString();
        int_sum_number = Integer.parseInt(sum_number);
        String holder_price = holder.book_price.getText().toString();

        int holder_price1 = Integer.parseInt(holder_price.replaceAll(",",""));
        int_sum_number = int_sum_number*holder_price1;
        Intent Sum_number_intent = new Intent();
        Sum_number_intent.putExtra("값",int_sum_number);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1 = position;
                title = holder.book_title.getText().toString();


                Map<String, Object> mHashmap = new HashMap<>();

                mHashmap.put("the_number","0");
                DatabaseReference1.child(snum).child(title).updateChildren(mHashmap);

                DatabaseReference1.child(snum).child(title).removeValue();
                Toast.makeText(context, title + "이 " + "장바구니에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public int getItemCount () {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0); //참이면 ? 뒤 실행 거짓이면 :뒤 실행
    }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView book_image;
            TextView book_title;
            TextView book_price;
            TextView book_author;
            TextView book_publisher,s_num;
            TextView the_number;
            Button delete;
            private book book;

            public CustomViewHolder(@NonNull View itemView) {

                super(itemView);

                this.book_image = itemView.findViewById(R.id.book_image_text);
                this.book_title = itemView.findViewById(R.id.book_title_text);
                this.book_price = itemView.findViewById(R.id.book_price_text);
                this.book_author = itemView.findViewById(R.id.book_author_text);
                this.book_publisher = itemView.findViewById(R.id.book_publisher_text);
                this.s_num = itemView.findViewById(R.id.item_s_num);
                this.delete=itemView.findViewById(R.id.shopping_delete);
                this.the_number=itemView.findViewById(R.id.the_number_edit);
            }
        }

    public MyShoppingBook(String title, String price, String author, String publisher, String image,String s_num){
            this.title = title;
            this.price = price;
            this.author = author;
            this.publisher = publisher;
            this.image = image;
            this.s_num=s_num;
        }
}