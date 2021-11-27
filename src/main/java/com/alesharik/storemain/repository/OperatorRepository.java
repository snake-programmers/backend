package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OperatorRepository extends CrudRepository<Operator, Long> {
    Operator findByLogin(String login);

    Page<Operator> findAllBy(Pageable pageable);
}
