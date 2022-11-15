package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.DetailsOrder;
import com.example.bookstore_tonglethang19ct2.Models.Order;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderBookAdapter extends BaseAdapter {
    Context context;
    ArrayList<DetailsOrder> arrOrderBook;

    public OrderBookAdapter(Context context, ArrayList<DetailsOrder> arrOrderBook) {
        this.context = context;
        this.arrOrderBook = arrOrderBook;
    }

    @Override
    public int getCount() {
        return arrOrderBook.size();
    }

    @Override
    public Object getItem(int i) {
        return arrOrderBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public static class ViewHolder{
        public TextView nameBook, soluongBook, totalPriceBook;
        public ImageView imgBook;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.orderbook, null);
            viewHolder.nameBook =  (TextView) view.findViewById(R.id.nameBook);
            viewHolder.soluongBook = (TextView) view.findViewById(R.id.soluongBook);
            viewHolder.totalPriceBook = (TextView) view.findViewById(R.id.priceBook);
            viewHolder.imgBook = view.findViewById(R.id.imgBook);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = ( OrderBookAdapter.ViewHolder) view.getTag();
        }
        DetailsOrder order = (DetailsOrder) getItem(i);
        viewHolder.nameBook.setText(order.getName());
        viewHolder.soluongBook.setText(order.getQuantity()+"");
        java.text.DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.totalPriceBook.setText(deci.format(order.getPrice()) + "đ");
        Picasso.get().load(order.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgBook);
        return view;
    }
}
