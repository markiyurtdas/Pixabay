package com.dev.marki.pixabay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.dev.marki.pixabay.Data.Item;
import com.dev.marki.pixabay.Util.ItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity   {
    public String search;
    private RecyclerView mRecycView;
    private ItemAdapter mAdapter;
    private ArrayList<Item> mItemList;
    private RequestQueue mRequestQueue;
    private final int OVERLAY_PERMISSION_REQ_CODE = 1;  // Choose any value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(MainActivity.this,Search.class));


    }



}



