package dao;

import org.springframework.stereotype.Repository;
import pojo.Student;

import java.util.Set;

@Repository
public interface StudentDao {
        Student selectStudentByUsername(String username);
        Set<String> getRoles(String username);
        Set<String> getPermissions(String username);
}
