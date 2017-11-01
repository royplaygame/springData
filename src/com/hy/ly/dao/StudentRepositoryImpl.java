package com.hy.ly.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hy.ly.po.Student;

public class StudentRepositoryImpl implements StudentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void test() {
		Student stu = entityManager.find(Student.class, 1005);
		System.out.println(stu);
	}

}
