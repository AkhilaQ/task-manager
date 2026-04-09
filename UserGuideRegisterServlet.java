package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.bean.UserGuideRegister;
import com.dao.DBConnection;

@MultipartConfig
@WebServlet("/UserGuideRegisterServlet")
public class UserGuideRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public UserGuideRegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("User Guide Register Servlet Running...");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String contact = request.getParameter("contact");
        String password = request.getParameter("password");
        String area = request.getParameter("Area");

        // Set data to bean
        UserGuideRegister ub = new UserGuideRegister();
        ub.setName(name);
        ub.setEmail(email);
        ub.setAge(age);
        ub.setGender(gender);
        ub.setContact(contact);
        ub.setPassword(password);
        ub.setArea(area);

        System.out.println("Name: " + ub.getName());
        System.out.println("Email: " + ub.getEmail());

        // SQL
        String sql = "INSERT INTO userguide VALUES(?,?,?,?,?,?,?)";

        int result = DBConnection.UserGuide1(sql, ub);

        if (result > 0) {
            out.println("<script>");
            out.println("alert('Register Successfully');");
            out.println("window.location='UserGuideLogin.jsp';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.println("alert('Please enter valid details / Already exists');");
            out.println("window.location='AddUserGuideRegister.jsp';");
            out.println("</script>");
        }
    }
}
