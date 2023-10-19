package com.example.SortBoxs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.InvoiceDevelopers;

@Repository
public interface InvoiceDeveloperRepository extends JpaRepository<InvoiceDevelopers, Long>{

}
