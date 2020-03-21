package com.example.bookstore2077.ui.audiobooks;

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
import com.example.bookstore2077.ui.personal_page.PersonalPageViewModel;

public class AudiobooksFragment extends Fragment {

    private AudiobookViewModel audiobookViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        audiobookViewModel =
                ViewModelProviders.of(this).get(AudiobookViewModel.class);
        View root = inflater.inflate(R.layout.fragment_audiobook, container, false);
        final TextView textView = root.findViewById(R.id.text_audiobook);
        audiobookViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
