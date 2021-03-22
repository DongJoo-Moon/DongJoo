package com.example.bookstore.ui.used;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookstore.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class usedFragment extends Fragment implements View.OnClickListener {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    String getId_name;
    List<Object> Array = new ArrayList<Object>();



        private usedViewModel galleryViewModel;

        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
            galleryViewModel =
                    ViewModelProviders.of(this).get(usedViewModel.class);

            View root = inflater.inflate(R.layout.fragment_used, container, false);
            final TextView textView = root.findViewById(R.id.text_used);
            Button mun_Button = (Button) root.findViewById(R.id.mun_gwa_go);

        if(getArguments() != null){
        getId_name = getArguments().getString(getId_name);}



        mun_Button.setOnClickListener(this);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Mun_gwa_used_activity.class);
        startActivity(intent);

    }

}
