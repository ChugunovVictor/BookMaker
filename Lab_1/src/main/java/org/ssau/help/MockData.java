package org.ssau.help;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssau.model.Hotel;
import org.ssau.model.User;
import org.ssau.repository.HotelRepository;
import org.ssau.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class MockData {
    private final DataSource ds;
    private final HotelRepository hr;
    private final UserRepository ur;

    @Autowired
    public MockData(DataSource dataSource, HotelRepository hotelRepository, UserRepository userRepository) {
        this.ds = dataSource;
        this.hr = hotelRepository;
        this.ur = userRepository;
    }

    @PostConstruct
    public void initData(){
        // Password encryption? Whatever!
        this.ur.save(new User("Administrator", "123", true));
        this.ur.save(new User("Common", "123", false));

        this.hr.save(new Hotel("Zeus", 3));
        this.hr.save(new Hotel("Iceberg", 2));
        this.hr.save(new Hotel("GreenLine", 5));
        this.hr.save(new Hotel("Count Orlov", 4));
    }

}


