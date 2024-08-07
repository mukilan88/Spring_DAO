package com.spring;

import java.util.List;
import java.util.Map;

public interface StudentDao {
	public void insertStudent(int id, String name, int age);

	public void updateStudent(int id, int age);

	public void deleteStudent(int id);

	public List<Map<String, Object>> listStudents();

	public int get(String string);
}
