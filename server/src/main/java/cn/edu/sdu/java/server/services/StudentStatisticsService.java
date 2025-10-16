package cn.edu.sdu.java.server.services;
import cn.edu.sdu.java.server.models.Honor;
import cn.edu.sdu.java.server.models.Person;
import cn.edu.sdu.java.server.models.Student;
import cn.edu.sdu.java.server.models.StudentStatistics;
import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.repositorys.*;
import cn.edu.sdu.java.server.util.CommonMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentStatisticsService {
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final StudentLeaveRepository studentLeaveRepository;
    private final StudentStatisticsRepository studentStatisticsRepository;
    private final HonorRepository honorRepository;
    private final SocialRepository socialRepository;
    private final VolunteerRepository volunteerRepository;
    public StudentStatisticsService(StudentRepository studentRepository, ScoreRepository scoreRepository, StudentLeaveRepository studentLeaveRepository, StudentStatisticsRepository studentStatisticsRepository, HonorRepository honorRepository, SocialRepository socialRepository, VolunteerRepository volunteerRepository) {
        this.studentRepository = studentRepository;
        this.scoreRepository = scoreRepository;
        this.studentLeaveRepository = studentLeaveRepository;
        this.studentStatisticsRepository = studentStatisticsRepository;
        this.honorRepository = honorRepository;
        this.socialRepository = socialRepository;
        this.volunteerRepository = volunteerRepository;
    }
    public DataResponse getStudentStatisticsList(DataRequest dataRequest) {
        String roleName = CommonMethod.getRoleName();
        Integer userId = CommonMethod.getPersonId();
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<StudentStatistics> sList = switch (roleName){
            case "ROLE_STUDENT" -> studentStatisticsRepository.findByUserId(userId);
            case "ROLE_ADMIN" -> studentStatisticsRepository.findAll();
            default -> null;
        }; //数据库查询操作
        for (StudentStatistics ss : sList) {
            Map<String, Object> m = new HashMap<>();
            Person p = ss.getStudent().getPerson();
            m.put("studentNum", p.getNum());
            m.put("studentName", p.getName());
            m.put("courseCount", ss.getCourseCount()+"");
            m.put("avgScore", ss.getAvgScore());
            m.put("gpa", ss.getGpa());
            m.put("no", ss.getNo()+"");
            m.put("leaveCount", ss.getLeaveCount()+"");
            m.put("honorCount", ss.getHonorCount()+"");
            m.put("socialCount", ss.getSocialCount()+"");
            m.put("vTime", ss.getVTime()+"");
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    };
    public DataResponse doStudentStatistics(DataRequest dataRequest) {
        String year = "2025";
        List<Student> sList = studentRepository.findAll();
        int i,j;
        Student s;
        Integer personId;
        Object[] as;
        Long l;
        int c;
        double creditMark;
        double creditSum;
        double sum;
        List<Integer> idList = new ArrayList<>();
        Map<Integer,StudentStatistics> sMap = new HashMap<>();
        StudentStatistics ss;
        for(i = 0; i < sList.size(); i++) {
            s= sList.get(i);
            ss = new StudentStatistics();
            ss.setStudent(s);
            ss.setYear(year);
            ss.setCourseCount(0);
            ss.setGpa(0d);
            ss.setAvgScore(0d);
            ss.setLeaveCount(0);
            ss.setHonorCount(0);
            ss.setSocialCount(0);
            ss.setVTime(0);
            personId = s.getPerson().getPersonId();
            sMap.put(personId, ss);
            idList.add(personId);
        }
        List<StudentStatistics> ssList = studentStatisticsRepository.findByYear(year, idList);
        if(ssList!= null && !ssList.isEmpty()) {
            for (i = 0; i < ssList.size();i++) {
                ss = ssList.get(i);
                personId = ss.getStudent().getPersonId();
                sMap.put(personId, ss);
                ss.setCourseCount(0);
                ss.setGpa(0d);
                ss.setAvgScore(0d);
                ss.setLeaveCount(0);
                ss.setHonorCount(0);
                ss.setSocialCount(0);
                ss.setVTime(0);
            }
        }
        List<?> list = scoreRepository.getStudentStatisticsList(idList);
        if(list != null && !list.isEmpty()) {
            for(i = 0;i<list.size();i++) {
                as = (Object[]) list.get(i);
                personId = (Integer)as[0];
                ss = sMap.get(personId);
                if(ss == null)
                    continue;
                l = (Long)as[1];
                if(l != null)
                    c = l.intValue();
                else
                    c = 0;
                if(c == 0)
                    continue;
                sum = (Long)as[2];
                ss.setCourseCount(c);
                ss.setAvgScore(CommonMethod.getDouble2(sum/c));
                creditSum = (Long)as[3];
                creditMark = (Long)as[4];
                ss.setGpa(CommonMethod.getDouble2(creditMark / creditSum));
            }
        }
        list = studentLeaveRepository.getStudentStatisticsList(idList);
        if(list != null && !list.isEmpty()) {
            for(i = 0;i<list.size();i++) {
                as = (Object[]) list.get(i);
                personId = (Integer)as[0];
                ss = sMap.get(personId);
                if(ss == null)
                    continue;
                l = (Long)as[1];
                if(l != null)
                    c = l.intValue();
                else
                    c = 0;
                if(c == 0)
                    continue;
                ss.setLeaveCount(c);
            }
        }
        list = honorRepository.getStudentStatisticsList(idList);
        if(list != null && !list.isEmpty()) {
            for(i = 0;i<list.size();i++) {
                as = (Object[]) list.get(i);
                personId = (Integer)as[0];
                ss = sMap.get(personId);
                if(ss == null)
                    continue;
                l = (Long)as[1];
                if(l != null)
                    c = l.intValue();
                else
                    c = 0;
                if(c == 0)
                    continue;
                ss.setHonorCount(c);
            }
        }
        list = socialRepository.getStudentStatisticsList(idList);
        if(list != null && !list.isEmpty()) {
            for(i = 0;i<list.size();i++) {
                as = (Object[]) list.get(i);
                personId = (Integer)as[0];
                ss = sMap.get(personId);
                if(ss == null)
                    continue;
                l = (Long)as[1];
                if(l != null)
                    c = l.intValue();
                else
                    c = 0;
                if(c == 0)
                    continue;
                ss.setSocialCount(c);
            }
        }
        list = volunteerRepository.getStudentStatisticsList(idList);
        if(list != null && !list.isEmpty()) {
            for(i = 0;i<list.size();i++) {
                as = (Object[]) list.get(i);
                personId = (Integer)as[0];
                ss = sMap.get(personId);
                if(ss == null)
                    continue;
                l = (Long)as[1];
                if(l != null)
                    c = l.intValue();
                else
                    c = 0;
                if(c == 0)
                    continue;
                ss.setVTime(c);
            }
        }
        StudentStatistics[] ssArray = new StudentStatistics[sMap.size()];
        sMap.values().toArray(ssArray);
        Arrays.sort(ssArray);
        for(i= 0; i < ssArray.length; i++) {
            ss = ssArray[i];
            ss.setNo(i+1);
            studentStatisticsRepository.save(ss);
        }
        return CommonMethod.getReturnMessageOK();
    }
}
