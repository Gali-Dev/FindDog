package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.BoardDao;
import dao.LocationInfoDao;
import dao.LoginDao;
import dao.RelationshipDao;
import vo.Board;
import vo.LocationInfo;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("*.do")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("서블릿까지 옴");
		PrintWriter out = response.getWriter();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String action = requestURI.substring(contextPath.length());
		if (action.equals("/list.do")) {
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lng = Double.parseDouble(request.getParameter("lng"));
			double km = Double.parseDouble(request.getParameter("km"));
			System.out.println(lat);
			System.out.println(lng);
			System.out.println(km);
			String id = LocationInfoDao.getInstance().select(1);
			System.out.println(id);
			List<LocationInfo> list = new ArrayList<LocationInfo>();
			list = LocationInfoDao.getInstance().selectList(lat, lng, km);
			JSONArray infoList = new JSONArray();
			String test = null;
			for (int i = 0; i < list.size(); i++) {
				JSONObject info = new JSONObject();
				info.put("dogNo", list.get(i).getDogNo());
				info.put("dogName", list.get(i).getDogName());
				info.put("dogInfo", list.get(i).getDogInfo());
				info.put("dogKinds", list.get(i).getDogkinds());
				if(list.get(i).getRelationship()==null) {
					test="0";
					info.put("relationship",test);
				}else {
					test=list.get(i).getRelationship();
					info.put("relationship", test);
				}
				info.put("lat", list.get(i).getLat());
				info.put("lng", list.get(i).getLng());
				info.put("distance", list.get(i).getDistance());
				infoList.add(info);
				System.out.println(test);
			}
			out.print(infoList);
			System.out.println(infoList);
		}
		else if(action.equals("/start.do")) {
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lng = Double.parseDouble(request.getParameter("lng"));
			double km = Double.parseDouble(request.getParameter("km"));
			System.out.println(lat);
			System.out.println(lng);
			System.out.println(km);
			String id = LocationInfoDao.getInstance().select(1);
			System.out.println(id);
			int dno=11;
			List<LocationInfo> list = new ArrayList<LocationInfo>();
			boolean b=LocationInfoDao.getInstance().deleteLocation(dno);
			boolean b1=LocationInfoDao.getInstance().insertLocation(dno, lat, lng);
			
			list = LocationInfoDao.getInstance().selectList(lat, lng, km,dno);
			
			
			JSONArray infoList = new JSONArray();
			String test=null;
			for (int i = 0; i < list.size(); i++) {
				JSONObject info = new JSONObject();
				info.put("dogNo", list.get(i).getDogNo());
				info.put("dogName", list.get(i).getDogName());
				info.put("dogInfo", list.get(i).getDogInfo());
				info.put("dogKinds", list.get(i).getDogkinds());
				if(list.get(i).getRelationship()==null) {
					test="0";
					info.put("relationship",test);
				}else {
					test=list.get(i).getRelationship();
					info.put("relationship", test);
				}
				info.put("lat", list.get(i).getLat());
				info.put("lng", list.get(i).getLng());
				info.put("distance", list.get(i).getDistance());
				infoList.add(info);
			}
			out.print(infoList);
			System.out.println(infoList);
			
		}else if(action.equals("/delete.do")) {
			System.out.println("delete.do:실행");
			int dno=Integer.parseInt(request.getParameter("dno"));
			System.out.println(dno);
			
			
			
			boolean b=LocationInfoDao.getInstance().deleteLocation(dno);
			out.print("end");
		}else if(action.equals("/login.do")) {
			String result=null;
			System.out.println("login.do:실행");
			String id=request.getParameter("id");
			System.out.println(id);
			String pw=request.getParameter("pw");
			System.out.println(pw);
			String str=LoginDao.getInstance().login(id, pw);
			if(str.equals("로그인 성공")) {
				result=id;
			}else {
				result="fail";
			}
//			List<String> str=new ArrayList<String>();
//			str=LoginDao.getInstance().login();
//			for(int i=0;i<str.size();i++) {
//				System.out.println(str.get(i).toString());
//			}
//			
			out.print(result);
			System.out.println("결과 : "+result);
			
		}else if(action.equals("/join.do")) {
			System.out.println("join.do:실행");
			String id=request.getParameter("id");
			String pw=request.getParameter("pw");
			String name=request.getParameter("name");
			String location=request.getParameter("location");
			String dog_ox=request.getParameter("dog_ox");
			String str=LoginDao.getInstance().insert(id, pw, name, location, dog_ox);
			int no=LoginDao.getInstance().getNo(id);
			String ox=LoginDao.getInstance().getOx(id);
			if(str.equals("이미 존재하는 아이디입니다")) {
				String sendMsg="";
				sendMsg="duplication";
				out.print(sendMsg);
				System.out.println("sendMsg:"+sendMsg);
			}else if(str.equals("회원가입 성공")) {
				JSONObject member = new JSONObject();
				member.put("no", no);
				member.put("ox", ox);
				out.print(member);
				System.out.println("memberJSON:"+member);
			}
			
		}else if(action.equals("/dogjoin.do")) {
			System.out.println("dogjoin.do:실행");
			int no=Integer.parseInt(request.getParameter("no"));
			String dog_name=request.getParameter("dog_name");
			String dog_info=request.getParameter("dog_info");
			String dog_kinds=request.getParameter("dog_kinds");
			
			
			boolean b=LoginDao.getInstance().insertDog(no,dog_name, dog_info, dog_kinds);
			System.out.println(b);
	
		}else if(action.equals("/boardList.do")) {
			System.out.println("boardList.do 실행");
			int category=Integer.parseInt(request.getParameter("category"));
			System.out.println(category);
			List<Board> listBoard=new ArrayList<Board>();
			listBoard=BoardDao.getInstance().selectBoardList();
			
			JSONArray infoList = new JSONArray();
			for (int i = 0; i < listBoard.size(); i++) {
				JSONObject info = new JSONObject();
				info.put("no",listBoard.get(i).getNo());
				info.put("bno",listBoard.get(i).getBno());
				info.put("title",listBoard.get(i).getTitle());
				info.put("category",listBoard.get(i).getCategory());
				info.put("content",listBoard.get(i).getContent());
				infoList.add(info);
			}
			out.print(infoList);
			System.out.println(infoList);
			
		}else if(action.equals("/relationship.do")) {
			//int no=Integer.parseInt(request.getParameter("no"));
			int dogNo=Integer.parseInt(request.getParameter("dogNo"));
			String idx=request.getParameter("idx");
			
			//임시
			int no=11;
			String str="";
			System.out.println("relationship.do parameter no: "+no+" dogNo :"+dogNo+" idx :"+idx);
			boolean b=RelationshipDao.getInstance().selectEmpty(no, dogNo);
			if(b==true) {
				str="fail";
			}else {
				boolean b1=RelationshipDao.getInstance().insertRelationship(no, dogNo, idx);
				if(b1==true) {
					str="success";
				}else {
					str="fail";
				}
			}
			out.print(str);
			System.out.println(str);
			
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

}
