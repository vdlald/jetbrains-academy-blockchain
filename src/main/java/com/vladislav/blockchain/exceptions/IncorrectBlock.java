package com.vladislav.blockchain.exceptions;

public class IncorrectBlock extends RuntimeException {

    public IncorrectBlock() {
        super("Incorrect block");
    }

}
