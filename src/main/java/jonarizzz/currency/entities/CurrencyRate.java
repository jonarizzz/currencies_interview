package jonarizzz.currency.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate {
    @Id
    String id;
    Bank bank;
    LocalDate day;
    @JsonProperty("Cur_Abbreviation")
    String currencyName;
    @JsonProperty("Cur_OfficialRate")
    double currencyRate;
    @JsonProperty("Cur_Scale")
    int scale;
}
