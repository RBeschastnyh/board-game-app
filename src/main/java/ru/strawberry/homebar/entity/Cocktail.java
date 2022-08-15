package ru.strawberry.homebar.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Feedback> feedbacks;
}
