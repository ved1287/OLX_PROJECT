package com.zensar.olx.master.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.master.bean.Category;
@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {
	
	
	

}
