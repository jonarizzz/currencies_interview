package jonarizzz.currency.service;

import jonarizzz.currency.entities.Bank;
import jonarizzz.currency.entities.CurrencyRate;
import jonarizzz.currency.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {

    private final static String NATIONAL_BANK_RATES_URL = "https://www.nbrb.by/api/exrates/rates/";
    private final static String USD_CODE = "145";
    private final static String EUR_CODE = "292";
    private final static String RUB_CODE = "298";
    private final static String[] CURRENCY_CODES = {USD_CODE, EUR_CODE, RUB_CODE};
    private final static String MTB_BANK_RATES_URL = "https://www.mtbank.by/currxml.php";
    private final static String USD_NAME = "USD";
    private final static String EUR_NAME = "EUR";
    private final static String RUB_NAME = "RUB";
    private final static String BYN_NAME = "BYN";



    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public void updateCurrentNationalRates() {
        if (currencyRateRepository.findByDay(LocalDate.now()).isEmpty() ||
                currencyRateRepository.findByDayAndBank(LocalDate.now(), Bank.NATIONAL).isEmpty()){
            RestTemplate template = new RestTemplate();
            Arrays.stream(CURRENCY_CODES).forEach(currencyCode -> {
                CurrencyRate rate = template.getForObject(NATIONAL_BANK_RATES_URL + currencyCode, CurrencyRate.class);
                rate.setBank(Bank.NATIONAL);
                rate.setDay(LocalDate.now());
                currencyRateRepository.save(rate);
            });
        }
    }

    public void updateCurrentMtbRates() throws ParserConfigurationException, IOException, SAXException {
        if (currencyRateRepository.findByDay(LocalDate.now()).isEmpty() ||
                currencyRateRepository.findByDayAndBank(LocalDate.now(), Bank.MTB).isEmpty()) {
            List<CurrencyRate> currencyRateList = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(MTB_BANK_RATES_URL);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String code = elem.getElementsByTagName("code")
                            .item(0).getChildNodes().item(0).getNodeValue();
                    String codeTo = elem.getElementsByTagName("codeTo")
                            .item(0).getChildNodes().item(0).getNodeValue();
                    double rate = Double.parseDouble(elem.getElementsByTagName("purchase")
                            .item(0).getChildNodes().item(0).getNodeValue());
                    if (code.equals(BYN_NAME)){
                        CurrencyRate currencyRate = new CurrencyRate();
                        currencyRate.setDay(LocalDate.now());
                        currencyRate.setBank(Bank.MTB);
                        if (codeTo.equals(USD_NAME)){
                            currencyRate.setCurrencyName(USD_NAME);
                            currencyRate.setCurrencyRate(rate);
                            currencyRateList.add(currencyRate);
                        }
                        if (codeTo.equals(EUR_NAME)){
                            currencyRate.setCurrencyName(EUR_NAME);
                            currencyRate.setCurrencyRate(rate);
                            currencyRateList.add(currencyRate);
                        }
                        if (codeTo.equals(RUB_NAME)){
                            currencyRate.setCurrencyName(RUB_NAME);
                            currencyRate.setCurrencyRate(rate);
                            currencyRateList.add(currencyRate);
                        }
                    }
                }
            }
            if (!currencyRateList.isEmpty()){
                currencyRateRepository.saveAll(currencyRateList);
            }
        }
    }

}
