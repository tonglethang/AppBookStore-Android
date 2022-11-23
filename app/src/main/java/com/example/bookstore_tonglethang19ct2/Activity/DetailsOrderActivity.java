package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.AdminOrderAdapter;
import com.example.bookstore_tonglethang19ct2.Adapter.OrderBookAdapter;
import com.example.bookstore_tonglethang19ct2.Models.DetailsOrder;
import com.example.bookstore_tonglethang19ct2.Models.Order;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsOrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    OrderBookAdapter orderBookAdapter;
    ArrayList<DetailsOrder> arrOrderBook;
    DrawerLayout drawerLayout;
    TextView idCus, nameCus, phoneCus, addressCus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            mapping();
            ActionToolBar();
            GetData();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check your connection again !");
        }
    }
    private void GetData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String link = Server.linkDetailsOrder + "?idCus=" +  getIntent().getSerializableExtra("idCus");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response != null && response.length() > 100){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("Customer");
                        idCus.setText(object.getString("_id"));
                        nameCus.setText(object.getString("fullname"));
                        phoneCus.setText(object.getString("phone"));
                        addressCus.setText(object.getString("address"));

                        JSONArray jsonArrayOrder = jsonObject.getJSONArray("DonHang");
                        JSONArray jsonArrayBook = jsonObject.getJSONArray("Book");
                        for(int i = 0; i < jsonArrayOrder.length(); i++){
                            for(int j = 0; j < jsonArrayBook.length(); j++){
                                JSONObject objOrder = jsonArrayOrder.getJSONObject(i);
                                JSONObject objBook = jsonArrayBook.getJSONObject(j);
                                String idBook = objOrder.getString("idBook");
                                String idBook2 = objBook.getString("_id");
                                if(idBook.equals(idBook2)){
                                    String nameBook = objBook.getString("name");
                                    String image = objBook.getString("image");
                                    int quantity = objOrder.getInt("quantity");
                                    long price = objOrder.getLong("price");
                                    Log.d("Name:", nameBook);
                                    Log.d("Iamge:", image);
                                    Log.d("Quantity:", String.valueOf(quantity));
                                    Log.d("Pirce:", String.valueOf(price));

                                    arrOrderBook.add(new DetailsOrder(nameBook, image, quantity, price));
                                    orderBookAdapter.notifyDataSetChanged();
                                }
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
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
                Intent intent = new Intent(getApplicationContext(), AdminActitvity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbarDetails);
        listView = findViewById(R.id.listViewDetailsOrder);
        idCus = findViewById(R.id.idCus);
        nameCus = findViewById(R.id.nameCus);
        phoneCus = findViewById(R.id.phoneCus);
        addressCus = findViewById(R.id.addressCus);
        arrOrderBook = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerAdmin);
        orderBookAdapter= new OrderBookAdapter(getApplicationContext(),  arrOrderBook);
        listView.setAdapter(orderBookAdapter);
    }
}