package com.example.forum.services;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakService {


   // private final Keycloak keycloak;

    public KeycloakService() {
       /* this.keycloak = Keycloak.getInstance(
                "http://keycloak:8180/auth",
                "MicroProject",
                "admin-cli",
                "admin",
                "admin",
                "n9rqiY0LIIm10jYWCnWXXqldaDWlGvud"
        );*/
    }
  /* public KeycloakService() {
       this.keycloak = Keycloak.getInstance(
                       "http://keycloak:8180/auth",
               .realm("MicroProject")
               .clientId("admin-cli")
               .clientSecret("n9rqiY0LIIm10jYWCnWXXqldaDWlGvud")
               .username("admin") // Assurez-vous qu'il est admin
               .password("admin")
               .build(); );
   }*/


}
