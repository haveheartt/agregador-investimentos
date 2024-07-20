package tech.gui.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gui.agregadorinvestimentos.controller.dto.AccountStockResponseDTO;
import tech.gui.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import tech.gui.agregadorinvestimentos.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                              @RequestBody AssociateAccountStockDTO associateAccountStockDTO) {
        accountService.associateStock(accountId, associateAccountStockDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listStocks(@PathVariable("accountId") String accountId) {
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
