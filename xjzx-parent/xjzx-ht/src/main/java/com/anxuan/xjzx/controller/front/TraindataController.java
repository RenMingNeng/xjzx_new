package com.anxuan.xjzx.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Traindata;
import com.anxuan.xjzx.service.TraindataService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class TraindataController {
	@Resource
	private TraindataService traindataService;
	@Resource
	private DictService dictService;
   @RequestMapping("/information/list")
   public ModelAndView list(HttpServletRequest request,HttpServletResponse response,String page,String did){
	   JModelAndView mav = new JModelAndView("/traindata_list", "USER", request, response);
	   long dictid = 0;
	   Map params = new HashMap();
	   params.put("deleteStatus", false);
	   params.put("pdictCode", "ZLLX");
	   List<Dict> dictList = dictService.find("from Dict bean where bean.deleteStatus=:deleteStatus and bean.parent.dictCode=:pdictCode", params, -1, -1);
	   if(did==null||"".equals(did)){
		   dictid = dictList.get(0).getId();
	   }else{
		   dictid = CommUtil.null2Long(did);
	   }
	   int curPage = page==null||"".equals(page)?1:CommUtil.null2Int(page);
	   //分页查询
	   InformationPage<Traindata> trainPage = new InformationPage<Traindata>(6, curPage);
	   params.clear();
	   params.put("state", 1);
	   params.put("dictid", dictid);
	   trainPage.setCount_all(traindataService.find("from Traindata bean where bean.state=:state and bean.dictType.id =:dictid", params, -1, -1).size());
	   trainPage.setRows(traindataService.find("from Traindata bean where bean.state=:state and bean.dictType.id =:dictid", params, trainPage.getStartPoint(), trainPage.getPage_size()));
	   mav.addObject("dictid", dictid);
	   mav.addObject("dictList", dictList);
	   mav.addObject("trainPage", trainPage);
	   mav.addObject("html", new StripHtml());
	   mav.addObject("page", curPage);
	   return mav;
   }
   //详情页面
   @RequestMapping("/information/detail")
   public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,int id){
	   JModelAndView mav = new JModelAndView("/information_detail", "USER", request, response);
	   mav.addObject("trainData", traindataService.getTrainDataById(id));
	   return mav;
   } 
}
