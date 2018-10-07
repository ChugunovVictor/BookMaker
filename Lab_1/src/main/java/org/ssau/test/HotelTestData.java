package org.ssau.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssau.model.Hotel;
import org.ssau.repository.HotelRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class HotelTestData {
    private final DataSource ds;
    private final HotelRepository hr;

    @Autowired
    public HotelTestData(DataSource dataSource, HotelRepository hotelRepository) {
        this.ds = dataSource;
        this.hr = hotelRepository;
    }

    @PostConstruct
    public void initTestData(){
        this.hr.save(new Hotel("Zeus", 3));
        this.hr.save(new Hotel("Iceberg", 2));
        this.hr.save(new Hotel("GreenLine", 5));
        this.hr.save(new Hotel("Count Orlov", 4));
    }

}


