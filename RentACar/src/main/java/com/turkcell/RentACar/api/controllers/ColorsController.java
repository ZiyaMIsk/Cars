package com.turkcell.RentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.RentACar.business.abstracts.ColorService;
import com.turkcell.RentACar.business.dtos.color.ColorDto;
import com.turkcell.RentACar.business.dtos.color.ListColorDto;
import com.turkcell.RentACar.business.requests.create.CreateColorRequest;
import com.turkcell.RentACar.business.requests.update.UpdateColorRequest;
import com.turkcell.RentACar.core.utilites.results.DataResult;
import com.turkcell.RentACar.core.utilites.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService=colorService;
	}
	
	@GetMapping("/listall")
	public DataResult<List<ListColorDto>> listAll(){
		return this.colorService.listAll();
	}
	
	@PostMapping("/create")
	public Result create(@RequestBody @Valid CreateColorRequest createColorRequest){
		return this.colorService.create(createColorRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestParam("colorId") int colorId, @RequestBody @Valid UpdateColorRequest updateColorRequest){
		return this.colorService.update(colorId, updateColorRequest);
	}
	
	@DeleteMapping("/delete")
    public Result delete(@RequestParam("colorId") int colorId){
		return this.colorService.delete(colorId);
    }
	
	@GetMapping("/getbyid")
	public DataResult<ColorDto> getById (@RequestParam @Valid int colorId){
		return this.colorService.getById(colorId);
	}
}
