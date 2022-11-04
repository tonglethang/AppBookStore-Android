package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bookstore_tonglethang19ct2.Models.Book;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter
{
    ArrayList<Book> arrBook;
    Context context;
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
        return i;
    }
    public  class ViewHolder{

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
