package com.eongiin.dividends;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DividendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendsApplication.class, args);

        Connection connect = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history?period1=99100800&period2=1659312000&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true");
        try {
            Document document = connect.get();

            Elements eles = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element table = eles.get(0);
            Element tbody = table.children().get(1);

            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = txt.split(" ");
                String month = splits[0];
                int day = Integer.parseInt(splits[1].replace(",", ""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
