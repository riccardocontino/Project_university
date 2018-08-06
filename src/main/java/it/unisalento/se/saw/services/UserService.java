package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.domain.User;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.repositories.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public User getUserByMail_Pwd(String mail, String password) throws UserNotFoundException {
		
		User user = userRepository.getUserByMail_Pwd(mail, password);
		
		
		if(user == null) {
			throw new UserNotFoundException();
		} 
		return user;

		
	}

	@Transactional
	public List<User> getUserEnrolledTeaching(String nameTeaching)  {
		return userRepository.getUserEnrolledTeaching(nameTeaching); 	//Get student enrolled in teaching
	}




	@Transactional
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Transactional
	public User getProfessorByNameTeaching(String nameTeaching) {
		return userRepository.getProfessorByNameTeaching(nameTeaching);
		
	}
	

	@Transactional
	public User getById(int id) {
		return userRepository.getOne(id);
		
	}
	
	@Transactional
	public User getUserByMail(String mail) {
		return userRepository.userByMail(mail);
	}



}
