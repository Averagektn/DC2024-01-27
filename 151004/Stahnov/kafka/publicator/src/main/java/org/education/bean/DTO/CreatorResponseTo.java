package org.education.bean.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatorResponseTo {
    int id;
    String login;
    String password;
    String firstname;
    String lastname;
}
