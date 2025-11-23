package com.porasl.notification.config;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

  @Query("""
      select config from Config config where config.id = :id
      """)
  List<Config> findAllConfig(@Param("id") long id);

  Optional<Config> findByConfigName(String configName);
}
