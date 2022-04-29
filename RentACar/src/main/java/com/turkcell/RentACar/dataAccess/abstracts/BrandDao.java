package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.Brand;

@Component
@Repository
public interface BrandDao extends JpaRepository<Brand, Integer>{

	boolean existsByBrandName(String name);
	
	Brand findByBrandId(int brandId);
	
}
