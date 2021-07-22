package com.example.photographycustomer.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("collectionid")
    @Expose
    private String collectionid;
    @SerializedName("addsetid")
    @Expose
    private String addsetid;
    @SerializedName("photographerid")
    @Expose
    private String photographerid;
    @SerializedName("customerid")
    @Expose
    private String customerid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectionid() {
        return collectionid;
    }

    public void setCollectionid(String collectionid) {
        this.collectionid = collectionid;
    }

    public String getAddsetid() {
        return addsetid;
    }

    public void setAddsetid(String addsetid) {
        this.addsetid = addsetid;
    }

    public String getPhotographerid() {
        return photographerid;
    }

    public void setPhotographerid(String photographerid) {
        this.photographerid = photographerid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("photos")
        @Expose
        private String photos;

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

    }
}
