package com.Servlet;

import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ✅ Correct place for constants
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public AdminLoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("Admin Login Servlet Running...");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {

            // Generate OTP
            String otp = generateOTP();
            long expirationTime = System.currentTimeMillis() + 2 * 60 * 1000;

            // Store in session
            HttpSession session = request.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("otpExpiration", expirationTime);

            // Print OTP (for testing)
            System.out.println("Generated OTP: " + otp);

            // Redirect
            response.sendRedirect("VerifyOTP.jsp");

        } else {
            response.sendRedirect("AdminLogin.jsp?error=Invalid credentials");
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
