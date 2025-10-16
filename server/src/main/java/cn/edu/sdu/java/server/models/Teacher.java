package cn.edu.sdu.java.server.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(	name = "teacher",
        uniqueConstraints = {
        })
public class Teacher {
    @Id
    private Integer personId;

    @OneToOne
    @JoinColumn(name = "personId")
    @JsonIgnore
    private Person person;

    @Size(max=50)
    private String title;//职称

    @Size(max=50)
    private String degree;//学位

    private Date enterTime;//入职时间
}