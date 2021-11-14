package ry.rudenko.englishlessonsdictionary.entity.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrentUser {
    private String name;
    private String email;
    private boolean enabled;
}
