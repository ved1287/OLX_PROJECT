package com.zensar.olx.master.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.master.bean.AdvertisementStatus;
import com.zensar.olx.master.service.AdvertisementStatusService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdvertisementStatusController {
	@Autowired
	AdvertisementStatusService service;
	@PostMapping("/addAdvertisementStatus")
	public AdvertisementStatus addAdvertisementStatus(@RequestBody AdvertisementStatus advertisementStatus) {
		return this.service.addAdvertisementStatus(advertisementStatus);
	}
	@GetMapping("/advertise/status")
	public List<AdvertisementStatus> getAllAdvertisementStatus(){
		return this.service.getAllAdvertisementStatus();
	}
	@GetMapping("/advertise/status/{id}")
	public AdvertisementStatus findAdvertisementStatusById(@PathVariable(name="id")int id) {
		return this.service.findAdvertisementStatus(id);
	}

}
