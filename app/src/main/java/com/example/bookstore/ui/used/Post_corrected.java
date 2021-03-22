package com.example.bookstore.ui.used;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Post_corrected extends Dialog {
    private TextView dig_id,dig_snum,dig_date;
    private EditText dig_title,dig_content, dig_price;
    private String title,content,id,price,snum,date,profile,used_number;
    private String Title_c,Content_c,Price_c,sale_c="판매 중";
    private String now_number;

    private RadioButton onSale, salesCompleted;
    private RadioGroup saleRadioGroup;



    private ImageView dig_profile;

    private FirebaseDatabase Database;
    private com.google.firebase.database.DatabaseReference DatabaseReference,reference;

    private Button corrected_button_ok,dig_Close_button_c;
    //====콤마기능
    private String result="";
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    //
    private Context context;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.edit_box_corrected);

        onSale = (RadioButton) findViewById(R.id.onSale);
        salesCompleted = (RadioButton) findViewById(R.id.salesCompleted);
        saleRadioGroup = (RadioGroup) findViewById(R.id.radioGroup); saleRadioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);




        dig_title = (EditText) findViewById(R.id.dig_title_edit);
        dig_content= (EditText) findViewById(R.id.dig_content_edit);
        dig_price =(EditText) findViewById(R.id.dig_price_edit);
        dig_price.addTextChangedListener(watcher); // 숫자 콤마

        corrected_button_ok=(Button)findViewById(R.id.dig_corrected_button_ok);
        dig_Close_button_c=(Button)findViewById(R.id.dig_Close_button_c);

        dig_id= (TextView) findViewById(R.id.dig_id);
        dig_snum = (TextView) findViewById(R.id.dig_snum);
        dig_date= (TextView) findViewById(R.id.dig_date);

        dig_profile= (ImageView)findViewById(R.id.dig_profile_c);


        dig_title.setText(title);
        dig_content.setText(content);
        dig_id.setText(id);
        dig_price.setText(price);
        dig_snum.setText(snum);
        dig_date.setText(date);

        Glide.with(getContext())
                .load(profile)
                .into(dig_profile);
        Database = FirebaseDatabase.getInstance();//db연결
        DatabaseReference = Database.getReference("used");
        dig_Close_button_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        corrected_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Title_c = dig_title.getText().toString();
                Content_c = dig_content.getText().toString();
                Price_c = dig_price.getText().toString();


                DatabaseReference = Database.getReference("used");//db테이블 연결

                DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapShot : snapshot.getChildren()) {
                            now_number = snapShot.getKey();
                            if(now_number.equals(used_number)) {

                                String pushKey = snapShot.getKey();
                                Map<String, Object> taskMap = new HashMap<String, Object>();
                                taskMap.put("title", Title_c);
                                taskMap.put("content", Content_c);
                                taskMap.put("price", Price_c);
                                taskMap.put("sale",sale_c);


                                DatabaseReference.child(pushKey).updateChildren(taskMap);

                                if(sale_c.equals("판매 완료")){
                                    reference = FirebaseDatabase.getInstance().getReference().child(used_number);
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    key = reference.push().getKey();
                                    reference.updateChildren(map);


                                    //com.google.firebase.database.DatabaseReference root = reference.child(used_number);

                                    Map<String, Object> objectMap = new HashMap<String, Object>();
                                    objectMap.put("name","SYSTEM");
                                    objectMap.put("message","판매가 완료된 상품입니다.");

                                    reference.child(key).updateChildren(objectMap);
                                }



                                Toast.makeText(getContext(), "수정완료" , Toast.LENGTH_SHORT).show();
                                dismiss();

                            }
                            else{}
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.onSale){
                Toast.makeText(getContext(), "판매 중 버튼을 체크했습니다.", Toast.LENGTH_SHORT).show();
                sale_c="판매 중";
            }
            else if(i == R.id.salesCompleted){
                Toast.makeText(getContext(), "판매 완료 버튼을 체크했습니다.", Toast.LENGTH_SHORT).show();
                sale_c="판매 완료";
            }
        }
    };



    //숫자 콤마표시
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                dig_price.setText(result);
                dig_price.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    };





    public Post_corrected(@NonNull Context context,
                          String title, String content, String id, String price, String snum, String date, String profile, String used_number) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title=title;
        this.content=content;
        this.price=price;
        this.id=id;
        this.snum=snum;
        this.date=date;
        this.profile=profile;
        this.used_number=used_number;

    }
}
