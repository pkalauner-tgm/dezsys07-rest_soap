package at.kalaunermalik.dezsys07;

import at.kalaunermalik.dezsys07.rest.RestApplication;
import at.kalaunermalik.dezsys07.soap.SoapApplication;
import org.springframework.boot.SpringApplication;

/**
 * Created by Paul on 08.12.2015.
 */
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{RestApplication.class, SoapApplication.class}, args);
    }
}
