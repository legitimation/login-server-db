package com.example.retrofit_ex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
//import com.kakao.auth.KakaoSDK;
//import com.dooboolab.kakaologins.KakaoSDKAdapter;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {



    public ISessionCallback mSessionCallback;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    //    private String BASE_URL = "http://172.10.18.137:80";
    private String BASE_URL = "http://192.249.18.137:80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        KakaoSdk.init(this, "0fb90095ab2bcd323decd3974882152d");

//        if (KakaoSDK.getAdapter() == null) {
//            KakaoSDK.init(new KakaoSDKAdapter(getApplicationContext()));
//        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignupDialog();
            }
        });

        findViewById(R.id.kakao_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlekakaoLoginDialog();
            }
        });
    }


    private void handleLoginDialog() {

        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {

                            LoginResult result = response.body();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setTitle(result.getName());
                            builder1.setMessage(result.getEmail());

                            builder1.show();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name",result.getName());
                            startActivity(intent);

                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }



    private void handlekakaoLoginDialog() {

//        mSessionCallback = new ISessionCallback()
//        {
//            @Override
//            public void onSessionOpened()
//            {
//                // 로그인 요청
//                UserManagement.getInstance().me(new MeV2ResponseCallback()
//                {
//
//                    @Override
//                    public void onFailure(ErrorResult errorResult)
//                    {
//                        // 로그인 실패
//                        Toast.makeText(LoginActivity.this, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSessionClosed(ErrorResult errorResult)
//                    {
//                        // 세션이 닫힘..
//                        Toast.makeText(LoginActivity.this, "세션이 닫혔습니다.. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(MeV2Response result)
//                    {
//                        // 로그인 성공
//                        Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
//
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("email", result.getKakaoAccount().getEmail());
//                        map.put("password", "");
//
//                        Call<LoginResult> call = retrofitInterface.executeLogin(map);
//
//                        call.enqueue(new Callback<LoginResult>() {
//                            @Override
//                            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
//
//                                if (response.code() == 200) {
//
//                                    LoginResult result = response.body();
//
//                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
//                                    builder1.setTitle(result.getName());
//                                    builder1.setMessage(result.getEmail());
//
//                                    builder1.show();
//                                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                                    intent.putExtra("name",result.getName());
//                                    startActivity(intent);
//
//                                } else if (response.code() == 404) {
//                                    Toast.makeText(LoginActivity.this, "Wrong Credentials",
//                                            Toast.LENGTH_LONG).show();
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<LoginResult> call, Throwable t) {
//                                Toast.makeText(LoginActivity.this, t.getMessage(),
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                        Log.i("id", result.getKakaoAccount().getProfile().getNickname());
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//
//                        Toast.makeText(LoginActivity.this, "환영합니다 !", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onSessionOpenFailed(KakaoException exception)
//            {
//                Toast.makeText(LoginActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
//            }
//        };
//        Session.getCurrentSession().addCallback(mSessionCallback);
//        Session.getCurrentSession().checkAndImplicitOpen();

//        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setView(view).show();

        ImageButton loginBtn = findViewById(R.id.kakao_login_button);
//        final EditText emailEdit = view.findViewById(R.id.emailEdit);
//        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", "jeff426@naver.com");
                map.put("password", "");

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {

                            LoginResult result = response.body();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setTitle(result.getName());
                            builder1.setMessage(result.getEmail());

                            builder1.show();
                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name",result.getName());
                            startActivity(intent);

                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signupBtn = view.findViewById(R.id.signup);
        final EditText nameEdit = view.findViewById(R.id.nameEdit);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("name", nameEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(LoginActivity.this,
                                    "Signed up successfully", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(LoginActivity.this,
                                    "Already registered", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}