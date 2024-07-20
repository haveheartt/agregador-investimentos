package tech.gui.agregadorinvestimentos.service;

import org.springframework.stereotype.Service;
import tech.gui.agregadorinvestimentos.controller.dto.CreateStockDTO;
import tech.gui.agregadorinvestimentos.entity.Stock;
import tech.gui.agregadorinvestimentos.repository.StockRepository;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
