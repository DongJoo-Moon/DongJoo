package com.example.bookstore.ui.used;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.bookstore.R;

public class MyCustomDialog extends Dialog {
    private TextView dig_title,dig_content,dig_id,dig_price,dig_snum,dig_date,dig_sales;
    private String title,content,id,price,snum,date,profile,sales;
    private ImageView dig_profile;
    private Button chat_Button;
    private Button corrected_button;
    private Button close_button;
    private Button delete_button;
    private View.OnClickListener chat_Button_Listener;
    private View.OnClickListener corrected_button_Listener;
    private View.OnClickListener close_button_Listener;
    private View.OnClickListener delete_button_Listener;
    private Context context;

    /*public MyCustomDialog(@NonNull Context context) {
        super(context);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.edit_box);


        dig_title = (TextView) findViewById(R.id.dig_title);
        dig_content= (TextView) findViewById(R.id.dig_content);
        dig_id= (TextView) findViewById(R.id.dig_id);
        dig_price =(TextView) findViewById(R.id.dig_price);
        dig_snum = (TextView) findViewById(R.id.dig_snum);
        dig_date= (TextView) findViewById(R.id.dig_date);
        dig_sales=(TextView)findViewById(R.id.dig_sales);
        dig_profile= (ImageView) findViewById(R.id.dig_profile);

        chat_Button = (Button) findViewById(R.id.dig_chatting_button_ok);
        corrected_button = (Button) findViewById(R.id.dig_corrected_button);
        close_button = (Button) findViewById(R.id.dig_Close_button);
        delete_button = (Button) findViewById(R.id.dig_Delete);

        chat_Button.setOnClickListener(chat_Button_Listener);
        corrected_button.setOnClickListener(corrected_button_Listener);
        close_button.setOnClickListener(close_button_Listener);
        delete_button.setOnClickListener(delete_button_Listener);



        dig_title.setText(title);
        dig_content.setText(content);
        dig_id.setText(id);
        dig_price.setText(price);
        dig_snum.setText(snum);
        dig_date.setText(date);
        dig_sales.setText(sales);


       /* Intent intent = new Intent();
        String image1 =intent.getExtras().getString("IMAGE1");*/
       // StorageReference ref = FirebaseStorage.getInstance().getReference("images/"+image1);


        Glide.with(getContext())
                .load(profile)
                .into(dig_profile);

    }

    public MyCustomDialog(@NonNull Context context, View.OnClickListener chat_Button_Listener, View.OnClickListener corrected_button_Listener, View.OnClickListener close_button_Listener, View.OnClickListener delete_button_Listener
                          , String title, String content, String id, String price, String snum, String date, String profile,String sales) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.chat_Button_Listener = chat_Button_Listener;
        this.corrected_button_Listener = corrected_button_Listener;
        this.close_button_Listener = close_button_Listener;
        this.delete_button_Listener = delete_button_Listener;
        this.title=title;
        this.content=content;
        this.id=id;
        this.price=price;
        this.snum=snum;
        this.date=date;
        this.profile=profile;
        this.sales=sales;


    }



}

