package com.shimoga.asesolMerchant.ui.signout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shimoga.asesolMerchant.DatabaseHelper;
import com.shimoga.asesolMerchant.LoginActivity;
import com.shimoga.asesolMerchant.R;
import com.shimoga.asesolMerchant.Common.LoggedUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class  SignOutFragment extends Fragment {

    private SignOutViewModel mViewModel;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    private SQLiteDatabase sql;
    DatabaseHelper db1;


    public static SignOutFragment newInstance() {
        return new SignOutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_out_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignOutViewModel.class);
        // TODO: Use the ViewModel
        db1 = new DatabaseHelper(getContext());

        LoggedUser loggedUser=new LoggedUser();
        loggedUser.setPhone(null);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signOut();
        db1.clearUser();
        Intent homeIntent = new Intent(getContext(), LoginActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

}
