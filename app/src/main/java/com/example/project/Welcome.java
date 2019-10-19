package com.example.project;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.example.project.databinding.ActivityWelcomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class Welcome extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    ConnectionChecking checking;
    String url="http://androindian.com/apps/fm/api.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(Welcome.this,R.layout.activity_welcome);

        checking=new ConnectionChecking();

        SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        /*Intent intent=getIntent();*/
        String sp=sharedPreferences.getString("Mobile",null);
        binding.phone.setText(sp);

        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcome.this,ShowAll.class);
                startActivity(intent);
            }
        });

        binding.bsaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checking.isConnectingToInternet(Welcome.this)){

                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("action","put_saving");
                        jsonObject.put("mobile",binding.phone.getText().toString().trim());
                        jsonObject.put("stype","S");
                        jsonObject.put("reason",binding.reason.getText().toString().trim());
                        jsonObject.put("amount",binding.amount.getText().toString().trim());
                        jsonObject.put("start_date",binding.start.getText().toString().trim());
                        jsonObject.put("end_date",binding.end.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("Res",jsonObject.toString());

                    LoadData loadData=new LoadData();
                    loadData.execute(jsonObject.toString());

                }
                else {
                    Dialog dialog=new Dialog(Welcome.this);
                    dialog.setContentView(R.layout.networkerror);
                    dialog.setCancelable(true);
                    Button button=dialog.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private class LoadData extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog=new ProgressDialog(Welcome.this);
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
                if (s1.equalsIgnoreCase("success")){
                    String s2=j1.getString("call_back");
                    Toast.makeText(Welcome.this,s2,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
