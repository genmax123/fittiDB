package de.genrichgolzsch.fittidb.model;


public class UserRoleView {

    private int userId;
    private String username;
    private String roleName;
    private String roleDescription;
    private boolean active;

    public UserRoleView(int userId, String username,
                        String roleName, String roleDescription,
                        boolean active) {
        this.userId = userId;
        this.username = username;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public boolean isActive() {
        return active;
    }
}
