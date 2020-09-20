package jonarizzz.currency;

import jonarizzz.currency.entities.CurrencyData;
import jonarizzz.currency.entities.Format;
import jonarizzz.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/")
    public String healthCheck(){
        return "Hello there, general Kenobi!";
    }

    @GetMapping("/test")
    public void test () throws IOException, SAXException, ParserConfigurationException {
        currencyService.updateCurrentNationalRates();
        currencyService.updateCurrentMtbRates();
    }

    @PostMapping("/rate")
    public CurrencyData getRate(@RequestParam LocalDate date,
                                @RequestParam(required = false, value = "JSON") Format format,
                                @RequestParam(name = "currency") List<String> currencies,
                                @RequestParam(name = "exchange") List<String> exchanges){
        return currencyService.getRate(date, format, currencies, exchanges);
    }



}
