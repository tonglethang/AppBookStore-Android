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

public class AdminBookAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> arrBook;

    public AdminBookAdapter(Context context, ArrayList<Book> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }

    @Override
    public int getCount() {
        return arrBook.size();
    }

    @Override
    public Object getItem(int i) {

        return arrBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        public TextView nameBook, priceBook, nxbBook;
        public ImageView imgBook;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdminBookAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new AdminBookAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adminbook, null);
            viewHolder.nameBook = (TextView) view.findViewById(R.id.nameBookAdmin);
            viewHolder.priceBook = (TextView) view.findViewById(R.id.priceBookAdmin);
            viewHolder.nxbBook = (TextView) view.findViewById(R.id.nxbBookAdmin);
            viewHolder.imgBook = (ImageView) view.findViewById(R.id.imgBookAdmin);
            view.setTag(viewHolder);
        } else {
            viewHolder = ( AdminBookAdapter.ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(i);
        viewHolder.nameBook.setText(book.getName());
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceBook.setText(deci.format(book.getPrice()) + "đ");
        viewHolder.nxbBook.setText("Nhà xuất bản: " + book.getNhaxuatban());
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBook);
        //set maxLine()
        return view;
    }
}
