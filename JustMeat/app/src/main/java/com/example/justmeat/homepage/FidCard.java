package com.example.justmeat.homepage;

public class FidCard {
    int id, chain;

    public FidCard(int id, int chain){
        this.chain = chain;
        this.id = id;
    }

    public int getChain() {
        return chain;
    }

    public int getId() {
        return id;
    }
}
