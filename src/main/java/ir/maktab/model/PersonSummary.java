package ir.maktab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
public class PersonSummary {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Date birthDate;

}
