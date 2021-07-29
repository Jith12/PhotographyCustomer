package com.example.photographycustomer.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("collection_id")
        @Expose
        private String collectionId;
        @SerializedName("addset_id")
        @Expose
        private String addsetId;
        @SerializedName("photographer_id")
        @Expose
        private String photographerId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("family_id")
        @Expose
        private String familyId;
        @SerializedName("email_id")
        @Expose
        private String emailId;
        @SerializedName("album_name")
        @Expose
        private String albumName;
        @SerializedName("photo_image")
        @Expose
        private String photoImage;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
        }

        public String getAddsetId() {
            return addsetId;
        }

        public void setAddsetId(String addsetId) {
            this.addsetId = addsetId;
        }

        public String getPhotographerId() {
            return photographerId;
        }

        public void setPhotographerId(String photographerId) {
            this.photographerId = photographerId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getFamilyId() {
            return familyId;
        }

        public void setFamilyId(String familyId) {
            this.familyId = familyId;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getAlbumName() {
            return albumName;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public String getPhotoImage() {
            return photoImage;
        }

        public void setPhotoImage(String photoImage) {
            this.photoImage = photoImage;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
