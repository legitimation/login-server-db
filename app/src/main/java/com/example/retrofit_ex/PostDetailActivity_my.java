package com.example.retrofit_ex;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class PostDetailActivity_my extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://172.10.18.137:80";

    String name, title, content;
    TextView Vname, Vtitle, Vcontent;
    Button deleteBtn, updateBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f3_myview);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        Vname = findViewById(R.id.name);
        Vtitle = findViewById(R.id.title);
        Vcontent = findViewById(R.id.content);



        Vname.setText("my name : " + name);
        Vtitle.setText("title : " + title);
        Vcontent.setText("content : \n" + content);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePost();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePost();
            }
        });

    }


    private void UpdatePost() {
        View view = getLayoutInflater().inflate(R.layout.f1_update, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailActivity_my.this);
        builder.setView(view).show();

        final EditText utitle = view.findViewById(R.id.utitle);
        final EditText ucontent = view.findViewById(R.id.ucontent);
        Button update = view.findViewById(R.id.update);

        utitle.setText(title);
        ucontent.setText(content);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("name", name);
                map.put("title", utitle.getText().toString());
                map.put("content", ucontent.getText().toString());

                Call<UpdateResult> call = retrofitInterface.executeUpdate(map);

                call.enqueue(new Callback<UpdateResult>() {
                    @Override
                    public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {

                        if (response.code() == 200) {

                            UpdateResult result = response.body();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(PostDetailActivity_my.this);
                            builder1.setTitle("Updated title : " + result.getTitle());
                            builder1.setMessage("Updated content : " + result.getContent());

                            builder1.show();

                        } else if (response.code() == 404) {
                            Toast.makeText(PostDetailActivity_my.this, "Title already exists",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateResult> call, Throwable t) {
                        Toast.makeText(PostDetailActivity_my.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void DeletePost() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete 선택").setMessage("정말 삭제하시겠습니까?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();

                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("title", title);

                Call<Void> call = retrofitInterface.deletePost(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(PostDetailActivity_my.this,
                                    "Deleted successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(PostDetailActivity_my.this,
                                    "There's no such title", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(PostDetailActivity_my.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}


