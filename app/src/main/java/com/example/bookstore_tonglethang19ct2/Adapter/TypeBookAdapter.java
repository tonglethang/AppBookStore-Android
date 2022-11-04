package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore_tonglethang19ct2.Models.TypeBook;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TypeBookAdapter extends BaseAdapter {
    ArrayList<TypeBook> arrTypeBook;
    Context context;

    public TypeBookAdapter(ArrayList<TypeBook> arrTypeBook, Context context) {
        this.arrTypeBook = arrTypeBook;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrTypeBook.size();
    }

    @Override
    public Object getItem(int i) {
        return arrTypeBook.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView nameType;
        ImageView imgType;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_typebook, null);
            viewHolder.imgType = view.findViewById(R.id.imgTypeBook);
            viewHolder.nameType = view.findViewById(R.id.nametypebook);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        TypeBook typeBook = (TypeBook) getItem(i);
        viewHolder.nameType.setText(typeBook.getName());
        Picasso.get().load(typeBook.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgType);

        return view;
    }
}
