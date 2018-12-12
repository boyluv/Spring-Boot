package com.example.greeting.MyObject;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends MongoRepository<Phone,Long> {
    @Override
    Optional<Phone> findById(Long s);


}
