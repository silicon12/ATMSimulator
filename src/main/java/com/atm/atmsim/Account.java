package com.atm.atmsim;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Range(max = 4294967295L)
    @Column(nullable = false, updatable = false, unique = true)
    private Long accountNumber;

    @Column(precision = 8, scale = 2, columnDefinition = "DECIMAL(19,4)")
    private BigDecimal balance;
    private boolean isClosed;

    public void Account(BigDecimal balance, boolean isClosed) {
        this.balance = balance;
        this.isClosed = isClosed;
    }
}
