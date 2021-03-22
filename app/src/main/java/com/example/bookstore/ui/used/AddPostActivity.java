package com.example.bookstore.ui.used;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.bookstore.BasicActivity;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class
AddPostActivity extends BasicActivity {


    // private static final String TAG="WritePostActivity";
    //private FirebaseUser user;
    private static final String TAG = "MainActivity";
    final static loginActivity loginactivity = new loginActivity();
    private static String id_name = loginActivity.id_name;
    private static String snum = loginActivity.id_Snum;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    StorageReference storageRef;

    private EditText editdt, Price, contents;
    private String number;
    private TextView IdViewtext,used_number;
    private Button check, add_file;
    //public String msg, price_msg, contents_msg;
    //String getId_name;
    private ImageView ivPreview;
    private Uri filePath;
    //숫자 콤마 표시
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result="";
    private int int_number;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        check = (Button) findViewById(R.id.check);
        add_file = (Button) findViewById(R.id.add_file);

        editdt = (EditText) findViewById(R.id.titleEditText);
        Price = (EditText) findViewById(R.id.PriceEditText);
        contents = (EditText) findViewById(R.id.contentsEditText);

        ivPreview = (ImageView) findViewById(R.id.iv_preview);

        IdViewtext = (TextView) findViewById(R.id.IdViewtext);
        used_number= (TextView)findViewById(R.id.Item_number);
        IdViewtext.setText(id_name);

        Price.addTextChangedListener(watcher);

        //TextView textView = (TextView) findViewById(R.id.idtext_main); //
        add_file.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사진첨부
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });
        check.setOnClickListener(new Button.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //게시글 작성 버튼
                //  used(String title, String content, String id, int price, String snum,String date,int layouttype){
                //게시글에 작성이 안되있으면 작성안내 toast
                if (editdt.getText().toString().getBytes().length <= 0 ||
                        Price.getText().toString().getBytes().length <= 0 ||
                        contents.getText().toString().getBytes().length <= 0) {
                    Toast.makeText(AddPostActivity.this, "제목,내용,가격을 작성해주세요.", Toast.LENGTH_LONG).show();


                }//제목 내용 가격 작성이 되있으면 게시글 등록.
                else {

                    uploadFile();

                }

            }
        });
        databaseReference.child("used").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapShot : snapshot.getChildren()) {
                    number =snapShot.getKey();
                    int_number =  Integer.parseInt(number);
                    int_number++;
                    number =Integer.toString(int_number);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
        //숫자 콤마표시
        TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                Price.setText(result);
                Price.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();

            Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.

             storageRef = storage.getReferenceFromUrl("gs://bookstore-51361.appspot.com/").child("images/" + filename);


            //올라가거라...



            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            //url 불러오는 작업 밑 파이어베이스에 used 어댑터형식에 맞게 저장
                            UploadTask uploadTask  = storageRef.putFile(filePath);
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();}
                                    return  storageRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        //url 불러오는 작업 밑 파이어베이스에 used 어댑터형식에 맞게 저장
                                        Uri downloadUri = task.getResult();

                                        SimpleDateFormat dateform = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = new Date();


                                        used used = new used(editdt.getText().toString(),
                                                contents.getText().toString(),
                                                id_name,
                                                Price.getText().toString(),
                                                snum,
                                                dateform.format(date),
                                                downloadUri.toString(),
                                                number,"판매 중"
                                                );
                                        databaseReference.child("used").child(number).setValue(used);
                                        }
                                    else {
                                        // Handle failures
                                        // ...
                                    }
                                }
                            });




                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다.
                                    double progress = (50 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }


    }

    /*private String get_number() {
        databaseReference.child("used_number").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapShot : snapshot.getChildren()) {
                    String key = snapShot.getKey();
                    number = snapShot.child("used_number").getValue().toString();
                    int_number =  Integer.parseInt(number);
                    int_number = int_number++;
                    number =Integer.toString(int_number);



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }*/


}
