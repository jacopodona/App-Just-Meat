package com.example.justmeat.whithdrawal;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.checkout.CheckoutActivity;

public class WithdrawalActivity extends AppCompatActivity {

    public int currentActiveId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        setActivityLayout();
    }

    private void setActivityLayout() {
        Button gotoCheckout = findViewById(R.id.withdrawal_btn_gotocheckout);
        gotoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        ImageView back_btn = findViewById(R.id.withdrawal_btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final TimePicker timePicker = findViewById(R.id.withdrawal_tp_time);
        timePicker.setIs24HourView(true);

        RecyclerView rv = findViewById(R.id.withdrawal_rv_day);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter rvA = new DayAdapter(this);

        rv.setAdapter(rvA);
        rv.setLayoutManager(rvLM);
    }
}
