package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.dao.DBConnection;

@WebServlet("/UserGuideLoginServlet")
public class UserGuideLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public UserGuideLoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter pw = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        try {
            Connection con = DBConnection.connect();

            // ✅ Use PreparedStatement (safe)
            String sql = "SELECT name FROM userguide WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login success
                String name = rs.getString("name");

                session.setAttribute("email", email);
                session.setAttribute("name", name);

                response.sendRedirect("UserGuideHome.jsp");

            } else {
                // Unauthorized access
                PreparedStatement ps2 = con.prepareStatement(
                        "INSERT INTO unauthorised(email, status) VALUES (?, ?)");
                ps2.setString(1, email);
                ps2.setString(2, "UnAuthorised");
                ps2.executeUpdate();

                pw.println("<script>");
                pw.println("alert('Un-Authorised Access Detected');");
                pw.println("window.location='UserGuideLogin.jsp';");
                pw.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
