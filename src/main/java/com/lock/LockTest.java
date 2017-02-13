package com.lock;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bean.Student;
import com.service.StudentService;
/**
 * 
 * hibernate 乐观锁示例
 * @author George
 *
 */
public class LockTest {
	@Autowired
	private StudentService stuService;
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSource dataSource=(DataSource) context.getBean(DataSource.class);
        System.out.println("dataSource================="+dataSource);
		LockTest  lockTest = new LockTest();
		Thread A = new Thread(lockTest.new LockRunnable());
		A.start();
		System.out.println("ThreadName="+A.getName());
		/*try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		Thread B = new Thread(lockTest.new LockRunnable());
		B.start();
		System.out.println("ThreadName="+A.getName());
    }
	
	
	
	class LockRunnable implements Runnable{
		@Override
		public void run() {
			List<Student> stuList = stuService.getStuByName("session1");
			Student stu = null;
			if(stuList != null && stuList.size()>0){
				stu = stuList.get(0);
				//这时候，两个版本号是相同的
			    System.out.println("V="+stuList.get(0).getVersion());
			    stu.setName("aaaaaaaa");
			    stuService.saveStu(stu);
			}
		}
	}

}
