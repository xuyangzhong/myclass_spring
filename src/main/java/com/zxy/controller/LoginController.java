package com.zxy.controller;

import com.zxy.model.CheckUserResultDO;
import com.zxy.dao.UserDao;
import com.zxy.model.UserLoginModel;
import com.zxy.model.UserSessionModel;
import lombok.Setter;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/userlogin")
public class LoginController {
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Setter
    private UserDao userDao;

    private static final String PARAM_NULL_ERROR = "null_error";

    private static final String LOGIN_FAIL = "user_error";

    private static final String LOGIN_SUCCESS = "success";

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("city", "test");
        mav.setViewName("hello");
        return mav;
    }

    @RequestMapping(value = "/checklogin")
    @ResponseBody
    public String checklogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = request.getParameter("username");
        String password = request.getParameter("password");
        log.info(String.format("username : %s , password : %s", pk, password));
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(pk) || StringUtils.isBlank(password)) {
            map.put("result", PARAM_NULL_ERROR);
            log.info("pk is null");
            return PARAM_NULL_ERROR;
        }
        UserLoginModel userLoginRequestModel = new UserLoginModel();
        userLoginRequestModel.setPk(pk);
        userLoginRequestModel.setPassword(password);
        CheckUserResultDO checkUserResultDO = userDao.checkUserLogin(userLoginRequestModel);
        if (!checkUserResultDO.isSuccess() || checkUserResultDO == null) {
            map.put("result", LOGIN_FAIL);
            log.info("username or passsword error");
            return LOGIN_FAIL;
        }
        UserLoginModel userLoginResultModel = (UserLoginModel) checkUserResultDO.getValue();
        UserSessionModel userSessionModel = new UserSessionModel();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
        String dateStr = sdf.format(date);
        log.info(String.format("the user %s login in %s", pk, dateStr));
        userSessionModel.setPk(userLoginResultModel.getPk());
        userSessionModel.setUsername(userLoginResultModel.getUsername());
        userSessionModel.setLoginTime(dateStr);
        request.getSession().setAttribute("userSession", userSessionModel);
        map.put("result", LOGIN_SUCCESS);
        return LOGIN_SUCCESS;
    }

    @RequestMapping(value = "/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getSession().removeAttribute("userSession");
        response.sendRedirect("/userlogin/index.html");
    }

}
