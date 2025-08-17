package com.example.nguyenhoanganhtuan_2121110255;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> cartList;

    public CartAdapter(Context context, ArrayList<Product> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        Product p = cartList.get(position);

        ImageView img = convertView.findViewById(R.id.imgCartProduct);
        TextView name = convertView.findViewById(R.id.txtCartName);
        TextView price = convertView.findViewById(R.id.txtCartPrice);

        img.setImageResource(p.getImage());
        name.setText(p.getName());
        price.setText(NumberFormat.getInstance(new Locale("vi", "VN")).format(p.getPrice()) + " đ");

        return convertView;
    }
}
