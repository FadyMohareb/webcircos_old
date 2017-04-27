package uk.ac.cranfield.bix.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
 * Representation of the Files table from the database
 * @author vmuser
 */
@Entity
@Table(name = "Files")
@JsonIgnoreProperties({"bytes"})
public class FileInput implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Integer id;

    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "file_name")
    private String f_name;
    
    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "file_path")
    private String f_path;
    
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "file_type")
    private String f_type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    public FileInput() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_path() {
        return f_path;
    }

    public void setF_path(String f_path) {
        this.f_path = f_path;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
