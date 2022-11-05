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

public class KinhTeAdapter extends BaseAdapter {

    Context context;
    ArrayList<Book> arrKinhTeBook;

    public KinhTeAdapter(Context context, ArrayList<Book> arrThieuNhiBook) {
        this.context = context;
        this.arrKinhTeBook = arrThieuNhiBook;
    }

    @Override
    public int getCount() {
        return arrKinhTeBook.size();
    }

    @Override
    public Object getItem(int i) {

        return arrKinhTeBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView nameBookKinhTe, priceBookKinhTe, nxbBookKinhTe;
        public ImageView imgBookKinhTe;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        KinhTeAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new KinhTeAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.kinhte, null);
            viewHolder.nameBookKinhTe = (TextView) view.findViewById(R.id.nameBookKinhTe);
            viewHolder.priceBookKinhTe = (TextView) view.findViewById(R.id.priceBookKinhTe);
            viewHolder.nxbBookKinhTe=  (TextView) view.findViewById(R.id.nxbBookKinhTe);
            viewHolder.imgBookKinhTe = (ImageView) view.findViewById(R.id.imgBookKinhTe);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (KinhTeAdapter.ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(i);
        viewHolder.nameBookKinhTe.setText(book.getName());
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceBookKinhTe.setText(deci.format(book.getPrice())+ "đ");
        viewHolder.nxbBookKinhTe.setText("Nhà xuất bản: " + book.getNhaxuatban());
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBookKinhTe);
        //set maxLine()
        return view;
    }
}
