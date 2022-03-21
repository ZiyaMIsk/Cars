package com.turkcell.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.ColorService;
import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.dtos.color.ListColorDto;
import com.turkcell.RentACar.business.requests.create.CreateColorRequest;
import com.turkcell.RentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorDataResult;
import com.turkcell.RentACar.core.utilites.results.ErrorResult;
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
		if (!checkIfColorListEmpty(colors).isSuccess()) {
			return new ErrorDataResult<List<ListColorDto>>(checkIfColorListEmpty(colors).getMessage());
		}
		List<ListColorDto> listColorDto = colors.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(listColorDto, "Data listed");
	}
	
	@Override
	public Result create(CreateColorRequest createColorRequest){
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if (!checkColorName(color.getColorName()).isSuccess()) {
			return new ErrorResult(checkColorName(color.getColorName()).getMessage());
		}
		this.colorDao.save(color);
		return new SuccessDataResult<CreateColorRequest>(createColorRequest, "Data added : " + color.getColorName());	
	}
	
	@Override
	public Result update(int id, UpdateColorRequest updateColorRequest){
		if (!checkColorId(updateColorRequest.getColorId()).isSuccess()) {
			return new ErrorResult(checkColorId(updateColorRequest.getColorId()).getMessage());
		}
		if (!checkColorName(updateColorRequest.getColorName()).isSuccess()) {
			return new ErrorResult(checkColorName(updateColorRequest.getColorName()).getMessage());
		}
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
		return new SuccessDataResult<UpdateColorRequest>(updateColorRequest,
				"Data updated to: " + color.getColorName());
	}
	
	@Override
	public Result delete(int colorId){
		if (!checkColorId(colorId).isSuccess()) {
			return new ErrorResult(checkColorId(colorId).getMessage());
		}
		String colorNameBeforeDelete = this.colorDao.findByColorId(colorId).getColorName();
		this.colorDao.deleteById(colorId);
		return new SuccessResult("Data deleted : " + colorNameBeforeDelete);   	
	}
	
	@Override
	public DataResult<ColorDto> getById(int colorId){
		if (!checkColorId(colorId).isSuccess()) {
			return new ErrorDataResult<ColorDto>(checkColorId(colorId).getMessage());
		}
		Color color = this.colorDao.getById(colorId);
		ColorDto colorDto = this.modelMapperService.forDto().map(color, ColorDto.class);
		return new SuccessDataResult<ColorDto>(colorDto, "Data getted by id");
	}
		
	private Result checkColorName(String colorName){
		if (this.colorDao.existsByColorName(colorName)) {
			return new ErrorResult("This color name is already exists: " + colorName);
		}
		if (colorName.isBlank() || colorName.isEmpty()) {
			return new ErrorResult("Color name cannot be blank or empty!");
		}
		return new SuccessResult();
	}
	
	private Result checkColorId(int colorId){
		if (!this.colorDao.existsById(colorId)) {
			return new ErrorResult("This color id is undefined!");
		}
		return new SuccessResult();
	}
	
	private Result checkIfColorListEmpty(List<Color> colors) {
		if (colors.isEmpty()) {
			return new ErrorDataResult<List<Color>>("There is no color exists in the list!");
		}
		return new SuccessResult();
	}

	@Override
	public boolean checkIfExistByColorId(int colorId) throws BusinessException {
		Color color = this.colorDao.findByColorId(colorId);
		if(color == null) {
			throw new BusinessException("Can not find color with this id.");
		}
		else {
			return true;
		}
	}

	
}
