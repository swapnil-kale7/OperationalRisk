package com.OperationalRisk.demo.Repository;

import com.OperationalRisk.demo.Entity.userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<userr,Integer> {

    userr findByUsername(String username);

}
