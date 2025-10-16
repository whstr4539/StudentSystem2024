package cn.edu.sdu.java.server.services;


import cn.edu.sdu.java.server.models.Honor;
import cn.edu.sdu.java.server.models.Student;
import cn.edu.sdu.java.server.payload.request.DataRequest;
import cn.edu.sdu.java.server.payload.response.DataResponse;
import cn.edu.sdu.java.server.payload.response.OptionItem;
import cn.edu.sdu.java.server.payload.response.OptionItemList;
import cn.edu.sdu.java.server.repositorys.HonorRepository;
import cn.edu.sdu.java.server.repositorys.StudentRepository;
import cn.edu.sdu.java.server.util.CommonMethod;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class HonorService {
    private final HonorRepository honorRepository;
    private final StudentRepository studentRepository;

    public HonorService(HonorRepository honorRepository, StudentRepository studentRepository) {
        this.honorRepository = honorRepository;
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

    public DataResponse getHonorList(DataRequest dataRequest) {
        String roleName = CommonMethod.getRoleName();
        Integer userId = CommonMethod.getPersonId();
        Integer personId = dataRequest.getInteger("personId");
        if(personId == null)
            personId = 0;
        assert roleName != null;
        List<Honor> sList = switch (roleName){
            case "ROLE_STUDENT" -> honorRepository.findByStudent(userId);
            case "ROLE_TEACHER" -> honorRepository.findByStudent(personId);
            case "ROLE_ADMIN" -> honorRepository.findByStudent(personId);
            default -> null;
        }; //数据库查询操作
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> m;
        if (sList != null && !sList.isEmpty()) {
            for (Honor s : sList) {
                m = new HashMap<>();
                m.put("id", s.getId()+"");
                m.put("personId",s.getStudent().getPersonId()+"");
                m.put("studentNum",s.getStudent().getPerson().getNum());
                m.put("studentName",s.getStudent().getPerson().getName());
                m.put("className",s.getStudent().getClassName());
                m.put("name",s.getName());
                m.put("type",s.getType());
                m.put("date",s.getDate());
                m.put("authority", s.getAuthority());
                dataList.add(m);
            }
        }
        return CommonMethod.getReturnData(dataList);
    }
    public DataResponse honorSave(DataRequest dataRequest) {
        Integer personId = dataRequest.getInteger("personId");
        String name= dataRequest.getString("name");
        String type= dataRequest.getString("type");
        String date= dataRequest.getString("date");
        String authority= dataRequest.getString("authority");
        Integer id = dataRequest.getInteger("id");
        Optional<Honor> op;
        Honor s = null;
        if(id != null) {
            op= honorRepository.findById(id);
            if(op.isPresent())
                s = op.get();
        }
        if(s == null) {
            s = new Honor();
            s.setStudent(studentRepository.findById(personId).get());
        }
        s.setName(name);
        s.setType(type);
        s.setAuthority(authority);
        s.setDate(date);
        honorRepository.save(s);
        return CommonMethod.getReturnMessageOK();
    }
    public DataResponse honorDelete(DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        Optional<Honor> op;
        Honor s = null;
        if(id != null) {
            op= honorRepository.findById(id);
            if(op.isPresent()) {
                s = op.get();
                honorRepository.delete(s);
            }
        }
        return CommonMethod.getReturnMessageOK();
    }

}
