package cn.edu.sdu.java.server.repositorys;

import cn.edu.sdu.java.server.models.StudentStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentStatisticsRepository extends JpaRepository<StudentStatistics,Integer> {
    @Query(value = "select s from StudentStatistics s where s.year = ?1 and  s.student.personId in ?2")
    List<StudentStatistics> findByYear(String year, List<Integer>idList);
    @Query(value = "from StudentStatistics where student.personId=?1")
    List<StudentStatistics> findByUserId(Integer id);
}
