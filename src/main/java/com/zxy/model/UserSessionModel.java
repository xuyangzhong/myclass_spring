package com.zxy.model;

import lombok.Data;
import java.util.Date;

@Data
public class UserSessionModel {
    private String pk;
    private String username;
    private String loginTime;
}
