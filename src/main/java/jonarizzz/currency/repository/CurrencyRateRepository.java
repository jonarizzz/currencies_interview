package jonarizzz.currency.repository;

import jonarizzz.currency.entities.CurrencyRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRateRepository extends MongoRepository<CurrencyRate, String> {
    public List<CurrencyRate> findByDay(LocalDate day);
}
