package dev.apanufnik.featureflagservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

}
