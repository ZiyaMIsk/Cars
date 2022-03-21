package com.turkcell.RentACar.business.abstracts;

import com.turkcell.RentACar.business.dtos.user.UserDto;
import com.turkcell.RentACar.business.requests.update.UpdateUserRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface UserService {

	DataResult<UserDto> getById(int userId);

	Result update(int id, UpdateUserRequest updateUserRequest) throws BusinessException;
	
	public boolean checkIfNotExistByEmail(String email);

}
