package com.zensar.olx.users.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.users.bean.OlxUser;
@Repository
public interface OlxUserDAO extends JpaRepository<OlxUser, Integer> {
   OlxUser findByUserName(String userName);
}
