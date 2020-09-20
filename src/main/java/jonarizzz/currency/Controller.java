package jonarizzz.currency;

import jonarizzz.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/")
    public String healthCheck(){
        return "Hello there, general Kenobi!";
    }

    @GetMapping("/test")
    public void hui (){
        currencyService.updateCurrentNationalRates();
    }



}
