package ru.strawberry.homebar.exception;

public enum ProblemEntity {
  COCKTAIL("cocktail");

  private final String entityName;

  ProblemEntity(String entityName) {
    this.entityName = entityName;
  }

  public String getEntityName() {
    return entityName;
  }
}
