package dao;

import org.springframework.stereotype.Repository;
import pojo.Student;

@Repository
public interface StudentDao {
        Student search(int id);
}
