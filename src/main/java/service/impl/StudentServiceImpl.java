package service.impl;

import dao.StudentDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Student;
import service.StudentService;

import javax.annotation.Resource;

@Transactional
@Service(value = "StudentServiceImpl")
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao StudentDao;

    public boolean loginUser(Student student) {

        if(student==null){return false;}

        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(student.getUsername(),student.getPassword()) ;
       try {
           subject.login(token);
       }catch (AuthenticationException e){
           return false;
       }
            return true;
    }

    /*原始
    public boolean loginUser(Student student) {
        Student result = StudentDao.search(student.getUsername());

        if(student!=null){

            if(result.getPassword().equals(student.getPassword())){
                return true;
            }
            return false;
        }

        return false;
    }*/




}
