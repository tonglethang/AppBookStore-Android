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

public class TamLyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> arrTamLyBook;

    public TamLyAdapter(Context context, ArrayList<Book> arrTamLyBook) {
        this.context = context;
        this.arrTamLyBook = arrTamLyBook;
    }

    @Override
    public int getCount() {
        return arrTamLyBook.size();
    }

    @Override
    public Object getItem(int i) {
        return arrTamLyBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView nameBookTamLy, priceBookTamLy, nxbBookTamLy;
        public ImageView imgBookTamLy;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TamLyAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new TamLyAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tamly, null);
            viewHolder.nameBookTamLy = (TextView) view.findViewById(R.id.nameBookTamLy);
            viewHolder.priceBookTamLy = (TextView) view.findViewById(R.id.priceBookTamLy);
            viewHolder.nxbBookTamLy=  (TextView) view.findViewById(R.id.nxbBookTamLy);
            viewHolder.imgBookTamLy = (ImageView) view.findViewById(R.id.imgBookTamLy);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (TamLyAdapter.ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(i);
        viewHolder.nameBookTamLy.setText(book.getName());
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceBookTamLy.setText(deci.format(book.getPrice())+ "đ");
        viewHolder.nxbBookTamLy.setText("Nhà xuất bản: " + book.getNhaxuatban());
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBookTamLy);
        //set maxLine()
        return view;
    }
}
