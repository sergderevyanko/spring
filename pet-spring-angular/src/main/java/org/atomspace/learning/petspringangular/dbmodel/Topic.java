package org.atomspace.learning.petspringangular.dbmodel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by sergey.derevyanko on 19.05.2019.
 */
@Entity
public class Topic implements Serializable{
    private Long id;
    private String name;
    private String description;
    private String textField1;
    private String textField2;

    public Topic() {
    }

    public Topic(String name, String description, String textField1, String textField2) {
        this.name = name;
        this.description = description;
        this.textField1 = textField1;
        this.textField2 = textField2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="name", length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="description", length = 5000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="text_field1", length = 5000)
    public String getTextField1() {
        return textField1;
    }

    public void setTextField1(String textField1) {
        this.textField1 = textField1;
    }

    @Column(name="text_field2", length = 5000)
    public String getTextField2() {
        return textField2;
    }

    public void setTextField2(String textField2) {
        this.textField2 = textField2;
    }
}
