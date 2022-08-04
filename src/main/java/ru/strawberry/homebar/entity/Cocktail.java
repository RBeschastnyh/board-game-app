package ru.strawberry.homebar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Cocktail DAO.
 *
 * @author RBeschastnykh
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "cocktail", schema = "brl_homebar")
public class Cocktail {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "cocktail_name")
  private String cocktailName;

  @Column(name = "description")
  private String description;

  @Column(name = "allergens")
  private String allergens;

  @Column(name = "ingredients")
  private String ingredients;

  @Column(name = "tags")
  private String tags;

  @Column(name = "is_stirred")
  private String isStirred;

}
