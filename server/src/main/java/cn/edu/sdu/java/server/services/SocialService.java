package cn.edu.sdu.java.server.services;
import cn.edu.sdu.java.server.models.Honor;
import cn.edu.sdu.java.server.models.SocialPractice;
import cn.edu.sdu.java.server.models.Student;
import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.payload.response.OptionItem;
import cn.edu.sdu.java.server.payload.response.OptionItemList;
import cn.edu.sdu.java.server.repositorys.SocialRepository;
import cn.edu.sdu.java.server.repositorys.StudentRepository;
import cn.edu.sdu.java.server.util.CommonMethod;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SocialService {
    private final SocialRepository socialRepository;
    private final StudentRepository studentRepository;

    public SocialService(SocialRepository socialRepository, StudentRepository studentRepository) {
        this.socialRepository = socialRepository;
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

    public DataResponse getSocialList(DataRequest dataRequest) {
        String roleName = CommonMethod.getRoleName();
        Integer userId = CommonMethod.getPersonId();
        Integer personId = dataRequest.getInteger("personId");
        if(personId == null)
            personId = 0;
        assert roleName != null;
        List<SocialPractice> sList = switch (roleName){
            case "ROLE_STUDENT" -> socialRepository.findByStudent(userId);
            case "ROLE_TEACHER" -> socialRepository.findByStudent(personId);
            case "ROLE_ADMIN" -> socialRepository.findByStudent(personId);
            default -> null;
        }; //数据库查询操作  //数据库查询操作
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> m;
        for (SocialPractice s : sList) {
            m = new HashMap<>();
            m.put("socialId", s.getSocialId()+"");
            m.put("personId",s.getStudent().getPersonId()+"");
            m.put("studentNum",s.getStudent().getPerson().getNum());
            m.put("studentName",s.getStudent().getPerson().getName());
            m.put("className",s.getStudent().getClassName());
            m.put("name",s.getName());
            m.put("date",s.getDate());
            m.put("authority", s.getAuthority());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }
    public DataResponse socialSave(DataRequest dataRequest) {
        Integer personId = dataRequest.getInteger("personId");
        String name= dataRequest.getString("name");
        String date= dataRequest.getString("date");
        String authority= dataRequest.getString("authority");
        Integer socialId = dataRequest.getInteger("socialId");
        Optional<SocialPractice> op;
        SocialPractice s = null;
        if(socialId != null) {
            op= socialRepository.findById(socialId);
            if(op.isPresent())
                s = op.get();
        }
        if(s == null) {
            s = new SocialPractice();
            s.setStudent(studentRepository.findById(personId).get());
        }
        s.setName(name);
        s.setAuthority(authority);
        s.setDate(date);
        socialRepository.save(s);
        return CommonMethod.getReturnMessageOK();
    }
    public DataResponse socialDelete(DataRequest dataRequest) {
        Integer socialId = dataRequest.getInteger("socialId");
        Optional<SocialPractice> op;
        SocialPractice s = null;
        if(socialId != null) {
            op= socialRepository.findById(socialId);
            if(op.isPresent()) {
                s = op.get();
                socialRepository.delete(s);
            }
        }
        return CommonMethod.getReturnMessageOK();
    }

}

