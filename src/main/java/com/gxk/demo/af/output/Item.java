package com.gxk.demo.af.output;

//{"items": [
//  {
//  "uid": "desktop",
//  "type": "file",
//  "title": "Desktop",
//  "subtitle": "~/Desktop",
//  "arg": "~/Desktop",
//  "autocomplete": "Desktop",
//  "icon": {
//  "type": "fileicon",
//  "path": "~/Desktop"
//  }
//  }
//  ]}

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item {
  private String uid;
  private String type;
  private String title;
  private String subtitle;
  private String arg;
  private String autocomplete;
  private Icon icon;

  public Item(String uid, String title, String subtitle, String arg, String autocomplete, Icon icon) {
    this.uid = uid;
    this.title = title;
    this.subtitle = subtitle;
    this.arg = arg;
    this.autocomplete = autocomplete;
    this.icon = icon;
  }

  public Item(String uid, String title, String subtitle, String arg, Icon icon) {
    this.uid = uid;
    this.title = title;
    this.subtitle = subtitle;
    this.arg = arg;
    this.icon = icon;
  }

  @Override
  public String toString() {
    List<String> temps = new ArrayList<>();

    temps.add(String.format("\"%s\": \"%s\"", "uid", uid));
    temps.add(String.format("\"%s\": \"%s\"", "title", title));
    temps.add(String.format("\"%s\": \"%s\"", "subtitle", subtitle));
    temps.add(String.format("\"%s\": \"%s\"", "arg", arg));

    if (!isEmpty(autocomplete)) {
      temps.add(String.format("\"%s\": \"%s\"", "autocomplete", autocomplete));
    }
    if (icon != null) {
      temps.add(String.format("\"%s\": %s", "icon", icon));
    }
    if (type != null) {
      temps.add(String.format("\"%s\": \"%s\"", "type", type));
    }

    return temps.stream().collect(Collectors.joining(",", "{", "}"));
  }

  public static boolean isEmpty(String val) {
    return val == null || val.isEmpty();
  }
}
