package com.geoximity.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import com.geoximity.model.UserBean;
import com.geoximity.model.SearchSelect;
import com.geoximity.model.SearchRequest;
import com.geoximity.model.MapCriteria;
import com.geoximity.dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {


public void doGet(HttpServletRequest request, HttpServletResponse response)
			           throws ServletException, java.io.IOException {
	try {
		String urlParameters = "grant_type=password"
                    + "&username"
                    + "=" + request.getParameter("un")
                    + "&password"
                    + "=" + request.getParameter("pw");

		String requesturl = "https://alpha.mccg.net/oauth2-service/oauth/token";
		URL url = new URL(requesturl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
 		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		byte[] message = ("internal-service"+":"+"c0c0nut").getBytes("UTF-8");
		String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary(message);
		conn.setRequestProperty("Authorization", "Basic "+encoded);
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("accept", "application/json");
		conn.setUseCaches(false);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(new InputStreamReader(conn.getInputStream(), "utf-8"), JsonObject.class);
		String mccg_token = jsonObject.get("access_token").getAsString();

		HttpSession session = request.getSession(true);
                session.setAttribute("mccg_token", mccg_token);


		SearchRequest searchReq = new SearchRequest();
		searchReq.exactLimit = 0;
		searchReq.limit = 50;
		searchReq.pageSize = 10;
		searchReq.searchKey = "0.123456789GMMMM";
		searchReq.type = "claim-master";

		MapCriteria mapCrit = new MapCriteria();
		mapCrit.key = "map.lossLocation";
		mapCrit.latitude = 42.946999225479516F;
		mapCrit.longitude = -81.29230499267578F;
		mapCrit.radius = 34342;

		searchReq.mapCriteria = mapCrit;

		searchReq.searchSelects = new ArrayList<SearchSelect>();

		SearchSelect searchSel = new SearchSelect();
		searchSel.key = "py.policykey";
		searchReq.searchSelects.add(searchSel);
		searchSel.key = "clm.claimkey";
		searchReq.searchSelects.add(searchSel);
		searchSel.key = "ins_ct.formattedname";
		searchReq.searchSelects.add(searchSel);
		searchSel.key = "loss_adr.formattedaddress";
		searchReq.searchSelects.add(searchSel);
		searchSel.key = "loss_loc.latitude";
		searchReq.searchSelects.add(searchSel);
		searchSel.key = "loss_loc.longitude";
		searchReq.searchSelects.add(searchSel);

		requesturl = "https://alpha.mccg.net/search-service/search";
                url = new URL(requesturl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer "+mccg_token);
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Type", "application/mccg.search-v2+json");
                conn.setRequestProperty("Accept", "application/mccg.search-v2+json");
                conn.setUseCaches(false);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(new Gson().toJson(searchReq));
                wr.flush();
                wr.close();
                gson = new Gson();
                jsonObject = gson.fromJson(new InputStreamReader(conn.getInputStream(), "utf-8"), JsonObject.class);

                session.setAttribute("search_result", jsonObject.toString());

                response.sendRedirect("userLogged.jsp"); //logged-in page
//                }  else {
//              response.sendRedirect("invalidLogin.jsp"); //error page

	} catch (Exception e) {
		e.printStackTrace();
	}
}

private void dbLogin(HttpServletRequest request, HttpServletResponse response) {
	try
	{
		UserBean user = new UserBean();
		user.setUserName(request.getParameter("un"));
		user.setPassword(request.getParameter("pw"));

		user = UserDAO.login(user);
		if (user.isValid())
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("currentSessionUser",user);
			response.sendRedirect("userLogged.jsp"); //logged-in page
		}  else {
			response.sendRedirect("invalidLogin.jsp"); //error page
		}
	}
	catch (Throwable theException)
	{
		System.out.println(theException);
	}
}

}

