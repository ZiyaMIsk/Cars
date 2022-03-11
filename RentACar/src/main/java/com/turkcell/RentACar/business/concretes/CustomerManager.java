
package com.turkcell.RentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
@Service
public class CustomerManager  implements CustomerService{

	private CustomerDao customerDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCustomerDto>> getAll() {
		
		var result = this.customerDao.findAll();
		List<ListCustomerDto> response = result.stream()
				.map(customer -> this.modelMapperService.forDto().map(customer, ListCustomerDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListCustomerDto>>(response);
	}

	@Override
	public DataResult<ListCustomerDto> getById(int id) {
		
		Customer result = this.customerDao.getById(id);
		if(result == null) {
			return new ErrorDataResult<ListCustomerDto>("Car.NotFound");
		}
		ListCustomerDto response = this.modelMapperService.forDto().map(result, ListCustomerDto.class);		
		return new SuccessDataResult<ListCustomerDto>(response);
	}

	@Override
	public Result create(CreateCustomerRequest createCustomerRequest) throws BusinessException {
		
		Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
		this.customerDao.save(customer);
		return new SuccessResult(Messages.CustomerAdded);
	}

}
