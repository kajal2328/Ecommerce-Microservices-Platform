package com.kajal.ecommerce.User.DTO;

import lombok.Data;

@Data
public class AddressDTO
{
    private String city;
    private String state;
    private String street;
    private String country;
    private String zipcode;
}
