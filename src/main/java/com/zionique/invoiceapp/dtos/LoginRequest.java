package com.zionique.invoiceapp.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String mobile;
    private String password;

}
