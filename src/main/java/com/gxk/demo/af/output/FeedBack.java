package com.gxk.demo.af.output;

import java.util.List;
import java.util.stream.Collectors;

public class FeedBack {

  private final List<Item> items;

  public FeedBack(List<Item> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    String itemstr = items.stream()
      .map(Item::toString)
      .collect(Collectors.joining(","));
    return "{\"items\": [" + itemstr + "]}";
  }
}
