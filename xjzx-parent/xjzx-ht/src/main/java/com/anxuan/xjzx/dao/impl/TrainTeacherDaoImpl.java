package com.anxuan.xjzx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.SafetymanageCard;
import com.anxuan.xjzx.bean.TrainTeacher;
import com.anxuan.xjzx.dao.TrainTeacherDao;
import com.anxuan.xjzx.util.DBConnection;
@Repository(value="trainTeacherDao")
public class TrainTeacherDaoImpl extends BaseDaoImpl<TrainTeacher> implements TrainTeacherDao{

	@Override
	public void deleteByState(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update TrainTeacher bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
		
	}

	@Override
	public int saveBatch(List<TrainTeacher> teacherList) {
		int count=0;
	    try
	    {
	      String insertSql = "insert into train_teacher ( name,idcard,sex,unit,certinum,jobs,state,type,pxtype) values %s";
	      List<String> items = new LinkedList();
	      List<String> values = null;
	      StringBuilder sBuilder = null;
	      for (TrainTeacher trainTeacher : teacherList) {
		        if (null != trainTeacher){
		          sBuilder = new StringBuilder();
		          values = new LinkedList();
		          values.add("'" + trainTeacher.getName() + "'");
		          values.add("'" + (trainTeacher.getIdcard()==null?"":trainTeacher.getIdcard()) + "'");
		          values.add("'" + (trainTeacher.getSex()==null?"":trainTeacher.getSex()) + "'");
		          values.add("'" + trainTeacher.getUnit() + "'");
		          values.add("'" + trainTeacher.getCertinum() + "'");
		          values.add("'" + (trainTeacher.getJobs()==null?"":trainTeacher.getJobs()) + "'");
		          values.add("'" + trainTeacher.getState() + "'");
		          values.add("'" + trainTeacher.getType() + "'");
		          values.add("'" + (trainTeacher.getPxtype()==null?"":trainTeacher.getPxtype()) + "'");
		          sBuilder.append("(");
		          sBuilder.append(StringUtils.join(values, ","));
		          sBuilder.append(")");
		          items.add(sBuilder.toString());
		        }
	      }
	      Connection conn = DBConnection.getConn();
	      PreparedStatement prep = conn.prepareStatement(String.format(insertSql, new Object[] { StringUtils.join(items, ",") }));
	      System.out.println(String.format(insertSql, new Object[] { StringUtils.join(items, ",") }));
	      conn.setAutoCommit(false);
	      count = prep.executeUpdate();
	      conn.commit();
	      prep.close();
	      DBConnection.closeConn();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
		return count;
	}

	@Override
	public List<String> selectCertinumByParams(Map params) {
		String type  = params.get("type").toString();
		Query query = getCurrentSession().createQuery("select bean.certinum from TrainTeacher bean where bean.state=1 and bean.type=:type");
		query.setInteger("type", Integer.valueOf(type));
		return query.list();
	}

}
