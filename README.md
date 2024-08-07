Spring DAO Module
-----------------
- DAO stands for Data Access Object which is an interface/class with set of methods
  to operate a database

	- Spring JDBC Module
	- Spring ORM Module (Hibernate)
	- Spring Data JPA Module


Spring JDBC Module
------------------
Plain JDBC
----------
- handle the exceptions
- load the driver class
- establish the connection
- create statement
- execute statement
- close statement
- close connection

Spring JDBC
------------
- execute statement

JdbcTemplate class of Spring API is used for Spring JDBC

Example Application
-------------------
- Create a table "Student" in MySQL
	mysql>create table Student (id int(3), name varchar(10), age int(2));

pom.xml

- Add the following dependencies in pom.xml spring-context, mysql, spring-beans & JDBC

		<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <groupId>spring</groupId>
        <artifactId>SpringProj</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <packaging>jar</packaging>
        <name>SpringProj</name>
        <url>http://maven.apache.org</url>
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>3.8.1</version>
                <scope>test</scope>
            </dependency>
            <!--
            https://mvnrepository.com/artifact/org.springframework/spring-context -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>6.1.11</version>
            </dependency>
            <!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-beans</artifactId>
                <version>6.1.11</version>
            </dependency>
            <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.33</version>
            </dependency>
            <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-jdbc</artifactId>
                <version>6.1.11</version>
            </dependency>
        </dependencies>
    </project>

- Create an interface "StudentDao" in com.spring package of src/main/java folder of SpringProj
	
    package com.spring;
    import java.util.List;
    import java.util.Map;
    public interface StudentDao {
        public void insertStudent(int id, String name, int age);
        public void updateStudent(int id, int age);
        public void deleteStudent(int id);
        public List<Map<String, Object>> listStudents();
    }
-----------------------------------------------------

- Create a Spring bean class "StudentDaoImpl" in com.spring package
	
    package com.spring;
    import java.util.List;
    import java.util.Map;
    import org.springframework.jdbc.core.JdbcTemplate;
    public class StudentDaoImpl implements StudentDao {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public void insertStudent(int id, String name, int age) {
		String SQL = "insert into Student values (?,?,?)";
		jdbcTemplate.update(SQL, id, name, age);
		System.out.println("Student Inserted");
	}
	public void updateStudent(int id, int age) {
		String SQL = "update Student set age=? where id=?";
		jdbcTemplate.update(SQL, age, id);
		System.out.println("Student Updated");
	}
	public void deleteStudent(int id) {
		String SQL = "delete from Student where id=?";
		jdbcTemplate.update(SQL, id);
		System.out.println("Student Deleted");
	}
	public List<Map<String, Object>> listStudents() {
		String SQL = "select * from Student";
		return jdbcTemplate.queryForList(SQL);
	}
    }
-----------------------------------------------------

- Create Spring bean XML configuration file "jdbc.xml" in src/main/java folder
	
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:util="http://www.springframework.org/schema/util"
        xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd"> <!-- bean definitions here -->
        <bean id="student" class="com.spring.StudentDaoImpl">
            <property name="jdbcTemplate" ref="jdbcTemplate" />
        </bean>
        <bean id="jdbcTemplate"
            class="org.springframework.jdbc.core.JdbcTemplate">
            <property name="dataSource" ref="dataSource" />
        </bean>
        <bean id="dataSource"
            class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <property name="driverClassName"
                value="com.mysql.cj.jdbc.Driver" />
            <property name="url"
                value="jdbc:mysql://localhost:3306/java05" />
            <property name="username" value="root" />
            <property name="password" value="mukilanroot" />
        </bean>
    </beans>
-----------------------------------------------------

- Create a test class "JdbcTest" in com.spring package
	
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
    //		dao.insertStudent(101, "Ramu", 20);
    //		dao.updateStudent(101, 22);
    //		dao.deleteStudent(101);
            List<Map<String, Object>> studList = dao.listStudents();
            for (Map stud : studList) {
                System.out.println(stud.get("id") + " " + stud.get("name") + " " + stud.get("age"));
            }
        }
    }
-----------------------------------------------------
