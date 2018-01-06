package com.netpan.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netpan.entity.User;
import com.netpan.service.FollowService;
import com.netpan.util.Constants;
import com.netpan.util.ResponseUtil;

import net.sf.json.JSONObject;

@Controller
public class FollowController {
	@Resource
	private FollowService followService;
	
	/**
	 * 获得关注人姓名与ID列表同时搜索用户
	 * @param httpSession
	 * @param searchName
	 * @return
	 */
	@RequestMapping("/followList.do")
	public ModelAndView followList(HttpSession httpSession, 
			@RequestParam(value="name", defaultValue="") String searchName) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		List<String> followList = followService.getFollowUser(user);
		List<String> userList = followService.searchUser(user, searchName);
		ModelAndView modelAndView = new ModelAndView("/cloud/follow");
		modelAndView.addObject("followList", followList);
		modelAndView.addObject("userList", userList);
		modelAndView.addObject("searchName", searchName);
		return modelAndView;
	}
	
	/**
	 * 关注某个用户
	 * @param httpSession
	 * @param response
	 * @param followName
	 * @return
	 */
	@RequestMapping("/followUser.do")
	public ModelAndView followUser(HttpSession httpSession, HttpServletResponse response, 
			@RequestParam(value="name") String followName) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		JSONObject result = new JSONObject();
		try {
			followService.addFollowUser(user, followName);
			result.put("errres", true);
			result.put("errmsg", "关注成功！");
		} catch (Exception e) {
			result.put("errres", false);
			result.put("errmsg", "关注失败！");
			e.printStackTrace();
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 取消关注
	 * @param httpSession
	 * @param response
	 * @param unfollowName
	 * @return
	 */
	@RequestMapping("/unfollowUser.do")
	public ModelAndView unfollowUser(HttpSession httpSession, HttpServletResponse response, 
			@RequestParam(value="name") String unfollowName ) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		JSONObject result = new JSONObject();
		try {
			followService.unFollowUser(user, unfollowName);
			result.put("errres", true);
			result.put("errmsg", "取消关注成功！");
		} catch (Exception e) {
			result.put("errres", false);
			result.put("errmsg", "取消关注失败！");
			e.printStackTrace();
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 分享中获得所关注的用户
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/getFollowUser.do")
	public ModelAndView getFollowUser(HttpSession httpSession, 
			@RequestParam(value="ids") String ids) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		List<String> followList = followService.getFollowUser(user);
		ModelAndView modelAndView = new ModelAndView("/cloud/share_getfollow");
		modelAndView.addObject("followList", followList);
		modelAndView.addObject("ids", ids);
		return modelAndView;
	}
	
}
