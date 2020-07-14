package com.example.justmeat.checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;

public class CheckoutActivity extends AppCompatActivity {
    public int currentActiveId = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setCheckoutLayout();
    }

    private void setCheckoutLayout() {
        ImageView back_btn = findViewById(R.id.checkout_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final TimePicker timePicker = findViewById(R.id.checkout_tp_time);
        timePicker.setIs24HourView(true);

        RecyclerView rv = findViewById(R.id.checkout_rv_day);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter rvA = new DayAdapter(this);

        rv.setAdapter(rvA);
        rv.setLayoutManager(rvLM);
    }
}
