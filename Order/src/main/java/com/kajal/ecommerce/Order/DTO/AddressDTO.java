package com.kajal.ecommerce.Order.DTO;

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
