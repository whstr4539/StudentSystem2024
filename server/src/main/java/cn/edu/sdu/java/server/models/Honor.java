package cn.edu.sdu.java.server.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "honor", uniqueConstraints = {
        })
public class Honor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    private String name;//名字

    @Size(max = 50)
    private String type;

    @Size(max = 50)
    private String date;//获得日期

    @Size(max = 200)
    private String authority;//颁发机构

    @ManyToOne
    @JoinColumn(name = "personId")
    private Student student;

}