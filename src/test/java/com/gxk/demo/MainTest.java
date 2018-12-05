package com.gxk.demo;

import com.gxk.demo.af.output.FeedBack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

  private Main main;

  @Before
  public void setup() {
    main = new Main();
  }

  @Test
  public void test() {

    FeedBack back = main.exec("jump", "/Users/gxk/toy/java", "sql");

    assertTrue(back.toString().contains("sql"));
  }
}
