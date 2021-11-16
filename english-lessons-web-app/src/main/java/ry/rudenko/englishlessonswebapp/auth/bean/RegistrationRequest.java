package ry.rudenko.englishlessonswebapp.auth.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest implements Serializable {
    @NotBlank(message = "Name is mandatory")
    private  String name;
    @Email(message = "not valid email")
    private  String email;
    private  String password;
    private  boolean checked;
}
