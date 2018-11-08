package com.dev.marki.pixabay.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dev.marki.pixabay.Data.Item;
import com.dev.marki.pixabay.R;
import com.dev.marki.pixabay.Util.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Search extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    public String search;
    public String lastSearch;
    private RecyclerView mRecycView;
    private ItemAdapter mAdapter;
    private ArrayList<Item> mItemList;
    private RequestQueue mRequestQueue;
    private String[] randomSearch = {"anime","galatasaray","football","music","hacker"};

    private AlertDialog.Builder dialogBuilder;
    private  AlertDialog dialog ;
    private boolean sortBy=false;
    TextView currentTextView;

    Button nextPage ;
    Button prevPage ;
    Button topOfPage;
    private int curPage =1;
    private int maxPageCount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentTextView = findViewById(R.id.search_current_page);
        currentTextView.setText(curPage+"");
        final TextView sortByButton = findViewById(R.id.sort_by);
        //maxPage = findViewById(R.id.max_page);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        mRecycView = findViewById(R.id.recycler_view);
        mRecycView.setHasFixedSize(true);
        mRecycView.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        Random rnd = new Random();
        search = randomSearch[rnd.nextInt(randomSearch.length)];
        String tempUrl ="https://pixabay.com/api/?key=7925845-047526e650d3691a10a353f31&q="
                + search + "&image_type=photo";
        parseJSON(tempUrl);




        lastSearch = tempUrl;
        if(sortBy)
            sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.latest));
        else
            sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.popular));

        sortByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu =new PopupMenu(Search.this,sortByButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup_sort,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (sortBy)
                        {
                            if(item.getItemId() == R.id.search_sort_popular)
                                sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.popular));
                            if(item.getItemId() == R.id.search_sort_latest){
                                sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.latest));
                                sortBy=false;
                                parseJSON(lastSearch);
                            }
                        }else{
                            if(item.getItemId() == R.id.search_sort_popular) {
                                sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.popular));
                                sortBy = true;
                                parseJSON(lastSearch);
                            }
                            if(item.getItemId() == R.id.search_sort_latest){
                                sortByButton.setText(getResources().getString(R.string.sort_by)+" "+getResources().getString(R.string.latest));
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });



        prevPage = findViewById(R.id.search_page_left);
        if(curPage<2)
            prevPage.setVisibility(View.INVISIBLE);
        prevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (curPage >1){
                        curPage--;
                        lastSearch +="&page="+curPage;
                        parseJSON(lastSearch);
                        currentTextView.setText(curPage+"");
                        nextPage.setVisibility(View.VISIBLE);

                    }
            }
        });
        nextPage = findViewById(R.id.search_page_right);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tempTVCount = findViewById(R.id.total_page_count);
                maxPageCount = Integer.parseInt(tempTVCount.getText().toString());
                Log.d("zxcv","total hits: "+maxPageCount);
                if(curPage<maxPageCount){
                    curPage++;
                    lastSearch +="&page="+curPage;
                    parseJSON(lastSearch);
                    prevPage.setVisibility(View.VISIBLE);
                    currentTextView.setText(curPage+"");
                    if (curPage >= maxPageCount)
                        nextPage.setVisibility(View.INVISIBLE);
                }

            }
        });
    }















    //get JSON
    private void parseJSON(String url) {
        mItemList.clear();
        //TODO change URL

        if(!sortBy)
            url +="&order=latest";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            final int totalHits = 1+response.getInt("totalHits")/20;//Settings.getPerPage();
                            //maxPage.setText(""+temp);
                            TextView totalPageCountTV = findViewById(R.id.total_page_count);
                            totalPageCountTV.setText(totalHits+"");
                            for(int i =0; i < jsonArray.length();i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String creatorName = obj.getString("user");
                                String imageUrl = obj.getString("webformatURL");
                                int likes = obj.getInt("likes");
                                int views = obj.getInt("views");
                                int photoId = obj.getInt("id");
                                int downloads = obj.getInt("downloads");
                                String tags = obj.getString("tags");
                                int favorites = obj.getInt("favorites");
                                int heigh = obj.getInt("imageHeight");
                                int width=obj.getInt("imageWidth");
                                mItemList.add(new Item(creatorName,likes,favorites,views,photoId,downloads,
                                        tags,imageUrl,width,heigh));
                            }
                            mAdapter = new ItemAdapter(Search.this, mItemList);
                            mRecycView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(Search.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailItem = new Intent(Search.this, Detail.class);
        Item clickedItem = mItemList.get(position);
        detailItem.putExtra("item", clickedItem);
        startActivity(detailItem);

    }

    public void createPopup(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.search_popup,null);
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        Button srch = (Button) view.findViewById(R.id.search_button);
        dialog.show();
        final String[] category = {""};
        final TextView categoriesTV = view.findViewById(R.id.category);
        final PopupMenu popupMenu =new PopupMenu(Search.this,categoriesTV);
        popupMenu.getMenuInflater().inflate(R.menu.popup_category,popupMenu.getMenu());

        categoriesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.category_fashion:
                                category[0] ="&category=fashion";
                                break;
                            case R.id.category_nature:
                                category[0] ="&category=nature";
                                break;
                            case R.id.category_backgrounds:
                                category[0] ="&category=backgrounds";
                                break;
                            case R.id.category_science:
                                category[0] ="&category=science";
                                break;
                            case R.id.category_education:
                                category[0] ="&category=education";
                                break;
                            case R.id.category_people:
                                category[0] ="&category=people";
                                break;
                            case R.id.category_feelings:
                                category[0] ="&category=feelings";
                                break;
                            case R.id.category_religion:
                                category[0] ="&category=religion";
                                break;
                            case R.id.category_health:
                                category[0] ="&category=fashion";
                                break;
                            case R.id.category_places:
                                category[0] ="&category=places";
                                break;
                            case R.id.category_animals:
                                category[0] ="&category=animals";
                                break;
                            case R.id.category_industry:
                                category[0] ="&category=industry";
                                break;
                            case R.id.category_food:
                                category[0] ="&category=food";
                                break;
                            case R.id.category_computer:
                                category[0] ="&category=computer";
                                break;
                            case R.id.category_sports:
                                category[0] ="&category=sports";
                                break;
                            case R.id.category_transportation:
                                category[0] ="&category=transportation";
                                break;
                            case R.id.category_travel:
                                category[0] ="&category=travel";
                                break;
                            case R.id.category_buildings:
                                category[0] ="&category=buildings";
                                break;
                            case R.id.category_business:
                                category[0] ="&category=business";
                                break;
                            case R.id.category_music:
                                category[0] ="&category=music";
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        final String popupHeighs = "&min_height=";
        final String popupWidths = "&min_width=";
        final EditText popupWidth = view.findViewById(R.id.popup_min_width);
        final EditText popupHeigh = view.findViewById(R.id.popup_min_heigh);
        //min_width=125&min_height=400        dialog.show();
        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchDef();
                EditText et = view.findViewById(R.id.editText_search);
                StringBuilder str = new StringBuilder();
                String pageUrl = "&pagi=";
                int page=1;

                String url ="https://pixabay.com/api/?key=7925845-047526e650d3691a10a353f31&image_type=photo"
                        +"&q="+ et.getText() ;
                str.append(url);


                str.append(pageUrl+page);
                str.append(category[0]);
                str.append(popupHeighs+popupHeigh.getText());
                str.append(popupWidths+popupWidth.getText());
                lastSearch = str.toString();
                parseJSON(str.toString());
                dialog.cancel();
            }
        });
    }

    private void makeSearchDef() {
        currentTextView.setText("1");
        TextView tempTotalTV = findViewById(R.id.total_page_count);
        tempTotalTV.setText("1");
        curPage =1;
        nextPage.setVisibility(View.VISIBLE);
        prevPage.setVisibility(View.VISIBLE);

    }
}