package com.example.project;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    ConnectionChecking checking;
    String url = "http://androindian.com/apps/fm/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(Login.this, R.layout.activity_login);

        checking = new ConnectionChecking();

        binding.newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checking.isConnectingToInternet(Login.this)) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("mobile", binding.mobile.getText().toString().trim());
                        jsonObject.put("pswrd", binding.pass.getText().toString().trim());
                        jsonObject.put("action", "login_user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("Res", jsonObject.toString());

                    LoginUser loginUser = new LoginUser();
                    loginUser.execute(jsonObject.toString());
                } else {
                    Dialog dialog = new Dialog(Login.this);
                    dialog.setContentView(R.layout.networkerror);
                    dialog.setCancelable(false);
                    Button button = dialog.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private class LoginUser extends AsyncTask<String, String, String> {

        Dialog dialog=new Dialog(Login.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setContentView(R.layout.custom);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            JSONObject object=JsonFuntion.DataHandle(url,param[0]);
            Log.i("Res",object.toString());
            return object.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            try {
                JSONObject j1=new JSONObject(s);
                String s1=j1.getString("response");

                if (s1.equalsIgnoreCase("success")){
                    String s2=j1.getString("data");
                    Toast.makeText(Login.this,s2,Toast.LENGTH_SHORT).show();
                    //sp
                    SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Mobile",binding.mobile.getText().toString().trim());
                    editor.putString("Pass",binding.pass.getText().toString().trim());
                    editor.commit();

                    //moving to next page
                    Intent intent=new Intent(Login.this,Welcome.class);
                    /*intent.putExtra("Mobile",binding.mobile.getText().toString().trim());*/
                    startActivity(intent);
                    finish();
                }
                else  if (s1.equalsIgnoreCase("failed")){
                    Toast.makeText(Login.this,"Failed",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
