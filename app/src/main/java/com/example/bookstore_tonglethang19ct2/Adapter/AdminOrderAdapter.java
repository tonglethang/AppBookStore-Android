package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.Customer;
import com.example.bookstore_tonglethang19ct2.Models.Order;
import com.example.bookstore_tonglethang19ct2.R;

import java.util.ArrayList;

public class AdminOrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> arrOrder;

    public AdminOrderAdapter(Context context, ArrayList<Order> arrOrder) {
        this.context = context;
        this.arrOrder = arrOrder;
    }

    @Override
    public int getCount() {
        return arrOrder.size();
    }

    @Override
    public Object getItem(int i) {
        return arrOrder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView idCus, nameBook, soluongBook, totalPriceBook;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdminOrderAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new  AdminOrderAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order, null);
            viewHolder.idCus = (TextView) view.findViewById(R.id.idCustomer);
            viewHolder.nameBook =  (TextView) view.findViewById(R.id.nameBook);
            viewHolder.soluongBook = (TextView) view.findViewById(R.id.soluongBook);
            viewHolder.totalPriceBook = (TextView) view.findViewById(R.id.totalPriceBook);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = ( AdminOrderAdapter.ViewHolder) view.getTag();
        }
        Order order = (Order) getItem(i);
        viewHolder.idCus.setText(order.getIdCus());
        viewHolder.nameBook.setText(order.getNameBook());
        viewHolder.soluongBook.setText(order.getSoluongBook()+"");
        viewHolder.totalPriceBook.setText(order.getTotalPriceBook() + "đ");
        return view;
    }
}
