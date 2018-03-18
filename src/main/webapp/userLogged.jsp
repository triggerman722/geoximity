  <%@ page language="java" 
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
         import="com.geoximity.model.UserBean"
   %>
 
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
   "http://www.w3.org/TR/html4/loose.dtd">

   <html>

      <head>
         <meta http-equiv="Content-Type" 
            content="text/html; charset=windows-1256">
         <title>   User Logged Successfully   </title>
      </head>
	
      <body>

         <center>
            <% //UserBean currentUser = (UserBean) session.getAttribute("currentSessionUser");%>
            <% String mccg_token = (String)session.getAttribute("mccg_token");%>
			
<!--            Welcome <%/*= currentUser.getFirstName() + " " + currentUser.getLastName()*/ %>-->
Token is: <%= mccg_token %>
         </center>

      </body>
	
   </html>

