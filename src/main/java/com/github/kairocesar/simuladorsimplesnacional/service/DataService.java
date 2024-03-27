package com.github.kairocesar.simuladorsimplesnacional.service;

import com.github.kairocesar.simuladorsimplesnacional.model.date.AnnexDate;
import com.github.kairocesar.simuladorsimplesnacional.repository.DataRepository;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public AnnexDate save(Double rbt12, Double revenueValue, String date) {
        AnnexDate annexDate = new AnnexDate(rbt12, revenueValue, date);
        dataRepository.save(annexDate);
        return annexDate;
    }
}
