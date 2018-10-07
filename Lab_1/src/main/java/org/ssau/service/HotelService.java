package org.ssau.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ssau.model.Hotel;
import org.ssau.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping({"/hotels"})
public class HotelService {

    @Autowired
    private HotelRepository hotels;

    @PostMapping
    public Hotel create(@RequestBody Hotel h){
        Hotel _h = hotels.save(new Hotel(h.getName(), h.getStars()));
        return _h;
    }

    @GetMapping(path = {"/{id}"})
    public java.util.Optional<Hotel> findOne(@PathVariable("id") long id){
        return hotels.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> update(
            @PathVariable("id") long id, @RequestBody Hotel hotel) {
        Optional<Hotel> hData = hotels.findById(id);

        if (hData.isPresent()) {
            Hotel _h = hData.get();
            _h.setName(hotel.getName());
            _h.setStars(hotel.getStars());
            return new ResponseEntity<>(hotels.save(_h), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<String>  delete(@PathVariable("id") long id) {
        hotels.deleteById(id);
        return new ResponseEntity<>("Hotel has been deleted!", HttpStatus.OK);
    }

    @GetMapping
    public java.lang.Iterable findAll(){
        return hotels.findAll();
    }
}
