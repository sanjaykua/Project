package com.example.project;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ConnectionChecking checking;
    String url="http://androindian.com/apps/fm/api.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);

        checking=new ConnectionChecking();
        binding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checking.isConnectingToInternet(MainActivity.this)){
                    //1
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name",binding.name.getText().toString().trim());
                        jsonObject.put("mobile",binding.phone.getText().toString().trim());
                        jsonObject.put("email",binding.email.getText().toString().trim());
                        jsonObject.put("pswrd",binding.pass.getText().toString().trim());
                        jsonObject.put("action","register_user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("request",jsonObject.toString());

                    //2
                    RegisterUser registerUser=new RegisterUser();
                    registerUser.execute(jsonObject.toString());

                }else {
                    Dialog dialog=new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.networkerror);
                    dialog.setCancelable(false);
                    Button button=dialog.findViewById(R.id.bottom);
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

    private class RegisterUser extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Content Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
            progressDialog.dismiss();

            try {
                JSONObject j1=new JSONObject(s);
                String s1=j1.getString("response");
                if (s1.equalsIgnoreCase("failed")){
                    String s2=j1.getString("user");
                    Toast.makeText(MainActivity.this,s2,Toast.LENGTH_SHORT).show();
                }
                else if (s1.equalsIgnoreCase("success")){
                    String s2=j1.getString("user");
                    Toast.makeText(MainActivity.this,s2,Toast.LENGTH_SHORT).show();
                }else {
                    String s2=j1.getString("user");
                    Toast.makeText(MainActivity.this,s2,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
