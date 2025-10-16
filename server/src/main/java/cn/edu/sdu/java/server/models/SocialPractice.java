package cn.edu.sdu.java.server.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "social_practice", uniqueConstraints = {
})
public class SocialPractice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer socialId;//主键id

    @Size(max = 50)
    private String name;//名称

    @Size(max = 50)
    private String date;//日期

    @Size(max = 200)
    private String authority;//实践机构

    @ManyToOne
    @JoinColumn(name = "personId")//多对一关联学生主键
    private Student student;
}