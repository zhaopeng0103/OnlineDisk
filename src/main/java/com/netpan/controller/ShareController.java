package com.netpan.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netpan.entity.File;
import com.netpan.entity.Share;
import com.netpan.entity.User;
import com.netpan.service.FileService;
import com.netpan.service.ShareService;
import com.netpan.util.Constants;
import com.netpan.util.DateUtil;
import com.netpan.util.ResponseUtil;

import net.sf.json.JSONObject;

@Controller
public class ShareController {
	@Resource
	private ShareService shareService;
	
	@Resource
	private FileService fileService;
	
	/**
	 * 我的分享
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/myShareList.do")
	public ModelAndView myShareList(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		List<Share> shareList = shareService.getMyShareList(user);
		ModelAndView modelAndView = new ModelAndView("/cloud/share");
		modelAndView.addObject("shareList", shareList);
		return modelAndView;
	}
	
	/**
	 * 收到分享
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/getShareList.do")
	public ModelAndView getShareList(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		List<Share> shareedList = shareService.getShareedList(user);
		ModelAndView modelAndView = new ModelAndView("/cloud/shareed");
		modelAndView.addObject("shareedList", shareedList);
		return modelAndView;
	}
	
	/**
	 * 分享文件
	 * @param httpSession
	 * @param response
	 * @param ids
	 * @param name
	 * @return
	 */
	@RequestMapping("/shareFile.do")
	public ModelAndView shareFile(HttpSession httpSession, HttpServletResponse response, 
			@RequestParam(value="ids") String ids, 
			@RequestParam(value="names") String names) {
		JSONObject result = new JSONObject();
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		try {
			String[] id = ids.split(",");
			String[] name = names.split(",");
			for (int i = 0; i < id.length; i++) {
				File file = fileService.getFileInfoById(Long.parseLong(id[i]));
				Share share = new Share();
				share.setPath(file.getPath());
				share.setOriginalPath(file.getOriginalPath());
				share.setType(file.getType());
				share.setName(file.getOriginalName());
				share.setSharetime(DateUtil.DateToString("yyyy-MM-dd HH:mm:ss", new Date()));
				for(int j = 0; j < name.length; j++) {
					shareService.addShare(user, share, name[j]);
				}
			}
			result.put("errres", true);
			result.put("errmsg", "分享成功！");
		} catch (Exception e) {
			result.put("errres", false);
			result.put("errmsg", "分享失败！");
			e.printStackTrace();
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除我的分享
	 * @param httpSession
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteShare.do")
	public ModelAndView deleteShare(HttpSession httpSession, HttpServletResponse response, 
			@RequestParam(value="ids") String ids) {
		JSONObject result = new JSONObject();
		User user = (User) httpSession.getAttribute(Constants.currentUserSessionKey);
		try {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				shareService.deleteMyShareList(user, Long.parseLong(id[i]));
			}
			result.put("errres", true);
			result.put("errmsg", "删除分享成功！");
		} catch (Exception e) {
			result.put("errres", false);
			result.put("errmsg", "删除分享失败！");
			e.printStackTrace();
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
}
