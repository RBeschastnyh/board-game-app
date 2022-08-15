package ru.strawberry.homebar.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Feedback DAO.
 *
 * @author RBeschastnykh
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "feedback", schema = "brl_homebar")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "feedback_text")
  private String feedbackText;

  @Column(name = "rating")
  private Integer rating;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Guest guest;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cocktail_id")
  private Cocktail cocktail;
}
