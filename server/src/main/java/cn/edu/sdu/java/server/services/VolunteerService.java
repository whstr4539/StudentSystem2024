package cn.edu.sdu.java.server.services;
import cn.edu.sdu.java.server.models.Honor;
import cn.edu.sdu.java.server.models.SocialPractice;
import cn.edu.sdu.java.server.models.Student;
import cn.edu.sdu.java.server.models.Volunteer;
import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.payload.response.OptionItem;
import cn.edu.sdu.java.server.payload.response.OptionItemList;
import cn.edu.sdu.java.server.repositorys.SocialRepository;
import cn.edu.sdu.java.server.repositorys.StudentRepository;
import cn.edu.sdu.java.server.repositorys.VolunteerRepository;
import cn.edu.sdu.java.server.util.CommonMethod;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final StudentRepository studentRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, StudentRepository studentRepository) {
        this.volunteerRepository = volunteerRepository;
        this.studentRepository = studentRepository;
    }
    public OptionItemList getStudentItemOptionList(DataRequest dataRequest) {
        List<Student> sList = studentRepository.findStudentListByNumName("");  //数据库查询操作
        List<OptionItem> itemList = new ArrayList<>();
        for (Student s : sList) {
            itemList.add(new OptionItem( s.getPersonId(),s.getPersonId()+"", s.getPerson().getNum()+"-"+s.getPerson().getName()));
        }
        return new OptionItemList(0, itemList);
    }

    public DataResponse getVolunteerList(DataRequest dataRequest) {
        String roleName = CommonMethod.getRoleName();
        Integer userId = CommonMethod.getPersonId();
        Integer personId = dataRequest.getInteger("personId");
        if(personId == null)
            personId = 0;
        assert roleName != null;
        List<Volunteer> sList = switch (roleName){
            case "ROLE_STUDENT" -> volunteerRepository.findByStudent(userId);
            case "ROLE_TEACHER" -> volunteerRepository.findByStudent(personId);
            case "ROLE_ADMIN" -> volunteerRepository.findByStudent(personId);
            default -> null;
        }; //数据库查询操作
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> m;
        for (Volunteer s : sList) {
            m = new HashMap<>();
            m.put("volunteerId", s.getVolunteerId()+"");
            m.put("personId",s.getStudent().getPersonId()+"");
            m.put("studentNum",s.getStudent().getPerson().getNum());
            m.put("studentName",s.getStudent().getPerson().getName());
            m.put("className",s.getStudent().getClassName());
            m.put("name",s.getName());
            m.put("date",s.getDate());
            m.put("authority", s.getAuthority());
            m.put("time",""+s.getTime());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }
    public DataResponse volunteerSave(DataRequest dataRequest) {
        Integer personId = dataRequest.getInteger("personId");
        String name= dataRequest.getString("name");
        String date= dataRequest.getString("date");
        String authority= dataRequest.getString("authority");
        Integer time = dataRequest.getInteger("time");
        Integer volunteerId = dataRequest.getInteger("volunteerId");
        Optional<Volunteer> op;
        Volunteer s = null;
        if(volunteerId != null) {
            op= volunteerRepository.findById(volunteerId);
            if(op.isPresent())
                s = op.get();
        }
        if(s == null) {
            s = new Volunteer();
            s.setStudent(studentRepository.findById(personId).get());
        }
        s.setName(name);
        s.setAuthority(authority);
        s.setDate(date);
        s.setTime(time);
        volunteerRepository.save(s);
        return CommonMethod.getReturnMessageOK();
    }
    public DataResponse volunteerDelete(DataRequest dataRequest) {
        Integer volunteerId = dataRequest.getInteger("volunteerId");
        Optional<Volunteer> op;
        Volunteer s = null;
        if(volunteerId != null) {
            op= volunteerRepository.findById(volunteerId);
            if(op.isPresent()) {
                s = op.get();
                volunteerRepository.delete(s);
            }
        }
        return CommonMethod.getReturnMessageOK();
    }

}

