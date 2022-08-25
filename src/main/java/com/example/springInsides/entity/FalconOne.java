package com.example.springInsides.entity;

import com.example.springInsides.annotation.TimeLogger;
import com.example.springInsides.annotation.DeprecatedClass;
import com.example.springInsides.annotation.InitPrepare;
import com.example.springInsides.annotation.PostProxy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@TimeLogger
@DeprecatedClass(newImpl = FalconNine.class )
public class FalconOne implements Rocket {

    @InitPrepare(min = 2, max = 7)
    int timePrepare;

    @PostConstruct
    public void init() {
        System.out.println("Phase 2");
    }

    public FalconOne() {
        System.out.println("Phase 1");
    }

    @Override
    @PostProxy
    public void start() {
        System.out.println("Phase 3");
        for (int i = 0; i < timePrepare; i++) {
            System.out.println("Prepare");
        }
    }
}
