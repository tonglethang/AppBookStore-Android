package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.Admin;
import com.example.bookstore_tonglethang19ct2.Models.TypeBook;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAdapter extends BaseAdapter {
    ArrayList<Admin> arrAdmin;
    Context context;

    public AdminAdapter(ArrayList<Admin> arrAdmin, Context context) {
        this.arrAdmin = arrAdmin;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrAdmin.size();
    }

    @Override
    public Object getItem(int i) {
        return arrAdmin.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView name;
        ImageView img;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new AdminAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.admin, null);
            viewHolder.img = view.findViewById(R.id.imgAdmin);
            viewHolder.name= view.findViewById(R.id.nameAdmin);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (AdminAdapter.ViewHolder) view.getTag();
        }
        Admin admin = (Admin) getItem(i);
        viewHolder.name.setText(admin.getName());
        Picasso.get().load(admin.getImg())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.img);

        return view;
    }
}
