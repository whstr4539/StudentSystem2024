package cn.edu.sdu.java.server.repositorys;
import cn.edu.sdu.java.server.models.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer,Integer> {
    List<Volunteer> findByStudentPersonId(Integer personId);
    @Query(value="from Volunteer where (?1=0 or student.personId=?1) " )
    List<Volunteer> findByStudent(Integer personId);
    @Query(value="select s.student.personId, sum(s.time) from Volunteer s where s.student.personId in ?1 group by s.student.personId" )
    List<?> getStudentStatisticsList(List<Integer> personId);
}