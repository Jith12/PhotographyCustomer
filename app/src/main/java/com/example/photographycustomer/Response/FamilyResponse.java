package com.example.photographycustomer.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FamilyResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("collection_id")
        @Expose
        private String collectionId;
        @SerializedName("photographer_id")
        @Expose
        private String photographerId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("family_name")
        @Expose
        private String familyName;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("studio_name")
        @Expose
        private String studioName;
        @SerializedName("roleid")
        @Expose
        private String roleid;

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

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getStudioName() {
            return studioName;
        }

        public void setStudioName(String studioName) {
            this.studioName = studioName;
        }

        public String getRoleid() {
            return roleid;
        }

        public void setRoleid(String roleid) {
            this.roleid = roleid;
        }

    }
}
