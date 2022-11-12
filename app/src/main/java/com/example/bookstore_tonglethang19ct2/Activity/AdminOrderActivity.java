package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.AdminCustomerAdapter;
import com.example.bookstore_tonglethang19ct2.Adapter.AdminOrderAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Admin;
import com.example.bookstore_tonglethang19ct2.Models.Customer;
import com.example.bookstore_tonglethang19ct2.Models.Order;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminOrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView, listAdmin;
    NavigationView naviAdmin;
    AdminOrderAdapter adminOrderAdapter;
    ArrayList<Order> arrOrder;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            mapping();
            ActionToolBar();
            GetData();
            catchOnItemListView();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check your connection again !");
        }
    }

    private void catchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminOrderActivity.this);
                builder.setTitle("Xác nhận !");
                builder.setMessage("Bạn có chắc xóa đơn hàng này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String link = Server.linkDeleteCustomerAdmin + "?idCus=" + arrOrder.get(pos).getIdCus();

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    if(status.equals("success")){
                                        CheckConnection.showToast_Short(getApplicationContext(), "Đã xóa đơn hàng thành công !");
                                        Intent intent = new Intent(getApplicationContext(), AdminOrderActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        CheckConnection.showToast_Short(getApplicationContext(), status);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(stringRequest);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    private void GetData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String link = Server.linkOrder;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String idCus = "";
                String soluongBook = "";
                String  totalPrice = "";
                if(response != null && response.length() > 100){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("DonHang");
                        for(int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            JSONObject obj = object .getJSONObject("_id");
                            idCus = obj.getString("idCustomer");
                            Log.d("idCus", idCus);
                            JSONArray array= object.getJSONArray("nameBook");
                            String nameBook = "";
                            for(int j = 0; j < array.length(); j++){
                                if(j == array.length() - 1){
                                    nameBook += (array.getString(j) + "");
                                }
                                else {
                                    nameBook += (array.getString(j) + ",\n");
                                }
                            }
                            Log.d("nameBook",nameBook);
                            soluongBook = object.getString("quantity");
                            Log.d("soluong",soluongBook);
                            totalPrice = object.getString("totalPrice");
                            Log.d("total",  totalPrice);
                            arrOrder.add(new Order(idCus, nameBook, Integer.parseInt(soluongBook), Long.parseLong(totalPrice)));
                            adminOrderAdapter.notifyDataSetChanged();
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
                finish();
            }
        });
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBarOrder);
        listView = findViewById(R.id.allOrder);
        arrOrder = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerAdmin);
        adminOrderAdapter = new AdminOrderAdapter(getApplicationContext(), arrOrder);
        listView.setAdapter( adminOrderAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    }
}