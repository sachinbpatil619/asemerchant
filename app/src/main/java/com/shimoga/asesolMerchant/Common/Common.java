package com.shimoga.asesolMerchant.Common;

import com.shimoga.asesolMerchant.Model.User;
import com.shimoga.asesolMerchant.Remote.APIService;
import com.shimoga.asesolMerchant.Remote.RetrofitClient;

public class Common {
    public static User currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    private String phone;
    public static final int PICK_IMAGE_REQUEST = 71;


    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }


    public Common() {
    }

    public Common(String phone) {
        this.phone = phone;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Common.currentUser = currentUser;
    }

    public static String convertCodeToStatus(String code) {
        if (code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "On the Way";
        else
            return "Shipped";
    }



}
