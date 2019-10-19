package com.example.project;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.databinding.ActivityShowAllBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity {

    ActivityShowAllBinding binding;
    ArrayList<String> IDArray=new ArrayList<String>();
    ArrayList<String> STATUSArray=new ArrayList<String>();
    ArrayList<String> MOBILEArray=new ArrayList<String>();
    ArrayList<String> AMOUNTArray=new ArrayList<String>();
    ArrayList<String> STYPEArray=new ArrayList<String>();
    ArrayList<String> DATETIMEArray=new ArrayList<String>();
    ArrayList<String> ENDDATEArray=new ArrayList<String>();
    ArrayList<String> REASONArray=new ArrayList<String>();
    String ID,STYPE,STATUS,REASON,MOBILE,AMOUNT,DATETIME,ENDDATE;
    String url="http://androindian.com/apps/fm/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(ShowAll.this,R.layout.activity_show_all);

        SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        String phone=sharedPreferences.getString("Mobile",null);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("action","get_savings");
            jsonObject.put("stype","S");
            jsonObject.put("user_id",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Res",jsonObject.toString());

        LoadData loadData=new LoadData();
        loadData.execute(jsonObject.toString());
    }

    private class LoadData extends AsyncTask<String,String,String> {

        Dialog dialog=new Dialog(ShowAll.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setContentView(R.layout.custom);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            JSONObject jsonObject=JsonFuntion.DataHandle(url,param[0]);
            Log.i("Res",jsonObject.toString());

            return jsonObject.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            try {
                JSONObject object=new JSONObject(s);

                String res=object.getString("response");
                if (res.equalsIgnoreCase("success")){

                    //array
                    JSONArray jsonArray=object.getJSONArray("data");
                    Log.i("Res",jsonArray.toString());
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject j2=jsonArray.getJSONObject(i);
                        //array closed

                        ID=j2.getString("id");
                        Log.i("Res", ID);

                        MOBILE=j2.getString("mobile_no");
                        Log.i("Res",MOBILE);

                        AMOUNT=j2.getString("amount");
                        Log.i("Res",AMOUNT);

                        STYPE=j2.getString("stype");
                        Log.i("Res",STYPE);

                        DATETIME=j2.getString("datetime");
                        Log.i("Res",DATETIME);

                        ENDDATE=j2.getString("enddate");
                        Log.i("Res",ENDDATE);

                        REASON=j2.getString("reason");
                        Log.i("Res",REASON);

                        STATUS=j2.getString("status");
                        Log.i("Res",STATUS);

                        IDArray.add(ID);
                        Log.i("Res",IDArray.toString());
                        MOBILEArray.add(MOBILE);
                        Log.i("Res",MOBILEArray.toString());
                        AMOUNTArray.add(AMOUNT);
                        Log.i("Res",AMOUNTArray.toString());
                        STYPEArray.add(STYPE);
                        Log.i("Res",STYPEArray.toString());
                        DATETIMEArray.add(DATETIME);
                        Log.i("Res",DATETIMEArray.toString());
                        ENDDATEArray.add(ENDDATE);
                        Log.i("Res",ENDDATEArray.toString());
                        REASONArray.add(REASON);
                        Log.i("Res",REASONArray.toString());
                        STATUSArray.add(STATUS);
                        Log.i("Res",STATUSArray.toString());

                    }

                    //rec
                    LinearLayoutManager layoutManager=new LinearLayoutManager(ShowAll.this, RecyclerView.VERTICAL,false);
                    binding.recycler.setLayoutManager(layoutManager);
                    binding.recycler.setAdapter(new AppAdb(ShowAll.this,IDArray,MOBILEArray,AMOUNTArray,STYPEArray,DATETIMEArray,ENDDATEArray,REASONArray,STATUSArray));
                }
                else {
                    Toast.makeText(ShowAll.this,"No Data",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
