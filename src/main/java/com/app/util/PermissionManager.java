package com.app.util;

import com.app.model.User;
import java.util.*;

public class PermissionManager {
    
    private static final Map<String, Set<String>> rolePermissions = new HashMap<>();
    
    static {
        initializePermissions();
    }
    
    private static void initializePermissions() {
        // ADMIN Permissions
        Set<String> adminPerms = new HashSet<>(Arrays.asList(
            "VIEW_USERS",
            "ADD_USER",
            "EDIT_USER",
            "DELETE_USER",
            "VIEW_TOPICS",
            "ADD_TOPIC",
            "EDIT_TOPIC",
            "DELETE_TOPIC",
            "MANAGE_ASSIGNMENTS",
            "APPROVE_ASSIGNMENT",
            "REJECT_ASSIGNMENT",
            "MANAGE_BOARDS",
            "ADD_BOARD",
            "EDIT_BOARD",
            "DELETE_BOARD",
            "MANAGE_BOARD_MEMBERS",
            "VIEW_ALL_PROPOSALS",
            "VIEW_ALL_GRADES",
            "EXPORT_REPORTS",
            "SYSTEM_SETTINGS"
        ));
        rolePermissions.put("ADMIN", adminPerms);
        
        // LECTURER Permissions
        Set<String> lecturerPerms = new HashSet<>(Arrays.asList(
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "CREATE_TOPIC",
            "EDIT_OWN_TOPIC",
            "VIEW_OWN_TOPICS",
            "VIEW_TOPIC_ASSIGNMENTS",
            "APPROVE_ASSIGNMENT",
            "REJECT_ASSIGNMENT",
            "GRADE_PROPOSALS",
            "VIEW_STUDENT_PROPOSALS",
            "EXPORT_REPORT"
        ));
        rolePermissions.put("LECTURER", lecturerPerms);
        
        // STUDENT Permissions
        Set<String> studentPerms = new HashSet<>(Arrays.asList(
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "VIEW_ALL_TOPICS",
            "REGISTER_TOPIC",
            "VIEW_OWN_ASSIGNMENTS",
            "VIEW_OWN_PROPOSALS",
            "CREATE_PROPOSAL",
            "EDIT_PROPOSAL",
            "SUBMIT_PROPOSAL",
            "VIEW_GRADES"
        ));
        rolePermissions.put("STUDENT", studentPerms);
        
        // REVIEWER Permissions
        Set<String> reviewerPerms = new HashSet<>(Arrays.asList(
            "VIEW_PROFILE",
            "EDIT_PROFILE",
            "VIEW_ASSIGNED_PROPOSALS",
            "GRADE_PROPOSALS",
            "VIEW_OWN_GRADES"
        ));
        rolePermissions.put("REVIEWER", reviewerPerms);
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
     * Kiểm tra role là reviewer không
     */
    public static boolean isReviewer(User user) {
        return user != null && "REVIEWER".equals(user.getRole());
    }
}
