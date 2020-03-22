package com.example.bookstore2077.ui.personal_page;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookstore2077.R;
import com.example.bookstore2077.ui.recycle.RecycleFragment;
import com.example.bookstore2077.ui.shop.ShopFragment;

public class PersonalPageFragment extends Fragment {

    private PersonalPageViewModel personalPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalPageViewModel =
                ViewModelProviders.of(this).get(PersonalPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal_page, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);

        Button recycle = getActivity().findViewById(R.id.personalPageRecycleButton);
        recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.personal_page_fragment,
                        new RecycleFragment()).commit();
            }
        });

        TextView userName = getActivity().findViewById(R.id.personalPageName);
        userName.setText(sharedPreferences.getString("name", "NONE") + "\n"
                + sharedPreferences.getString("surname", "NONE"));
    }
}
