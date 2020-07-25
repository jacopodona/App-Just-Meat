package com.example.justmeat.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;

class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {
    ArrayList<Coupon> couponArrayList;
    CheckoutActivity checkoutActivity;

    public CouponAdapter(CheckoutActivity checkoutActivity) {
        this.couponArrayList = checkoutActivity.couponArrayList;
        this.checkoutActivity = checkoutActivity;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_coupon, parent, false);
        CouponViewHolder cVH = new CouponViewHolder(v);
        return cVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        final CouponAdapter couponAdapter = this;
        double detractionPrice;
        final Coupon currentItem = couponArrayList.get(position);
        holder.code.setText(currentItem.getCode());
        holder.percentage.setText(currentItem.getPercentage()*100 +"%");
        detractionPrice = checkoutActivity.tot * currentItem.getPercentage();
        holder.detraction.setText("- "+ String.format("%.2f",detractionPrice) + " €");
        checkoutActivity.tot -= detractionPrice;
        holder.btn_removeCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponArrayList.remove(currentItem);
                checkoutActivity.tot = checkoutActivity.subtotal;
                couponAdapter.notifyDataSetChanged();
            }
        });
        checkoutActivity.tot_txt.setText(String.format("%.2f",checkoutActivity.tot) + " €");
    }

    @Override
    public int getItemCount() {
        return couponArrayList.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder {
        TextView code, percentage, detraction;
        ImageView btn_removeCoupon;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.checkout_txt_code_coupon);
            percentage = itemView.findViewById(R.id.checkout_txt_type_coupon);
            detraction = itemView.findViewById(R.id.checkout_txt_price_coupon);
            btn_removeCoupon = itemView.findViewById(R.id.checkout_btn_delete_coupon);
        }
    }
}
