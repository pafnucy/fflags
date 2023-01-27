package dev.apanufnik.featureflagservice;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestData {


    public static String userToken() {
        return "Bearer: user-1,read-history,add-products";  // TODO use properly encoded token
    }

}
