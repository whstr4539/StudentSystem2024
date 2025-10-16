package cn.edu.sdu.java.server.repositorys;

import cn.edu.sdu.java.server.models.Honor;
import cn.edu.sdu.java.server.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HonorRepository extends JpaRepository<Honor,Integer> {
    List<Honor> findByStudentPersonId(Integer personId);
    @Query(value="from Honor where (?1=0 or student.personId=?1) " )
    List<Honor> findByStudent(Integer personId);
    @Query(value="select s.student.personId, count(s.id) from Honor s where s.student.personId in ?1 group by s.student.personId" )
    List<?> getStudentStatisticsList(List<Integer> personId);
}
