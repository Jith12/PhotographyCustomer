package com.example.photographycustomer.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistResponse {

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
        @SerializedName("studio_name")
        @Expose
        private String studioName;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobile_no")
        @Expose
        private String mobileNo;
        @SerializedName("alter_mobile_no")
        @Expose
        private String alterMobileNo;
        @SerializedName("email_id")
        @Expose
        private String emailId;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("about_me")
        @Expose
        private String aboutMe;
        @SerializedName("camera")
        @Expose
        private String camera;
        @SerializedName("camera_model")
        @Expose
        private String cameraModel;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("experience_position")
        @Expose
        private String experiencePosition;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("price_position")
        @Expose
        private String pricePosition;
        @SerializedName("album_image")
        @Expose
        private String albumImage;
        @SerializedName("video_link")
        @Expose
        private String videoLink;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("plan_id")
        @Expose
        private String planId;
        @SerializedName("days")
        @Expose
        private String days;
        @SerializedName("Otp_No")
        @Expose
        private String otpNo;
        @SerializedName("Otp_verified")
        @Expose
        private String otpVerified;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudioName() {
            return studioName;
        }

        public void setStudioName(String studioName) {
            this.studioName = studioName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getAlterMobileNo() {
            return alterMobileNo;
        }

        public void setAlterMobileNo(String alterMobileNo) {
            this.alterMobileNo = alterMobileNo;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getAboutMe() {
            return aboutMe;
        }

        public void setAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
        }

        public String getCamera() {
            return camera;
        }

        public void setCamera(String camera) {
            this.camera = camera;
        }

        public String getCameraModel() {
            return cameraModel;
        }

        public void setCameraModel(String cameraModel) {
            this.cameraModel = cameraModel;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getExperiencePosition() {
            return experiencePosition;
        }

        public void setExperiencePosition(String experiencePosition) {
            this.experiencePosition = experiencePosition;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPricePosition() {
            return pricePosition;
        }

        public void setPricePosition(String pricePosition) {
            this.pricePosition = pricePosition;
        }

        public String getAlbumImage() {
            return albumImage;
        }

        public void setAlbumImage(String albumImage) {
            this.albumImage = albumImage;
        }

        public String getVideoLink() {
            return videoLink;
        }

        public void setVideoLink(String videoLink) {
            this.videoLink = videoLink;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPlanId() {
            return planId;
        }

        public void setPlanId(String planId) {
            this.planId = planId;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getOtpNo() {
            return otpNo;
        }

        public void setOtpNo(String otpNo) {
            this.otpNo = otpNo;
        }

        public String getOtpVerified() {
            return otpVerified;
        }

        public void setOtpVerified(String otpVerified) {
            this.otpVerified = otpVerified;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
