package com.hy.ly.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hy.ly.dao.StudentRepository;
import com.hy.ly.po.Student;

public class SpringDataTest {

	private ApplicationContext ctx = null;

	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	public void testDataSource() {
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		System.out.println(dataSource);
	}

	@Test
	public void testJPA() {

	}

	@Test
	public void testHelloWord() {
		StudentRepository studentRepository = ctx.getBean(StudentRepository.class);
		Student stu = studentRepository.getByName("李四");
		System.out.println(stu);
	}

}
