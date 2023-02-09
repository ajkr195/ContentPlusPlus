package com.contentplusplus.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contentplusplus.springboot.model.AppInventory;

@Repository
public interface AppInventoryRepository extends JpaRepository<AppInventory, Long>{

	Page<AppInventory> findByActiveTrueAndTitleContainingIgnoreCase(String keyword, Pageable pageable);

	Page<AppInventory> findByTitleContainingIgnoreCaseAndActiveFalse(String keyword, Pageable pageable);

	Page<AppInventory> findByActiveTrue(Pageable pageable);

	Page<AppInventory> findByActiveFalse(Pageable pageable);

	Page<AppInventory> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
