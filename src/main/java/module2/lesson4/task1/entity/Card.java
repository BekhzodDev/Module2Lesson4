package module2.lesson4.task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cards")

public class Card {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
@Column(unique = true)
private String number;
private double balance;
private Date expireDate;
private Boolean active=true;
private String userName;
}
