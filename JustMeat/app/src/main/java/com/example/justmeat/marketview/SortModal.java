package com.example.justmeat.marketview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SortModal extends BottomSheetDialogFragment {
    ArrayList<String> sortingMethods = new ArrayList<>();
    MarketViewFragment marketViewFragment;
    String activeSortingMethod = "Consigliato";

    public SortModal(MarketViewFragment marketViewFragment){
        this.marketViewFragment = marketViewFragment;
    }

    private void setSortingMethod() {
        this.sortingMethods.add("Consigliato");
        this.sortingMethods.add("Risparmio");
        this.sortingMethods.add("Prezzo più basso");
        this.sortingMethods.add("Prezzo più alto");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_sort, container, false);
        ImageView dividerLine = view.findViewById(R.id.marketview_img_divide_modalsort);
        dividerLine.setEnabled(false);
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View v) {
        if(sortingMethods.isEmpty())
            setSortingMethod();
        RecyclerView rv = v.findViewById(R.id.marketview_rv_modalsort);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter rvA = new SortAdapter(this);
        rv.setLayoutManager(rvLM);
        rv.setAdapter(rvA);
    }

    public void sortBy(String sortMethod){

        activeSortingMethod = sortMethod;
        System.out.println(activeSortingMethod);

        ProductItem[] tempArray = new  ProductItem[marketViewFragment.pList.size()];
        ArrayList<ProductItem> sortArrayList = new ArrayList<>();
        tempArray = marketViewFragment.pList.toArray(tempArray);

        ProductItem[] tempArrayFull = new  ProductItem[marketViewFragment.pListFull.size()];
        ArrayList<ProductItem> sortArrayListFull = new ArrayList<>();
        tempArrayFull = marketViewFragment.pListFull.toArray(tempArray);
        //cambia in base al sort selezionato
        switch (sortMethod){
            case "Prezzo più basso": {
                Arrays.sort(tempArray, ProductItem.increasePComparator);
                Arrays.sort(tempArrayFull, ProductItem.increasePComparator);
                break;
            }
            case "Prezzo più alto" : {
                Arrays.sort(tempArray, ProductItem.decreasePComparator);
                Arrays.sort(tempArrayFull, ProductItem.decreasePComparator);
                break;
            }
            case "Default" : {
                Arrays.sort(tempArray, ProductItem.increaseNameComparator);
                Arrays.sort(tempArrayFull, ProductItem.increaseNameComparator);
                break;
            }
            case "Risparmio" : {
                Arrays.sort(tempArray, ProductItem.discountComparator);
                Arrays.sort(tempArrayFull, ProductItem.discountComparator);
                break;
            }
        }

        Collections.addAll(sortArrayList, tempArray);
        Collections.addAll(sortArrayListFull, tempArrayFull);

        marketViewFragment.pList.clear();
        marketViewFragment.pList.addAll(sortArrayList);
        marketViewFragment.pListFull.clear();
        marketViewFragment.pListFull.addAll(sortArrayListFull);

        marketViewFragment.productGridAdapter.notifyDataSetChanged();

    }
}
