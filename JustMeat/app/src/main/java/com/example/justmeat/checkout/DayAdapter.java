package com.example.justmeat.checkout;

import android.content.res.ColorStateList;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    final int NDAY = 6; //totale giorni visualizzati = NDAY+1
    ArrayList<Date> calendario = new ArrayList<>();
    DayViewHolder currentActive;
    CheckoutActivity checkoutActivity;

    public DayAdapter(CheckoutActivity checkoutActivity){
        this.checkoutActivity = checkoutActivity;
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours()<20){
            calendario.add(calendar.getTime());
        } else {
            calendar.add(Calendar.DAY_OF_YEAR,1);
            calendario.add(calendar.getTime());
        }
        for (int i = 0; i<NDAY; i++){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            calendario.add(calendar.getTime());
        }

    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_checkout_day, parent, false);
        DayViewHolder dayViewHolder = new DayViewHolder(view);
        return dayViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String month_txt, day_txt, numb_txt;
        Date currentDate = this.calendario.get(position);
        month_txt = (String) DateFormat.format("MMMM", currentDate );
        holder.month.setText(month_txt);
        numb_txt = (String) DateFormat.format("dd", currentDate);
        holder.numb.setText(numb_txt);
        day_txt = (String) DateFormat.format("EE", currentDate);
        holder.day.setText(day_txt);
        holder.id = position;
    }

    @Override
    public int getItemCount() {
        return calendario.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView numb, month, day;
        CardView cardView;
        ColorStateList defaultColor;
        int id;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            numb = itemView.findViewById(R.id.checkout_txt_numb);
            month = itemView.findViewById(R.id.checkout_txt_month);
            day = itemView.findViewById(R.id.checkout_txt_day);
            cardView = itemView.findViewById(R.id.checkout_card_day);
            defaultColor = month.getTextColors();

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(currentActive != null){
                currentActive.DayUnactive(v);
            }
            currentActive = this;
            if(checkoutActivity.currentActiveId != id){
                checkoutActivity.currentActiveId = this.id;
                this.DayActive(v);
            } else {
                checkoutActivity.currentActiveId = -1;
                this.DayUnactive(v);
            }
        }
        public void DayActive(View v){
            cardView.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.maximumyellowred));
            numb.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
            month.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
            day.setTextColor(ContextCompat.getColor(v.getContext(), R.color.white));
        }
        public void DayUnactive(View v){
            cardView.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.white));
            numb.setTextColor(ContextCompat.getColor(v.getContext(), R.color.cerise));
            month.setTextColor(defaultColor);
            day.setTextColor(defaultColor);
        }
    }
}
