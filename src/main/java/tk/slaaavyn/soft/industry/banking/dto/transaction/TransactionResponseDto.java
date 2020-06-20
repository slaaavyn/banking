package tk.slaaavyn.soft.industry.banking.dto.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.Transaction;
import tk.slaaavyn.soft.industry.banking.model.TransactionType;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponseDto {
    private Long id;
    private Date date;
    private Long balanceId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal counted;
    private CurrencyType transactionCurrencyType;
    private CurrencyType balanceCurrencyType;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
    public static TransactionResponseDto toDto(Transaction transaction) {
        TransactionResponseDto dto = new TransactionResponseDto();

        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setBalanceId(transaction.getBalance().getId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setCounted(transaction.getCounted());
        dto.setTransactionCurrencyType(transaction.getTransactionCurrencyType());
        dto.setBalanceCurrencyType(transaction.getBalance().getCurrencyType());

        return dto;
    }

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

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public CurrencyType getTransactionCurrencyType() {
        return transactionCurrencyType;
    }

    public void setTransactionCurrencyType(CurrencyType transactionCurrencyType) {
        this.transactionCurrencyType = transactionCurrencyType;
    }

    public CurrencyType getBalanceCurrencyType() {
        return balanceCurrencyType;
    }

    public void setBalanceCurrencyType(CurrencyType balanceCurrencyType) {
        this.balanceCurrencyType = balanceCurrencyType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "TransactionResponseDto{" +
                "id=" + id +
                ", date=" + date +
                ", balanceId=" + balanceId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", counted=" + counted +
                ", transactionCurrencyType=" + transactionCurrencyType +
                ", balanceCurrencyType=" + balanceCurrencyType +
                ", deleted=" + deleted +
                '}';
    }
}
