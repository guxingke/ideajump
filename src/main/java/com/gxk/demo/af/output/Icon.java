package com.gxk.demo.af.output;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Icon {
  private final String type;
  private final String path;

  public Icon(String type, String path) {
    this.type = type;
    this.path = path;
  }

  public Icon(String path) {
    this.type = "path";
    this.path = path;
  }

  @Override
  public String toString() {
    List<String> temps = new ArrayList<>();

    temps.add(String.format("\"%s\": \"%s\"", "type", type));
    temps.add(String.format("\"%s\": \"%s\"", "path", path));
    return temps.stream().collect(Collectors.joining(",", "{", "}"));
  }
}
