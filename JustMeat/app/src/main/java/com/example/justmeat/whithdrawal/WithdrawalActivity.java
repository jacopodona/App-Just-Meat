package com.example.justmeat.whithdrawal;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Date;

public class WithdrawalActivity extends AppCompatActivity {
    ArrayList<Date> calendario = new ArrayList<>();
    public int currentActiveId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        setActivityLayout();
    }

    private void setActivityLayout() {
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
        Button gotoCheckout = findViewById(R.id.withdrawal_btn_gotocheckout);
        gotoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date selectedDate = calendario.get(currentActiveId);
                String dataSelezionata = (selectedDate.getYear()+1900)
                        +"-"+String.format("%02d", (selectedDate.getMonth()+1))+"-"
                        +String.format("%02d", selectedDate.getDate())+"T";
                dataSelezionata += timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute()+":00.000Z";
                System.out.println(dataSelezionata);
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                intent.putExtra("pickup_date", dataSelezionata);
                startActivity(intent);
            }
        });
    }
}
