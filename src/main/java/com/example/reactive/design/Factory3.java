package com.example.reactive.design;

public class Factory3 {

    public Factory1 getFactory1(String type, String ram, String hdd, String cpu) {
        if ("PC".equals(type)) {
            return new Factory2(ram, hdd, cpu);
        } else {
            return new Factory2(ram, hdd, cpu);
        }
    }

}
