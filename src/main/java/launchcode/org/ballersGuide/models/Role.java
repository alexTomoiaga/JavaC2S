package launchcode.org.ballersGuide.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, message = "Please insert a role")
    private String name;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> users;

    @ManyToMany
    private List<RightRole> rightRoles;

    public Role() {
    }

    public Role(String name, List<RightRole> rightRoles) {
        this.name = name;
        this.rightRoles = rightRoles;
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

    public List<RightRole> getRightRoles() {
        return rightRoles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addRight(RightRole rightRole) {
        rightRoles.add(rightRole);
    }

    public void deleteRight(RightRole rightRole) {
        rightRoles.remove(rightRole);
    }

}