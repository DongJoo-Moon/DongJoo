package com.example.bookstore.ui.book;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.bookstore.ui.engineering_college.computer;
import com.google.common.base.MoreObjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBook extends RecyclerView.Adapter<MyBook.CustomViewHolder> {
    private ArrayList<book> arrayList;
    private Context context;
    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference,DatabaseReference1;
    private RecyclerView.Adapter adapter;
    private int position1;
    String title,price,author,publisher,stock,image,book1,the_number;


    public MyBook(ArrayList<book> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public MyBook.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        context = parent.getContext();
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyBook.CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getimage())
                .into(holder.book_image);
        holder.book_title.setText(arrayList.get(position).gettitle());
        holder.book_price.setText(arrayList.get(position).getprice());
        holder.book_author.setText(arrayList.get(position).getauthor());
        holder.book_publisher.setText(arrayList.get(position).getpublisher());
        holder.book_stock.setText(arrayList.get(position).getstock());
        //holder.the_number.setText(arrayList.get(position).getstock());

        final String snum = loginActivity.id_Snum;

        Database = FirebaseDatabase.getInstance(); //db연결
        DatabaseReference = Database.getReference("computer_book"); //db테이블 연결
        DatabaseReference1 = Database.getReference().child("shopping");



        holder.itemView.setTag(position);

        holder.book_Purchasebtn.setOnClickListener(new View.OnClickListener() { // 구매하기 클릭시
            @Override
            public void onClick(View v) {
                position1=position;
                title = holder.book_title.getText().toString();
                title = holder.book_title.getText().toString();
                price = holder.book_price.getText().toString();
                author = holder.book_author.getText().toString();
                publisher = holder.book_publisher.getText().toString();
                stock = holder.book_stock.getText().toString();
                //image = arrayList.get(position1).getimage();
                /////////////////////////////////////////////////////


                final EditText edittext = new EditText(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("구매");

                builder.setMessage("개수를 입력해주세요.");


                builder.setView(edittext);
                builder.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                the_number = edittext.getText().toString();


                               /* Map<String, Object> mHashmap = new HashMap<>();
                                mHashmap.put("title", title);
                                mHashmap.put("price", price);
                                mHashmap.put("author", author);
                                mHashmap.put("publisher", publisher);
                                mHashmap.put("image",  image);
                                mHashmap.put("shopping_S_number",snum);
                                mHashmap.put("the_number",the_number);*/


                                DatabaseReference.child(title).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//반복문으로 데이터 list 추
                                            String stock = dataSnapshot.child("stock").getValue().toString();
                                            String shooping_stock = edittext.getText().toString();
                                            int int_shooping_stock = Integer.parseInt(shooping_stock);
                                            int int_stock = Integer.parseInt(stock);


                                            int_stock = int_stock-int_shooping_stock;
                                            stock = String.valueOf(int_stock);
                                            if(int_stock<0){//솔드아웃 구문
                                                Sold_out_dialog();
                                                break;
                                            }
                                            DatabaseReference.child(title).child("stock").setValue(stock);
                                            Toast.makeText(context,  "stock = "+stock, Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        //
                                    }

                                });

                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });

        holder.book_shoppingbtn.setOnClickListener(new View.OnClickListener() {//장바구니 클릭시
            @Override
            public void onClick(View v) {
                position1 = position;
                title = holder.book_title.getText().toString();
                price = holder.book_price.getText().toString();
                author = holder.book_author.getText().toString();
                publisher = holder.book_publisher.getText().toString();
                image = arrayList.get(position1).getimage();
                //the_number = holder.the_number.getText().toString();

                final EditText edittext = new EditText(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("장바구니 개수 입력");
                builder.setMessage("개수를 입력해주세요.");
                builder.setView(edittext);
                builder.setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                the_number = edittext.getText().toString();


                                Map<String, Object> mHashmap = new HashMap<>();
                                mHashmap.put("title", title);
                                mHashmap.put("price", price);
                                mHashmap.put("author", author);
                                mHashmap.put("publisher", publisher);
                                mHashmap.put("image",  image);
                                mHashmap.put("shopping_S_number",snum);
                                mHashmap.put("the_number",the_number);


                                DatabaseReference1.child(snum).child(title).updateChildren(mHashmap);

                                Toast.makeText(context, title + "이 " + "장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0); //참이면 ? 뒤 실행 거짓이면 :뒤 실행
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView book_image;
        TextView book_title;
        TextView book_price;
        TextView book_author;
        TextView book_publisher;
        TextView book_stock;
        Button book_Purchasebtn,book_shoppingbtn;
        EditText the_number;
        private book book;



        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);


            this.book_image = itemView.findViewById(R.id.book_image_text);
            this.book_title = itemView.findViewById(R.id.book_title_text);
            this.book_price = itemView.findViewById(R.id.book_price_text);
            this.book_author = itemView.findViewById(R.id.book_author_text);
            this.book_publisher = itemView.findViewById(R.id.book_publisher_text);
            this.book_stock = itemView.findViewById(R.id.stock_text);
            this.book_Purchasebtn = itemView.findViewById(R.id.Purchasebtn);
            this.book_shoppingbtn = itemView.findViewById(R.id.shoppingbtn);
            //this.the_number = itemView.findViewById(R.id.the_number_edit);





        }
    }
    public MyBook(String title,String price,String author,String publisher,String stock,String image,String the_number){
        this.title = title;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
        this.stock = stock;
        this.image = image;
        this.the_number=the_number;
    }
    public void Sold_out_dialog() {

        androidx.appcompat.app.AlertDialog.Builder oDialog = new androidx.appcompat.app.AlertDialog.Builder(context,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        String strHtml =
                "<b>구매하려는 제품의 재고보다 </b>  현재 재고가 부족한 제품 입니다. <br/>"+title+"<b>의 제품 재고량 :</b>"+stock;//<font color='#ff0000'> 재고가 부족한</font>
        Spanned oHtml;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            oHtml = Html.fromHtml(strHtml);
        } else {
            oHtml = Html.fromHtml(strHtml, Html.FROM_HTML_MODE_LEGACY);
        }

        oDialog.setTitle("")
                .setMessage(oHtml)
                .setPositiveButton("확인", null)
                .setCancelable(false)
                .show();

    }


}