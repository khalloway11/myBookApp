/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.controllers;

import edu.wctc.kh.mybookapp2.entity.Book;
import edu.wctc.kh.mybookapp2.service.BookService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @book Keiji
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {
    
    // NO MAGIC NUMBERS!

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String ADD_PAGE = "/addBook.jsp";
    private static final String EDIT_PAGE = "/editBook.jsp";
    private static final String SUBMIT_ACTION = "submitRequest";
    private static final String SUBMIT_PARAM = "submit";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "Add";
    private static final String UPDATE_ACTION = "Edit";
    private static final String DELETE_ACTION = "Delete";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String SAVE_ACTION = "Save";
    private static final String ACTION_PARAM = "action";

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
        response.setContentType("text/html;charset=UTF-8");
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        /*
            This is how you inject a Spring service object into your servlet. Note
            that the bean name must match the name in your service class.
        */
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        BookService bookService = (BookService) ctx.getBean("bookService");

        try {
            /*
             Here's what the connection pool version looks like.
             */
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("jdbc/book");
//            BookDaoStrategy authDao = new ConnPoolBookDao(ds, new MySqlDbStrategy());
//            BookService bookService = new BookService(authDao);

            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
            switch (action){
                case LIST_ACTION:
                    this.refreshList(bookService, request);
                    destination = LIST_PAGE;
                    break;
                case SUBMIT_ACTION:
                    String subrequest = request.getParameter(SUBMIT_PARAM);
                    switch(subrequest){
                        case ADD_ACTION:
                            destination = ADD_PAGE;
                            break;
                        case UPDATE_ACTION:
                            String bookID = request.getParameter("bookId");
                            Book book = bookService.findById(bookID);
                            destination = EDIT_PAGE;
                            request.setAttribute("book", book);
                            break;
                        case DELETE_ACTION:
                            bookID = request.getParameter("bookId");
                            book = bookService.findById(bookID);
                            bookService.remove(book);
                            this.refreshList(bookService, request);
                            destination = LIST_PAGE;
                            break;
                        case CANCEL_ACTION:
                            destination = LIST_PAGE;
                            this.refreshList(bookService, request);
                            break;
                        case SAVE_ACTION:
                            destination=LIST_PAGE;
                            bookID = request.getParameter("bookId");
                            book = bookService.findById(bookID);
                    }
                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
            }
            
            
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
    
    private void refreshList(BookService bookService, HttpServletRequest request){
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }
    
    private Book getUpdatedBook(HttpServletRequest request) throws Exception {
        Book updated = new Book();
        DateFormat df = DateFormat.getDateInstance();
        updated.setBookId(Integer.parseInt(request.getParameter("bookId")));
        updated.setBookTitle(request.getParameter("bookName"));
        
        return updated;
    }
    
    @Override
    public void init(){

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
