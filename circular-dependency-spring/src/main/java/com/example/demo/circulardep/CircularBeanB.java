package com.example.demo.circulardep;

import org.springframework.stereotype.Component;

@Component
public class CircularBeanB {
    private CircularBeanA circularBeanA;

    public CircularBeanB(CircularBeanA circularBeanA) {
        this.circularBeanA = circularBeanA;
    }
}

