package com.OperationalRisk.demo.Repository;


import com.OperationalRisk.demo.Entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRepo extends JpaRepository<Risk, Long> {


    List<Risk> findBySeverity(String severity);

    List<Risk> findByLocation(String location);

    List<Risk> findByStatus(String status);

    public interface RiskRepository extends JpaRepository<Risk, Long> {
//        List<Risk> findBySeverity(String severity);

//        @Query(value = "select * from  risk where severity= %:severity%"
//        ,nativeQuery = true)
//        List<Risk> findBySeverity(@Param("severity") String severity);

//        List<Risk> findByLocation(String location);
//        List<Risk> findByStatus(String status);
    }


}

