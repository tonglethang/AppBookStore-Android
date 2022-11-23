package com.example.bookstore_tonglethang19ct2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore_tonglethang19ct2.Activity.DetailsActivity;
import com.example.bookstore_tonglethang19ct2.Interface.ItemClickListener;
import com.example.bookstore_tonglethang19ct2.Models.Book;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private ArrayList<Book> arrBookSearch;
    private ArrayList<Book> arrTmp;
    Context context;
    public SearchAdapter(Context context, ArrayList<Book> arrBookSearch) {
        this.context = context;
        this.arrBookSearch = arrBookSearch;
        this.arrTmp = arrBookSearch;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booksrearch, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Book book = arrBookSearch.get(position);
        if(book == null){
            return;
        }
        holder.name.setText(book.getName());
        holder.nxb.setText(book.getNhaxuatban());
        DecimalFormat deci = new DecimalFormat("###,###,###");
        holder.price.setText(deci.format(book.getPrice())+ "đ");
        Picasso.get().load(book.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(holder.img);
        holder.setItemOnclick(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick == true){
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("infomaitionBook", arrBookSearch.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrBookSearch != null){
            return arrBookSearch.size();
        }
        else{
            return 0;
        }

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    arrBookSearch = arrTmp;
                }
                else{
                    ArrayList<Book> arrBook = new ArrayList<>();
                    for(Book book : arrTmp){
                        if(book.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            arrBook.add(book);
                        }
                    }
                    arrBookSearch = arrBook;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrBookSearch;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrBookSearch = (ArrayList<Book>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        private TextView name, price, nxb;
        private ItemClickListener itemOnclick;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgBookSearch);
            name = itemView.findViewById(R.id.nameBookSearch);
            price = itemView.findViewById(R.id.priceBookSearch);
            nxb = itemView.findViewById(R.id.nxbBookSearch);
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
