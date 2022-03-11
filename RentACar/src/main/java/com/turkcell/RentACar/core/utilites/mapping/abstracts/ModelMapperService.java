package com.turkcell.RentACar.core.utilites.mapping.abstracts;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

	ModelMapper forDto();
	ModelMapper forRequest();
}
