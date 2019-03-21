package launchcode.org.ballersGuide.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class RightRole {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany(mappedBy = "rightRoles")
    private List<Role> roles;

    @NotNull
    @Size(min = 3, message = "Please insert a valid rightRole")
    private String name;

    public RightRole() {
    }

    public RightRole(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
