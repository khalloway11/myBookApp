package edu.wctc.kh.mybookapp2.models;

import java.util.List;

/**
 * The high-level class in the DIP acts as a service to the rest of the app.
 * It depends on one or more strategy objects (DAOs) to delegate the work of 
 * accessing data on the database.
 * 
 * @author jlombardo
 */
public class AuthorService {
    private AuthorDaoStrategy dao;


    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;

    }
    
    public final List<Author> getAllAuthors() throws Exception {
        return dao.getAllAuthors();
    }
    
    // Test harness - not used in production
    public static void main(String[] args) throws Exception {
        AuthorService authServ = new AuthorService(
                new AuthorDao(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin")
        );
        
        List<Author> authors = authServ.getAllAuthors();
        for(Author a: authors) {
            System.out.println(a);
        }
    }
}
