package com.anxuan.xjzx.dao.impl;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.xjzx.bean.Specialic;
import com.anxuan.xjzx.dao.SpecialicDao;
import com.anxuan.xjzx.util.DBConnection;
import com.anxuan.xjzx.util.SortUtil;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.springframework.stereotype.Repository;

@Repository(value = "specialicDao")
public class SpecialicDaoImpl extends BaseDaoImpl<Specialic> implements SpecialicDao {
	public void addIndex() {
		try {
			getFullTextSession().createIndexer(Specialic.class).startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Specialic> findAll() {
		Criteria criteria = getCurrentSession().createCriteria(Specialic.class);
		return criteria.list();
	}

	@Override
	public List<Specialic> findSpecialicByCardnumber(String cardnumber) {
		cardnumber = judgeContainsStr(cardnumber)?cardnumber.toLowerCase():cardnumber;
		//TermQuery lunceQuery = new TermQuery(new Term("cardnumber", cardnumber));
		WildcardQuery lunceQuery = new WildcardQuery(new Term("cardnumber", "*"+cardnumber));
		FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(lunceQuery, Specialic.class);
		List<Specialic> specialicList = fullTextQuery.list();
		/*if (specialicList.size() > 1) {
			SortUtil.sortList(specialicList, "releastime", "DESC");
			for (int i = 0; i < specialicList.size(); i++) {
				Specialic tSpecialic = specialicList.get(i);
				for (int j = specialicList.size() - 1; j > i; j--) {
					Specialic xSpecialic = specialicList.get(j);
					if (xSpecialic.getJobcategory().equals(tSpecialic.getJobcategory())
							&& xSpecialic.getPrepareproject().equals(tSpecialic.getPrepareproject())) {
						specialicList.remove(j);
					}
				}
			}
		}*/
		return specialicList;
	}

	   public boolean judgeContainsStr(String cardNum) {
	    	String regex=".*[a-zA-Z]+.*";
		    Matcher m=Pattern.compile(regex).matcher(cardNum);
		    return m.matches();
	    }
	   
   @Override
   public Integer deleteBatch(List<Specialic> list) {
     int i = 0;
     try {
       String deleteSql = "delete from t_spelialic where handleno in %s";
       List<String> handlenos = new LinkedList();
       StringBuilder sBuilder = new StringBuilder();
       sBuilder.append("(");
       for (Specialic specialic : list) {
         if ((null != specialic) && (StringUtils.isNotEmpty(specialic.getHandleno()))) {
           handlenos.add("'" + specialic.getHandleno() + "'");
         }
       }
       sBuilder.append(StringUtils.join(handlenos, ","));
       sBuilder.append(")");
       Connection conn = DBConnection.getConn();
       PreparedStatement prep = conn.prepareStatement(String.format(deleteSql, new Object[] { sBuilder.toString() }));
       System.out.println(String.format(deleteSql, new Object[] { sBuilder.toString() }));
       conn.setAutoCommit(false);
       i = prep.executeUpdate();
       conn.commit();
       prep.close();
       conn.close();
     } catch (SQLException e) {
       e.printStackTrace();
     }
     return Integer.valueOf(i);
   } 
	   
   @Override
   public Integer saveBatch(List<Specialic> list){
     int i = 0;
     try{
       String insertSql = "insert into t_spelialic ( address,badgestime,cardnumber,education,endtime,firstreview,firsttrial,handleno,health,idcard,isserial,issuingorgan,jobcategory,labotype,lscores,`name`,nameno,param3,param4,prepareproject,recordno,releastime,reviewid,seniority,sex,sscores,starttime,telephone,trainingunits,treviewrecord1,treviewrecord2,twotreview,twotrial,whereofrecord,worktype,workunit ) values %s";
       List<String> items = new LinkedList();
       List<String> values = null;
       StringBuilder sBuilder = null;
       for (Specialic specialic : list) {
         if (null != specialic)
         {
           sBuilder = new StringBuilder();
           values = new LinkedList();
           values.add("'" + specialic.getAddress() + "'");
           values.add("'" + specialic.getBadgestime() + "'");
           values.add("'" + specialic.getCardnumber() + "'");
           values.add("'" + specialic.getEducation() + "'");
           values.add("'" + specialic.getEndtime() + "'");
           values.add("'" + specialic.getFirstreview() + "'");
           values.add("'" + specialic.getFirsttrial() + "'");
           values.add("'" + specialic.getHandleno() + "'");
           values.add("'" + specialic.getHealth() + "'");
           values.add("'" + specialic.getIdcard() + "'");
           values.add("'" + specialic.getIsserial() + "'");
           values.add("'" + specialic.getIssuingorgan() + "'");
           values.add("'" + specialic.getJobcategory() + "'");
           values.add("'" + specialic.getLabotype() + "'");
           values.add("'" + specialic.getLscores() + "'");
           values.add("'" + specialic.getName() + "'");
           values.add("'" + specialic.getNameno() + "'");
           values.add("'" + specialic.getParam3() + "'");
           values.add("'" + specialic.getParam4() + "'");
           values.add("'" + specialic.getPrepareproject() + "'");
           values.add("'" + specialic.getRecordno() + "'");
           values.add("'" + specialic.getReleastime() + "'");
           values.add("'" + specialic.getReviewid() + "'");
           values.add("'" + specialic.getSeniority() + "'");
           values.add("'" + specialic.getSex() + "'");
           values.add("'" + specialic.getSscores() + "'");
           values.add("'" + specialic.getStarttime() + "'");
           values.add("'" + specialic.getTelephone() + "'");
           values.add("'" + specialic.getTrainingunits() + "'");
           values.add("'" + specialic.getTreviewrecord1() + "'");
           values.add("'" + specialic.getTreviewrecord2() + "'");
           values.add("'" + specialic.getTwotreview() + "'");
           values.add("'" + specialic.getTwotrial() + "'");
           values.add("'" + specialic.getWhereofrecord() + "'");
           values.add("'" + specialic.getWorktype() + "'");
           values.add("'" + specialic.getWorkunit() + "'");
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
       conn.close();
     }catch (SQLException e){
       e.printStackTrace();
     }
     return Integer.valueOf(i);
   }
}
