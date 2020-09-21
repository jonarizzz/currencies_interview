package jonarizzz.currency.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CurrencyData {
    private Bank bank;
    private LocalDate date;
    private List<Exchange> exchangeRates;
    private List<Pair<String, Double>> currencyRates;
    private Format format;
}
