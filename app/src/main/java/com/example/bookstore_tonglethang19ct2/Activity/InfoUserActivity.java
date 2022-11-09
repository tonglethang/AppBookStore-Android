package com.example.bookstore_tonglethang19ct2.Activity;

import static java.util.stream.Collectors.mapping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InfoUserActivity extends AppCompatActivity {
    Button btnXacnhan;
    Button btnCancel;
    EditText fullName, email, phone, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        
        mapping();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EnventButton();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check your internet again !");
        }
    }

    private void EnventButton() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullname_cus = fullName.getText().toString().trim();
                final String email_cus = email.getText().toString().trim();
                final String phone_cus = phone.getText().toString().trim();
                final String address_cus = address.getText().toString().trim();
                final String[] idCustommer = new String[1];
                if(fullname_cus.length() > 0 && email_cus.length() > 0 && phone_cus.length() > 0 && address_cus.length() > 0){
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    String linkCus = Server.linkCustomer + "?fullname=" + fullname_cus +"&email=" + email_cus +"&phone=" + phone_cus +"&address=" + address_cus;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, linkCus, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.length() > 50){
                                try {
                                    JSONObject object = new JSONObject(response);
                                    idCustommer[0] =  object.getString("idCustommer");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONArray jsonArray = new JSONArray();
                                for(int i= 0; i< MainActivity.arrCart.size(); i++){
                                    JSONObject jsonObject =  new JSONObject();
                                    try {
                                        jsonObject.put("idCustomer", idCustommer[0]);
                                        jsonObject.put("idBook", MainActivity.arrCart.get(i).getIdBook());
                                        jsonObject.put("nameBook", MainActivity.arrCart.get(i).getName());
                                        jsonObject.put("priceBook", MainActivity.arrCart.get(i).getPrice());
                                        jsonObject.put("quantityBook", MainActivity.arrCart.get(i).getQuantity());

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                String linkDonhang = Server.linkDonhang + "?jsonCart=" + jsonArray.toString();
                                StringRequest request = new StringRequest(Request.Method.GET, linkDonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String responseDonhang) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(responseDonhang);
                                            String status = jsonObject.getString("status");
                                            if(status.equals("success")){
                                                MainActivity.arrCart.clear();
                                                CheckConnection.showToast_Short(getApplicationContext(),"Đơn hàng của bạn đã được thêm thành công !");
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                CheckConnection.showToast_Short(getApplicationContext(),"Có lỗi trong quá trình thêm đơn hàng. Vui lòng thử lại sau !");
                                            }
                                        } catch (JSONException e) {
                                            CheckConnection.showToast_Short(getApplicationContext(),"ABCJASBKJ ");
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requestQueue.add(request);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(stringRequest);
                }
                else{
                    CheckConnection.showToast_Short(getApplicationContext(), "Bạn chưa điền đầy đủ thông tin !");
                }
            }
        });
    }

    private void mapping() {
        btnXacnhan = findViewById(R.id.xacnhanUser);
        btnCancel = findViewById(R.id.cancelUser);
        fullName = findViewById(R.id.fullnameUser);
        email = findViewById(R.id.emailUser);
        phone  = findViewById(R.id.phoneUser);
        address = findViewById(R.id.addressUser);
    }
}