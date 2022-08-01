package com.eongiin.dividends.persist;


import com.eongiin.dividends.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity,Long> {
}
