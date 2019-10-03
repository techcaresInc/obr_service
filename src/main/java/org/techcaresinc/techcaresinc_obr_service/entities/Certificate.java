package org.techcaresinc.techcaresinc_obr_service.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String child_name;
    private String sex;

    @NotEmpty
    private String age;

    private String fathers_name;

    @Lob
    private Byte[] fathers_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Byte[] getFathers_id() {
        return fathers_id;
    }

    public void setFathers_id(Byte[] fathers_id) {
        this.fathers_id = fathers_id;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        this.fathers_name = fathers_name;
    }
}
