package com.turkcell.RentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.RentACar.business.abstracts.CreditCardService;
import com.turkcell.RentACar.business.abstracts.CustomerService;
import com.turkcell.RentACar.business.constants.Messages;
import com.turkcell.RentACar.business.dtos.creditCard.CreditCardDto;
import com.turkcell.RentACar.business.dtos.creditCard.ListCreditCardDto;
import com.turkcell.RentACar.business.requests.create.CreateCreditCardRequest;
import com.turkcell.RentACar.business.requests.update.UpdateCreditCardRequest;
import com.turkcell.RentACar.core.exceptions.BusinessException;
import com.turkcell.RentACar.core.utilites.mapping.abstracts.ModelMapperService;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;
import com.turkcell.RentACar.core.utilites.results.SuccessDataResult;
import com.turkcell.RentACar.core.utilites.results.SuccessResult;
import com.turkcell.RentACar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.RentACar.entities.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;
	

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService,CustomerService customerService) {
		super();
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
		this.customerService =customerService;
	}
	
	@Override
	public DataResult<List<ListCreditCardDto>> listAll() throws BusinessException {
		
		List<CreditCard> creditCards = this.creditCardDao.findAll();
    	
    	List<ListCreditCardDto> listCreditCardDto = creditCards.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, ListCreditCardDto.class)).collect(Collectors.toList());
            
    	return new SuccessDataResult<List<ListCreditCardDto>>(listCreditCardDto, Messages.CREDITCARDLISTED);
	}	

	@Override
	public Result create(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {

        isCardExistsByCardNumber(createCreditCardRequest.getCardNumber());
        checkIfExpirationYearIsValid(createCreditCardRequest.getExpirationYear());

        CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

        creditCard.setCreditCardId(0);
        this.creditCardDao.save(creditCard);	
        
        return new SuccessResult(Messages.CREDITCARDADDED);   
    
	}


	@Override
	public Result update(int id, UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
		
		checkCreditCardId(updateCreditCardRequest.getCreditCardId());	
        	
		CreditCard creditCard = this.modelMapperService.forRequest().map(updateCreditCardRequest, CreditCard.class);     
    	this.creditCardDao.save(creditCard);
    
    	
    	return new SuccessResult(Messages.CREDITCARDUPDATED);
	}

	@Override
	public Result delete(int id) throws BusinessException {
		
		checkCreditCardId(id);
        
       	this.creditCardDao.deleteById(id);
       
       	return new SuccessResult(Messages.CREDITCARDDELETED);
	}

	@Override
	public DataResult<CreditCardDto> getById(int creditCardId) throws BusinessException {
		checkCreditCardId(creditCardId); 
    	
		CreditCard creditCard = this.creditCardDao.getById(creditCardId);
		CreditCardDto creditCardDto = this.modelMapperService.forDto().map(creditCard,CreditCardDto.class);
        
        
        return new SuccessDataResult<CreditCardDto>(creditCardDto, Messages.CREDITCARDFOUND);
           
	}
	
	@Override
	public DataResult<List<CreditCardDto>> getByCustomerId(int customerId) throws BusinessException {
		
		this.customerService.getByCustomerId(customerId);
		
		List<CreditCard> result = this.creditCardDao.getCreditCardByCustomer_CustomerId(customerId);
		
		List<CreditCardDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CreditCardDto>>(response, Messages.CREDITCARDLISTEDBYCUSTOMERID);
	}

	
    private void checkCreditCardId(int creditCardId) throws BusinessException {
       
    	if (!this.creditCardDao.existsById(creditCardId)) {
           
    		throw new BusinessException(Messages.CREDITCARDNOTFOUND);
        } 
    }

	@Override
	public void save(CreateCreditCardRequest creditCard) throws BusinessException {
		CreateCreditCardRequest createCreditCardRequest = new CreateCreditCardRequest();
		createCreditCardRequest.setCardNumber(creditCard.getCardNumber());
		createCreditCardRequest.setCardCvvNumber(creditCard.getCardCvvNumber());
		createCreditCardRequest.setExpirationMonth(creditCard.getExpirationMonth());
		createCreditCardRequest.setExpirationYear(creditCard.getExpirationYear());
		createCreditCardRequest.setCardOwnerName(creditCard.getCardOwnerName());
		
		create(createCreditCardRequest);	
		
	}

	private void isCardExistsByCardNumber(String cardNumber) throws BusinessException {
		  if (this.creditCardDao.existsCreditCardByCardNumber(cardNumber)){
	            throw new BusinessException(Messages.CREDITCARDEXİSTS);
	        }
		
	}

	private void checkIfExpirationYearIsValid(int year) throws BusinessException {
        int currentYear = LocalDate.now().getYear();
        if (year<currentYear){
            throw new BusinessException(Messages.DATEFORMATNOTVALID);
        }
	}
}
