package com.app.repository;

import com.app.entities.LoggerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {

	LoggerEntity findByToken(String token);

}
