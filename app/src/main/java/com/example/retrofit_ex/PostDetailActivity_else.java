package com.example.retrofit_ex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostDetailActivity_else extends AppCompatActivity {
    String name, title, content;
    TextView Vname, Vtitle, Vcontent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f3_elseview);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");

        Vname=findViewById(R.id.name);
        Vtitle=findViewById(R.id.title);
        Vcontent=findViewById(R.id.content);

        Vname.setText("name : "+name);
        Vtitle.setText("title : "+title);
        Vcontent.setText("content : \n"+content);
    }
}
