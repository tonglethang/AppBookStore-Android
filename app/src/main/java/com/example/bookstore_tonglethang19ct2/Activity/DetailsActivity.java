package com.example.bookstore_tonglethang19ct2.Activity;

import static java.util.stream.Collectors.mapping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.bookstore_tonglethang19ct2.Adapter.CartAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.Models.Cart;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgDetail;
    TextView nameDetails, priceDetails, motaDetails, nxbDetails;
    Button addCart;
    Spinner spinner;

    String id = "";
    String name = "";
    Integer price = 0;
    String img = "";
    String  nhaxuatban = "";
    Integer soluong = 0;
    String type = "";
    String mota = "";
    String idType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mapping();
        ActionToolBar();
        GetInfo();
        EventCart();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
    private void EventCart() {
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arrCart.size() > 0) {
                    int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit = false;
                    for(int i = 0; i < MainActivity.arrCart.size(); i++) {
                        if(MainActivity.arrCart.get(i).getIdBook() == id) {
                            MainActivity.arrCart.get(i).setQuantity(MainActivity.arrCart.get(i).getQuantity() + quantity);
                            if(MainActivity.arrCart.get(i).getQuantity() >= soluong){
                                MainActivity.arrCart.get(i).setQuantity(soluong);
                            }
                            MainActivity.arrCart.get(i).setPrice(price * MainActivity.arrCart.get(i).getQuantity());
                            exit = true;
                        }
                    }
                    if (exit == false){
                        int quantity_new = Integer.parseInt(spinner.getSelectedItem().toString());
                        long priceTotal = quantity_new * price;
                        MainActivity.arrCart.add(new Cart(id, name, priceTotal, img,quantity_new, soluong));
                    }
                }else{
                    int quantity_new = Integer.parseInt(spinner.getSelectedItem().toString());
                    long priceTotal = quantity_new * price;
                    MainActivity.arrCart.add(new Cart(id, name, priceTotal, img,quantity_new, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void GetInfo() {
        Book book = (Book) getIntent().getSerializableExtra("infomaitionBook");
        id = book.getId();
        name = book.getName();
        price = book.getPrice();
        img = book.getImage();
        nhaxuatban = book.getNhaxuatban();
        soluong = book.getSoluong();
        type = book.getType();
        mota = book.getMota();
        idType = book.getIdType();

        Integer[] quanSpinner = new Integer[soluong];
        for (int i = 0; i < soluong; i++) {
            int tmp = i + 1;
            quanSpinner[i] = tmp;
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, quanSpinner);
        spinner.setAdapter(arrayAdapter);

        nameDetails.setText(name);
        nxbDetails.setText("Nhà xuất bản: " + nhaxuatban);
        DecimalFormat deci = new DecimalFormat("###,###,###");
        priceDetails.setText(deci.format(price) + "đ");
        motaDetails.setText(mota);
        Picasso.get().load(img)
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(imgDetail);


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
        toolbar = findViewById(R.id.toolbarDetails);
        imgDetail = findViewById(R.id.imgDetails);
        nameDetails = findViewById(R.id.nameDetails);
        motaDetails = findViewById(R.id.motaDetails);
        priceDetails = findViewById(R.id.priceDetails);
        nxbDetails = findViewById(R.id.nxbDetails);
        addCart = findViewById(R.id.addCart);
        spinner = findViewById(R.id.spinner);
    }
}