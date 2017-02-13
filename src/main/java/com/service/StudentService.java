package com.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bean.Student;

public class StudentService {
 @Autowired
 private SessionFactory sessionFt;
 
 public Student getStuById(String id){
	 String hql = "from Student where id =?";
	 Query query = getSession().createQuery(hql).setString(0, id);
	 Student stu = (Student) query.uniqueResult();
	 return stu;
 }
 
 public List<Student> getStuByName(String name){
	 String hql = "from Student where name =?";
	 Query query = getSession().createQuery(hql).setString(0, name);
	 return query.list();
 }
 
 public void saveStu(Student stu){
	  getSession().save(stu);
 }
 
 public Session  getSession(){
	 return sessionFt.getCurrentSession();
 }
}
