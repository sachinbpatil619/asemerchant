package com.shimoga.asesolMerchant;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WhatsappActivity extends AppCompatActivity {

    String phoneNumber = "+918553777130";
    String textMessage = "I would like to seek help from Malnad Chicken.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        boolean isOrNot = installedOrNot("com.whatsapp");
        if (isOrNot) {
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            whatsappIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + textMessage));
            startActivity(whatsappIntent);
        }
        else {
            Toast.makeText(this, "Sorry... You don't have whatsapp installed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean installedOrNot(String url) {
        PackageManager packageManager = getPackageManager();
        boolean isOk;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            isOk = true;
        } catch (PackageManager.NameNotFoundException e) {
            isOk = false;
        }
        return isOk;
    }
}