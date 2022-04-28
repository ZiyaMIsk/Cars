package com.turkcell.RentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.UserService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.user.UserDto;
import com.turkcell.RentACar.business.requests.update.UpdateUserRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.UserDao;
import com.turkcell.RentACar.entities.User;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
		
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}
	
	@Override
	public DataResult<UserDto> getById(int userId) {
		User result = this.userDao.getByUserId(userId);
		UserDto response = this.modelMapperService.forDto().map(result, UserDto.class);

		return new SuccessDataResult<UserDto>(response, Messages.USERFOUND);
	}

	@Override
	public Result update(int id, UpdateUserRequest updateUserRequest) throws BusinessException {

		checkIfUserExists(id);
		
		User user = this.userDao.getByUserId(id);
		updateOperation(user, updateUserRequest);
		this.userDao.save(user);

		return new SuccessResult(Messages.USERUPDATED);
	}

	private void updateOperation(User user, UpdateUserRequest updateUserRequest) {
		user.setEmail(updateUserRequest.getEmail());
		user.setPassword(updateUserRequest.getPassword());
		
	}

	private boolean checkIfUserExists(int id) throws BusinessException {
		if (this.userDao.getByUserId(id) != null) {
			return true;
		}
		throw new BusinessException("Cannot find an user with this Id.");
		
	}

}
