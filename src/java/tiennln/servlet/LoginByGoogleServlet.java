/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tiennln.users.UsersDAO;
import tiennln.users.UsersDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoginByGoogleServlet", urlPatterns = {"/LoginByGoogleServlet"})
public class LoginByGoogleServlet extends HttpServlet {
    
    private final Logger logger = Logger.getLogger(LoginByGoogleServlet.class);
    private final String CLIENT_ID = "154810970511-re6vslhed4s3dm115n8m1mcfu7g79ga1.apps.googleusercontent.com";
    private final String ERROR_PAGE = "loginFail.jsp";
    private final String CREATE_INFORMATION_PAGE = "createInformation.html";
    private final String ADMIN_PAGE = "admin.jsp";
    private final String INVALID_PAGE = "invalid.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String url = ERROR_PAGE;
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GoogleIdTokenVerifier verifier
                    = new GoogleIdTokenVerifier.Builder(httpTransport, new JacksonFactory())
                            .setAudience(Collections.singletonList(CLIENT_ID))
                            .build();
            String idTokenString = request.getParameter("token_id");
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get profile information from payload
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // Use or store profile information
                // ...
                UsersDAO dao = new UsersDAO();
                UsersDTO user = dao.checkLoginByGoogle(email, name);
                
                HttpSession session = request.getSession();
                session.setAttribute("LAST_USER", user);
                
                if (user.getUserID() == null) {
                    url = CREATE_INFORMATION_PAGE;
                } else {
                    url = ADMIN_PAGE;
                }
            } else {
                url = INVALID_PAGE;
            }
        } catch (GeneralSecurityException ex) {
            logger.error(ex.getMessage());
        } catch (NamingException ex) {
            logger.error(ex.getMessage());
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            out.print(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
