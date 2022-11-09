package com.example.bookstore_tonglethang19ct2.Activity;

import static com.example.bookstore_tonglethang19ct2.Activity.MainActivity.arrCart;
import static java.util.stream.Collectors.mapping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Adapter.CartAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;

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
        catchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arrCart.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), InfoUserActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.showToast_Short(getApplicationContext(), "Giỏ hàng của bạn đang trống !");
                }
            }
        });
    }

    private void catchOnItemListView() {
        listViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận !");
                builder.setMessage("Bạn có chắc xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.arrCart.size() <= 0) {
                            messCart.setVisibility(View.VISIBLE);
                        }
                        else{
                            MainActivity.arrCart.remove(pos);
                            cartAdapter.notifyDataSetChanged();
                            EventUtills();
                            if(MainActivity.arrCart.size() <= 0){
                                messCart.setVisibility(View.VISIBLE);
                            }
                            else{
                                messCart.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUtills();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.notifyDataSetChanged();
                        EventUtills();

                    }
                });
                builder.show();
                return true;
            }
        });
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