package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.SafetymanageCard;
import com.anxuan.xjzx.bean.Specialic;
import com.anxuan.xjzx.service.SafetymanageCardService;
import com.anxuan.xjzx.service.SpecialicService;
import com.anxuan.xjzx.util.MessageUtf8;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

@Controller
public class SpecialicController {
	@Resource
	private SpecialicService specialicService;
	@Resource
	private SafetymanageCardService safetymanageCardService;

	//生成索引
	 @RequestMapping("/system/specialica/ddIndex")
	 public void addIndex(HttpServletResponse response) throws Exception {
		 ReturnValue ret = new ReturnValue();
		 try{
		   long startTime = System.currentTimeMillis();  //获取开始时间
		   specialicService.addIndex();
		   safetymanageCardService.addIndex();
		   long endTime = System.currentTimeMillis();  //获取结束时间
		   	ret.setCode("success");
			ret.setData("耗时："+(endTime-startTime)/1000+" s");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setData("失败");
			ret.setMessage(e.getMessage());
		} 
		 MessageUtf8.writeMessageUft8(response,ret.toJsonString());
	 }

	@RequestMapping("/system/spelialic/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/specialic/specialic_index", "ADMIN", request, response);
		return mav;
	}
	
	@RequestMapping("/system/spelialic/quchongfu")
	public void quchongfu(PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			ret.setCode("success");
			ret.setMessage("去重成功!");
			safetymanageCardService.quchong(2015);
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	 
	/**
	 * 特种证书推送
	 * @param response
	 * @param specialics
	 * @throws Exception
	 */
	@RequestMapping(value={"/system/spelialic/t_spelialic"}, method={RequestMethod.POST})
	  public void add_spelialic(HttpServletResponse response, @RequestBody List<Specialic> specialics)
	    throws Exception
	  {
	    ReturnValue ret = new ReturnValue();
	    try{
	      if (specialics.size() > 0){
	        this.specialicService.deleteBatch(specialics);
	        this.specialicService.saveBatch(specialics);
	        ret.setCode("success");
	        ret.setData("0000");
	        ret.setMessage("共处理" + specialics.size() + "条数据");
	      }else{
	        ret.setCode("failure");
	        ret.setData("0001");
	        ret.setMessage("无数据!");
	      }
	    }catch (Exception e){
	      e.printStackTrace();
	      ret.setCode("failure");
	      ret.setData("0002");
	      ret.setMessage(e.getMessage());
	    }
	    MessageUtf8.writeMessageUft8(response, ret.toJsonString());
	  }
	  
	  /**
	   * 安管证书推送
	   * @param response
	   * @param safetymanageCards
	   * @throws Exception
	   */
	  @RequestMapping(value={"/system/spelialic/t_safetymanagecard"}, method={RequestMethod.POST})
	  public void add_safetymanagecard(HttpServletResponse response, @RequestBody List<SafetymanageCard> safetymanageCards)
	    throws Exception
	  {
	    ReturnValue ret = new ReturnValue();
	    try{
	      if (safetymanageCards.size() > 0){
	        this.safetymanageCardService.deleteBatch(safetymanageCards);
	        this.safetymanageCardService.saveBatch(safetymanageCards);
	        ret.setCode("success");
	        ret.setData("0000");
	        ret.setMessage("共处理" + safetymanageCards.size() + "条数据");
	      }else{
	        ret.setCode("failure");
	        ret.setData("0001");
	        ret.setMessage("无数据!");
	      }
	    }catch (Exception e){
	      e.printStackTrace();
	      ret.setCode("failure");
	      ret.setData("0002");
	      ret.setMessage(e.getMessage());
	    }
	    MessageUtf8.writeMessageUft8(response, ret.toJsonString());
	  }
}
