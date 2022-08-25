package com.example.springInsides.entity;

public class FalconNine extends FalconOne implements Rocket {
    @Override
    public void start() {
        System.out.println("let's to mars");
    }
}
