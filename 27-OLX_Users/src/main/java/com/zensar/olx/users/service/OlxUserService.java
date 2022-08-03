package com.zensar.olx.users.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.users.bean.OlxUser;
import com.zensar.olx.users.db.OlxUserDAO;

@Service
public class OlxUserService {
	@Autowired
	OlxUserDAO dao;
	public OlxUser addOlxUser(OlxUser olxuser) {
		return this.dao.save(olxuser);
	}
	public OlxUser findOlxUserById(int id) {
		Optional <OlxUser> optional=this.dao.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			return null;
		}
	}
	public OlxUser findOlxUserByName(String name) {
		OlxUser olxuser=this.dao.findByUserName(name);
		return olxuser;
	}

}
