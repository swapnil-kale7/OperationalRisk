package com.OperationalRisk.demo.Repository;

import com.OperationalRisk.demo.Entity.KRI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface KriRepo extends JpaRepository<KRI,Long> {

    List<KRI> findByRiskTypeAndDateRecordedBetween(String riskType, Date startDate, Date endDate);
}
