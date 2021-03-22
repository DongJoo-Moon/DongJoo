package com.example.bookstore.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btn1,btn2,btnn1,btnn2,btn3,btn4,btn5,btn6,btn7,btn8;
    private FragmentManager fragmentManager;
    FragmentTransaction tran;
    private FragmentActivity root;
    MainActivity activity;
    SearchView searchView;
    Context context;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        btn1 = (Button)root.findViewById(R.id.btn1);
        btn2 = (Button)root.findViewById(R.id.btn2);
        btnn1 = (Button)root.findViewById(R.id.btnn1);
        btnn2 = (Button)root.findViewById(R.id.btnn2);
        btn3 = (Button)root.findViewById(R.id.btn3);
        btn4 = (Button)root.findViewById(R.id.btn4);
        btn5 = (Button)root.findViewById(R.id.btn5);
        btn6 = (Button)root.findViewById(R.id.btn6);
        btn7 = (Button)root.findViewById(R.id.btn7);
        btn8 = (Button)root.findViewById(R.id.btn8);
        //searchView
        searchView = (SearchView) root.findViewById(R.id.searchView);


        final TextView txtView = (TextView)root.findViewById(R.id.No_content);

        ImageSlider imageSlider = root.findViewById(R.id.slider);  //슬라이드 부분

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/bookstore-51361.appspot.com/o/NCS1.jpg?alt=media&token=c382b5db-2438-45e7-bc05-b298bf7314ec"));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/bookstore-51361.appspot.com/o/degeter2.jpeg?alt=media&token=af97d574-1df2-4d3f-9613-d549bbff0807"));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/bookstore-51361.appspot.com/o/degeter3.jpg?alt=media&token=2c1bcfe7-7daa-4931-a0ab-5618e01d722d"));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/bookstore-51361.appspot.com/o/degeterr.jpg?alt=media&token=28d0c218-e5e7-417c-8ff3-df24b54f2d8c"));
        imageSlider.setImageList(slideModels, true);

        //((MainActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFrag(0);
                txtView.setVisibility(View.INVISIBLE);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            private View v;

            @Override
            public void onClick(View v) {
                this.v = v;
                ((MainActivity)getActivity()).setFrag(1);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).popupMenu2(v);
            }

        });
        //searchView

        //searchView
        /*list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Canana");
        list.add("Danana");
        list.add("Eanana");
        list.add("Fanana");
        list.add("Ganana");
        list.add("Hanana");

        adapter = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "검색창 클릭하셧습니다.", Toast.LENGTH_SHORT).show();
            }
        });*/

        return root;


    }
}
