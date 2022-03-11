package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.Color;

@Repository
public interface ColorDao extends JpaRepository<Color, Integer> {

	boolean existsByColorName(String name);
	Color findByColorId(int id);
}
