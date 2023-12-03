package com.example.BankingApplication.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "data")
    private LocalDateTime dateTime;

    @Column(name= "amount")
    private BigDecimal amount;

    @Column(name = "message")
    private String message;

    @ManyToMany(mappedBy = "accountTransactions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Account> account = new HashSet<>();

}
