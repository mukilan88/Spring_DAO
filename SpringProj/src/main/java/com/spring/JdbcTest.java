package com.spring;

import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("jdbc.xml");
		StudentDao dao = (StudentDao) context.getBean("student");
		// insert data to db
//		dao.insertStudent(101, "Ramu", 20);
//		System.out.println(dao.get("id") + " " + dao.get("name") + " " + dao.get("age"));
		// update the data to db
//		dao.updateStudent(101, 22);
		// delete the data in db
//		dao.deleteStudent(101);
		// printing all data in terminal
		List<Map<String, Object>> studList = dao.listStudents();
		for (Map stud : studList) {
			System.out.println(stud.get("id") + " " + stud.get("name") + " " + stud.get("age"));
		}
	}
}
