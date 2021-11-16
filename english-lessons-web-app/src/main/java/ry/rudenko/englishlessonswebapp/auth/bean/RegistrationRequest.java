package ry.rudenko.englishlessonswebapp.auth.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest implements Serializable {
    private  String name;
    private  String email;
    private  String password;
    private  boolean checked;
}
