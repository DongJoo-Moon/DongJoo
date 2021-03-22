package com.example.bookstore.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookstore.BasicActivity;
import com.example.bookstore.R;

public class purchase_completed extends BasicActivity {

    Button Pick_up_position,Close_but;
    @Override
    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.purchase_completed);

        Pick_up_position = (Button)findViewById(R.id.position_but);
        Close_but = (Button)findViewById(R.id.Close_but);

        Pick_up_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EA%B2%BD%EB%82%A8+%EC%B0%BD%EC%9B%90%EC%8B%9C+%EB%A7%88%EC%82%B0%ED%95%A9%ED%8F%AC%EA%B5%AC+%EA%B2%BD%EB%82%A8%EB%8C%80%ED%95%99%EB%A1%9C+7+%28%EC%9B%94%EC%98%81%EB%8F%99%2C+%EA%B2%BD%EB%82%A8%EB%8C%80%ED%95%99%EA%B5%90%29+%EC%9B%94%EC%98%81%EB%8F%99449&oquery=%EA%B2%BD%EB%82%A8+%EC%B0%BD%EC%9B%90%EC%8B%9C+%EB%A7%88%EC%82%B0%ED%95%A9%ED%8F%AC%EA%B5%AC+%EA%B2%BD%EB%82%A8%EB%8C%80%ED%95%99%EB%A1%9C+7+%EA%B2%BD%EB%82%A8%EB%8C%80%ED%95%99%EA%B5%90+%EA%B5%AC%EB%82%B4%EC%84%9C%EC%A0%90&tqi=U2EwUwprvhGss44uRthssssstws-197725"));
                startActivity(intent);
            }
        });
        Close_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
