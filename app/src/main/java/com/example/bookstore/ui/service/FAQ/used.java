package com.example.bookstore.ui.service.FAQ;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.ui.service.serviceFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class used extends AppCompatActivity {

    private ExpandableListView listView;
    private serviceFragment.ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_used);

        listView = (ExpandableListView)findViewById(R.id.list);
        initData();
        listAdapter = new serviceFragment.ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("자주 묻는 문의 내역");
        listDataHeader.add("아이디가 학번으로 되어 있는데 변경할 수 없나요??");
        listDataHeader.add("아이디가 학번으로 되어 있는데 변경할 수 없나요??");
        listDataHeader.add("아이디가 학번으로 되어 있는데 변경할 수 없나요??");
        listDataHeader.add("아이디가 학번으로 되어 있는데 변경할 수 없나요??");

        List<String> service0 = new ArrayList<>();
        service0.add("자세한 문의 사항은 055-xxx-xxxx");

        List<String> service1 = new ArrayList<>();
        service1.add("저희 경남대BookStore는 경남대 elass와 연동되어 있습니다.\n" +
                "따라서 아이디는 학번으로 쓰도록 고정되어있습니다." +
                "자세한 문의 사항은 055-xxx-xxxx");

        List<String> service2 = new ArrayList<>();
        service2.add("저희 경남대BookStore는 경남대 elass와 연동되어 있습니다." +
                "따라서 아이디는 학번으로 쓰도록 고정되어있습니다." +
                "자세한 문의 사항은 055-xxx-xxxx");

        List<String> service3 = new ArrayList<>();
        service3.add("저희 경남대BookStore는 경남대 elass와 연동되어 있습니다." +
                "따라서 아이디는 학번으로 쓰도록 고정되어있습니다." +
                "자세한 문의 사항은 055-xxx-xxxx");

        List<String> service4 = new ArrayList<>();
        service4.add("저희 경남대BookStore는 경남대 elass와 연동되어 있습니다." +
                "따라서 아이디는 학번으로 쓰도록 고정되어있습니다." +
                "자세한 문의 사항은 055-xxx-xxxx");

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
        serviceFragment.ExpandableListAdapter listAdapter = (serviceFragment.ExpandableListAdapter) listView.getExpandableListAdapter();
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
