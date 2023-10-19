package com.example.SortBoxs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	

}
