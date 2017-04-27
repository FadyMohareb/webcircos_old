package uk.ac.cranfield.bix.models;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Representation of the USERS table from the database
 * @author vmuser
 */
@Entity
@Table(name="Users")
public class User implements Serializable {
    
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Integer id;
  
  @NotNull
  @Size(min = 2, max = 45)
  @Column(name = "user_login")
  private String login;
  
  @NotNull
  @Size(min = 2, max = 64)
  @Column(name = "user_pswd")
  private String password;
  
  @NotNull
  @Size(min = 3, max = 45)
  @Column(name = "user_email")
  private String email;
  
  @NotNull
  @Size(min = 2, max = 45)
  @Column(name = "user_name")
  private String name;

  @Null
  @Size(min = 2, max = 45)
  @Column(name = "user_avatar")
  private String avatar;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private Set<Project> projects;
  
  public User() { }

public User(Integer id) {
    this.id = id;
}


public User(String login, String password, String email, String name) {
    this.login = login;
    this.password = password;
    this.email = email;
    this.name = name;
}

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String value) {
    this.email = value;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
  
} // class User
