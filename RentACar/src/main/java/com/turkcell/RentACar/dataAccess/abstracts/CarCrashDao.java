package com.turkcell.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.CarCrush;

@Repository
public interface CarCrashDao extends JpaRepository <CarCrush, Integer> {

	public CarCrush getByCarCrushId (int carCrushId);
	public List<CarCrush> getByCar_CarId (int carId);

}
