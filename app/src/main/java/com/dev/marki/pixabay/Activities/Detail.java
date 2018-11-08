package com.dev.marki.pixabay.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.marki.pixabay.Data.Item;
import com.dev.marki.pixabay.R;
import com.squareup.picasso.Picasso;


public class Detail extends AppCompatActivity {

    TextView likes;TextView favorites;TextView tags;TextView views;TextView downloads;TextView user;TextView id;
    TextView resolution;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        setUi();

        image.getLayoutParams().height =height/2;

        Intent intent = getIntent();
        final Item item;
        item = (Item) intent.getSerializableExtra("item");
        if (item !=null){
            user.setText(user.getText().toString()+" "+item.getName());
            likes.setText(likes.getText().toString()+" "+item.getLike());
            views.setText(views.getText().toString()+" "+item.getViews());
            favorites.setText(favorites.getText().toString()+" "+item.getFavorites());
            downloads.setText(downloads.getText().toString()+" "+item.getDownloads());
            id.setText(id.getText().toString()+" "+item.getPhotoId()+" ");
            tags.setText(tags.getText().toString()+" "+item.getTags());
            tags.setTextColor(Color.parseColor("#ff0000"));
            resolution.setText(resolution.getText().toString()+" "+item.getWidht()+"x"+item.getHeigh());
            //TODO databasede resim yok ise
            Picasso.with(this).load(item.getUrl()).fit().centerInside().into(image);
        }

        ((Button)findViewById(R.id.detail_button_copy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", item.getPhotoId()+"");
                if(clipboard != null){
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(Detail.this, getResources().getString(R.string.succes_copy), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setUi(){
        image = findViewById(R.id.detail_image);
        user = findViewById(R.id.detail_user);
        likes = findViewById(R.id.detail_likes);
        views = findViewById(R.id.detail_views);
        favorites= findViewById(R.id.detail_favorites);
        downloads = findViewById(R.id.detail_downloads);
        id = findViewById(R.id.detail_id);
        tags = findViewById(R.id.detail_tags);
        resolution = findViewById(R.id.detail_res);
    }

}




/**/