package dev.apanufnik.featureflagservice.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("version")
public class VersionController {

    @GetMapping
    ResponseEntity<String> version() {
        return ResponseEntity.ok("1.0.0");  // TODO take from artifact version via BuildProperties
    }
}
