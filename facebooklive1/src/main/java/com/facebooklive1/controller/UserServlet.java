package com.facebooklive1.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.facebooklive1.entity.FacebookUser;
import com.facebooklive1.entity.TimeLine;
import com.facebooklive1.service.FacebookServiceInterface;
import com.facebooklive1.utility.ServiceFactory;

public class UserServlet extends HttpServlet {
	Logger log=Logger.getLogger("UserServlet");
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String m=request.getParameter("method");
		System.out.println(m);
		response.setContentType("text/html");

		PrintWriter out=response.getWriter();
		
		out.println("<html><body>");
		
		FacebookServiceInterface fs=ServiceFactory.createObject();
		
		 if(m.equals("signup")) {
			 log.info("Signup Part");
			
			String name=request.getParameter("nam");
			String email=request.getParameter("ema");
			String password=request.getParameter("pas");	
			String address=request.getParameter("adr");
			
			FacebookUser fb=new FacebookUser();
			fb.setName(name);
			fb.setPassword(password);
			fb.setEmail(email);
			fb.setAddress(address);
			
			int i=fs.createProfileService(fb);
			
			if(i>0) {
				
				out.println("<h1 style='color:red;text-decoration:underline;text-align:center;background-color:pink;'>Profile created successfully</h1>");
				out.println("<div style='background: linear-gradient(45deg,yellow,white,pink,cyan);padding-bottom:700px'>");
				out.println("<br><h2>Your Username :</h2> "+email+" <br><br><h2> password : </h2>"+password);
				out.println("<br><a href=signin.html><button style='background-color:blue;color:white;'>Continue</button></a>");
				out.println("</div>");
			}
			else {
				out.println("could not create profile");
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/signup.html");
				rd.include(request, response);
			}
			
			
			
			
		 }
		 if(m.equals("signin")) {
			 log.info("Signin Part");
				
				String password=request.getParameter("pass");
				String email=request.getParameter("em");
				
				
				FacebookUser fb=new FacebookUser();
				fb.setPassword(password);
				fb.setEmail(email);
				
				
				int i=fs.loginProfileService(fb);
				
				if(i>0) {
					
					HttpSession hs=request.getSession(true);
					hs.setAttribute("userid", email);
					
					RequestDispatcher rd=getServletContext().getRequestDispatcher("/UserServlet?method=UserHomePage");
					rd.forward(request, response);
				}
				else {
					out.println("<h1>Username or Password wrong</h1>");
					RequestDispatcher rd=getServletContext().getRequestDispatcher("/signin.html");
					rd.include(request, response);
				}
			}
		 if(m.equals("UserHomePage")) {
			 log.info("UserHome Part");
				HttpSession hs=request.getSession(true);
				String email=hs.getAttribute("userid").toString();
				out.println(
						"<div style='background-color: #525c4c;padding-bottom:50px'><h1 align=center style='color:white;text-decoration:underline;'>Welcome :  "+email+ "</h1>"
								+ "<a href=UserServlet?method=viewProfile><button style='font-size: 20px; background-color: rgb(31, 124, 245);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>View Profile</button></a><a href=UserServlet?method=editProfile><button style='font-size: 20px;background-color: rgb(218, 155, 73);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>Edit Profile</button></a>"
								+ "<a href=UserServlet?method=searchProfile><button style='font-size: 20px;background-color: rgb(43, 218, 28);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>Search Profile</button></a><a href=UserServlet?method=uploadphoto><button style='font-size: 20px;background-color: rgb(23, 72, 209);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>upload photo</button></a>"
								+ "<a href=UserServlet?method=deleteprofile><button style='font-size: 20px;background-color: rgb(231, 72, 9);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>Delete Profile</button></a></div>"
						
						
						);

				FacebookUser fb=new FacebookUser();
				
				fb.setEmail(email);
				List<TimeLine> tline=fs.timeLineService(fb);
				if(tline.size()>0) {
					out.println("<div style='background-color:pink;'><h1>Time Line Part</h1><table border=0>");
					out.println("<table border=0>");
					for(TimeLine tt:tline) {
						if(tt.getReceiver_Id().equals(email)) {
						out.println("<tr><td>"+tt.getSender_id()+"</td><td><textarea cols=100 rows=5>"+tt.getMessage()+"</textarea></td></tr>");
						out.println("<tr><td></td><td><a style='color:black;' href=UserServlet?method=like>Like ("+tt.getMessagelike()+")</a><a style='color:black;' href=UserServlet?method=dislike>Dislike("+tt.getDislike()+")</a></td><td><a href=UserServlet?method=reply>reply</td></tr>");
					}
					}
				}
				else {
				
					out.println("<tr><td></td><td> no timeline message</td></tr>");
				}
				out.println("</table></div>");
			}

		 
		 
		 
		 if(m.equals("searchProfile"))	
			{
				
				System.out.println("Searching Started");
				
		
				
				String email=request.getParameter("email");
				
				 FacebookUser fb=new FacebookUser();
				
				fb.getEmail();
				

				
			List<FacebookUser> i2=fs.searchProfileService(fb);
			
			if(i2.size()>0)
			{
				out.println("Profile Searched");
				for(FacebookUser ii:i2)
				{
					out.println(ii.getEmail());
				
				}
				
				
			}
			else
			{
				out.println("<a href=UserServlet?method=viewProfile><button style='font-size: 20px; background-color: rgb(31, 124, 245);border-radius: 15px;margin: 100px;box-shadow: 0px 0px 10px white;padding:20px;'>View Profile</button></a>");
			}
			
			}
		 
		 
		 

		 
		 if(m.equals("viewProfile")) {
			 log.info("View profile Part");
			 HttpSession hs=request.getSession(true);
			 String email=hs.getAttribute("userid").toString();
			 
			 FacebookUser fb=new FacebookUser();
			 fb.setEmail(email);
			 FacebookUser fb1=fs.viewProfileService(fb);
			
			out.println("<header>\r\n" + 
			 		"        <div style=\"width: 100%; height: 50px; background-color: bisque; ;\">\r\n" + 
			 		"            <a href='signin.html'><button style=\"background-color: blue; color: white;float: right; margin: 20px 20px 0px 0px;\">LOGOUT</button><button style=\"background-color: rgb(255, 174, 0); color: white;float: right; margin: 20px 20px 0px 0px;\">Edit Profile</button></a><a href='UserServlet?method=deleteprofile'><button style=\"background-color: rgb(247, 19, 11); color: white;float: right; margin: 20px 20px 0px 0px;\">delete Profile</button></a>\r\n" + 
			 		"\r\n" + 
			 		"\r\n" + 
			 		"        </div>\r\n" + 
			 		"    </header>\r\n" + 
			 		"    <section>\r\n" + 
			 		"        <div style=\"float: left; width: 250px; height: 600px; background-color: aquamarine;\">\r\n" + 
			 		"            <div>\r\n" + 
			 		"                <img  style=\"width: 150px; height: 150px; border-radius: 50%; margin: 30px 0px 0px 30px; border: tomato 5px solid; box-shadow: 0px 0px 10px black;\" src=\"https://www.seekajob.in/wp-content/uploads/2021/05/Revature-Off-Campus.png\"  alt=\"\">\r\n" + 
			 		"        \r\n" + 
			 		"            </div>\r\n" + 
			 		"            <h2 style=\"text-decoration: underline;\">Name:</h2>\r\n" + 
			 		"            <div style='color:red'>"+fb1.getName()+ "</div>\r\n" + 
			 		"\r\n" + 
			 		"        \r\n" + 
			 		"        <h2 style=\"text-decoration: underline;\">\r\n" + 
			 		"            Email:\r\n" + 
			 		"        </h2>\r\n" + 
			 		"        <div style='color:red'>"+ fb1.getEmail()+ "</div>\r\n" + 
			 		"            <h2 style=\"text-decoration: underline;\">City:</h2>\r\n" + 
			 		"            <div style='color:red'>"+fb1.getAddress()+ "\r\n" + 
			 		"\r\n" + 
			 		"            </div>\r\n" + 
			 		"\r\n" + 
			 		"        </div>\r\n" + 
			 		"    </section>\r\n" + 
			 		"    \r\n" + 
			 		"    <section>\r\n" + 
			 		"        <div style=\"background-color: chartreuse; width: 100%; height: 500px;margin-top: -18px; padding-bottom:170px; \">\r\n" + 
			 		"            <h3 align=\"center\">Profile Pic</h3>\r\n" + 
			 		"            <img style=\"width: 250px; height: 250px; margin: 30px 0px 0px 30px; border: tomato 5px solid; box-shadow: 0px 0px 10px black;\" src=\"https://www.seekajob.in/wp-content/uploads/2021/05/Revature-Off-Campus.png\"  alt=\"\">\r\n" + 
			 		"            <div>\r\n" + 
			 		"            <a style=\"margin-left: 30px;\" href=\"\">like</a>\r\n" + 
			 		"            <a style=\"margin-left: 30px;\" href=\"\">dislike</a>\r\n" + 
			 		"            <a style=\"margin-left: 30px;\" href=\" \">Reply</a>\r\n" + 
			 		"           \r\n" + 
			 		"        </div>\r\n" + 
			 		"\r\n" + 
			 		"\r\n" + 
			 		"        </div>\r\n" + 
			 		"\r\n" + 
			 		"    </section>");
		
		
			 
		 }
		if(m.trim().equals("editProfile")) {
			 log.info("edit profile Part");
			String email=request.getParameter("nam");
			String password=request.getParameter("pass");
			System.out.println("editProfile");
			FacebookUser fb=new FacebookUser();
			fb.setEmail(email);
			fb.setPassword(password);
			int i=fs.editprofileService(fb);
			if(i>0) {
				out.println("<br><br><tr><td><h1 style=color:green><Strong>profile created successfully</Strong><h1>");
				out.println("<br><a href =home.html>Continue...</a></td></tr>");
				HttpSession hs=request.getSession(true);
				hs.setAttribute("UserId", email);
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/signin.html");
				rd.include(request, response);
			}
			else {
				out.println("<br><br><h1 sytyle=colo:red><Strong>could not edit profile<Strong></h1>");
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/signin.html");
				rd.include(request, response);
			}
			
		}
		if(m.equals("deleteprofile")) {
			 log.info("delete profile Part");
			System.out.println("welcom");
			
			
			
			HttpSession hs=request.getSession(true);
			
			
			String email=hs.getAttribute("userid").toString();
			 
			FacebookUser fb=new FacebookUser();
			fb.setEmail(email);
			
			int i=fs.deleteProfileService(fb);
			
			if(i>0)
			{

				out.println("<html><body><center>");
				out.println(" <div style=\"background:linear-gradient(45deg,pink,cyan,yellow,black);padding-bottom: 700px;\">\r\n" + 
						"        <h1 style=\"text-align: center; background-color: seashell;\">Profile Deleted <span style=\"color: chocolate;\">Sucessfully!!!</span></h1>\r\n" + 
						"        <a href=\"signin.html\"><button style=\"background:radial-gradient(circle,green,orange,red,black);font-size: 25px; color: white;\">Home Page</button></a>\r\n" + 
						"    </div>");
				
				out.println("</center></body></html>");
			}
			else 
			{
				out.println("deletion failed");
			}
		}
		if(m.equals("uploadphoto")) {
			out.println("<html lang=\"en\">\r\n" + 
					"<head>\r\n" + 
					"  <meta charset=\"UTF-8\">\r\n" + 
					"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
					"  <title>aravind</title>\r\n" + 
					"  <link rel=\"stylesheet\" href=\"style.css\">\r\n" + 
					"</head>\r\n" + 
					"<body style='background: linear-gradient(45deg,pink,yellow,orange,black,gold);'>\r\n" + 
					"\r\n" + 
					"<h1 style='color:white;background-color:black; text-align:center;'>Upload Photo</h1>"+
					 
					"<div style='margin-top:-250px; margin-left:550px; border:5px white solid; box-shadow:0px 10px 10px black;' class=\"profile-pic-div\">\r\n" + 
					
					"  <img src=\"image.jpg\" id=\"photo\">\r\n" + 
					"  <input type=\"file\" id=\"file\">\r\n" + 
					"  <label for=\"file\" id=\"uploadBtn\">Choose Photo</label>\r\n" + 
					"</div>\r\n" + 
					"\r\n" + 
					"<script src=\"app.js\"></script>\r\n" + 
					
					"\r\n" + 
					"<style>*{\r\n" + 
					"					    margin: 0;\r\n" + 
					"					    padding: 0;\r\n" + 
					"					    box-sizing: border-box;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					body{\r\n" + 
					"					    height: 100vh;\r\n" + 
					"					    width: 100%;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					h1{\r\n" + 
					"					    font-family: sans-serif;\r\n" + 
					"					    text-align: center;\r\n" + 
					"					    font-size: 30px;\r\n" + 
					"					    color: #222;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					.profile-pic-div{\r\n" + 
					"					    height: 200px;\r\n" + 
					"					    width: 200px;\r\n" + 
					"					    position: absolute;\r\n" + 
					"					    top: 70%;\r\n" + 
					"					    left: 10%;\r\n" + 
					"					    transform: translate(-50%,-50%);\r\n" + 
					"					    border-radius:50%;\r\n" +
											""+
					"					    overflow: hidden;\r\n" + 
					"					    border: 1px solid grey;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					#photo{\r\n" + 
					"					    height: 100%;\r\n" + 
					"					    width: 100%;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					#file{\r\n" + 
					"					    display: none;\r\n" + 
					"					}\r\n" + 
					"\r\n" + 
					"					#uploadBtn{\r\n" + 
					"					    height: 40px;\r\n" + 
					"					    width: 100%;\r\n" + 
					"					    position: absolute;\r\n" + 
					"					    bottom: 0;\r\n" + 
					"					    left: 50%;\r\n" + 
					"					    transform: translateX(-50%);\r\n" + 
					"					    text-align: center;\r\n" + 
					"					    background: rgba(0, 0, 0, 0.7);\r\n" + 
					"					    color: wheat;\r\n" + 
					"					    line-height: 30px;\r\n" + 
					"					    font-family: sans-serif;\r\n" + 
					"					    font-size: 15px;\r\n" + 
					"					    cursor: pointer;\r\n" + 
					"					    display: none;\r\n" + 
					"}\r\n" + 
					"</style>\r\n" + 
					"</body>\r\n" +
					"<script> //declearing html elements\r\n" + 
					"\r\n" + 
					"const imgDiv = document.querySelector('.profile-pic-div');\r\n" + 
					"const img = document.querySelector('#photo');\r\n" + 
					"const file = document.querySelector('#file');\r\n" + 
					"const uploadBtn = document.querySelector('#uploadBtn');\r\n" + 
					"\r\n" + 
					"//if user hover on img div \r\n" + 
					"\r\n" + 
					"imgDiv.addEventListener('mouseenter', function(){\r\n" + 
					"    uploadBtn.style.display = \"block\";\r\n" + 
					"});\r\n" + 
					"\r\n" + 
					"//if we hover out from img div\r\n" + 
					"\r\n" + 
					"imgDiv.addEventListener('mouseleave', function(){\r\n" + 
					"    uploadBtn.style.display = \"none\";\r\n" + 
					"});\r\n" + 
					"\r\n" + 
					"//lets work for image showing functionality when we choose an image to upload\r\n" + 
					"\r\n" + 
					"//when we choose a foto to upload\r\n" + 
					"\r\n" + 
					"file.addEventListener('change', function(){\r\n" + 
					"    //this refers to file\r\n" + 
					"    const choosedFile = this.files[0];\r\n" + 
					"\r\n" + 
					"    if (choosedFile) {\r\n" + 
					"\r\n" + 
					"        const reader = new FileReader(); //FileReader is a predefined function of JS\r\n" + 
					"\r\n" + 
					"        reader.addEventListener('load', function(){\r\n" + 
					"            img.setAttribute('src', reader.result);\r\n" + 
					"        });\r\n" + 
					"\r\n" + 
					"        reader.readAsDataURL(choosedFile);\r\n" + 
					"\r\n" + 
					"        //Allright is done\r\n" + 
					"\r\n" + 
					 
					"    }\r\n" + 
					"});</script>\r\n" +
 
					"</html>");
			
			
		}
		if(m.equals("friendrequest")) {
			
		}
		
		
		out.println("</body></html>");

		
		
		
	}

}
