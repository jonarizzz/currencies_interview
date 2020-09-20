package jonarizzz.currency.service;

import jonarizzz.currency.entities.Bank;
import jonarizzz.currency.entities.CurrencyRate;
import jonarizzz.currency.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class CurrencyService {

    private final static String NATIONAL_BANK_RATES_URL = "https://www.nbrb.by/api/exrates/rates/";
    private final static String USD_CODE = "145";
    private final static String EUR_CODE = "292";
    private final static String RUB_CODE = "298";
    private final static String [] CURRENCY_CODES = {USD_CODE, EUR_CODE, RUB_CODE};

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public void updateCurrentNationalRates(){
        if (currencyRateRepository.findByDay(LocalDate.now()).isEmpty()){
            RestTemplate template = new RestTemplate();
            Arrays.stream(CURRENCY_CODES).forEach(currencyCode -> {
                CurrencyRate rate = template.getForObject(NATIONAL_BANK_RATES_URL+currencyCode, CurrencyRate.class);
                rate.setBank(Bank.NATIONAL);
                rate.setDay(LocalDate.now());
                currencyRateRepository.save(rate);
            });
        }
    }

    public void update

}
