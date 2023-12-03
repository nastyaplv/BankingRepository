package com.example.BankingApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    public Account(String beneficiary_name, String pin) {
        this.beneficiaryName = beneficiary_name;
        this.pin = pin;
        this.balance=BigDecimal.ZERO;
    }

    @Column(name = "pin", length = 4)
    private String pin;

    @Column(name = "balance")
    @Min(value = 0, message = "Значение поля должно быть не меньше 0")
    private BigDecimal balance;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "accounts_transactions",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private Set<Transaction> accountTransactions;
}
