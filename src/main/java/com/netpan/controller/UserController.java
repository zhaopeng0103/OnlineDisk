package com.netpan.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netpan.entity.User;
import com.netpan.service.UserService;
import com.netpan.util.Constants;
import com.netpan.util.ResponseUtil;
import net.sf.json.JSONObject;

@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/login.do")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	/**
	 * 首页面
	 * @return
	 */
	@RequestMapping("/index.do")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute(Constants.currentUserSessionKey);
		return new ModelAndView("redirect:/login.do");
	}
	
	/**
	 * 注册用户操作
	 * @param user
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/regUserAction.do")
	public ModelAndView register(User user, HttpServletResponse response) throws IOException{
		JSONObject result = new JSONObject();
		if (!userService.checkEmail(user.getEmail())) {
			result.put("errres", false);
			result.put("errmsg", "邮箱已经存在！");
		} else if (!userService.checkName(user.getName())) {
			result.put("errres", false);
			result.put("errmsg", "姓名已经存在！");
		}else {
			userService.addUser(user);
			result.put("errres", true);
			result.put("errmsg", "注册成功！");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/loginUserAction.do")
	public ModelAndView gologin(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		int res = userService.login(user);
		if (res == 1) {
			HttpSession session = request.getSession();
			System.out.println(user.toString());
			session.setAttribute(Constants.currentUserSessionKey, user);
			result.put("errres", true);
			result.put("errmsg", "登陆成功！");
		}else if(res == 0){
			result.put("errres", false);
			result.put("errmsg", "密码不正确！");
		}else if(res == -1){
			result.put("errres", false);
			result.put("errmsg", "用户名不正确！");
		}else {
			result.put("errres", false);
			result.put("errmsg", "登录失败！");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	
}
