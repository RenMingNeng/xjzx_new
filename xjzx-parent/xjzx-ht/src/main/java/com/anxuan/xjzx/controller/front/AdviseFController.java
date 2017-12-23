package com.anxuan.xjzx.controller.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Advise;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.service.AdviseService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.MessageUtf8;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class AdviseFController {
	@Resource
	private AdviseService adviseService;
 
	@RequestMapping("/advise/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		
		JModelAndView mav = new JModelAndView("/advise_index", "USER", request, response);

		return mav;
	}

	// ajax插入留言
	@RequestMapping("/advise/addadvise_ajax")
	public void addadvise_ajax(HttpServletRequest request, HttpServletResponse response,String adcontent,
			String title,String username,String phone,String email,String address) throws Exception {
		String result="";
		try {
			Advise adsingle=new Advise();
			adsingle.setTitle(title);
			adsingle.setAdcontent(adcontent);
			adsingle.setAddress(address);
			adsingle.setUsername(username);
			adsingle.setPhone(phone);
			adsingle.setEmail(email);
			adsingle.setCreatedate(new Date());
			adsingle.setIslook(0);
			adsingle.setState(1);
			adviseService.save(adsingle);
			result="1";
			MessageUtf8.writeMessageUft8(response,result );
		} catch (Exception e) {
			result="0";
			MessageUtf8.writeMessageUft8(response,result );
		}
	}
	
	// 建议插入
	@RequestMapping("/advise/addadvise")
	public ModelAndView addadvise(HttpServletRequest request, HttpServletResponse response,
			String adcontent,String username,String phone,String email,String address,String title) {
		JModelAndView mav = new JModelAndView("/advise_index", "USER", request, response);
		String result="";
		String msg="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Advise adsingle=new Advise();
			adsingle.setAdcontent(adcontent);
			adsingle.setTitle(title);
			adsingle.setAddress(address);
			adsingle.setUsername(username);
			adsingle.setPhone(phone);
			adsingle.setEmail(email);
			adsingle.setCreatedate(new Date());
			adsingle.setIslook(0);
			adsingle.setState(1);
			adviseService.save(adsingle);
			result="1";
			msg="留言提交成功!";
		} catch (Exception e) {
			result="0";
			msg="留言提交失败!请正确填写信息";
		} finally {
		
		}
		mav.addObject("adcontent", adcontent);
		mav.addObject("username", username);
		mav.addObject("phone", phone);
		mav.addObject("email", email);
		mav.addObject("address", address);
		mav.addObject("title", title);
		mav.addObject("result", result);
		mav.addObject("msg", msg);
		return mav;
	}

	private Date getStringToDate(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	  * 首页进入投诉反馈列表
	  * @param request
	  * @param response
	  * @param page
	  * @return
	  */
	 @RequestMapping("/advice/adviceList")
	 public ModelAndView adviseList(HttpServletRequest request,HttpServletResponse response,String textSeacher,String page){
		 JModelAndView mav = new JModelAndView("/advice_list", "USER", request, response);
		 Map params = new HashMap();
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<Advise> advisePage = new InformationPage<Advise>(15, curPage);
		 if(null == textSeacher) textSeacher="";
		 params.clear();
		 params.put("state", 1);
		 params.put("islook", 1);
		 params.put("textSeacher", "%"+textSeacher+"%");
		 advisePage.setCount_all(adviseService.find("from Advise bean where bean.state=:state and bean.islook=:islook and bean.title like:textSeacher order by bean.createdate desc", params, -1, -1).size());
		 advisePage.setRows(adviseService.find("from Advise bean where bean.state=:state and bean.islook=:islook and bean.title like:textSeacher order by bean.createdate desc", params, advisePage.getStartPoint(), advisePage.getPage_size()));
		 mav.addObject("advisePage", advisePage);
		 mav.addObject("textSeacher", textSeacher);
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 return mav;
	 }
	 
	 /**
	  * 投诉反馈详情
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  */
	 @RequestMapping("/advice/detail")
	  public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,int id){
		JModelAndView  mav = new JModelAndView("/advice_detail", "USER", request, response); 
		Advise advice = adviseService.getAdviseById(id);
		mav.addObject("advice", advice);
		return mav;
	  }
}
