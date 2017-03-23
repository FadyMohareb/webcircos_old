/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author s262012
 */
@Entity
@Table(name = "Files")
public class File {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Integer id;

    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "file_name")
    private Integer f_name;
    
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "file_path")
    private Integer f_path;
    
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "file_type")
    private Integer f_type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public File() {
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getF_name() {
        return f_name;
    }

    public void setF_name(Integer f_name) {
        this.f_name = f_name;
    }

    public Integer getF_path() {
        return f_path;
    }

    public void setF_path(Integer f_path) {
        this.f_path = f_path;
    }

    public Integer getF_type() {
        return f_type;
    }

    public void setF_type(Integer f_type) {
        this.f_type = f_type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
    
}
