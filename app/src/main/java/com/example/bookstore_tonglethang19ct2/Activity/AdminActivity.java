package com.example.bookstore_tonglethang19ct2.Activity;

import static java.util.stream.Collectors.mapping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.AdminAdapter;
import com.example.bookstore_tonglethang19ct2.Adapter.AdminBookAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Admin;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView, listAdmin;
    NavigationView naviAdmin;
    AdminBookAdapter adminbookAdapter;
    AdminAdapter adminAdapter;
    ArrayList<Book> arrBook;
    ArrayList<Admin> arrAdmin;
    int page = 1;
    View footerView;
    boolean isLoading;
    boolean limitData = false;
    AdminActivity.myHandler myHandler;
    DrawerLayout drawerLayout;

    PopupMenu popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            mapping();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
            catchOnItemListView();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check your connection again !");
        }
    }

    private void catchOnItemListView() {
        listAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), AdminCustomerActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

    }

    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popup = new PopupMenu(getApplicationContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menubook, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.menuUpdate:

                                break;

                            case R.id.menuDelete:

                                break;

                        }
                        return false;
                    }
                });
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    AdminActivity.ThreadData threadData = new AdminActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void GetData(int Page) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String link = Server.linkAllBookAdmin+"?page=" + Page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String id = "";
                String name = "";
                Integer price = 0;
                String img = "";
                String  nhaxuatban = "";
                Integer soluong = 0;
                String type = "";
                String mota = "";
                String idType = "";
                if(response != null && response.length() > 100){
                    listView.removeFooterView(footerView);
                    try {
                        JSONObject jsonObject_tmp = new JSONObject(response);
                        JSONArray jsonArray  =  jsonObject_tmp.getJSONArray("Books");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getString("_id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            img = jsonObject.getString("image");
                            nhaxuatban = jsonObject.getString("nhaxuatban");
                            soluong = jsonObject.getInt("soluong");
                            type = jsonObject.getString("type");
                            mota = jsonObject.getString("mota");
                            idType = jsonObject.getString("idType");
                            arrBook.add(new Book(id, name, price, img, nhaxuatban, soluong, type, mota, idType));
                            adminbookAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitData = true;
                    listView.removeFooterView(footerView);
                    CheckConnection.showToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBarAdminBook);
        listView = findViewById(R.id.allBook);
        listAdmin = findViewById(R.id.listViewAdmin);
        naviAdmin = findViewById(R.id.naviAdmin);
        arrBook = new ArrayList<>();
        arrAdmin = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerAdmin);
        adminbookAdapter = new AdminBookAdapter(getApplicationContext(), arrBook);
        listView.setAdapter(adminbookAdapter);
        arrAdmin.add(0, new Admin("Quản lý sách", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Closed_Book_Icon.svg/1200px-Closed_Book_Icon.svg.png"));
        arrAdmin.add(1, new Admin("Quản lý khách hàng", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtF3o2PvqxOMHgdrpj_YRItsLBjxyTeNZu_Q&usqp=CAU"));
        arrAdmin.add(2, new Admin("Quản lý đơn hàng", "https://c8.alamy.com/comp/2ANK9RP/order-receipt-flat-icon-2ANK9RP.jpg"));
        arrAdmin.add(3, new Admin("Đăng xuất", "https://cdn-icons-png.flaticon.com/512/3094/3094700.png"));
        adminAdapter = new AdminAdapter(arrAdmin, getApplicationContext());
        listAdmin.setAdapter(adminAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar, null);
        myHandler = new myHandler();
    }
    public class myHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch(msg.what){
                case 0:
                    listView.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends  Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message mess = myHandler.obtainMessage(1);
            myHandler.sendMessage(mess);
            super.run();
        }
    }
}