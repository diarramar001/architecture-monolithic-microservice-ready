package org.nom_entreprise.borrow_infrastructure.database.repository;

import org.nom_entreprise.borrow_infrastructure.database.model.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookDao, String> {
    Optional<BookDao> findByInventoryNumberAndStatus(String inventoryNumber, BookDao.BookStatus status);
    Optional<BookDao> findByInventoryNumber(String inventoryNumber);
}
