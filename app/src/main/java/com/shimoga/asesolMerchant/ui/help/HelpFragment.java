package com.shimoga.asesolMerchant.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shimoga.asesolMerchant.R;
//import com.shimoga.asesolMerchant.WhatsappActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HelpFragment extends Fragment {

    private HelpViewModel mViewModel;


    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HelpViewModel.class);
        // TODO: Use the ViewModel


       /* Intent whatsappIntent=new Intent(getContext(), WhatsappActivity.class);
        startActivity(whatsappIntent);*/

    }


}
