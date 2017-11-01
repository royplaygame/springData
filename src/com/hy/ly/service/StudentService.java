package com.hy.ly.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.ly.dao.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public void updateStudentEmail(Integer id,String email){
		studentRepository.updateStudentEmail(id, email);
	}
}
