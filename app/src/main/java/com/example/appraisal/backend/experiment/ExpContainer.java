package com.example.appraisal.backend.experiment;

import java.util.ArrayList;

public class ExpContainer {
    private ArrayList<Experiment> exp_list;
    public static ExpContainer single_instance;

    private ExpContainer() {
        exp_list = new ArrayList<>();
    }

    public static void getInstance() {
        if (single_instance == null) {
            single_instance = new ExpContainer();
        }
    }

    public static ArrayList<Experiment> getExpList() {
        return single_instance.exp_list;
    }
}
