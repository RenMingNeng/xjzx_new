package com.anxuan.xjzx.dao.impl;

import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.hibernate.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.xjzx.bean.SafetymanageCard;
import com.anxuan.xjzx.dao.SafetymanageCardDao;
import com.anxuan.xjzx.util.DBConnection;


@Repository(value="safetymanageCardDao")
public class SafetymanageCardDaoImpl extends BaseDaoImpl<SafetymanageCard> implements SafetymanageCardDao{

	@Override
	public void addIndex() {
		try {
			getFullTextSession().createIndexer(SafetymanageCard.class).startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<SafetymanageCard> getSafetymanageCardByIdcard(String idcard, int line) {
		FullTextSession fullTextSession = getFullTextSession();
		BooleanQuery bq = new BooleanQuery();
		TermQuery idcardQuery = new TermQuery(new Term("idcard", idcard));
		TermQuery lineQuery = new TermQuery(new Term("line", String.valueOf(line)));
		bq.add(idcardQuery, BooleanClause.Occur.MUST);
		bq.add(lineQuery, BooleanClause.Occur.MUST);
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(bq,SafetymanageCard.class);
		return fullTextQuery.list();
	}

	

	@Override
	public void deleteSafetymanageCard(int key) {
		Query query = getCurrentSession().createSQLQuery("delete from t_safetymanagecard where `key` =:key");
		query.setInteger("key", key);
		query.executeUpdate();
	}
	
	@Override
	public Integer deleteBatch(List<SafetymanageCard> list)
	  {
	    int i = 0;
	    try
	    {
	      String deleteSql = "delete from t_safetymanagecard where job in %s";
	      List<String> jobs = new LinkedList();
	      StringBuilder sBuilder = new StringBuilder();
	      sBuilder.append("(");
	      for (SafetymanageCard safetymanageCard : list) {
	        if ((null != safetymanageCard) && (StringUtils.isNotEmpty(safetymanageCard.getJob()))) {
	          jobs.add("'" + safetymanageCard.getJob() + "'");
	        }
	      }
	      sBuilder.append(StringUtils.join(jobs, ","));
	      sBuilder.append(")");
	      Connection conn = DBConnection.getConn();
	      PreparedStatement prep = conn.prepareStatement(String.format(deleteSql, new Object[] { sBuilder.toString() }));
	      System.out.println(String.format(deleteSql, new Object[] { sBuilder.toString() }));
	      conn.setAutoCommit(false);
	      i = prep.executeUpdate();
	      conn.commit();
	      prep.close();
	      conn.close();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return Integer.valueOf(i);
	  }
	  
	@Override
	 public Integer saveBatch(List<SafetymanageCard> list){
	    int i = 0;
	    try
	    {
	      String insertSql = "insert into t_safetymanagecard ( id,name,sex,idcard,unit,job,starttime,endtime,ftime,wfzd,zc,dwlx,xs1,cj1,xs2,cj2,pxlx,line ) values %s";
	      List<String> items = new LinkedList();
	      List<String> values = null;
	      StringBuilder sBuilder = null;
	      for (SafetymanageCard safetymanageCard : list) {
	        if (null != safetymanageCard)
	        {
	          sBuilder = new StringBuilder();
	          values = new LinkedList();
	          values.add("'" + safetymanageCard.getId() + "'");
	          values.add("'" + safetymanageCard.getName() + "'");
	          values.add("'" + safetymanageCard.getSex() + "'");
	          values.add("'" + safetymanageCard.getIdcard() + "'");
	          values.add("'" + safetymanageCard.getUnit() + "'");
	          values.add("'" + safetymanageCard.getJob() + "'");
	          values.add("'" + safetymanageCard.getStarttime() + "'");
	          values.add("'" + safetymanageCard.getEndtime() + "'");
	          values.add("'" + safetymanageCard.getFtime() + "'");
	          values.add("'" + safetymanageCard.getWfzd() + "'");
	          values.add("'" + safetymanageCard.getZc() + "'");
	          values.add("'" + safetymanageCard.getDwlx() + "'");
	          values.add("'" + safetymanageCard.getXs1() + "'");
	          values.add("'" + safetymanageCard.getCj1() + "'");
	          values.add("'" + safetymanageCard.getXs2() + "'");
	          values.add("'" + safetymanageCard.getCj2() + "'");
	          values.add("'" + safetymanageCard.getPxlx() + "'");
	          values.add("'" + safetymanageCard.getLine() + "'");
	          sBuilder.append("(");
	          sBuilder.append(StringUtils.join(values, ","));
	          sBuilder.append(")");
	          items.add(sBuilder.toString());
	        }
	      }
	      Connection conn = DBConnection.getConn();
	      PreparedStatement prep = conn.prepareStatement(String.format(insertSql, new Object[] { StringUtils.join(items, ",") }));
	      //System.out.println(String.format(insertSql, new Object[] { StringUtils.join(items, ",") }));
	      conn.setAutoCommit(false);
	      i = prep.executeUpdate();
	      conn.commit();
	      prep.close();
	      DBConnection.closeConn();
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return Integer.valueOf(i);
	  }
  
}
