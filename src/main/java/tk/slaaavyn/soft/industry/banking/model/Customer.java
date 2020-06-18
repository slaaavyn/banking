package tk.slaaavyn.soft.industry.banking.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Balance> balanceList;

    public Customer() {}

    public Customer(User user) {
        this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setRole(Role.ROLE_USER);
    }

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + super.getId() + ", " +
                "balanceList=" + balanceList +
                "} ";
    }
}
