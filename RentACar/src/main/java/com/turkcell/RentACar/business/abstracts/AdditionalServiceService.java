package com.turkcell.RentACar.business.abstracts;

import java.util.List;

import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

public interface AdditionalServiceService {
	DataResult<List<ListAdditionalServiceDto>> listAll();
    Result create(CreateAdditionalServiceRequest createAdditionalServiceRequest);
    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);
    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
    DataResult<AdditionalServiceDto> getById(int additionalServiceId);
}
