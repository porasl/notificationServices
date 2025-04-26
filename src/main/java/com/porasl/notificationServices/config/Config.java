package com.porasl.notificationServices.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Config {

  @Id
  @GeneratedValue
  public long id;

  @Column(unique = true)
  public String configName;
  
  @Column(unique = true)
  public String configValue;

}


