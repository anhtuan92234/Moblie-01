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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.imgCartProduct);
            holder.name = convertView.findViewById(R.id.txtCartName);
            holder.price = convertView.findViewById(R.id.txtCartPrice);
            holder.btnRemove = convertView.findViewById(R.id.btnRemove);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product p = cartList.get(position);

        holder.img.setImageResource(p.getImage());
        holder.name.setText(p.getName());
        holder.price.setText(NumberFormat.getInstance(new Locale("vi", "VN")).format(p.getPrice()) + " đ");

        // Xóa sản phẩm
        holder.btnRemove.setOnClickListener(v -> {
            CartManager.removeFromCart(p);
            notifyDataSetChanged();

            if (context instanceof CartActivity) {
                ((CartActivity) context).updateTotalPrice();
            }
        });
        return convertView;
    }
    static class ViewHolder {
        ImageView img, btnRemove;
        TextView name, price;
    }
}
