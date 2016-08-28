package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.api.datastore.Text;
import com.google.gson.Gson;

@Controller
@RequestMapping("")
public class ControllerClass {
	String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	public static String safe(String input) {
		
		String md5 = null;
		
		if(null == input) return null;
		
		try {
			
		//Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");
		
		//Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		//Converts message digest value in base 16 (hex) 
		md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}
	
	
	
	@RequestMapping(method= RequestMethod.POST, value="Diary/add")
	public void addEntry(HttpServletRequest request, HttpServletResponse resp, ModelMap model,HttpSession session) throws IOException {

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		
		Cookie ck[] = request.getCookies();
        String id = ck[0].getValue();
        System.out.println(id+" Add ");
        id = (id.matches(EMAIL_REGEX) ? id : null);
        
        
		PersistenceManager pm= PMF.get().getPersistenceManager();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String input = buffer.toString();
		JSONObject JSON = null;
		try{
			JSON = new JSONObject(input);
			String title=JSON.getString("title");
			String date=JSON.getString("date");
			Text description=new Text(JSON.getString("description"));
			
			Query q= pm.newQuery(Diary.class);
			q.setFilter("date == '"+date+"'"+"&&"+"	uid == '"+id+"'");
			q.setOrdering("date desc");
			List<Diary> results = (List<Diary>) q.execute(date);
			if(!(results.isEmpty()))
			{
				

			}else{
			Diary r=new Diary();
			r.setTitle(title);
			r.setDate(date);
			r.setDescr(description);
			r.setUid(id);
			pm.makePersistent(r);
			pm.close();
			Gson obj = new Gson();
			String retVal = obj.toJson(r);
			}
			} catch (JSONException e) {
						e.printStackTrace();
			} finally{
			}

		}

	
	@RequestMapping(method=RequestMethod.GET, value="Diary/Diary")
    @ResponseBody
	 public String displayData(HttpServletRequest request,HttpServletResponse resp, ModelMap model,HttpSession session) throws IOException {
		
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		resp.setDateHeader("Expires", 0); // Proxies.
		
		Cookie ck[] = request.getCookies();
        String id = ck[0].getValue();
        System.out.println(id);
        id = (id.matches(EMAIL_REGEX) ? id : null);
  
		PersistenceManager pm= PMF.get().getPersistenceManager();
			Query q = pm.newQuery(Diary.class);
			q.setFilter("uid == '"+id+"'");
			q.setOrdering("date desc");
			List<Diary> list= (List<Diary>) q.execute();
			Gson obj = new Gson();
			String retVal = obj.toJson(list);
			return retVal;
			
	}
	

	@RequestMapping(method=RequestMethod.GET, value="Diary/add/{id}")
    @ResponseBody
	 public String editing(HttpServletRequest request,HttpServletResponse resp, ModelMap model,HttpSession session,@PathVariable String id) throws IOException {
    		PersistenceManager pm= PMF.get().getPersistenceManager();
    		
    		Cookie ck[] = request.getCookies();
            String uid = ck[0].getValue();
            System.out.println(uid);
            uid = (uid.matches(EMAIL_REGEX) ? uid : null);
    		
    		long key = Long.parseLong(id);
			Query q = pm.newQuery(Diary.class);
			q.setFilter("id == idParameter"+"&&"+"uid == '"+uid+"'");
			q.declareParameters("String idParameter");
			List<Diary> list= (List<Diary>) q.execute(key);
			Diary	dobj	=	(Diary)list.get(0);
			
			Gson obj = new Gson();
			String retVal = obj.toJson(dobj);
			
			return retVal;

		}
    @RequestMapping(method=RequestMethod.PUT, value="Diary/add/{id}")
	public void updateEntry(HttpServletRequest request, HttpServletResponse resp,@PathVariable String id) throws IOException{
		
		String k=id;
		long key=Long.parseLong(k);
		PersistenceManager pm= PMF.get().getPersistenceManager();
			
			try{
			StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		    
		    //Key key=KeyFactory.stringToKey(key1);
		    JSONObject JSON = null;
			try {
				JSON = new JSONObject(data);
				String title=JSON.getString("title");
				String date=JSON.getString("date");
				Text description=new Text(JSON.getString("description"));

				//System.out.println(ID);
				Diary c = pm.getObjectById(Diary.class, key);
				c.setTitle(title);
				c.setDate(date);
				c.setDescr(description);


			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			catch(Exception e){
				System.out.println(e);
			}

    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="Diary/add/{id}")
    public void deleteData(HttpServletRequest request,HttpServletResponse resp, @PathVariable String id) throws IOException {
    	
    	String k=id;
		long key=Long.parseLong(k);
    	
    	try{
    		
	    	PersistenceManager pm = PMF.get().getPersistenceManager();
	    	

    		Diary c = pm.getObjectById(Diary.class, key);
    		pm.deletePersistent(c);
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}

    }
	@RequestMapping(value="Diary/Signup",method={RequestMethod.POST})
	public void Signup(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
	throws IOException, ServletException {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		Query q= pm.newQuery(Login.class);
		q.setFilter("email == '" + email + "'");	
		List<Login> results = (List<Login>) q.execute(email);
		if(!(results.isEmpty()))
		{
			
			PrintWriter out = resp.getWriter();  
			resp.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Email is existing');");
			out.print("window.location='../LoginPage';");
			out.println("</script>");
			
			
		}	else{
		Login c = new Login();
		password= safe(password);
		
		c.setEmail(email);
		c.setName(name);
		c.setPassword(password);
						
		try {
			pm.makePersistent(c);
			this.signIn(req, resp, session);
		} finally {
			pm.close();
			}
		}
		
		
   		
	this.signIn(req, resp, session);
   }
	
	@RequestMapping(value="Diary/Signin",method={RequestMethod.POST})
	public void signIn(HttpServletRequest request, HttpServletResponse res, HttpSession session)
	throws IOException, ServletException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Query q= pm.newQuery(Login.class);
		q.setFilter(" email == '" + email + "'"+ "&&" + " password == '" + safe(password) + "'");
		List<Login> results = (List<Login>) q.execute(email);
		if(!(results.isEmpty()))
		{
			
			Cookie ck=new Cookie("email", email);//creating cookie object  
		    res.addCookie(ck);//adding cookie in the response  
			
	   		session.setAttribute("email", email);
	   		session.setMaxInactiveInterval(6*60*60);
	   		res.sendRedirect("../Diary");
	   		
            
		}else{
			PrintWriter out = res.getWriter();  
			res.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Dude Please Give Correct Credential');");
			out.print("window.location='../LoginPage';");
			out.println("</script>");
			
			
		}

	
	}
	
	
	// Login Credentials //
    @RequestMapping(value="Diary/Login",method={RequestMethod.GET})
	public void doGet(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
	throws IOException {

	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	
	
	resp.setContentType("text/html; charset=UTF-8");
	resp.setCharacterEncoding("UTF-8");
	resp.getWriter().println("<h2>Integrating Google user account</h2>");

	if (user != null) {
		String name = user.getNickname();
		String email = user.getEmail();
		
		Cookie ck=new Cookie("email", email);//creating cookie object  
	    resp.addCookie(ck);//adding cookie in the response  
		
   		session.setAttribute("email", email);
   		session.setMaxInactiveInterval(6*60*60);
   		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q= pm.newQuery(Login.class);
		q.setFilter(" email == '" + email + "'");	
		List<Login> results = (List<Login>) q.execute(email);
		if(!(results.isEmpty()))
		{
		
		}	
		else{
			
		
		Login c = new Login();
		c.setName(name);
		c.setEmail(email);
		
		try {
			pm.makePersistent(c);
		}
		finally{
			pm.close();
			}
		}
		ck=new Cookie("email", email);//creating cookie object  
	    resp.addCookie(ck);//adding cookie in the response  
		

		} else {
			resp.getWriter().println(
					"Please <a href='"
							+ userService.createLoginURL(req.getRequestURI())
							+ "'> LogIn </a>");

			}
		}

    
    
    
    @RequestMapping(value="Diary/Logout",method={RequestMethod.GET})
    public void logOut(HttpServletRequest req, HttpServletResponse resp,HttpSession session)
	throws IOException {
    	Cookie ck=new Cookie("email","");  
        ck.setMaxAge(0);  
        resp.addCookie(ck);    	
		resp.sendRedirect("../LoginPage");
    }
    
    
    @RequestMapping(value = "/LoginPage", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpServletResponse resp,HttpServletRequest req,HttpSession session) throws IOException, ServletException {
    	
    	resp.sendRedirect("/Diary");
    	return new ModelAndView("signin-page");	
	}
    @RequestMapping(value = "/SignupPage", method = RequestMethod.GET)
    public ModelAndView signUpPage(HttpServletResponse resp,HttpServletRequest req,HttpSession session) throws IOException, ServletException {
    	
    	return new ModelAndView("signup-page");
	}
    
    @RequestMapping(value = "/Diary", method = RequestMethod.GET)
	public ModelAndView Main(HttpServletResponse resp,HttpServletRequest req,HttpSession session) throws IOException {
    	Cookie ck[] = req.getCookies();
        String id = ck[0].getValue();
        id = (id.matches(EMAIL_REGEX) ? id : null);
        if(id==null){
        	return new ModelAndView("signin-page");
        }else{
        	return new ModelAndView("diary-page");	
        }
    	
	}
    
		
}
