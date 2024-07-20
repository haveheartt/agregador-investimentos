package tech.gui.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gui.agregadorinvestimentos.controller.dto.CreateStockDTO;
import tech.gui.agregadorinvestimentos.controller.dto.CreateUserDTO;
import tech.gui.agregadorinvestimentos.entity.User;
import tech.gui.agregadorinvestimentos.service.StockService;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO){

        stockService.createStock(createStockDTO);

        return ResponseEntity.ok().build();
    }

}
