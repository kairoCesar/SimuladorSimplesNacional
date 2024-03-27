package com.github.kairocesar.simuladorsimplesnacional.repository;


import com.github.kairocesar.simuladorsimplesnacional.model.date.AnnexDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<AnnexDate, Long> {
}
