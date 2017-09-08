package dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.impl.Student;

public class StudentDaoTest {

    @Autowired
    private StudentDao StudentDao;

    @Test
    public void testStudentDao(){


        Student s = StudentDao.search("hy");

        System.out.println("password:-->"+s.getPassword());

    }

}
