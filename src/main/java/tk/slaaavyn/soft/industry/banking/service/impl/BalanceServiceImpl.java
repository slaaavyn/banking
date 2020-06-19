package tk.slaaavyn.soft.industry.banking.service.impl;

import org.springframework.stereotype.Service;
import tk.slaaavyn.soft.industry.banking.exceptions.ApiRequestException;
import tk.slaaavyn.soft.industry.banking.exceptions.ConflictException;
import tk.slaaavyn.soft.industry.banking.exceptions.NotFoundException;
import tk.slaaavyn.soft.industry.banking.model.*;
import tk.slaaavyn.soft.industry.banking.repostitory.BalanceRepository;
import tk.slaaavyn.soft.industry.banking.service.BalanceService;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserService userService;

    public BalanceServiceImpl(BalanceRepository balanceRepository, UserService userService) {
        this.balanceRepository = balanceRepository;
        this.userService = userService;
    }

    @Override
    public Balance create(Long customerId, CurrencyType currencyType) {
        User user = userService.getUser(customerId);

        if(user.getRole() != Role.ROLE_USER) {
            throw new ApiRequestException("this user is not a customer");
        }

        if(balanceRepository.findByCustomer_IdAndCurrencyType(user.getId(), currencyType) != null) {
            throw new ConflictException("balance with currency type: " + currencyType + " already exist in this customer");
        }

        Balance balance = new Balance();
        balance.setCustomer((Customer) user);
        balance.setCurrencyType(currencyType);
        balance.setDeposit(0L);

        return balanceRepository.save(balance);
    }

    @Override
    public Balance getById(long balanceId) {
        Balance balance = balanceRepository.findById(balanceId);

        if (balance == null) {
            throw new NotFoundException("balance with id: " + balanceId + " not found");
        }

        return balance;
    }

    @Override
    public List<Balance> getALlCustomerBalances(long customerId) {
        return balanceRepository.findAllByCustomer_Id(customerId);
    }

    @Override
    public void delete(Long balanceId) {
        Balance balance = getById(balanceId);

        if (balance.getDeposit() != 0) {
            throw new ApiRequestException("balance with id: " + balanceId + " not empty");
        }

        balanceRepository.delete(balance);
    }
}
