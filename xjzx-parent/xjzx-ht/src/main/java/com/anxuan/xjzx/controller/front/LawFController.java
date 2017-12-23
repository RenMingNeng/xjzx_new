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
import com.anxuan.xjzx.bean.Law;
import com.anxuan.xjzx.service.LawService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class LawFController {
   @Resource
   private LawService lawService;
   @Resource
   private DictService dictService;
   @RequestMapping("/law/list")
   public ModelAndView index(HttpServletRequest request,HttpServletResponse response,String dictid,String page){
    JModelAndView mav = new JModelAndView("/law_list", "USER", request, response);
    Map params = new HashMap();
    params.put("deleteStatus", false);
    params.put("pdictCode", "FGLX");
    List<Dict> dictList = dictService.find("from Dict bean where bean.deleteStatus=:deleteStatus and bean.parent.dictCode=:pdictCode", params, -1, -1);
    mav.addObject("dictList", dictList);
    long did = 0l;
    if(dictid==null||"".equals(dictid)){
    	 did = dictList.get(0).getId();
    }else{
    	did = CommUtil.null2Long(dictid);
    }
    int curPage = page==null||"".equals(page)?1:CommUtil.null2Int(page);
    InformationPage<Law> lawPage = new InformationPage<Law>(10, curPage);
    params.clear();
    params.put("ltid", did);
    params.put("state", 1);
    params.put("ispass", 1);
    lawPage.setCount_all(lawService.find("from Law bean where bean.state=:state and bean.lawType.id=:ltid and bean.ispass=:ispass ", params, -1, -1).size());
    lawPage.setRows(lawService.find("from Law bean where bean.state=:state and bean.lawType.id=:ltid and bean.ispass=:ispass  order by bean.adddate desc", params, -1, -1));
    mav.addObject("lawPage", lawPage);
    mav.addObject("did", did);
    mav.addObject("page", curPage);
    mav.addObject("html",new StripHtml());
    return mav;
   }
   @RequestMapping("/law/detail")
   public ModelAndView lawDetail(HttpServletRequest request,HttpServletResponse response,int id){
	   JModelAndView mav = new JModelAndView("/law_detail", "USER", request, response);
	   mav.addObject("law", lawService.getLawById(id));
	   return mav;
   }
}
