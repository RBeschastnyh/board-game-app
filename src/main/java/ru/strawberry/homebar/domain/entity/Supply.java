package ru.strawberry.homebar.domain.entity;

public enum Supply {
  NONE("NONE"),
  FEW("FEW"),
  LOT("LOT");

  private final String value;

  Supply(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
