package com.app.util;

import com.app.model.User;
import java.util.*;

public class PermissionManager {
    
    private static final Map<String, Set<String>> rolePermissions = new HashMap<>();
    
    static {
        initializePermissions();
    }
    
    private static void initializePermissions() {
        // STUDENT (Sinh viên) Permissions
        Set<String> studentPerms = new HashSet<>(Arrays.asList(
            "LOGIN",
            "LOGOUT",
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "VIEW_ALL_TOPICS",
            "REGISTER_TOPIC",
            "VIEW_OWN_TOPICS",
            "VIEW_OWN_ASSIGNMENTS",
            "VIEW_GRADES"
        ));
        rolePermissions.put("STUDENT", studentPerms);
        
        // LECTURER (Giảng viên) Permissions
        Set<String> lecturerPerms = new HashSet<>(Arrays.asList(
            "LOGIN",
            "LOGOUT",
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "CREATE_TOPIC",
            "EDIT_OWN_TOPIC",
            "VIEW_OWN_TOPICS",
            "VIEW_TOPIC_ASSIGNMENTS",
            "GRADE_TOPICS",
            "VIEW_STUDENT_PROPOSALS",
            "VIEW_GRADES"
        ));
        rolePermissions.put("LECTURER", lecturerPerms);
        
        // MANAGER (Người quản lý) Permissions
        Set<String> managerPerms = new HashSet<>(Arrays.asList(
            "LOGIN",
            "LOGOUT",
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "MANAGE_TOPICS",
            "VIEW_ALL_TOPICS",
            "EDIT_TOPIC",
            "DELETE_TOPIC",
            "MANAGE_BOARDS",
            "ADD_BOARD",
            "EDIT_BOARD",
            "DELETE_BOARD",
            "MANAGE_BOARD_MEMBERS",
            "MANAGE_STUDENTS",
            "VIEW_USERS",
            "EDIT_USER",
            "MANAGE_LECTURERS",
            "GRADE_TOPICS",
            "VIEW_ALL_GRADES",
            "VIEW_ALL_PROPOSALS",
            "EXPORT_REPORTS",
            "VIEW_STATISTICS"
        ));
        rolePermissions.put("MANAGER", managerPerms);
        
        // ADMIN Permissions
        Set<String> adminPerms = new HashSet<>(Arrays.asList(
            "LOGIN",
            "LOGOUT",
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "MANAGE_USERS",
            "VIEW_USERS",
            "ADD_USER",
            "EDIT_USER",
            "DELETE_USER",
            "MANAGE_STUDENTS",
            "MANAGE_LECTURERS",
            "MANAGE_TOPICS",
            "VIEW_ALL_TOPICS",
            "ADD_TOPIC",
            "EDIT_TOPIC",
            "DELETE_TOPIC",
            "MANAGE_BOARDS",
            "ADD_BOARD",
            "EDIT_BOARD",
            "DELETE_BOARD",
            "MANAGE_BOARD_MEMBERS",
            "MANAGE_ASSIGNMENTS",
            "APPROVE_ASSIGNMENT",
            "REJECT_ASSIGNMENT",
            "GRADE_TOPICS",
            "VIEW_ALL_PROPOSALS",
            "VIEW_ALL_GRADES",
            "EXPORT_REPORTS",
            "VIEW_STATISTICS",
            "SYSTEM_SETTINGS",
            "MANAGE_PERMISSIONS"
        ));
        rolePermissions.put("ADMIN", adminPerms);
    }
    
    /**
     * Kiểm tra người dùng có quyền thực hiện hành động nào đó không
     */
    public static boolean hasPermission(User user, String permission) {
        if (user == null) return false;
        
        Set<String> perms = rolePermissions.get(user.getRole());
        return perms != null && perms.contains(permission);
    }
    
    /**
     * Kiểm tra người dùng có một trong các quyền không
     */
    public static boolean hasAnyPermission(User user, String... permissions) {
        if (user == null) return false;
        
        for (String perm : permissions) {
            if (hasPermission(user, perm)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Kiểm tra người dùng có tất cả các quyền không
     */
    public static boolean hasAllPermissions(User user, String... permissions) {
        if (user == null) return false;
        
        for (String perm : permissions) {
            if (!hasPermission(user, perm)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Lấy tất cả các quyền của role
     */
    public static Set<String> getPermissions(String role) {
        return rolePermissions.getOrDefault(role, new HashSet<>());
    }
    
    /**
     * Kiểm tra role có quyền quản trị không
     */
    public static boolean isAdmin(User user) {
        return user != null && "ADMIN".equals(user.getRole());
    }
    
    /**
     * Kiểm tra role là giảng viên không
     */
    public static boolean isLecturer(User user) {
        return user != null && "LECTURER".equals(user.getRole());
    }
    
    /**
     * Kiểm tra role là sinh viên không
     */
    public static boolean isStudent(User user) {
        return user != null && "STUDENT".equals(user.getRole());
    }
    
    /**
     * Kiểm tra role là người quản lý không
     */
    public static boolean isManager(User user) {
        return user != null && "MANAGER".equals(user.getRole());
    }
}
