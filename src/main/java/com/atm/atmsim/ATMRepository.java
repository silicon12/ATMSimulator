package com.atm.atmsim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ATMRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();

    Optional<Account> findByAccountNumber(Long aLong);



}
