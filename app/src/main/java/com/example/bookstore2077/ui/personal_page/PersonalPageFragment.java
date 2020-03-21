package com.example.bookstore2077.ui.personal_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookstore2077.R;

public class PersonalPageFragment extends Fragment {

    private PersonalPageViewModel personalPageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalPageViewModel =
                ViewModelProviders.of(this).get(PersonalPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal_page, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        personalPageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
