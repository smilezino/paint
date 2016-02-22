package top.wangjun.enums;

/**
 * 用户类型
 */
public enum UserType {
    NORMAL(0, "普通用户"),
    MANAGER(1, "管理员"),
    ADMIN(2, "超级管理员"),
    SYSTEM(3, "系统");

    UserType(int role, String description) {
        this.role = role;
        this.description = description;
    }

    private int role;
    private String description;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
