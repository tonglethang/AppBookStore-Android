package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ItemHolder>
{
    Context context;
    ArrayList<Book> arrBook;
    public BookAdapter(Context context, ArrayList<Book> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newbook, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Book book = arrBook.get(position);
        holder.nameBook.setText(book.getName());
        DecimalFormat deci = new DecimalFormat("###,###,###");
        holder.priceBook.setText(deci.format(book.getPrice())+ "đ");
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(holder.imgBook);
    }

    @Override
    public int getItemCount() {
        return arrBook.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgBook;
        public TextView nameBook;
        public TextView priceBook;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = (ImageView) itemView.findViewById(R.id.imgBook);
            nameBook = (TextView) itemView.findViewById(R.id.nameBook);
            priceBook = (TextView) itemView.findViewById(R.id.priceBook);
        }
    }
}
