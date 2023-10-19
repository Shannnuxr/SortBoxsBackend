package com.example.SortBoxs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SortBoxs.entitys.ClockInRecord;

@Repository
public interface ClockInRepository extends JpaRepository<ClockInRecord, Long>{
	


	ClockInRecord findClockInRecordByUserId(Long userId);

}
