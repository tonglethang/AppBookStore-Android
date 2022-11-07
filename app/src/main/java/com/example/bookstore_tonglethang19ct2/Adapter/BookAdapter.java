package com.example.bookstore_tonglethang19ct2.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore_tonglethang19ct2.Activity.DetailsActivity;
import com.example.bookstore_tonglethang19ct2.Interface.ItemClickListener;
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
        holder.setItemOnclick(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick == true){
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("infomaitionBook", arrBook.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrBook.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imgBook;
        public TextView nameBook;
        public TextView priceBook;
        private ItemClickListener itemOnclick;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = (ImageView) itemView.findViewById(R.id.imgBook);
            nameBook = (TextView) itemView.findViewById(R.id.nameBook);
            priceBook = (TextView) itemView.findViewById(R.id.priceBook);
            itemView.setOnClickListener(this);


        }
        public void setItemOnclick(ItemClickListener itemOnclick) {
            this.itemOnclick = itemOnclick;
        }

        @Override
        public void onClick(View view) {
            itemOnclick.onClick(view, getAdapterPosition(), true);
        }
    }
}
