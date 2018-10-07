package org.ssau.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.ssau.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository <Hotel, Long> {
}
