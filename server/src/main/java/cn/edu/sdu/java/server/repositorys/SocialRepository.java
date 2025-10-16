package cn.edu.sdu.java.server.repositorys;


import cn.edu.sdu.java.server.models.SocialPractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocialRepository extends JpaRepository<SocialPractice,Integer> {
    List<SocialPractice> findByStudentPersonId(Integer personId);
    @Query(value="from SocialPractice where (?1=0 or student.personId=?1) " )
    List<SocialPractice> findByStudent(Integer personId);
    @Query(value="select s.student.personId, count(s.socialId) from SocialPractice s where s.student.personId in ?1 group by s.student.personId" )
    List<?> getStudentStatisticsList(List<Integer> personId);
}
