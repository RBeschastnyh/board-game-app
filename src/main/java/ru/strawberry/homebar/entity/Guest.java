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
 * Guest info.
 *
 * @author RBeschastnykh
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "guest", schema = "brl_homebar")
public class Guest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "login")
  private String login;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "password")
  private Character password;
}
