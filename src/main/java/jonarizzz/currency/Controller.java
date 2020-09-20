package jonarizzz.currency;

import jonarizzz.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
public class Controller {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/")
    public String healthCheck(){
        return "Hello there, general Kenobi!";
    }

    @GetMapping("/test")
    public void hui () throws IOException, SAXException, ParserConfigurationException {
        currencyService.updateCurrentMtbRates();
    }



}
