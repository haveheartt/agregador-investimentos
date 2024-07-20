package tech.gui.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gui.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import tech.gui.agregadorinvestimentos.controller.dto.CreateAccountDTO;
import tech.gui.agregadorinvestimentos.service.AccountService;

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
}
