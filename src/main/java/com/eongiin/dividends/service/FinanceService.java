package com.eongiin.dividends.service;

import com.eongiin.dividends.model.Company;
import com.eongiin.dividends.model.Dividend;
import com.eongiin.dividends.model.ScrapedResult;
import com.eongiin.dividends.persist.CompanyRepository;
import com.eongiin.dividends.persist.DividendRepository;
import com.eongiin.dividends.persist.entity.CompanyEntity;
import com.eongiin.dividends.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {
        //1. 회사명을 기준으로 회사 정보 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다"));

        //2. 조회된 회사 ID로 배당금 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        //3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> Dividend.builder()
                        .date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker())
                .name(company.getTicker())
                .build(),
                dividends);
    }
}
