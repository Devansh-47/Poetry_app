package com.example.mypoetry.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypoetry.MainActivity;
import com.example.mypoetry.R;
import com.example.mypoetry.api.Poetry_response;
import com.example.mypoetry.api.api_interface;
import com.example.mypoetry.api.apiclient;
import com.example.mypoetry.api.delete_response;
import com.example.mypoetry.modelclass.poetry_class;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class poetry_adapter extends RecyclerView.Adapter<poetry_adapter.ViewHolder> {
    private ArrayList<poetry_class> list;
    private Context context;
    View view,update_poetry;
    AlertDialog.Builder alertdialogBuilder2;
    AlertDialog alertDialog;
    ImageButton cancel_btn_alert;
    Button updatebtn_alertbox;
    TextInputLayout poetry,poet_name;
    api_interface apiInterface;
    static int count=0;
    static String[] colors={"#F8DFC2","#F8F3C2","#E2C2F8","#EAF8C2","#F8C2F5","#D5F8C2","#C2F8D8","#C2E5F8"};


    public poetry_adapter(ArrayList<poetry_class> listt, Context context){

        list=listt;
        Retrofit retrofit= apiclient.getRetrofit();
        apiInterface=retrofit.create(api_interface.class);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view= LayoutInflater.from(context)
                .inflate(R.layout.poetry_card, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        count=count+1;
        count=count%6;

    holder.getLl().setBackgroundColor(Color.parseColor(colors[count]));

        holder.getPoetry().setText(list.get(position).getPoetry());
        holder.getPoet_name().setText(list.get(position).getPoet_name());
        holder.getDate().setText(list.get(position).getDate());
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.deleteportry(list.get(holder.getAdapterPosition()).getId()+"").enqueue(new Callback<delete_response>() {
                    @Override
                    public void onResponse(Call<delete_response> call, Response<delete_response> response) {
                        try{
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                if(response.body().getStatus().equals("1")){
                                    list.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                        }catch (Exception e){
                            Log.d("ssssss",e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<delete_response> call, Throwable t) {
Log.d("ssssss",t.getLocalizedMessage());
                    }
                });
            }
        });
        holder.getUpdate().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                update_poetry = li.inflate(R.layout.add_service_alertbox, null);
                alertdialogBuilder2 = new AlertDialog.Builder(
                        context);
                alertdialogBuilder2
                        .setCancelable(false);
                alertdialogBuilder2.setView(update_poetry);
                alertDialog=alertdialogBuilder2.create();
                cancel_btn_alert=update_poetry.findViewById(R.id.cancel_addservicelayout);
                poetry=update_poetry.findViewById(R.id.poetry_addalertbox);
                poet_name=update_poetry.findViewById(R.id.poet_name_addalertbox);
                updatebtn_alertbox=update_poetry.findViewById(R.id.add_poetry_inalertbox);
                updatebtn_alertbox.setText("Update");
                Objects.requireNonNull(poetry.getEditText()).setText(list.get(holder.getAdapterPosition()).getPoetry()+"");
                Objects.requireNonNull(poet_name.getEditText()).setText(list.get(holder.getAdapterPosition()).getPoet_name()+"");
                alertDialog.show();

                cancel_btn_alert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                updatebtn_alertbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        apiInterface.updateportry(poetry.getEditText().getText().toString(),poet_name.getEditText().getText().toString(),list.get(holder.getAdapterPosition()).getId()).enqueue(new Callback<delete_response>() {
                            @Override
                            public void onResponse(Call<delete_response> call, Response<delete_response> response) {
                                try{
                                    assert response.body() != null;
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    if(response.body().getStatus().equals("1")){
                                        apiInterface.getpoetry().enqueue(new Callback<Poetry_response>() {
                                            @Override
                                            public void onResponse(Call<Poetry_response> call, Response<Poetry_response> response) {
                                                try{
                                                    if(response!=null){
                                                        if(response.body().getStatus().equals("1")){
                                                            list= (ArrayList<poetry_class>) response.body().getData();
                                                            notifyDataSetChanged();
                                                        }else {
                                                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                                }catch (Exception e){
                                    Log.d("ssssss",e.getLocalizedMessage());
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
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView poet_name,date,poetry;
        private Button update,delete;
        private LinearLayout ll;

        private MaterialCardView cv;
        public ViewHolder(View view) {
            super(view);
            cv=view.findViewById(R.id.carditem);
            poetry = view.findViewById(R.id.poetry);
            poet_name = view.findViewById(R.id.poet_name);
            date = view.findViewById(R.id.date);
            update = view.findViewById(R.id.update_btn);
            delete = view.findViewById(R.id.delete_btn);
            ll=view.findViewById(R.id.l_layout);
        }

        public LinearLayout getLl() {
            return ll;
        }

        public MaterialCardView getCv() {
            return cv;
        }

        public TextView getPoet_name() {
            return poet_name;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getPoetry() {
            return poetry;
        }

        public Button getUpdate() {
            return update;
        }

        public Button getDelete() {
            return delete;
        }
    }
}