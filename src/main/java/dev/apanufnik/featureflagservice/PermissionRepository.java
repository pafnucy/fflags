package dev.apanufnik.featureflagservice;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


interface PermissionRepository extends MongoRepository<Permission, String> {

    Optional<Permission> findByUsername(String username);

}
