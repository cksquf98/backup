package com.carstay.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSMSReq {
    public PostSMSReq() {}
    private String phoneNumber;
}
