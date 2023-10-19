package com.example.SortBoxs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.BankDetails;

@Repository
public interface BankRepository  extends JpaRepository<BankDetails, Long>{

	List<BankDetails> findByUserId(Long userId);

}
