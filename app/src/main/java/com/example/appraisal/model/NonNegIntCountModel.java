package com.example.appraisal.model;

import com.example.appraisal.backend.NonNegIntCountTrial;

public class NonNegIntCountModel {
  private NonNegIntCountTrial data;

  public NonNegIntCountModel() { data = new NonNegIntCountTrial(); }

  public void saveCount(long count) { data.setCounter(count); }

  public long getCount() { return data.getCounter(); }
}
