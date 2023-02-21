package ru.strawberry.homebar.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

  @Column(name = "name")
  private String name;

  @Column(name = "ingredients")
  private String ingredients;

  @Column(name = "description")
  private String description;

  @Column(name = "is_stirred")
  private String isStirred;

  @Column(name = "group_id")
  private Long groupId;

  @Column(name = "availability")
  @Enumerated(EnumType.STRING)
  private Supply supply;

  @ManyToMany(targetEntity = Tags.class, cascade = CascadeType.ALL)
  @JoinTable(
      schema = "brl_homebar",
      name = "tagmap",
      joinColumns = @JoinColumn(name  = "cocktail_id"),
      inverseJoinColumns = @JoinColumn(name  = "tag_id")
  )
  private List<Tags> tags;
}
