package com.sec.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sec.news.model.News;
import com.sec.news.model.PageModel;
import com.sec.news.model.Type;
import com.sec.news.model.User;

public class NewDao {
	
	/**
	 * 发布新闻
	 * @param news
	 * @return
	 */
	public int insert(News news)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("insert into newsInfo(typeId,userId,title,content,recommended,keywords)" +
				" values(?,?,?,?,?,?);");
			pstmt.setInt(1, news.getType().getTypeId());
			pstmt.setInt(2, news.getUser().getUserId());
			pstmt.setString(3, news.getTitle());
			pstmt.setString(4, news.getContent());
			pstmt.setInt(5, news.getRecommended());
			pstmt.setString(6, news.getKeywords());
			//获得结果集
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
	
	/**
	 * 修改新闻
	 * @param news
	 * @return
	 */
	public int update(News news)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("update newsInfo set title=?,typeId=?,content=?,keywords=?,recommended=? where newsId = ?");
			pstmt.setString(1, news.getTitle());
			pstmt.setInt(2,news.getType().getTypeId());
			pstmt.setString(3, news.getContent());
			pstmt.setString(4, news.getKeywords());
			pstmt.setInt(5, news.getRecommended());
			pstmt.setInt(6, news.getNewsId());
			System.out.println("update newsInfo set title="+news.getTitle()+",typeId="+news.getType().getTypeId()+"," +
					" content="+news.getContent()+",keywords="+news.getKeywords()+",recommended="+news.getRecommended()+" where newsId = "+news.getNewsId()+"");
			//获取结果集
			i = pstmt.executeUpdate();
			System.out.println("修改成功"+i);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
	
	/**
	 * 删除新闻
	 * @param news
	 * @return
	 */
	public int delete(int newsId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("delete from newsInfo where newsId = ?");
			pstmt.setInt(1,newsId);
			//获取结果集
			i = pstmt.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
	/**
	 * (下拉列表查询指定类型新闻)类型管理查询
	 * 根据类型查询新闻信息并且分页
	 * @param pm 封装该类型的新闻分页对象
	 * @param typeId 类型编号
	 * @return 该类型新闻总数
	 */
	public int select(PageModel<News> pm,int typeId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select top "+pm.getPageSize()+" * from (select *  from newsInfo where typeId = "+typeId+")as table1 "+
					" where newsId not in(select top "+pm.getPageSize()*(pm.getPageNo()-1)+" newsId from " +
					"(select *  from newsInfo where typeId = "+typeId+")as table1)");
			//获取结果集
			rs = pstmt.executeQuery();
			List<News> lst = new ArrayList<News>();
			TypeDao typeDao  = new TypeDao();
			UserDao userdao = new UserDao();
			
			while(rs.next())
			{
				News news = new News();
				String title = rs.getString("title");
				int click = rs.getInt("click");
				int recommended = rs.getInt("recommended");
				String content = rs.getString("content");
				String releaseTime = rs.getString("releaseTime");
			 	String keywords =  rs.getString("keywords");
				
				//类型
				Type type = typeDao.select(rs.getInt("typeId"));
				//用户
				User user = new User();
				user = userdao.selectUser(rs.getInt("userId"));
				//新闻信息
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(title);
				
				news.setType(type);
				news.setClick(click);
				news.setReleaseTime(releaseTime);
				news.setContent(content);
				news.setKeywords(keywords);
				news.setUser(user);
				news.setRecommended(recommended);
				lst.add(news);
			}
			pm.setPage(lst);
			
			//查询该类型编号的行数
			pstmt = con.prepareStatement("select * from newsInfo where typeId = ?");
			pstmt.setInt(1, typeId);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return i;
	}
	
	/**
	 * （查询所有类型的新闻）
	 * 查询所有新闻(分页查询)
	 * @param pm 所有新闻集合
	 * @return 所有新闻总数
	 */
	public int select(PageModel<News> pm)
	{	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			String str = String.format("select top %d * from newsInfo where newsId not in" +
					"(select top %d newsId from newsInfo " +
					"order by newsId desc)order by newsId desc", pm.getPageSize(),pm.getPageSize()*(pm.getPageNo()-1));
			
			pstmt = con.prepareStatement(str);
//			pstmt.setInt(1, pm.getPageSize());
//			pstmt.setInt(2, pm.getPageSize());
//			pstmt.setInt(3, pm.getPageNo());
		
			//获取结果集
			rs = pstmt.executeQuery();
			List<News> lst = new ArrayList<News>();
			TypeDao typeDao  = new TypeDao();
			UserDao userdao = new UserDao();
			while(rs.next())
			{
				News news = new News();
				String title = rs.getString("title");
				int click = rs.getInt("click");
				String content = rs.getString("content");
				String releaseTime = rs.getString("releaseTime");
			 	String keywords =  rs.getString("keywords");
			 	int recommended = rs.getInt("recommended");
				
				news.setNewsId(rs.getInt("newsId"));
				
				//类型
				Type type = typeDao.select(rs.getInt("typeId"));
				
				//用户
				User user = new User();
				user.setUserId(rs.getInt("userId"));
				userdao.selectUser(user);
				//新闻信息
				
				news.setTitle(title);
				news.setType(type);
				news.setUser(user);
				news.setClick(click);
				news.setReleaseTime(releaseTime);
				news.setContent(content);
				news.setKeywords(keywords);
				news.setRecommended(recommended);
				lst.add(news);
			}
			pm.setPage(lst);
			
			//得到本次查询新闻总数
			pm.setCountData(lst.size());
			
			//所有新闻数量
			str = "select * from newsInfo";
			pstmt = con.prepareStatement(str);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return i;
	}
	
	/**
	 * 用户事先设置好类型Id然后在该方法，初始化其他值
	 * 根据新闻编号查询新闻信息
	 * @param news 封装该类型的所有新闻
	 * 新闻对象
	 */
	public void SelectNewsId(News news)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsInfo where newsid = ?");
			pstmt.setInt(1, news.getNewsId());
			//获取结果集
			rs = pstmt.executeQuery();
			TypeDao typeDao  = new TypeDao();
			UserDao userdao = new UserDao();
			while(rs.next())
			{
				String title = rs.getString("title");
				int click = rs.getInt("click");
				int recommended = rs.getInt("recommended");
				String content = rs.getString("content");
				String releaseTime = rs.getString("releaseTime");
			 	String keywords =  rs.getString("keywords");
				
				//类型
				Type type = typeDao.select(rs.getInt("typeId"));
				//用户
				User user = new User();
				user = userdao.selectUser(rs.getInt("userId"));
				//新闻信息
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(title);
				
				news.setType(type);
				news.setClick(click);
				news.setReleaseTime(releaseTime);
				news.setContent(content);
				news.setKeywords(keywords);
				news.setUser(user);
				news.setRecommended(recommended);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
	}

}
