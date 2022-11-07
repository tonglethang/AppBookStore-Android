package com.example.bookstore_tonglethang19ct2.Activity;

import static com.example.bookstore_tonglethang19ct2.Activity.MainActivity.arrCart;
import static java.util.stream.Collectors.mapping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Adapter.CartAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView messCart;
    static TextView priceTotal;
    Button btnThanhtoan;
    Button btnTiepTuc;
    Toolbar toolbar;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mapping();
        ActionToolBar();
        CheckData();
        EventUtills();
    }

    public static void EventUtills() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.arrCart.size(); i++){
            tongtien += MainActivity.arrCart.get(i).getPrice();
        }
        DecimalFormat deci = new DecimalFormat("###,###,###");
        priceTotal.setText(deci.format(tongtien)+ "đ");
    }

    private void CheckData() {
        if(arrCart.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            messCart.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        }else{
            cartAdapter.notifyDataSetChanged();
            messCart.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
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
        listViewCart = findViewById(R.id.listviewCart);
        messCart = findViewById(R.id.messCart);
        priceTotal = findViewById(R.id.priceTotal);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
        toolbar = findViewById(R.id.toolbarCart);
        cartAdapter = new CartAdapter(CartActivity.this, arrCart);
        listViewCart.setAdapter(cartAdapter);

    }
}