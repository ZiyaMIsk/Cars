package com.turkcell.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{

	CarMaintenance findByCarMaintenanceId(int id);
	
	boolean existsByDescription(String description);
	
	List<CarMaintenance>  getCarMaintenanceByCarMaintenanceCar_CarId(int carId);
}
