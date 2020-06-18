package tk.slaaavyn.soft.industry.banking.model;

import javax.persistence.*;

@Entity
@Table(name = "balance")
public class Balance {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deposit", nullable = false)
    private Long deposit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", deposit=" + deposit +
                ", customer=" + customer +
                ", currencyType=" + currencyType +
                '}';
    }
}
