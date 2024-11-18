package com.OperationalRisk.demo.Service;


import com.OperationalRisk.demo.Entity.KRI;
import com.OperationalRisk.demo.Repository.KriRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KriService {


    @Autowired
    private KriRepo kriRepository;

    public KRI saveKRI(KRI kri) {
        return kriRepository.save(kri);
    }

    public List<KRI> getAllKris() {
        return kriRepository.findAll();
    }

    public List<KRI> getKRIsByRiskTypeAndDateRange(String riskType, Date startDate, Date endDate) {
        return kriRepository.findByRiskTypeAndDateRecordedBetween(riskType, startDate, endDate);
    }

    public double calculateAggregateKriScore(List<KRI> kris) {
        double totalScore = 0.0;
        double totalWeight = 0.0;
        for (KRI kri : kris) {
            totalScore += kri.getValue() * kri.getWeight();
            totalWeight += kri.getWeight();
        }
        return totalWeight == 0 ? 0 : totalScore / totalWeight;
    }
}




