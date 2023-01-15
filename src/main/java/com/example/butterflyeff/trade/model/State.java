package com.example.butterflyeff.trade.model;

import lombok.Getter;

@Getter
public enum State {
    WAIT(0),
    ANSWER(1),
    FINISH(2);

    private int State;
    State(int num) {
        this.State = num;
    }
}