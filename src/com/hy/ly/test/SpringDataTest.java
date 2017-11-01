package com.hy.ly.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

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
		List<Student> stus = studentRepository.testQueryAnnotationParams1("张三", "test@126.com");
		System.out.println(stus);
	}

	@Test
	public void testQuery3() {
		List<Student> stus = studentRepository.testQueryAnnotationParams2("test@126.com", "张三");
		System.out.println(stus);
	}

	@Test
	public void testQueryAnnotationLikeParam() {
		List<Student> stus = studentRepository.testQueryAnnotationLikeParam("张", "test");
		System.out.println(stus);
	}

	@Test
	public void testQueryAnnotationLikeParam2() {
		List<Student> stus = studentRepository.testQueryAnnotationLikeParam2("张", "test");
		System.out.println(stus);
	}

	@Test
	public void testgetTotalCount() {
		long count = studentRepository.getTotalCount();
		System.out.println(count);
	}

	@Test
	public void updateStudentEmail() {
		// studentRepository.updateStudentEmail(1001, "test01@126.com");
		studentService.updateStudentEmail(1001, "test01@126.com");
	}

	@Test
	public void testCrudRepository() {
		List<Student> stus = new ArrayList<Student>();
		for (int i = 'a'; i < 'z'; i++) {
			Student stu = new Student(i + 1000, "a" + i, "test" + i + "@126.com", Long.toHexString(18338823252L + i),
					new Date());
			stus.add(stu);
		}
		studentService.saveStudents(stus);
	}

	@Test
	public void testPagingAndSortingRepository() {
		int page = 6, size = 5;
		Order od1 = new Order(Direction.DESC, "id");
		Order od2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(od1, od2);
		// PageRequest pageable = new PageRequest(page, size);
		PageRequest pageable = new PageRequest(page, size, sort);
		Page<Student> stus = studentRepository.findAll(pageable);
		System.out.println(stus.getTotalElements());
		System.out.println(stus.getNumber());
		System.out.println(stus.getTotalPages());
		System.out.println(stus.getContent());
		System.out.println(stus.getNumberOfElements());

	}

	@Test
	public void testJpaRepository() {
		Student stu = new Student(2001, "a20001", "test20001@126.com", Long.toHexString(18338823252L + 111),
				new Date());
		studentRepository.saveAndFlush(stu);
	}

	/**
	 * 目标: 实现带查询条件的分页. id > 5 的条件
	 * 
	 * 调用 JpaSpecificationExecutor 的 Page<T> findAll(Specification<T> spec,
	 * Pageable pageable); Specification: 封装了 JPA Criteria 查询的查询条件 Pageable:
	 * 封装了请求分页的信息: 例如 pageNo, pageSize, Sort
	 */
	@Test
	public void testJpaSpecificationExecutor() {
		int pageNo = 3 - 1;
		int pageSize = 5;
		PageRequest pageable = new PageRequest(pageNo, pageSize);

		// 通常使用 Specification 的匿名内部类
		Specification<Student> specification = new Specification<Student>() {
			/**
			 * @param *root:代表查询的实体类.
			 * @param query:
			 *            可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
			 *            来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象.
			 * @param *cb:
			 *            CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到
			 *            Predicate 对象
			 * @return: *Predicate 类型, 代表一个查询条件.
			 */
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path path = root.get("id");
				Predicate predicate = cb.gt(path, 5);
				return predicate;
			}
		};

		Page<Student> page = studentRepository.findAll(specification, pageable);

		System.out.println("总记录数: " + page.getTotalElements());
		System.out.println("当前第几页: " + (page.getNumber() + 1));
		System.out.println("总页数: " + page.getTotalPages());
		System.out.println("当前页面的 List: " + page.getContent());
		System.out.println("当前页面的记录数: " + page.getNumberOfElements());
	}
	
	@Test
	public void testCustomRepositoryMethod(){
		studentRepository.test();
	}
}
