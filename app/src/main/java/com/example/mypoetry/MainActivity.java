package com.example.mypoetry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mypoetry.adapter.poetry_adapter;
import com.example.mypoetry.api.Poetry_response;
import com.example.mypoetry.api.api_interface;
import com.example.mypoetry.api.apiclient;
import com.example.mypoetry.api.delete_response;
import com.example.mypoetry.modelclass.poetry_class;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
RecyclerView rv;Retrofit retrofit;api_interface api_interface;
ArrayList<poetry_class> list;
FloatingActionButton addbtn;
AlertDialog.Builder alertdialogBuilder2;
TextInputLayout poetry,poet_name;
Button addbtn_alertbox;
ImageButton cancel_btn_alert;
LinearLayout lv;
AlertDialog alertDialog;
View add_poetry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        add_poetry = li.inflate(R.layout.add_service_alertbox, null);
        alertdialogBuilder2 = new AlertDialog.Builder(
                MainActivity.this);
        alertdialogBuilder2
                .setCancelable(false);
        alertdialogBuilder2.setView(add_poetry);
        alertDialog=alertdialogBuilder2.create();
        cancel_btn_alert=add_poetry.findViewById(R.id.cancel_addservicelayout);
        poetry=add_poetry.findViewById(R.id.poetry_addalertbox);
        poet_name=add_poetry.findViewById(R.id.poet_name_addalertbox);
        addbtn_alertbox=add_poetry.findViewById(R.id.add_poetry_inalertbox);
        lv=add_poetry.findViewById(R.id.l_layout_addpoetry);

        list=new ArrayList<>();
        addbtn=findViewById(R.id.add_poetry);
        rv=findViewById(R.id.rv);
         retrofit= apiclient.getRetrofit();
        api_interface=retrofit.create(com.example.mypoetry.api.api_interface.class);

      getdata();


      cancel_btn_alert.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              alertDialog.dismiss();
          }
      });

      addbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

        lv.setBackgroundColor(Color.parseColor("#9DF1C6"));
              alertDialog.show();
          }
      });
addbtn_alertbox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alertDialog.dismiss();
            api_interface.addportry(poetry.getEditText().getText().toString(),poet_name.getEditText().getText().toString()).enqueue(new Callback<delete_response>() {
                @Override
                public void onResponse(Call<delete_response> call, Response<delete_response> response) {
                    try{
                        getdata();

                        if(response!=null){
                            if(response.body().getStatus().equals("1")){
                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Log.d("faill",e.getLocalizedMessage());
                    }
                }

                @Override
                public void onFailure(Call<delete_response> call, Throwable t) {
                Log.d("faill",t.getLocalizedMessage());
                }
            });
    }
});

    }


    public void getdata(){
        api_interface.getpoetry().enqueue(new Callback<Poetry_response>() {
            @Override
            public void onResponse(Call<Poetry_response> call, Response<Poetry_response> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            list= (ArrayList<poetry_class>) response.body().getData();
                            poetry_adapter adapter=new poetry_adapter(list,MainActivity.this);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.d("faill",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<Poetry_response> call, Throwable t) {
                Log.d("ssssss",t.getLocalizedMessage());
            }
        });
    }
}