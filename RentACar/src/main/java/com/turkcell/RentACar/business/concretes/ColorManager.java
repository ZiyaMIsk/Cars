package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.ColorService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.dtos.color.ListColorDto;
import com.turkcell.RentACar.business.requests.create.CreateColorRequest;
import com.turkcell.RentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.RentACar.entities.Color;

@Service
public class ColorManager implements ColorService{

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
	
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}
	
	@Override
	public DataResult<List<ListColorDto>> listAll() {
		
		List<Color> colors = this.colorDao.findAll();
		
		List<ListColorDto> listColorDto = colors.stream().map(color -> this.modelMapperService.forDto().
				map(color, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(listColorDto, Messages.COLORLISTED);
	}
	
	@Override
	public Result create(CreateColorRequest createColorRequest) throws BusinessException{
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		checkColorName(createColorRequest.getColorName());
		
		this.colorDao.save(color);
		
		return new SuccessDataResult<CreateColorRequest>(createColorRequest, Messages.COLORADDED);	
	}
	
	@Override
	public Result update(int colorId, UpdateColorRequest updateColorRequest) throws BusinessException{
		checkColorId(colorId);
		checkColorName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		this.colorDao.save(color);
		
		return new SuccessDataResult<UpdateColorRequest>(updateColorRequest, Messages.COLORUPDATED);
	}
	
	@Override
	public Result delete(int colorId) throws BusinessException{
		checkColorId(colorId);
		
		this.colorDao.deleteById(colorId);
		
		return new SuccessResult(Messages.COLORDELETE);   	
	}
	
	@Override
	public DataResult<ColorDto> getById(int colorId) throws BusinessException{
		
		checkColorId(colorId);
		
		Color color = this.colorDao.getById(colorId);
		
		ColorDto colorDto = this.modelMapperService.forDto().map(color, ColorDto.class);
		
		return new SuccessDataResult<ColorDto>(colorDto, Messages.COLORFOUND);
	}
		
	private void checkColorName(String colorName) throws BusinessException{
		
		if (this.colorDao.existsByColorName(colorName)) {
			throw new BusinessException(Messages.COLORNAMEERROR);
		}

	}
	
	private void checkColorId(int colorId) throws BusinessException{
		if (!this.colorDao.existsById(colorId)) {
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
		
	}

	@Override
	public boolean checkIfExistByColorId(int colorId) throws BusinessException {
		Color color = this.colorDao.findByColorId(colorId);
		
		if(color == null) {
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
		else {
			
			return true;
		}
	}

	
}
