package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookstore_tonglethang19ct2.Adapter.BookAdapter;
import com.example.bookstore_tonglethang19ct2.Adapter.TypeBookAdapter;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.Models.Cart;
import com.example.bookstore_tonglethang19ct2.Models.TypeBook;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFipper;
    RecyclerView recyclerTrangchu;
    NavigationView naviChinh;
    ListView listView;
    DrawerLayout drawerLayout;

    ArrayList<TypeBook> arrTypeBook;
    TypeBookAdapter typeBookAdapter;

    ArrayList<Book> arrNewBook;
    BookAdapter bookAdapter;

    String id = "";
    String name = "";
    String image = "";

    public static ArrayList<Cart> arrCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            getDataTypeBook();
            getDataAllBook();

            catchOnItemListView();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(), "You check connection !");
            finish();
        }

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

    private void catchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position) {
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SachThieuNhiActivity.class);
                            intent.putExtra("idType", arrTypeBook.get(position).getId());
                            startActivity(intent);;
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SachKinhTeActivity.class);
                            intent.putExtra("idType", arrTypeBook.get(position).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SachNgoaiNguActivity.class);
                            intent.putExtra("idType", arrTypeBook.get(position).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, SachTamLyActivity.class);
                            intent.putExtra("idType", arrTypeBook.get(position).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(), "You check internet again !");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getDataAllBook() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Server.linkAllBook, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response != null){
                    String id = "";
                    String name = "";
                    Integer price = 0;
                    String img = "";
                    String  nhaxuatban = "";
                    Integer soluong = 0;
                    String type = "";
                    String mota = "";
                    String idType = "";
                    try {
                        JSONArray jsonArray = response.getJSONArray("Books");
                        for(int j = 0; j < jsonArray.length(); j++){
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            id = jsonObject.getString("_id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            img = jsonObject.getString("image");
                            nhaxuatban = jsonObject.getString("nhaxuatban");
                            soluong = jsonObject.getInt("soluong");
                            type = jsonObject.getString("type");
                            mota = jsonObject.getString("mota");
                            idType = jsonObject.getString("idType");
                            arrNewBook.add(new Book(id, name, price, img, nhaxuatban, soluong, type, mota, idType));
                            bookAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    private void getDataTypeBook() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Server.linkTypeBook, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response != null){
                    try {
                        JSONArray jsonArray = response.getJSONArray("TypeBooks");
                        for(int j = 0; j < jsonArray.length(); j++){
                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                            id = jsonObject.getString("_id");
                            name = jsonObject.getString("name");
                            image = jsonObject.getString("image");
                            arrTypeBook.add(new TypeBook(id, name, image));
                            typeBookAdapter.notifyDataSetChanged();
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    arrTypeBook.add(5, new TypeBook("", "Liên hệ", "https://tamquatthanthien.vn/uploads/news/2020_03/telephone-icon.png "));
                    arrTypeBook.add(6, new TypeBook("", "Thông tin", "https://support.casio.com/global/en/wat/manual/5413_en/fig/App_icon_02_VPCVILcirwnbhj.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> arrImage = new ArrayList<>();
        arrImage.add("https://theme.hstatic.net/1000363117/1000911694/14/ms_banner_img3.jpg?v=173");
        arrImage.add("https://theme.hstatic.net/1000363117/1000911694/14/ms_banner_img2.jpg?v=173");
        arrImage.add("https://theme.hstatic.net/1000363117/1000911694/14/ms_banner_img3.jpg?v=173");
        for( int i = 0; i<arrImage.size(); i++ ){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(arrImage.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFipper.addView(imageView);
        }
        viewFipper.setFlipInterval(3000);
        viewFipper.setAutoStart(true);
        Animation ani_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_right);
        Animation ani_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFipper.setInAnimation(ani_slide_in);
        viewFipper.setOutAnimation(ani_slide_out);
    }

    private void ActionBar() {
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
        toolbar = (Toolbar) findViewById(R.id.toolBarChinh);
        viewFipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerTrangchu = (RecyclerView) findViewById(R.id.recyNew);
        naviChinh  = (NavigationView) findViewById(R.id.naviChinh);
        listView = (ListView) findViewById(R.id.listViewChinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        arrTypeBook = new ArrayList<>();
        arrTypeBook.add(0, new TypeBook("", "Trang chủ", "https://cdn.pixabay.com/photo/2015/12/28/02/58/home-1110868_960_720.png"));
        typeBookAdapter = new TypeBookAdapter(arrTypeBook, getApplicationContext());
        listView.setAdapter(typeBookAdapter);
        arrNewBook = new ArrayList<>();
        bookAdapter = new BookAdapter(getApplicationContext(), arrNewBook);
        recyclerTrangchu.setHasFixedSize(true);
        recyclerTrangchu.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerTrangchu.setAdapter(bookAdapter);
        if(arrCart != null){

        }
        else{
            arrCart = new ArrayList<>();
        }

    }
    

}