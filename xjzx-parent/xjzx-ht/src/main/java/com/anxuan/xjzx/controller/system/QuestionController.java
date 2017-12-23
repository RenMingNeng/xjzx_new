package com.anxuan.xjzx.controller.system;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.util.UUIDGenerator;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Question;
import com.anxuan.xjzx.bean.QuestionAnswer;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.bean.QuestionResult;
import com.anxuan.xjzx.service.QuestionAnswerService;
import com.anxuan.xjzx.service.QuestionResultService;
import com.anxuan.xjzx.service.QuestionService;
import com.anxuan.xjzx.util.InformationPage;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/system/question")
public class QuestionController {
  @Resource
  private QuestionService questionService;
  @Resource
  private QuestionResultService questionResultService;
  @Resource
  private QuestionAnswerService questionAnswerService;

  @RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/question/question_index", "ADMIN", request, response);
		return mav; 
	}

  	//加载新增页面
	@RequestMapping("loadAddQuestion")
	public ModelAndView loadAddQuestion(String ids,HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/question/question_add", "ADMIN", request, response);
		  String questionId = request.getParameter("id");
		  mav.addObject("questionId", questionId);
		  return mav;
	}
  
  	// 加载修改页面
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping("loadUpdateQuestion")
	public ModelAndView loadUpdateQuestion(String id,HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/question/question_update", "ADMIN", request, response);
		  String questionId = request.getParameter("id");
		  Question question = questionService.selectById(Integer.valueOf(questionId));
		  if(null != question) {
			  String option = question.getAnswer_option();
			  if(StringUtils.isNotEmpty(option)) {
				  question.setQuestionOptions(
					  (List<QuestionOption>)JSONArray.toList(JSONArray.fromObject(option), QuestionOption.class)
				  );
			  }
		  }
		  mav.addObject("question", question);
		  return mav;
	}
	
	// 加载后台查看结果页面 （简答）
	@RequestMapping("/loadQuestionAnswer")
	public ModelAndView loadQuestionAnswer(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/question/question_answer", "ADMIN", request, response);
		String questionId = request.getParameter("id");
		Question question = questionService.selectById(Integer.valueOf(questionId));
		mav.addObject("title",question.getTitle());
		mav.addObject("questionId", questionId);
		return mav;
	}
	
	// 加载后台查看结果页面 （选择）
	@RequestMapping("/loadQuestionResult")
	public ModelAndView loadUpdate(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/question/question_result", "ADMIN", request, response);
		String id = request.getParameter("id");
		Question question = questionService.selectById(Integer.valueOf(id)); 
		// 某调查下的总票数
		Integer allCount = questionResultService.sumCountByQuestionId(Integer.valueOf(id));
		if(null != question && !"3".equals(question.getType())){
			String option = question.getAnswer_option();
			  if(StringUtils.isNotEmpty(option)) {
				  question.setQuestionOptions(
					  (List<QuestionOption>)JSONArray.toList(JSONArray.fromObject(option), QuestionOption.class)
				  );
			  }
			  List<QuestionOption> options = question.getQuestionOptions();
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
			  mav.addObject("optionList", options);
		}
		mav.addObject("allCount", allCount);
		mav.addObject("question", question);
		mav.addObject("id", id);
		return mav;
	}
	
	// 新增(保存)
	@RequestMapping("addQuestion")
	public void addQuestion(Question question,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			//处理字符串
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(!"3".equals(question.getType())){
				String option = question.getAnswer_option();
				List<String> options_ = Arrays.asList(option.split(","));
				Map<String, Object> param = null;
				List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < options_.size(); i++) {
					param = new HashMap<String, Object>();
					param.put("value", options_.get(i));
					param.put("key",UUIDGenerator.genID());
					params.add(param);
				}
				String jsonStr = JSONArray.fromObject(params).toString(); 
				question.setAnswer_option(jsonStr);
			}else{
				question.setAnswer_option("");
			}
			question.setCreate_time(sdf.format(new Date()));
			question.setBack_valid("2");  // 反馈推送初始化
			question.setFeedback("");     // 反馈内容初始化
			questionService.save(question);
			// 初始化选项表
			Map map = new HashMap();
			map.put("question_id", question.getId());
			map.put("options", question.getAnswer_option());
			questionResultService.batchInsert(map);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	// 修改(保存)
	@RequestMapping("updateQuestion")
	public void updateQuestion(Question question,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONArray jsonArray = new JSONArray();
			if(!"3".equals(question.getType())){
				if(null != question.getQuestionOptions() && question.getQuestionOptions().size() > 0){
					List<Map<String, Object>> params1 = new ArrayList<Map<String, Object>>();
					Map<String, Object> param1 = null;
					List<QuestionOption> questionOptions = question.getQuestionOptions();
					for (QuestionOption questionOption : questionOptions) {
						param1 = new HashMap<String, Object>();
						param1.put("value", questionOption.getValue());
						param1.put("key",questionOption.getKey());
						params1.add(param1);
					}
					jsonArray.addAll(JSONArray.fromObject(params1));
				}
				String option = question.getAnswer_option();
				if(StringUtils.isNotEmpty(option)){
					List<String> options_ = Arrays.asList(option.split(","));
					Map<String, Object> param2 = null;
					List<Map<String, Object>> params2 = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < options_.size(); i++) {
						param2 = new HashMap<String, Object>();
						param2.put("value", options_.get(i));
						param2.put("key",UUIDGenerator.genID());
						params2.add(param2);
					}
					jsonArray.addAll(JSONArray.fromObject(params2));
					// 初始化选项表
					Map map = new HashMap();
					map.put("question_id", question.getId());
					map.put("options", JSONArray.fromObject(params2).toString());
					questionResultService.batchInsert(map);
				}
				question.setAnswer_option(jsonArray.toString());
			}else{
				question.setAnswer_option("");
			}
			question.setOperate_time(sdf.format(new Date()));
			questionService.update(question);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	// 删除
	@RequestMapping("deleteQuestion")
	public void deleteQuestion(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			// 删除调查卷
			questionService.deleteByIds(ids);
			// 同时删除对应的调查结果
			questionResultService.deleteByQIds(ids);
			ret.setCode("success");
			ret.setMessage("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	// 提交反馈
	@RequestMapping("addFeedBack")
	public void addFeedBack(Question question,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			questionService.editFeedBack(question);
			ret.setCode("success");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
		
	// 调查列表页
	@RequestMapping("getQuestionPage")
	  public void getQuestionPage(Question question,EasyuiPage easyuiPage,PrintWriter out){
		  ReturnValue ret = new ReturnValue();
		  try {
			  String hql ="from Question bean where 1=1";
			  Map params = new HashMap();
			  if(question.getTitle()!=null&&!"".equals(question.getTitle())){
				  hql+=" and bean.title like :title";
				  params.put("title", "%"+question.getTitle()+"%");
			  }if(question.getIs_valid()!=null&&!"".equals(question.getIs_valid())){
				  hql+=" and bean.is_valid =:is_valid";
				  params.put("is_valid", question.getIs_valid());
			  }
			  hql+=" order by bean.desc_no,bean.create_time desc";
		     
			  easyuiPage = questionService.showListPage(hql, easyuiPage, params);
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

	// 修改票数
	@RequestMapping("editResult")
	public void editResult(String question_id, String option_id, String count,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			Map params = new HashMap();
			params.put("question_id", question_id);
			params.put("option_id", option_id);
			params.put("count", count);
			questionResultService.updateByParams(params);
			ret.setCode("success");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	// 删除选项
	@RequestMapping("deleteOption")
	public void deleteOption(String id,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			// 同时删除对应的调查结果
			questionResultService.deleteByOptionId(id);
			ret.setCode("success");
			ret.setMessage("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
  // 调查结果列表查询
  @RequestMapping("questionAnswerList")
  public void questionAnserList(QuestionAnswer questionAnswer, EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  String hql ="from QuestionAnswer bean where 1=1";
		  Map params = new HashMap();
		  hql+=" and bean.question_id = :question_id";
		  params.put("question_id", questionAnswer.getQuestion_id());
		  if(StringUtils.isNotEmpty(questionAnswer.getIs_valid())){
			  hql+=" and bean.is_valid =:is_valid";
			  params.put("is_valid", questionAnswer.getIs_valid());
		  }
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
  
  	// 删除调查结果（简答）
	@RequestMapping("deleteQuestionAnswer")
	public void deleteQuestionAnswer(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			// 删除
			questionAnswerService.deleteByIds(ids);
			ret.setCode("success");
			ret.setMessage("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	// 审核
	@RequestMapping("answerReview")
	public void answerReview(String ids, String review, PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			Map params = new HashMap();
			params.put("ids", ids);
			params.put("review", review);
			questionAnswerService.answerReview(params);
			ret.setCode("success");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
}
