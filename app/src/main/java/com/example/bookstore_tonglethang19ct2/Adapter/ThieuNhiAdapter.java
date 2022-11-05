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

public class ThieuNhiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> arrThieuNhiBook;

    public ThieuNhiAdapter(Context context, ArrayList<Book> arrThieuNhiBook) {
        this.context = context;
        this.arrThieuNhiBook = arrThieuNhiBook;
    }

    @Override
    public int getCount() {
        return arrThieuNhiBook.size();
    }

    @Override
    public Object getItem(int i) {

        return arrThieuNhiBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView nameBookThieuNhi, priceBookThieuNhi, nxbBookThieuNhi;
        public ImageView imgBookThieuNhi;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.thieunhi, null);
            viewHolder.nameBookThieuNhi = (TextView) view.findViewById(R.id.nameBookThieuNhi);
            viewHolder.priceBookThieuNhi = (TextView) view.findViewById(R.id.priceBookThieuNhi);
            viewHolder.nxbBookThieuNhi =  (TextView) view.findViewById(R.id.nxbBookThieuNhi);
            viewHolder.imgBookThieuNhi = (ImageView) view.findViewById(R.id.imgBookThieuNhi);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(i);
        viewHolder.nameBookThieuNhi.setText(book.getName());
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceBookThieuNhi.setText(deci.format(book.getPrice())+ "đ");
        viewHolder.nxbBookThieuNhi.setText("Nhà xuất bản: " + book.getNhaxuatban());
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBookThieuNhi);
        //set maxLine()
        return view;
    }
}
