package com.hy.ly.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hy.ly.dao.StudentRepository;
import com.hy.ly.po.Student;
import com.hy.ly.service.StudentService;

public class SpringDataTest {

	private ApplicationContext ctx = null;
	private StudentRepository studentRepository = null;
	private StudentService studentService = null;

	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		studentRepository = ctx.getBean(StudentRepository.class);
		studentService = ctx.getBean(StudentService.class);
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
		Student stu = studentRepository.getByName("李四");
		System.out.println(stu);
	}

	@Test
	public void testFindByName() {
		List<Student> stus = studentRepository.getByNameStartingWithAndIdLessThan("张", 1003);
		System.out.println(stus);
	}

	@Test
	public void testFindByName2() {
		List<Student> stus = studentRepository.getByNameEndingWithAndIdLessThan("飞", 1005);
		System.out.println(stus);
	}

	@Test
	public void testFindByName3() {
		List<String> emails = new ArrayList<>();
		emails.add("test03@126.com");
		emails.add("test04@126.com");
		emails.add("test05@126.com");
		emails.add("test06@126.com");
		List<Student> stus = studentRepository.getByEmailInOrBirthLessThan(emails, new Date());
		System.out.println(stus);
	}

	@Test
	public void testKeyWords() {
		List<Student> stus = studentRepository.getByAddress_IdGreaterThan(1001);
		System.out.println(stus);
	}
	@Test
	public void testQuery() {
		Student stu = studentRepository.getMaxIdStudent();
		System.out.println(stu);
	}
	@Test
	public void testQuery2() {
		List<Student> stus= studentRepository.testQueryAnnotationParams1("张三", "test@126.com");
		System.out.println(stus);
	}
	@Test
	public void testQuery3() {
		List<Student> stus= studentRepository.testQueryAnnotationParams2("test@126.com","张三");
		System.out.println(stus);
	}
	@Test
	public void testQueryAnnotationLikeParam() {
		List<Student> stus= studentRepository.testQueryAnnotationLikeParam("张", "test");
		System.out.println(stus);
	}
	@Test
	public void testQueryAnnotationLikeParam2() {
		List<Student> stus= studentRepository.testQueryAnnotationLikeParam2("张", "test");
		System.out.println(stus);
	}
	@Test
	public void testgetTotalCount() {
		long count= studentRepository.getTotalCount();
		System.out.println(count);
	}
	@Test
	public void updateStudentEmail() {
		//studentRepository.updateStudentEmail(1001, "test01@126.com");
		studentService.updateStudentEmail(1001, "test01@126.com");
	}
}
