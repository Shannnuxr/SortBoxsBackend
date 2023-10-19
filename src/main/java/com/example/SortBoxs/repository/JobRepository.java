package com.example.SortBoxs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.JobTitle;

@Repository
public interface JobRepository extends JpaRepository<JobTitle, Long> {

}
