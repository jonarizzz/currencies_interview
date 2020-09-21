package jonarizzz.currency.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exchange {
    private String firstCurrency;
    private String secondCurrency;
    private double rate;
}
