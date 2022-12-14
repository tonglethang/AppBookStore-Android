package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.KinhTeAdapter;
import com.example.bookstore_tonglethang19ct2.Adapter.TamLyAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SachTamLyActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    TamLyAdapter tamlyAdapter;
    ArrayList<Book> arrTamLyBook;
    String idType = "";
    int page = 1;
    View footerView;
    boolean isLoading;
    boolean limitData = false;
    myHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_tam_ly);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            mapping();
            getIdType();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check again  your connection !");
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
            case R.id.menuSearch:
                Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("infomaitionBook", arrTamLyBook.get(i));
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
                    SachTamLyActivity.ThreadData threadData = new SachTamLyActivity.ThreadData();
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
                            arrTamLyBook.add(new Book(id, name, price, img, nhaxuatban, soluong, type, mota, idType));
                            tamlyAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitData = true;
                    listView.removeFooterView(footerView);
                    CheckConnection.showToast_Short(getApplicationContext(), "???? h???t d??? li???u");
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
    private void mapping() {
        toolbar = findViewById(R.id.toolBarTamLy);
        listView = findViewById(R.id.listViewTamLy);
        arrTamLyBook = new ArrayList<>();
        tamlyAdapter = new TamLyAdapter(getApplicationContext(), arrTamLyBook);
        listView.setAdapter(tamlyAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar, null);
        myHandler = new myHandler();
    }
    private void getIdType() {
        idType = getIntent().getStringExtra("idType");
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