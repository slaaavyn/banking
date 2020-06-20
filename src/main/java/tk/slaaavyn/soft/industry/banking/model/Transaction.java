package tk.slaaavyn.soft.industry.banking.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "counted", nullable = false)
    private BigDecimal counted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "balance_id")
    private Balance balance;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType transactionCurrencyType;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCounted() {
        return counted;
    }

    public void setCounted(BigDecimal counted) {
        this.counted = counted;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public CurrencyType getTransactionCurrencyType() {
        return transactionCurrencyType;
    }

    public void setTransactionCurrencyType(CurrencyType transactionCurrencyType) {
        this.transactionCurrencyType = transactionCurrencyType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", counted=" + counted +
                ", balanceId=" + (balance == null ? null : balance.getId()) +
                ", transactionType=" + transactionType +
                ", transactionCurrencyType=" + transactionCurrencyType +
                ", deleted=" + deleted +
                '}';
    }
}
