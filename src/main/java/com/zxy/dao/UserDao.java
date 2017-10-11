package com.zxy.dao;

import com.zxy.model.CheckUserResultDO;
import com.zxy.model.UserLoginModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public CheckUserResultDO checkUserLogin(UserLoginModel userLoginModel){
        CheckUserResultDO resultDO = new CheckUserResultDO();
        resultDO.setSuccess(true);
        resultDO.setCode(200);
        userLoginModel.setUsername("张三");
        resultDO.setValue(userLoginModel);
        return resultDO;
    }

}
