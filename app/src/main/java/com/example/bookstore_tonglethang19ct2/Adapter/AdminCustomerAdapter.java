package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.Customer;
import com.example.bookstore_tonglethang19ct2.R;
import java.util.ArrayList;

public class AdminCustomerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Customer> arrCustomer;

    public AdminCustomerAdapter(Context context, ArrayList<Customer> arrCustomer) {
        this.context = context;
        this.arrCustomer = arrCustomer;
    }

    @Override
    public int getCount() {
        return arrCustomer.size();
    }

    @Override
    public Object getItem(int i) {
        return arrCustomer.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView idCus, fullname, email, phone, address;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdminCustomerAdapter.ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new  AdminCustomerAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customer, null);
            viewHolder.idCus = (TextView) view.findViewById(R.id.idCustomer);
            viewHolder.fullname = (TextView) view.findViewById(R.id.nameCustomer);
            viewHolder.email =  (TextView) view.findViewById(R.id.emailCustomer);
            viewHolder.phone = (TextView) view.findViewById(R.id.phoneCustomer);
            viewHolder.address = (TextView) view.findViewById(R.id.addressCustomer);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = ( AdminCustomerAdapter.ViewHolder) view.getTag();
        }
        Customer customer =  (Customer) getItem(i);
        viewHolder.idCus.setText(customer.getId());
        viewHolder.fullname.setText(customer.getName());
        viewHolder.email.setText(customer.getEmail());
        viewHolder.phone.setText(customer.getPhone());
        viewHolder.address.setText(customer.getAddress());
        return view;
    }
}
