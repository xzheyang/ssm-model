package service.impl;

import dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Student;
import service.StudentService;

@Transactional
@Service(value = "StudentServiceImpl")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao StudentDao;

    public boolean loginUser(Student student) {
        Student result = StudentDao.search(student.getId());

        if(student!=null){

            if(result.getPassword().equals(student.getPassword())){
                return true;
            }
            return false;
        }

        return false;
    }

}
