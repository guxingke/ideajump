package com.gxk.demo.af;

import com.gxk.demo.af.output.FeedBack;
import com.gxk.demo.af.output.Item;

import java.util.ArrayList;
import java.util.List;

public class OutUtils {

  public static FeedBack notFound() {
    List<Item> items = new ArrayList<>();
    Item item = new Item("NOT FOUND", "NOT FOUND", "NOT FOUND", "NOT FOUND", null);
    items.add(item);
    FeedBack feedBack = new FeedBack(items);
    return feedBack;
  }
}
