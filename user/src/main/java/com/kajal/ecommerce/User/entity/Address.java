package com.kajal.ecommerce.User.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
//@Entity
//@Table(name="address")

@Document(collection = "address")
public class Address
{
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String city;
    private String state;
    private String street;
    private String country;
    private String zipcode;


}
