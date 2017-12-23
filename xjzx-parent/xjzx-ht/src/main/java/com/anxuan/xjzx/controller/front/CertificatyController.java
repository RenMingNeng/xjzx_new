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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.SafetymanageCard;
import com.anxuan.xjzx.bean.Specialic;
import com.anxuan.xjzx.service.SafetymanageCardService;
import com.anxuan.xjzx.service.SpecialicService;

@Controller
public class CertificatyController {
	@Resource
	private SafetymanageCardService safetymanageCardService;
	@Resource
	private SpecialicService specialicService;


	
	/**
	 * 特种作业证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/certificate/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/query_certificate", "USER", request, response);
		return mav;
	}
	
	/**
	 * 其他作业证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/certificate/index2")
	public ModelAndView index2(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/query_certificate2", "USER", request, response);
		return mav;
	}

	// 证件查询
	@RequestMapping("/certificate/query")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response, String cardNumber,
			String types) {
		JModelAndView mav = new JModelAndView("/jieguo_certificate", "USER", request, response);
		mav.addObject("cardNumber", cardNumber);
		if ("safetymanageCard".equals(types)) {
			mav.addObject("safetymanageCards", safetymanageCardService.getSafetymanageCardByIdcard(cardNumber, 2015));
		} else if ("safetymanageCardNew".equals(types)) {
			// 安全生产新证查询
			cardNumber = cardNumber.toLowerCase();
			mav.addObject("safetymanageCardNews",
					safetymanageCardService.getSafetymanageCardByIdcard(cardNumber, 2016));
		} else if ("spelialic".equals(types)) {
			/*// 容错：特种的查证书要改成 （T+身份证）or 身份证 都可以查询
			if(null!=cardNumber && !"".equals(cardNumber)){
				if(cardNumber.startsWith("T", 0) || cardNumber.startsWith("t", 0)){
					cardNumber = cardNumber.substring(1);
				}
			}*/
			// 特种作业查证
			List<Specialic> specialicList = specialicService.findSpecialicByCardnumber(cardNumber);
			for (Specialic spec : specialicList) {
				spec.setReviewid("未复审");
				if ((spec.getTreviewrecord1() != null && !"".equals(spec.getTreviewrecord1()))
						|| (spec.getTreviewrecord2() != null && !"".equals(spec.getTreviewrecord2()))) {
					spec.setReviewid("已复审");
				}
			}
			mav.addObject("specialicList", specialicList);
		}
		mav.addObject("types", types);
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
}
