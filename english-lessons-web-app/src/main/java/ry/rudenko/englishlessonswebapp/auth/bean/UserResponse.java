package ry.rudenko.englishlessonswebapp.auth.bean;

import lombok.Getter;
import lombok.Setter;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;


@Getter
@Setter
public class UserResponse extends BaseResponse {
    private String name;
    private String email;
    private boolean enabled;

    public UserResponse(UserEntity appUser) {
        this.name = appUser.getName();
        this.email = appUser.getEmail();
        this.enabled = appUser.isEnabled();
    }
}
