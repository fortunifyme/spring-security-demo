package com.mkyong.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mkyong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
  @Autowired
  private AccountService service;

  @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
  public ModelAndView defaultPage() {
    ModelAndView model = new ModelAndView();
    model.addObject("title", "Spring Security Remember Me");
    model.addObject("message", "This is default page!");
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.getPrincipal().equals("anonymousUser")) {
      model.setViewName("login");
    } else {
      model.setViewName("redirect:" + "/admin/list-accounts");
    }
    return model;
  }

  @RequestMapping(value = "/admin/list-accounts", method = RequestMethod.GET)
  public String showAccounts(ModelMap model) {
    model.addAttribute("ACCOUNT_LIST", service.getAccountList());
    return "list-accounts";
  }

  @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
  public String searchPage() {
    return "search";
  }

  @RequestMapping(value = {"/admin/search-auth"}, method = RequestMethod.GET)
  public String searchPage1() {
    return "search-auth";
  }

  @RequestMapping(value = "/admin**", method = RequestMethod.GET)
  public ModelAndView adminPage() {
    ModelAndView model = new ModelAndView();
    model.addObject("title", "Spring Security Remember Me");
    model.addObject("message", "This page is for ROLE_ADMIN only!");
    model.setViewName("admin");
    return model;
  }

  /**
   * This update page is for user login with password only.
   * If user is logjn via remember me cookie, send login to ask for password again.
   * To avoid stolen remember me cookie to update anything
   */
  @RequestMapping(value = "/admin/update**", method = RequestMethod.GET)
  public ModelAndView updatePage(HttpServletRequest request) {
    ModelAndView model = new ModelAndView();
    if (isRememberMeAuthenticated()) {
      //send login for update
      setRememberMeTargetUrlToSession(request);
      model.addObject("loginUpdate", true);
      model.setViewName("/login");
    } else {
      model.setViewName("update");
    }
    return model;
  }

  /**
   * both "normal login" and "login for update" shared this form.
   */
  @RequestMapping(value = "/login**", method = RequestMethod.GET)
  public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Invalid username and password!");
      //login form for update, if login error, get the targetUrl from session again.
      String targetUrl = getRememberMeTargetUrlFromSession(request);
      System.out.println(targetUrl);
      if (StringUtils.hasText(targetUrl)) {
        model.addObject("targetUrl", targetUrl);
        model.addObject("loginUpdate", true);
      }
    }
    if (logout != null) {
      model.addObject("msg", "You've been logged out successfully.");
      // model.setViewName("login");
    }
    model.setViewName("login");
    return model;
  }

  /**
   * If the login in from remember me cookie, refer
   * org.springframework.security.authentication.AuthenticationTrustResolverImpl
   */
  private boolean isRememberMeAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null && RememberMeAuthenticationToken.
      class.isAssignableFrom(authentication.getClass());
  }

  /**
   * save targetURL in session
   */
  private void setRememberMeTargetUrlToSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.setAttribute("targetUrl", "/admin/update");
    }
  }

  /**
   * get targetURL from session
   */
  private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
    String targetUrl = "admin";
    HttpSession session = request.getSession(false);
    if (session != null) {
      targetUrl = session.getAttribute("targetUrl") == null
        ? "admin" : session.getAttribute("targetUrl").toString();
    }
    return targetUrl;
  }
}