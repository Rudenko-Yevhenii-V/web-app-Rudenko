package ry.rudenko.englishlessonswebapp.auth.bean;


import lombok.Data;
import ry.rudenko.englishlessonswebapp.enums.UserRole;

@Data
public class RoleRequest {
    private  Long id;
    private UserRole role;
}
