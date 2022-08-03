package com.zensar.olx.advertise.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.advertise.bean.Advertisement;
import com.zensar.olx.advertise.bean.AdvertisementPostRequest;
import com.zensar.olx.advertise.bean.AdvertisementPostResponse;
import com.zensar.olx.advertise.bean.AdvertisementStatus;
import com.zensar.olx.advertise.bean.Category;
import com.zensar.olx.advertise.bean.OlxUser;
import com.zensar.olx.advertise.service.AdvertisementService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdvertisementController {
	@Autowired
	AdvertisementService service;

	@PostMapping("/advertise/{un}")
	public AdvertisementPostResponse add(@RequestBody AdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {
		Advertisement advt = new Advertisement();
		advt.setTitle(request.getTitle());
		advt.setPrice(request.getPrice());
		advt.setDescription(request.getDescription());
		int categoryId = request.getCategoryId();
		RestTemplate restTemplate = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getCategoryById/" + categoryId;
		category = restTemplate.getForObject(url, Category.class);
		advt.setCategory(category);
		url = "http://localhost:9051/findUserByName/" + userName;
		OlxUser olxuser = restTemplate.getForObject(url, OlxUser.class);
		advt.setOlxUser(olxuser);
		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "open");
		advt.setAdvertisementStatus(advertisementStatus);
		Advertisement advertisement = this.service.addAdevertisement(advt);
		AdvertisementPostResponse response = new AdvertisementPostResponse();
		response.setId(advertisement.getId());
		response.setTitle(advertisement.getTitle());
		response.setPrice(advertisement.getPrice());
		response.setCategory(advertisement.getCategory().getName());
		response.setDescription(advertisement.getDescription());
		response.setUserName(advertisement.getOlxUser().getUserName());
		response.setCreatedDate(advertisement.getCreatedDate());
		response.setModifiedDate(advertisement.getModifiedDate());
		response.setStatus(advertisement.getAdvertisementStatus().getStatus());
		return response;

	}

	@PutMapping("/advertise/{aid}/{userName}")
	public AdvertisementPostResponse f2(@RequestBody AdvertisementPostRequest request,
			@PathVariable(name = "aid") int id, @PathVariable(name = "userName") String UserName) {
		Advertisement advt = this.service.getAdvertisementById(id);
		advt.setTitle(request.getTitle());
		advt.setDescription(request.getDescription());
		advt.setPrice(request.getPrice());
		RestTemplate restTemplate = new RestTemplate();
		Category category;
		String url = "http://localhost:9052/advertise/getCategoryById/" + request.getCategoryId();
		category = restTemplate.getForObject(url, Category.class);
		advt.setCategory(category);
		url = "http://localhost:9051/findUserByName/" + UserName;
		OlxUser olxuser = restTemplate.getForObject(url, OlxUser.class);
		advt.setOlxUser(olxuser);
		url = "http://localhost:9052/advertise/status/" + request.getStatusId();
		AdvertisementStatus advertisementStatus;
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		advt.setAdvertisementStatus(advertisementStatus);
		Advertisement advertisement = this.service.updateAdvertisement(advt);
		AdvertisementPostResponse postResponse;
		postResponse = new AdvertisementPostResponse();
		postResponse.setId(advertisement.getId());
		postResponse.setTitle(advertisement.getTitle());
		postResponse.setDescription(advertisement.getDescription());
		postResponse.setPrice(advertisement.getPrice());
		postResponse.setUserName(advertisement.getOlxUser().getUserName());
		postResponse.setCategory(advertisement.getCategory().getName());
		postResponse.setCreatedDate(advertisement.getCreatedDate());
		postResponse.setModifiedDate(advertisement.getModifiedDate());
		postResponse.setStatus(advertisement.getAdvertisementStatus().getStatus());
		return postResponse;
	}

	@GetMapping("/user/advertise/{userName}")
	public List<AdvertisementPostResponse> f3(@PathVariable(name = "userName") String userName) {
		List<Advertisement> advPost = this.service.getAllAdvertisements();
		RestTemplate restTemplate = new RestTemplate();
		List<Advertisement> filterList = new ArrayList<>();
		String url = "http://localhost:9051/findUserByName/" + userName;
		OlxUser olxuser = restTemplate.getForObject(url, OlxUser.class);
		for (Advertisement post : advPost) {
			Category category;
			url = "http://localhost:9052/advertise/getCategoryById/" + post.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			post.setCategory(category);
			System.out.println("Category--------" + post);
			url = "http://localhost:9052/advertise/status/" + post.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			post.setAdvertisementStatus(advertisementStatus);
			System.out.println("AdvertisementStatus" + post);
			if (olxuser.getUserId() == post.getOlxUser().getUserId()) {
				post.setOlxUser(olxuser);
				filterList.add(post);
			}
		}
		System.out.println("List----------" + filterList);
		List<AdvertisementPostResponse> responseList = new ArrayList<>();
		for (Advertisement advertisementPost : filterList) {
			AdvertisementPostResponse postResponse = new AdvertisementPostResponse();
			postResponse.setId(advertisementPost.getId());
			postResponse.setTitle(advertisementPost.getTitle());
			postResponse.setDescription(advertisementPost.getDescription());
			postResponse.setPrice(advertisementPost.getPrice());
			postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
			postResponse.setCategory(advertisementPost.getCategory().getName());
			postResponse.setCreatedDate(advertisementPost.getCreatedDate());
			postResponse.setModifiedDate(advertisementPost.getModifiedDate());
			postResponse.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
			responseList.add(postResponse);
		}
		return responseList;
	}

	@GetMapping("/user/advertiseById/{advId}")
	public AdvertisementPostResponse f4(@PathVariable(name = "advId") int advertisementId)

	{
		AdvertisementPostResponse response = new AdvertisementPostResponse();
		Advertisement advt = this.service.getAdvertisementById(advertisementId);
		RestTemplate restTemplate = new RestTemplate();
		String url;
		Category category;
		url = "http://localhost:9052/advertise/getCategoryById/" + advt.getCategory().getId();
		category = restTemplate.getForObject(url, Category.class);
		advt.setCategory(category);
		System.out.println("Category-----" + advt);
		url = "http://localhost:9052/advertise/status/" + advt.getAdvertisementStatus().getId();
		AdvertisementStatus advertisementStatus;
		
		advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		advt.setAdvertisementStatus(advertisementStatus);
		url="http://localhost:9051/findUserById/"+advt.getOlxUser().getUserId();
		OlxUser olxuser=restTemplate.getForObject(url, OlxUser.class);
		advt.setOlxUser(olxuser);
		response.setId(advt.getId());
		response.setTitle(advt.getTitle());
		response.setDescription(advt.getDescription());
		response.setPrice(advt.getPrice());
		response.setUserName(advt.getOlxUser().getUserName());
		response.setCategory(advt.getCategory().getName());
		response.setCreatedDate(advt.getCreatedDate());
		response.setModifiedDate(advt.getModifiedDate());
		response.setStatus(advt.getAdvertisementStatus().getStatus());

		return response;

	}
	@DeleteMapping("/user/advertise/{adid}")
	public boolean f5(@PathVariable(name="adid")int advertisementId) {
		Advertisement advt=this.service.getAdvertisementById(advertisementId);
		boolean result=this.service.deleteAdvertisement(advt);
		return result;
	}
	@GetMapping("/advertise/search/{criteria}")
	public List<AdvertisementPostResponse>f6(@PathVariable(name = "criteria") String criteria) {
		List<Advertisement> advPost = this.service.getAllAdvertisements();
		String[] str=criteria.split(":");
		
		
		List<Advertisement> filterList = new ArrayList<>();
	
		
		for (Advertisement post : advPost) {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:9051/findUserById/" + post.getOlxUser().getUserId();
			OlxUser olxuser = restTemplate.getForObject(url, OlxUser.class);
			post.setOlxUser(olxuser);
		
			Category category;
			url = "http://localhost:9052/advertise/getCategoryById/" + post.getCategory().getId();
			category = restTemplate.getForObject(url, Category.class);
			post.setCategory(category);
			System.out.println("Category--------" + post);
			url = "http://localhost:9052/advertise/status/" + post.getAdvertisementStatus().getId();
			AdvertisementStatus advertisementStatus;
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			post.setAdvertisementStatus(advertisementStatus);
			
			System.out.println("AdvertisementStatus" + post);
			if(str[0].equalsIgnoreCase("title")) {
				if(post.getTitle().contains(str[1]))
					filterList.add(post);
			}
			if(str[0].equalsIgnoreCase("category")) {
				if(post.getCategory().getDescription().contains(str[1]))
					filterList.add(post);
			}
			if(str[0].equalsIgnoreCase("description")) {
				if(post.getDescription().contains(str[1]))
					filterList.add(post);
			}
			if(str[0].equals("createdDate")) {
				if(post.getCreatedDate().equals(str[1]))
					filterList.add(post);
			}
			if(str[0].equals("modifiedDate")) {
				if(post.getModifiedDate().equals(str[1]))
					filterList.add(post);
			}
			
			
			}
		
		
		List<AdvertisementPostResponse> responseList = new ArrayList<>();
		for (Advertisement advertisementPost : filterList) {
			AdvertisementPostResponse postResponse = new AdvertisementPostResponse();
			postResponse.setId(advertisementPost.getId());
			postResponse.setTitle(advertisementPost.getTitle());
			postResponse.setDescription(advertisementPost.getDescription());
			postResponse.setPrice(advertisementPost.getPrice());
			postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
			postResponse.setCategory(advertisementPost.getCategory().getName());
			postResponse.setCreatedDate(advertisementPost.getCreatedDate());
			postResponse.setModifiedDate(advertisementPost.getModifiedDate());
			postResponse.setStatus(advertisementPost.getAdvertisementStatus().getStatus());
			responseList.add(postResponse);
		}
		return responseList;
	
}
}
