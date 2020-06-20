package tk.slaaavyn.soft.industry.banking.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deposit", nullable = false)
    private BigDecimal deposit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", deposit=" + deposit +
                ", user=" + user +
                ", currencyType=" + currencyType +
                '}';
    }
}
