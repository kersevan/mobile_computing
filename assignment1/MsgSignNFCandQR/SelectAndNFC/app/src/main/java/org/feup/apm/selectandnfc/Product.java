package org.feup.apm.selectandnfc;

public class Product {
  public final static String[] products = {
    "Oranges",
    "Mandarins",
    "Peaches",
    "Pears",
    "Apples",
    "Pineapples",
    "Plums",
    "Grapes"
  };

  public String name;
  public int type;
  public boolean selected;

  public Product(String name, int type) {
    this.name = name;
    this.type = type;
    selected = false;
  }
}
