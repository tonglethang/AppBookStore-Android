package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.ThieuNhiAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.Models.TypeBook;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SachThieuNhiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ThieuNhiAdapter thieunhiAdapter;
    ArrayList<Book> arrThieuNhiBook;
    String idType = "";
    int page = 1;
    View footerView;
    boolean isLoading;
    boolean limitData = false;
    myHandler  myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_thieu_nhi);

        mapping();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIdType();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check your connection angain !");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.menuCart);
        View actionView = item.getActionView();
        TextView cartBadge = actionView.findViewById(R.id.cart_badge);
        cartBadge.setText(String.valueOf(MainActivity.arrCart.size()));


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(item);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("infomaitionBook", arrThieuNhiBook.get(i));
                startActivity(intent);
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
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String link = Server.linkBooks + idType + "?page=" + Page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_RE", response);
                String id = "";
                String name = "";
                Integer price = 0;
                String img = "";
                String  nhaxuatban = "";
                Integer soluong = 0;
                String type = "";
                String mota = "";
                String idType = "";
                if(response != null && response.length() > 100) {
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
                            arrThieuNhiBook.add(new Book(id, name, price, img, nhaxuatban, soluong, type, mota, idType));
                            thieunhiAdapter.notifyDataSetChanged();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIdType() {
        idType = getIntent().getStringExtra("idType");
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBarThieuNhi);
        listView = findViewById(R.id.listViewThieuNhi);
        arrThieuNhiBook = new ArrayList<>();
        thieunhiAdapter = new ThieuNhiAdapter(getApplicationContext(), arrThieuNhiBook);
        listView.setAdapter(thieunhiAdapter);
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