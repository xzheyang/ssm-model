package dao;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.Student;

public class StudentDaoTest extends BaseTest {

    @Autowired
    private StudentDao StudentDao;

    @Test
    public void testStudentDao(){


        Student s = StudentDao.selectStudentByUsername("hy");

        System.out.println("username:"+s.getUsername());
        System.out.println("password:"+s.getPassword());

    }

}
