package tk.slaaavyn.soft.industry.banking.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.soft.industry.banking.config.EndpointConstants;
import tk.slaaavyn.soft.industry.banking.dto.transaction.TransactionCreateRequestDto;
import tk.slaaavyn.soft.industry.banking.dto.transaction.TransactionResponseDto;
import tk.slaaavyn.soft.industry.banking.model.Transaction;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtUser;
import tk.slaaavyn.soft.industry.banking.service.TransactionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = EndpointConstants.TRANSACTION_ENDPOINT)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "Make deposit to balance. " +
            "If the transferred currency is different from the balance currency, then conversion takes place. " +
            "Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<TransactionResponseDto> makeDeposit(
            @Valid @RequestBody TransactionCreateRequestDto requestDto) {

        Transaction result = transactionService.makeDeposit(
                requestDto.getBalanceId(),
                requestDto.getCurrencyType(),
                requestDto.getAmount());

        return ResponseEntity.ok(TransactionResponseDto.toDto(result));
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "Make withdraw from balance. " +
            "If the withdrawal currency is different from the balance currency, then conversion takes place." +
            "Action as role: ADMIN")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<TransactionResponseDto> makeWithdraw(
            @Valid @RequestBody TransactionCreateRequestDto requestDto) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Transaction result = transactionService.makeWithdraw(
                jwtUser.getId(),
                requestDto.getBalanceId(),
                requestDto.getCurrencyType(),
                requestDto.getAmount());

        return ResponseEntity.ok(TransactionResponseDto.toDto(result));
    }

    @GetMapping
    @ApiOperation(value = "Getting all transactions for user who sent the request. " +
            "If pass balanceId, we receive transactions only for the requested balance. " +
            "All information only about user who sent the request" +
            "Action as role: USER")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<List<TransactionResponseDto>> getByBalanceId(
            @RequestParam(required = false, defaultValue = "0") Long balanceId) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        List<Transaction> transactionList = transactionService.getTransactions(jwtUser.getId(), balanceId);
        List<TransactionResponseDto> result = transactionList.stream()
                .map(TransactionResponseDto::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
