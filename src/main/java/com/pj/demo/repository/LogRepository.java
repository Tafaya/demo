package com.pj.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pj.demo.model.LogEntry;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, Integer> {

}
