package com.eongiin.dividends.scraper;

import com.eongiin.dividends.model.Company;
import com.eongiin.dividends.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
