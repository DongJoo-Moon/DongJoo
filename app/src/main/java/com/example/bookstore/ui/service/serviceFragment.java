package com.example.bookstore.ui.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.loginActivity;
import com.example.bookstore.shoppingActivity;
import com.example.bookstore.ui.service.FAQ.Member_account;
import com.example.bookstore.ui.service.FAQ.Member_managment;
import com.example.bookstore.ui.service.FAQ.Shipping;
import com.example.bookstore.ui.service.FAQ.Transaction;
import com.example.bookstore.ui.service.FAQ.and_so;
import com.example.bookstore.ui.service.FAQ.cancell;
import com.example.bookstore.ui.service.FAQ.goods;
import com.example.bookstore.ui.service.FAQ.order;
import com.example.bookstore.ui.service.FAQ.used;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class serviceFragment extends Fragment {

    private serviceViewModel serviceViewModel;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    Button loggout_btn,Shipping_btn,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serviceViewModel =
                ViewModelProviders.of(this).get(serviceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_service, container, false);
        final TextView textView = root.findViewById(R.id.text_service);

        loggout_btn = (Button)root.findViewById(R.id.loggout_btn);
        Shipping_btn = (Button) root.findViewById(R.id.Shipping_btn);
        btn1 = (Button)root.findViewById(R.id.btn1);
        btn2 = (Button)root.findViewById(R.id.btn2);
        btn3 = (Button)root.findViewById(R.id.btn3);
        btn4 = (Button)root.findViewById(R.id.btn4);
        btn5 = (Button)root.findViewById(R.id.btn5);
        btn6 = (Button)root.findViewById(R.id.btn6);
        btn7 = (Button)root.findViewById(R.id.btn7);
        btn8 = (Button)root.findViewById(R.id.btn8);
        btn9 = (Button)root.findViewById(R.id.btn9);

        //ExpandableListView
        listView = (ExpandableListView) root.findViewById(R.id.list);
        initData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        loggout_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), loginActivity.class);
                startActivity(intent);
            }
        });

        Shipping_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shoppingActivity.class);
                startActivity(intent);
            }
        });

        // 공지사항 윗 부분 버튼
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), goods.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), order.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Shipping.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cancell.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), used.class);
                startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Transaction.class);
                startActivity(intent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Member_managment.class);
                startActivity(intent);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Member_account.class);
                startActivity(intent);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), and_so.class);
                startActivity(intent);
            }
        });

        serviceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    //ExpandableListView
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("자주 묻는 문의 내역");
        listDataHeader.add("아이디가 학번으로 되어 있는데 변경할 수 없나요??");
        listDataHeader.add("비밀번호를 까먹었어요!");
        listDataHeader.add("상품이 배송이 안와요");
        listDataHeader.add("결제가 안됩니다.");

        List<String> service0 = new ArrayList<>();
        service0.add("자세한 문의 사항은 055-xxx-xxxx");

        List<String> service1 = new ArrayList<>();
        service1.add("저희 경남대BookStore는 경남대 elass와 연동되어 있습니다.\n" +
                "따라서 아이디는 학번으로 쓰도록 고정되어있습니다." +
                "자세한 문의 사항은 055-xxx-xxxx");

        List<String> service2 = new ArrayList<>();
        service2.add("자세한 문의 사항은 055-xxx-xxxx");

        List<String> service3 = new ArrayList<>();
        service3.add("자세한 문의 사항은 055-xxx-xxxx");

        List<String> service4 = new ArrayList<>();
        service4.add("자세한 문의 사항은 055-xxx-xxxx");

        listHash.put(listDataHeader.get(0), service0);
        listHash.put(listDataHeader.get(1), service1);
        listHash.put(listDataHeader.get(2), service2);
        listHash.put(listDataHeader.get(3), service3);
        listHash.put(listDataHeader.get(4), service4);
    }
    public static class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private List<String> listDataHeader;
        private HashMap<String, List<String>> listHashMap;

        public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
            this.context = context;
            this.listDataHeader = listDataHeader;
            this.listHashMap = listHashMap;
        }

        @Override
        public int getGroupCount() {
            return listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return listHashMap.get(listDataHeader.get(i)).size();
        }

        @Override
        public Object getGroup(int i) {
            return listDataHeader.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return listHashMap.get(listDataHeader.get(i)).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            String headerTitle = (String) getGroup(i);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_group, null);
            }
            TextView ListHeader = (TextView) view.findViewById(R.id.ListHeader);
            ListHeader.setTypeface(null, Typeface.BOLD);
            ListHeader.setText(headerTitle);
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            final String childText = (String) getChild(i, i1);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, null);
            }
            TextView ListChild = (TextView) view.findViewById(R.id.ListItem);
            ListChild.setText(childText);
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }


    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

// pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();


    }
}
