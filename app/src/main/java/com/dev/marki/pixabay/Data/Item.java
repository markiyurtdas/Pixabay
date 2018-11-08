package com.dev.marki.pixabay.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marki on 06.02.2018.
 */


public class Item implements Serializable {
    private int favorites;
    private int views;
    private int photoId;
    private int downloads;
    private String tags;
    private String url;
    private String name;
    private int like;
    private int widht;
    private int heigh;

    public Item(String name, int like,  int favorites, int views, int photoId,
                int downloads, String tags, String url,int widht, int heigh) {
        this.favorites = favorites;
        this.views = views;
        this.photoId = photoId;
        this.downloads = downloads;
        this.tags = tags;
        this.url = url;
        this.name = name;
        this.like = like;
        this.widht = widht;
        this.heigh = heigh;
    }

    public int getFavorites() {        return favorites;    }

    public void setFavorites(int favorites) {        this.favorites = favorites;    }

    public int getViews() {        return views;    }

    public void setViews(int views) {        this.views = views;    }

    public int getPhotoId() {        return photoId;    }

    public void setPhotoId(int photoId) {        this.photoId = photoId;    }

    public int getDownloads() {        return downloads;    }

    public void setDownloads(int downloads) {        this.downloads = downloads;    }

    public String getTags() {        return tags;    }

    public void setTags(String tags) {        this.tags = tags;    }

    public void setUrl(String url) {        this.url = url;    }

    public void setName(String name) {        this.name = name;    }

    public void setLike(int like) {        this.like = like;    }

    public int getWidht() {
        return widht;
    }

    public void setWidht(int widht) {
        this.widht = widht;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public Item(String url, String name, int like) {
        this.url = url;
        this.name = name;
        this.like = like;
    }

    public String getUrl() {      return url;   }
    public String getName() {     return name;  }
    public int getLike() {        return like;  }


//    public ArrayList<Item> itemSorter(ArrayList<Item> list, boolean sortBy){
//        if(sortBy){
//
//        }
//    }
}
