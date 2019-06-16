package com.example.win10.jakmall;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llList;
    private LayoutInflater layoutInflater;
    private TextView tvAdd;
    private int data;
    private ArrayList<String> arrJoke, arrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdd = findViewById(R.id.tv_add);
        llList = findViewById(R.id.ll_list);
        arrJoke = new ArrayList<>();
        arrId = new ArrayList<>();

        layoutInflater =
                 (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tvAdd.setOnClickListener(this);

        setDefault();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addRow();
                break;
        }
    }
    private void addRow() {
        if (data!=5){
            final View row = layoutInflater.inflate(R.layout.row,null);
            TextView tvContent = row.findViewById(R.id.tv_content);
            ImageView ivUp = row.findViewById(R.id.iv_up);

            tvContent.setText(arrJoke.get(data));

            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = ((LinearLayout) row.getParent()).indexOfChild(row);

                    AlertDialog.Builder jokeDialog = new AlertDialog.Builder(MainActivity.this);
                    jokeDialog.setTitle("Joke #" + arrId.get(index))
                            .setMessage(arrJoke.get(index))
                            .setPositiveButton("Haha", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(),"Chuck Norris likes that", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Close",null)
                            .create();

                    if(index==0){
                        jokeDialog.setTitle("Joke #" + arrId.get(index)+" - Funniest Joke");
                    }
                    jokeDialog.show();
                }
            });

            ivUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = ((LinearLayout) row.getParent()).indexOfChild(row);
                    Collections.swap(arrJoke,index, 0);
                    Collections.swap(arrId,index,0);
                    Collections.rotate(arrJoke.subList(1,index+1),1);
                    Collections.rotate(arrId.subList(1,index+1),1);

                    llList.removeAllViews();
                    int total = data;
                    data=0;
                    for(int i=0; i<total; i++){
                        addRow();
                    }
                    Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_SHORT).show();
                }
            });

            if(data==0){
                ivUp.setImageResource(R.drawable.first);
                ivUp.setPadding(8,16,8,16);
            }
            llList.addView(row);
            data++;

            if (data == 5) {
                tvAdd.setText(R.string.refresh);
            }
        }
        else{
            setDefault();
        }
    }

    public void getText(){
        Resources res = getResources();
        arrJoke.addAll(Arrays.asList(res.getStringArray(R.array.joke)));
        arrId.addAll(Arrays.asList(res.getStringArray(R.array.id)));
    }

    private void setDefault(){
        llList.removeAllViews();
        data=0;

        getText();
        addRow();
        addRow();
        addRow();

        tvAdd.setText(R.string.add_more);
    }
}
