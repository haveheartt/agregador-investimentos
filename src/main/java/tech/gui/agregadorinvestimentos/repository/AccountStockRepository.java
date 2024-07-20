package tech.gui.agregadorinvestimentos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.gui.agregadorinvestimentos.entity.AccountStock;
import tech.gui.agregadorinvestimentos.entity.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
