package com.example.bookstore2077.ui.recycle;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookstore2077.R;
import com.example.bookstore2077.ui.personal_page.PersonalPageFragment;
import com.example.bookstore2077.ui.shop.ShopFragment;

public class RecycleFragment extends Fragment {

    private RecycleViewModel mViewModel;

    public static RecycleFragment newInstance() {
        return new RecycleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycle_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecycleViewModel.class);


        Button button_back = getActivity().findViewById(R.id.button_recycle_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.recycle_fragment,
                        new PersonalPageFragment()).commit();
            }
        });
        // TODO: Use the ViewModel
    }

}
