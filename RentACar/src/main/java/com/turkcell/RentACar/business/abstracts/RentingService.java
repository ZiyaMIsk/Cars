package com.turkcell.RentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.RentACar.business.dtos.renting.ListRentingDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByCarIdDto;
import com.turkcell.RentACar.business.dtos.renting.RentingByIdDto;
import com.turkcell.RentACar.business.requests.create.CreateRentingRequest;
import com.turkcell.RentACar.business.requests.create.EndRentRequest;
import com.turkcell.RentACar.business.requests.update.UpdateRentingRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.entities.Renting;

public interface RentingService {
	
	Result create(CreateRentingRequest createRentingRequest) throws BusinessException;
	
	Result delete(int id) throws BusinessException;

	Result update(int id, UpdateRentingRequest updateRentingRequest) throws BusinessException;

	DataResult<List<ListRentingDto>> getAll();

	DataResult<List<RentingByCarIdDto>> getRentingByCarId(int carId) throws BusinessException;
	
	DataResult<RentingByIdDto> getRentingById(int rentingId) throws BusinessException;
	
	public boolean checkIfCarNotInRent(int carId) throws BusinessException;
	
	public boolean checkIfExistById(int rentingId) throws BusinessException;

	Result endRent(EndRentRequest endRentRequest) throws BusinessException;

	//void checkIfRentAlreadyEnded(Renting renting) throws BusinessException;

	void checkIfReturnDateDelayed(Renting renting) throws BusinessException;

	double calculateExtraDaysPrice(int rentId, LocalDate date) throws BusinessException;

	void checkIfEndKilometerValueIsCorrect(double startKilometer, double endKilometer) throws BusinessException;	

}
