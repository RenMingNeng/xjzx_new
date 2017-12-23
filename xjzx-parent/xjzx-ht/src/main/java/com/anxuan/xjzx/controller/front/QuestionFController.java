package com.anxuan.xjzx.controller.front;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.MessageUtf8;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Email;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.Question;
import com.anxuan.xjzx.bean.QuestionAnswer;
import com.anxuan.xjzx.bean.QuestionIp;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.bean.QuestionResult;
import com.anxuan.xjzx.service.EmailService;
import com.anxuan.xjzx.service.NewsService;
import com.anxuan.xjzx.service.QuestionAnswerService;
import com.anxuan.xjzx.service.QuestionIpService;
import com.anxuan.xjzx.service.QuestionResultService;
import com.anxuan.xjzx.service.QuestionService;
import com.anxuan.xjzx.util.GetClientIp;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/question")
public class QuestionFController {
	@Resource
	private QuestionService questionService;
	@Resource
	private QuestionResultService questionResultService;
	@Resource
	private QuestionAnswerService questionAnswerService;
	@Resource
	private QuestionIpService questionIpService;
	@Resource
	private EmailService emailService;
	
	/**
	 * 互动页面
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping("interact")
	  public ModelAndView Newslist(HttpServletRequest request,HttpServletResponse response,String type,String page){
		 JModelAndView mav = new JModelAndView("/interact_list", "USER", request, response);
		 Map params = new HashMap();
		 if("1".equals(type)){
			// 在线调查问卷列表
			 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
			 InformationPage<Question> newsPage = new InformationPage<Question>(10, curPage);
			 params.clear();
			 params.put("is_valid", "1");
			 newsPage.setCount_all(questionService.find("from Question bean where bean.is_valid =:is_valid", params, -1, -1).size());
			 newsPage.setRows(questionService.find("from Question bean where bean.is_valid =:is_valid order by bean.desc_no,bean.create_time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
			 mav.addObject("newsPage", newsPage);
			 mav.addObject("html", new StripHtml());
			 mav.addObject("page", curPage);
		 }else if("2".equals(type)){
			// 主任信箱列表
			 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
			 InformationPage<Email> emailPage = new InformationPage<Email>(10, curPage);
			 params.clear();
			 params.put("isValid", 1);
			 emailPage.setCount_all(emailService.find("from Email bean where bean.isValid =:isValid", params, -1, -1).size());
			 emailPage.setRows(emailService.find("from Email bean where bean.isValid =:isValid order by bean.id desc", params, emailPage.getStartPoint(), emailPage.getPage_size()));
			 mav.addObject("emailPage", emailPage);
			 mav.addObject("html", new StripHtml());
			 mav.addObject("page", curPage);
		 }
		 mav.addObject("type",type);
		 return mav;
	 }
	
	// 调查展开
	@RequestMapping("open")
	  public void open(String id,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			  Integer id_ = Integer.valueOf(id);
			  Question question = new Question();
			  List<QuestionOption> option_list = new ArrayList<QuestionOption>();
			  Map params = new HashMap();
			  params.put("id", id_);
			  List<Question> questions = questionService.find("from Question bean where bean.id =:id", params, -1, -1);
			  if(null != questions && questions.size()>0){
				  question = questions.get(0);
				  String option = question.getAnswer_option();
				  if(StringUtils.isNotEmpty(option)) {
					  question.setQuestionOptions(
							  (List<QuestionOption>)JSONArray.toList(JSONArray.fromObject(option), QuestionOption.class)
					  );
					  ret.setListData(question.getQuestionOptions());
				  }
			  }
			  ret.setData(question);
			  ret.setCode("success");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
		 
	  }
	
	/**
	 * 查看结果弹窗(选择)
	 */
	@RequestMapping("/lookResult")
	public ModelAndView index(String id,PrintWriter out,HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/look_result", "USER", request, response);
		Integer id_ = Integer.valueOf(id);
		List<QuestionOption> options = new ArrayList<QuestionOption>();
		Map params = new HashMap();
		params.put("id", id_);
		// 某调查下的总票数
		Integer allCount = questionResultService.sumCountByQuestionId(id_);
		List<Question> questions = questionService.find("from Question bean where bean.id =:id", params, -1, -1);
		if(null != questions && questions.size()>0){
			Question question = questions.get(0);
			String option = question.getAnswer_option();
			if(StringUtils.isNotEmpty(option)) {
			    question.setQuestionOptions(
					  (List<QuestionOption>)JSONArray.toList(JSONArray.fromObject(option), QuestionOption.class)
			    );
			    options = question.getQuestionOptions();
			    for (QuestionOption questionOption : options) {
					  // 统计票数
					  Map param = new HashMap();
					  param.put("question_id", question.getId());
					  param.put("option_id", questionOption.getKey());
					  List<QuestionResult> questionResult = questionResultService.find("from QuestionResult bean where bean.question_id =:question_id and option_id =:option_id", param, -1, -1);
					  if(null != questionResult && questionResult.size() > 0){
						  questionOption.setCount(questionResult.get(0).getCount());
					  }else{
						  questionOption.setCount(0);
					  }
			    }
			  mav.addObject("optionList",options);
			}
			mav.addObject("question",question);
	   }
	   mav.addObject("allCount",allCount);
	   mav.addObject("id",id);
	   return mav;
	}
	
	/**
	 * 查看结果弹窗(简答)
	 */
	@RequestMapping("/lookAnswer")
	public ModelAndView lookAnswer(Integer id,PrintWriter out,HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/look_answer", "USER", request, response);
		Question question = questionService.selectById(id);
		Map params = new HashMap();
		params.put("question_id", id);
		params.put("is_valid", "1");
		Integer allCount = questionAnswerService.find("from QuestionAnswer bean where bean.is_valid=:is_valid and bean.question_id=:question_id", params, -1, -1).size();
		mav.addObject("questionId", id);
		mav.addObject("allCount", allCount);
		mav.addObject("title", question.getTitle());
		return mav;
	}
   /**
    * 查看结果列表(简答)
    */
    @RequestMapping("lookAnswerList")
    public void questionAnserList(Integer id, EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  String hql ="from QuestionAnswer bean where bean.is_valid=1";
		  Map params = new HashMap();
		  hql+=" and bean.question_id = :question_id";
		  params.put("question_id", id);
		  hql+=" order by bean.create_time desc";
		  easyuiPage = questionAnswerService.showListPage(hql, easyuiPage, params);
		  ret.setData(easyuiPage);
		  ret.setCode("success");
		  ret.setMessage("加载成功!");
	  } catch (Exception e) {
		  e.printStackTrace();
		  ret.setCode("success");
		  ret.setMessage(e.getMessage());
	  }finally {
		  out.print(ret.toJsonString());
		  out.flush();
		  out.close();
	  }
   }
	
	// 参加调查  
	@RequestMapping("joinSurvey")
	  public void joinSurvey( Integer question_id, String type,String content,
			  String option_ids, PrintWriter out, HttpServletRequest request){
		ReturnValue ret = new ReturnValue();
		try {
			String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//Integer count = questionResultService.find("from QuestionResult bean where bean.question_id =:question_id and bean.ip =:ip", params, -1, -1).size();
			Integer count = 0;
			if(count == 0){  //表示为未投过票，可以投票
				if("3".equals(type)){
					if(StringUtils.isNotEmpty(content)){
						QuestionAnswer questionAnswer = new QuestionAnswer();
						questionAnswer.setQuestion_id(question_id);
						questionAnswer.setIs_valid("0");
						questionAnswer.setCreate_time(nowTime);
						questionAnswer.setContent(content);
						questionAnswerService.save(questionAnswer);
						ret.setCode("success");
					}
				}else{
					if(StringUtils.isNotEmpty(option_ids)){
						Map params = new HashMap();
						params.put("option_ids", option_ids);
						params.put("question_id", question_id);
						questionResultService.updateCount(params);
						ret.setCode("success");
					}
				}
				// 记录投票ip
				String ip = GetClientIp.getIpAddr(request);  // 获取请求客户端ip
				QuestionIp questionIp = new QuestionIp();
				questionIp.setQuestion_id(question_id);
				questionIp.setCreate_time(nowTime);
				questionIp.setCliant_ip(ip);
				questionIpService.save(questionIp);
			}else{
				ret.setCode("already");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
		 
	  }
}
