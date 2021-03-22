package com.example.bookstore;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BasicActivity extends AppCompatActivity {//화면을 안돌아가게끔하는 Activity
    @Override
    protected void onCreate(Bundle savedlnstanceState)  {
        super.onCreate(savedlnstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
