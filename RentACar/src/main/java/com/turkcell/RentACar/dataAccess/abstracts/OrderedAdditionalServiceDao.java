package com.turkcell.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> {
	
	OrderedAdditionalService getByOrderedAdditionalServiceId(int orderedAdditionalServiceId);
	
	OrderedAdditionalService getByRenting_RentingIdAndAdditionalService_AdditionalServiceId(int rentingId, int additionalServiceId);
	
	List<OrderedAdditionalService> getByRenting_RentingId(int rentingId);

}
