package com.turkcell.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.RentACar.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	User getByUserId(int id);

}
