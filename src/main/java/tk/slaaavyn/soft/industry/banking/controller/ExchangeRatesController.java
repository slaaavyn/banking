package tk.slaaavyn.soft.industry.banking.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.slaaavyn.soft.industry.banking.config.EndpointConstants;
import tk.slaaavyn.soft.industry.banking.model.CurrencyType;
import tk.slaaavyn.soft.industry.banking.model.ExchangeRate;
import tk.slaaavyn.soft.industry.banking.service.ExchangeService;

import java.util.List;

@RestController
@RequestMapping(value = EndpointConstants.EXCHANGE_RATES_ENDPOINT)
public class ExchangeRatesController {

    private final ExchangeService exchangeService;

    public ExchangeRatesController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("{currencyType}")
    @ApiOperation(value = "Get exchange rate by currency type. Action as role: PERMIT ALL")
    protected ResponseEntity<ExchangeRate> getAllRates(@PathVariable CurrencyType currencyType) {
        return ResponseEntity.ok(exchangeService.getExchangeRate(currencyType));
    }

    @GetMapping
    @ApiOperation(value = "Get all exchange rates. Action as role: PERMIT ALL")
    protected ResponseEntity<List<ExchangeRate>> getAllRates() {
        return ResponseEntity.ok(exchangeService.getAllRates());
    }
}
