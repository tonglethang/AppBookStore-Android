package com.example.bookstore_tonglethang19ct2.Adapter;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bookstore_tonglethang19ct2.Activity.CartActivity;
import com.example.bookstore_tonglethang19ct2.Activity.MainActivity;
import com.example.bookstore_tonglethang19ct2.Models.Cart;
import com.example.bookstore_tonglethang19ct2.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrCart;

    public CartAdapter(Context context, ArrayList<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView nameCart;
        public TextView priceCart;
        public ImageView imgCart;
        public Button btnMinus, btnPlus, btnValue;
    }
    ViewHolder viewHolder = null;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
             viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cart, null);
            viewHolder.nameCart = view.findViewById(R.id.nameCart);
            viewHolder.priceCart = view.findViewById(R.id.priceCart);
            viewHolder.imgCart = view.findViewById(R.id.imgCart);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            viewHolder.btnValue = view.findViewById(R.id.btnValue);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHolder.nameCart.setText(cart.getName());
        DecimalFormat deci = new DecimalFormat("###,###,###");
        viewHolder.priceCart.setText(deci.format(cart.getPrice()) + "đ");
        Picasso.get().load(cart.getImage())
                .placeholder(R.drawable.img)
                .error(R.drawable.img_1)
                .into(viewHolder.imgCart);
        viewHolder.btnValue.setText(cart.getQuantity() +"");
        int quan = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(quan >= cart.getQuanMax()){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }else if(quan <=1 ){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }
        else{
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quanNew = Integer.parseInt(viewHolder.btnValue.getText().toString()) +1 ;
                int quanNow = MainActivity.arrCart.get(i).getQuantity();
                long priceNow = MainActivity.arrCart.get(i).getPrice();

                MainActivity.arrCart.get(i).setQuantity(quanNew);
                long priceNew =( priceNow * quanNew ) / quanNow;
                MainActivity.arrCart.get(i).setPrice(priceNew);

                DecimalFormat deci = new DecimalFormat("###,###,###");
                viewHolder.priceCart.setText(deci.format(priceNew) + "đ");
                CartActivity.EventUtills();
                if(quanNew > arrCart.get(i).getQuanMax() - 1){
                    viewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(quanNew));
                }
                else{
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(quanNew));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quanNew = Integer.parseInt(viewHolder.btnValue.getText().toString()) - 1 ;
                int quanNow = MainActivity.arrCart.get(i).getQuantity();
                long priceNow = MainActivity.arrCart.get(i).getPrice();

                MainActivity.arrCart.get(i).setQuantity(quanNew);
                long priceNew =( priceNow * quanNew ) / quanNow;
                MainActivity.arrCart.get(i).setPrice(priceNew);

                DecimalFormat deci = new DecimalFormat("###,###,###");
                viewHolder.priceCart.setText(deci.format(priceNew) + "đ");
                CartActivity.EventUtills();
                if(quanNew  < 2){
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(quanNew));
                }
                else{
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(quanNew));
                }
            }
        });
        return view;
    }
}
