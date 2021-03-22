package com.example.bookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookstore.ui.Fragment.certificateFragment;
import com.example.bookstore.ui.Fragment.eventFragment;
import com.example.bookstore.ui.Fragment.majorFragment;
import com.example.bookstore.ui.Fragment.newsFragment;
import com.example.bookstore.ui.book.searchView;
import com.example.bookstore.ui.engineering_college.architecture;
import com.example.bookstore.ui.engineering_college.computer;
import com.example.bookstore.ui.engineering_college.electrical;
import com.example.bookstore.ui.engineering_college.electronic;
import com.example.bookstore.ui.engineering_college.fire_prevention;
import com.example.bookstore.ui.engineering_college.industrial_design;
import com.example.bookstore.ui.engineering_college.information_communication;
import com.example.bookstore.ui.engineering_college.mechanical;
import com.example.bookstore.ui.engineering_college.nano_new_materials;
import com.example.bookstore.ui.engineering_college.shipbuilding_marine;
import com.example.bookstore.ui.mypage.myPageActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainLog";
    public static String book_name;
    private FirebaseFirestore db;
    private TextView idtext_main;

    newsFragment frag1;
    eventFragment frag2;
    certificateFragment frag3;
    majorFragment frag4;

    private BackPressCloseHandler backPressCloseHandler;
    private AppBarConfiguration mAppBarConfiguration;
    Button logoutButton;

    FragmentManager fm;
    FragmentTransaction tran;

    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchViewCode();

        String main_name = loginActivity.id_name;
        idtext_main = (TextView)findViewById(R.id.idtext_main);
        idtext_main.setText(main_name);

        frag1 = new newsFragment();
        frag2 = new eventFragment();
        frag3 = new certificateFragment();
        frag4 = new majorFragment();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        backPressCloseHandler = new BackPressCloseHandler(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_used, R.id.nav_service)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void browser1(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=MVluFcg5yVE"));
        startActivity(browserIntent);

    }

    public void browser2(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/kTLcOk06XkA"));
        startActivity(intent);
    }
    public void go_Computer(View view){
        Intent intent = new Intent(MainActivity.this, computer.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingButton2:
                shopping();
                break;
            case R.id.myPageButton2:
                mypage();
                break;
            case R.id.action_search :
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void shopping() {
        Intent intent = new Intent(MainActivity.this, shoppingActivity.class);
        startActivity(intent);
    }

    public void mypage() {
        Intent intent = new Intent(MainActivity.this, myPageActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }else{
            super.onBackPressed();
        }
        System.exit(1);
    }

    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.main_frame, frag1).commit();
                break;
            case 1:
                tran.replace(R.id.main_frame, frag2).commit();
                break;
        }
    }
    public  void setFrag1 (int n) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.pick_frame,frag3).commit();
                break;
            case 1:
                tran.replace(R.id.pick_frame,frag4).commit();
                break;

        }

    }

    // 메인화면 상단바(공과대학)
    public void popupMenu2(View v){
        PopupMenu popup = new PopupMenu(this,v);
        popup.getMenuInflater().inflate(R.menu.menu6,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item){
                switch(item.getItemId()){
                    case R.id.bt61 :
                        Intent intent61 = new Intent(MainActivity.this, mechanical.class);
                        startActivity(intent61);
                        break;
                    case R.id.bt62 :
                        Intent intent62 = new Intent(MainActivity.this, electrical.class);
                        startActivity(intent62);
                        break;
                    case R.id.bt63 :
                        Intent intent63 = new Intent(MainActivity.this, electronic.class);
                        startActivity(intent63);
                        break;
                    case R.id.bt64 :
                        Intent intent64 = new Intent(MainActivity.this, nano_new_materials.class);
                        startActivity(intent64);
                        break;
                    case R.id.bt65 :
                        Intent intent65 = new Intent(MainActivity.this, computer.class);
                        startActivity(intent65);
                        break;
                    case R.id.bt66 :
                        Intent intent66 = new Intent(MainActivity.this, information_communication.class);
                        startActivity(intent66);
                        break;
                    case R.id.bt67 :
                        Intent intent67 = new Intent(MainActivity.this, architecture.class);
                        startActivity(intent67);
                        break;
                    case R.id.bt68 :
                        Intent intent68 = new Intent(MainActivity.this, fire_prevention.class);
                        startActivity(intent68);
                        break;
                    case R.id.bt69 :
                        Intent intent69 = new Intent(MainActivity.this, shipbuilding_marine.class);
                        startActivity(intent69);
                        break;
                    case R.id.bt611 :
                        Intent intent611 = new Intent(MainActivity.this, industrial_design.class);
                        startActivity(intent611);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
    //searchView
    private void searchViewCode(){
        searchView=(MaterialSearchView)findViewById(R.id.search_view);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                book_name = query;
                Intent intent = new Intent(MainActivity.this, searchView.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }
}
