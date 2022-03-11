package com.turkcell.RentACar.core.utilites.mapping.concretes;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;

@Service
public class ModelMapperManager implements ModelMapperService{
	private ModelMapper modelMapper;
	
	public ModelMapperManager(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ModelMapper forDto() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true)
		.setMatchingStrategy(MatchingStrategies.LOOSE);
		return this.modelMapper;
	}

	@Override
	public ModelMapper forRequest() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true)
		.setMatchingStrategy(MatchingStrategies.STANDARD);
		return this.modelMapper;
	}
}
