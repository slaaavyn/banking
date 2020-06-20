package tk.slaaavyn.soft.industry.banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.soft.industry.banking.config.EndpointConstants;
import tk.slaaavyn.soft.industry.banking.dto.balance.BalanceResponseDto;
import tk.slaaavyn.soft.industry.banking.exceptions.ApiRequestException;
import tk.slaaavyn.soft.industry.banking.model.Balance;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtUser;
import tk.slaaavyn.soft.industry.banking.service.BalanceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = EndpointConstants.BALANCE_ENDPOINT)
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/create")
    protected ResponseEntity<BalanceResponseDto> create(@RequestParam long userId,
                                                        @RequestParam CurrencyType currencyType) {
        return ResponseEntity.ok(BalanceResponseDto.toDto(balanceService.create(userId, currencyType)));
    }

    @GetMapping("{id}")
    protected ResponseEntity<BalanceResponseDto> getBalance(@PathVariable(name = "id") long id) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Balance balance = balanceService.getById(id);

        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), Role.ROLE_USER.name())
                && !jwtUser.getId().equals(balance.getUser().getId())) {
            throw new ApiRequestException("user cannot get balance not belonging to his account");
        }

        return ResponseEntity.ok(BalanceResponseDto.toDto(balance));
    }

    @GetMapping
    protected ResponseEntity<List<BalanceResponseDto>> getAllBalancesByCustomer(@RequestParam long userId) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), Role.ROLE_USER.name())
                && jwtUser.getId() != userId) {
            throw new ApiRequestException("user cannot get balance not belonging to his account");
        }

        List<Balance> balanceList = balanceService.getALlCustomerBalances(userId);

        List<BalanceResponseDto> result = balanceList
                .stream()
                .map(BalanceResponseDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    protected ResponseEntity<Object> deleteBalance(@PathVariable(name = "id") long id) {
        balanceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
