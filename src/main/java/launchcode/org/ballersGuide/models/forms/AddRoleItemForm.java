package launchcode.org.ballersGuide.models.forms;

import launchcode.org.ballersGuide.models.RightRole;
import launchcode.org.ballersGuide.models.Role;

public class AddRoleItemForm {

    private Role role;
    private Iterable<RightRole> rightRoles;
    private int roleId;
    private int rightId;

    public AddRoleItemForm() {
    }

    public AddRoleItemForm(Role role, Iterable<RightRole> rightRoles) {
        this.role = role;
        this.rightRoles = rightRoles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Iterable<RightRole> getRightRoles() {
        return rightRoles;
    }

    public void setRightRoles(Iterable<RightRole> rightRoles) {
        this.rightRoles = rightRoles;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getRightId() {
        return rightId;
    }

    public void setRightId(int rightId) {
        this.rightId = rightId;
    }
}
