package com.example.retrofit_ex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//aa
public class Fragment1 extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
//    private String BASE_URL = "http://172.10.18.137:80";
    private String BASE_URL = "http://192.249.18.137:80";
    String name;

    private RecyclerView per_postRV;
    private PostVAdapter postRVAdapter;
    ArrayList<PostInfo> Postlist=new ArrayList<PostInfo>();
    ArrayList<PostInfo> personalPostlist=new ArrayList<PostInfo>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getActivity().getIntent();
        name=intent.getStringExtra("name");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_1, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<ResponseBody> call = retrofitInterface.getPost();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //List<JSONObject> result= response.body();
                //showPostResult
                if (response.code() == 500) {
                    Toast.makeText(getActivity(),
                            "database has failed", Toast.LENGTH_LONG).show();
                } else if (response.code() == 200) {
                    Toast.makeText(getActivity(),
                            "Uploaded successfully", Toast.LENGTH_LONG).show();

                    Gson gson = new Gson();
                    try {
                        String jsonString = response.body().string();
                        List<PostInfo> list = gson.fromJson(jsonString, new TypeToken<List<PostInfo>>() {
                        }.getType());

                        for (int i = 0; i < list.size(); i++) {
                            Postlist.add(new PostInfo(list.get(i).getName(), list.get(i).getTitle(), list.get(i).getContent()));
                        }


                        for (int i = 0; i < Postlist.size(); i++) {
                            if (name.equals(Postlist.get(i).getName())) {
                                personalPostlist.add(new PostInfo(Postlist.get(i).getName(), Postlist.get(i).getTitle(), Postlist.get(i).getContent()));
                            }
                        }

                        ////////////////////////
                        per_postRV = getView().findViewById(R.id.idRVContacts);
                        //on below line we are setting layout mnager.
                        per_postRV.setLayoutManager(new LinearLayoutManager(getContext()));
                        per_postRV.addItemDecoration(new DividerItemDecoration(getView().getContext(), 1));

                        postRVAdapter = new PostVAdapter(personalPostlist, name);
                        //on below line we are setting adapter to our recycler view.
                        per_postRV.setAdapter(postRVAdapter);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
    public void onResume() {
        super.onResume();
        personalPostlist=new ArrayList<PostInfo>();
        Postlist=new ArrayList<PostInfo>();
    }
}
