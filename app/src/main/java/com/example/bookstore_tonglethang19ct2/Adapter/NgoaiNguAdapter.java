package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NgoaiNguAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> arrNgoaiNguBook;

    public NgoaiNguAdapter(Context context, ArrayList<Book> arrNgoaiNguBook) {
        this.context = context;
        this.arrNgoaiNguBook = arrNgoaiNguBook;
    }

    @Override
    public int getCount() {
        return arrNgoaiNguBook.size();
    }

    @Override
    public Object getItem(int i) {
        return arrNgoaiNguBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView nameBookNgoaiNgu, priceBookNgoaiNgu, nxbBookNgoaiNgu;
        public ImageView imgBookNgoaiNgu;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NgoaiNguAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new NgoaiNguAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ngoaingu, null);
            viewHolder.nameBookNgoaiNgu = (TextView) view.findViewById(R.id.nameBookNgoaiNgu);
            viewHolder.priceBookNgoaiNgu = (TextView) view.findViewById(R.id.priceBookNgoaiNgu);
            viewHolder.nxbBookNgoaiNgu=  (TextView) view.findViewById(R.id.nxbBookNgoaiNgu);
            viewHolder.imgBookNgoaiNgu = (ImageView) view.findViewById(R.id.imgBookNgoaiNgu);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (NgoaiNguAdapter.ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(i);
        viewHolder.nameBookNgoaiNgu.setText(book.getName());
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceBookNgoaiNgu.setText(deci.format(book.getPrice())+ "đ");
        viewHolder.nxbBookNgoaiNgu.setText("Nhà xuất bản: " + book.getNhaxuatban());
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBookNgoaiNgu);
        //set maxLine()
        return view;
    }
}
