package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;
import com.example.bookstore_tonglethang19ct2.Utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateBookActivity extends AppCompatActivity {
    ImageView imgPick, imgOld;
    Button updateBook, back;

    private static int IMAGE_REQ;
    private static String TAG = "Upload image @@@@";
    private Uri imgPath;
    String urlImg;

    String idBook;
    String typeBook;
    String urlImgOld;
    EditText name, price, soluong, nhaxuatban, mota;
    RadioButton thieunhiBook, kinhteBook, ngoainguBook, tamlyBook;
    Toolbar toolbar;
    LinearLayout linear;
    View footerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        mapping();
        ActionToolBar();
        GetData();
        imgPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });


        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPath == null){
                    Log.d(TAG, "urlOld: " + urlImgOld);
                    Log.d(TAG, "url: " + urlImg);
                    InsertData(urlImgOld);
                }
                else{
                    configCloud();
                    MediaManager.get().upload(imgPath).callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Log.d(TAG, "onStart: " + "started");
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            Log.d(TAG, "onStart: " + "uploading");
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            Log.d(TAG, "onStart: " + "success");
                            urlImg = Objects.requireNonNull(resultData.get("url").toString());
                            Log.d(TAG, "url: " + urlImg);
                            InsertData(urlImg);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Log.d(TAG, "onStart: " + error.toString());
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            Log.d(TAG, "onStart: " + error.toString());
                        }
                    }).dispatch();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void  InsertData(String urlImg) {
        if(name.getText().toString().length() <= 0 || price.getText().toString().length() <= 0|| soluong.getText().toString().length() <= 0|| nhaxuatban.getText().toString().length() <= 0|| mota.getText().toString().length() <= 0){
            CheckConnection.showToast_Short(getApplicationContext(),"Vui l??ng nh???p ?????y ????? th??ng tin s??ch !");
        }
        else{
            if(thieunhiBook.isChecked() || kinhteBook.isChecked() || ngoainguBook.isChecked() || tamlyBook.isChecked()){
                if(thieunhiBook.isChecked()){
                    typeBook = "S??ch thi???u nhi";
                }if(kinhteBook.isChecked()){
                    typeBook = "S??ch kinh t???";
                }if(ngoainguBook.isChecked()){
                    typeBook = "S??ch ngo???i ng???";
                }if(tamlyBook.isChecked()){
                    typeBook = "S??ch t??m l??";
                }
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                Log.d("acb" ,"ABC: " + urlImg);
                String link = Server.linkUpdateBook+ "?id="+ idBook + "&name=" + name.getText().toString() + "&price=" + price.getText().toString() + "&image=" + urlImg
                        + "&soluong=" + soluong.getText().toString() + "&nhaxuatban=" + nhaxuatban.getText().toString() + "&type=" + typeBook + "&mota=" + mota.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")){
                                CheckConnection.showToast_Short(getApplicationContext(), "???? update s??ch th??nh c??ng !");
                                Intent intent = new Intent(getApplicationContext(), AdminBookActivity.class);
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
            else{
                CheckConnection.showToast_Short(getApplicationContext(),"Vui l??ng nh???p ?????y ????? th??ng tin s??ch !");
            }
        }
    }
    private void GetData() {
        Book book = (Book) getIntent().getSerializableExtra("infoBook");
        idBook = book.getId();
        name.setText(book.getName());
        price.setText(book.getPrice()+"");
        soluong.setText(book.getSoluong()+"");
        nhaxuatban.setText(book.getNhaxuatban());
        mota.setText(book.getMota());
        if(book.getType().equals("S??ch thi???u nhi")){
            thieunhiBook.setChecked(true);
        }
        if(book.getType().equals("S??ch kinh t???")){
            kinhteBook.setChecked(true);
        }
        if(book.getType().equals("S??ch ngo???i ng???")){
            ngoainguBook.setChecked(true);
        }
        if(book.getType().equals("S??ch t??m l??")){
            tamlyBook.setChecked(true);
        }
        urlImgOld = book.getImage();
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(imgOld);
    }

    private void requestPermissions() {
        if(ContextCompat.checkSelfPermission(UpdateBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            selectImg();
        }
        else{
            ActivityCompat.requestPermissions(UpdateBookActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

//    private void selectImg() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,IMAGE_REQ);
//
//    }

    private void configCloud() {
        try{
            linear.addView(footerView);
            Map config = new HashMap();
            config.put("cloud_name", "dadcmqprj");
            config.put("api_key", "156293957484648");
            config.put("api_secret", "hEcqgn1-ktN4c-1WYbEKwZQ8PPQ");
            MediaManager.init(this, config);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(intent,IMAGE_REQ);
        someActivityResultLauncher.launch(intent);

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        imgPath = data.getData();
                        Picasso.get().load(imgPath)
                                .placeholder(R.drawable.img)
                                .error(R.drawable.img_1)
                                .into(imgPick);
                    }
                }
            });

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
        toolbar = findViewById(R.id.toolBarUpdateBook);
        imgOld = findViewById(R.id.imgOld);
        imgPick = findViewById(R.id.imgPick);
        updateBook = findViewById(R.id.updateBook);
        back = findViewById(R.id.back);
        name = findViewById(R.id.nameBook);
        price  =findViewById(R.id.priceBook);
        soluong = findViewById(R.id.soluongBook);
        nhaxuatban = findViewById(R.id.nhaxuatbanBook);
        mota = findViewById(R.id.motaBook);
        thieunhiBook = findViewById(R.id.thieunhiBook);
        kinhteBook = findViewById(R.id.kinhteBook);
        ngoainguBook = findViewById(R.id.ngoainguBook);
        tamlyBook = findViewById(R.id.tamlyBook);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.processbar, null);
        linear = findViewById(R.id.linearProgress);
    }
}