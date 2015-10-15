package edu.wctc.kh.mybookapp2.models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This DAO is a strategy object that uses a manual connection to a database. 
 * Such connections are not very performant so it's best to use a connection 
 * pool created on the app server. See ConnPoolAuthorDao.java for an 
 * example of a DAO that talks to such a pool.
 * 
 * @author jlombardo
 */
public class AuthorDao implements AuthorDaoStrategy {
    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    public AuthorDao(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public final List<Author> getAllAuthors() throws Exception {
        db.openConnection(driverClass, url, userName, password);
        List<Author> records = new ArrayList<>();

        List<Map<String,Object>> rawData = db.findAllRecords("author");
        for(Map rawRec : rawData) {
            Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));
            
            String name = rawRec.get("author_name") == null ? "" : rawRec.get("author_name").toString();
            author.setAuthorName(name);
            
            obj = rawRec.get("date_added");
            Date dateAdded = (obj == null) ? new Date() : (Date)rawRec.get("date_added");
            author.setDateAdded(dateAdded);
            records.add(author);
        }
        
        // Actually closes connection
        db.closeConnection();
        
        return records;
        
    }
    
    @Override
    public Author getAuthorById(Integer authorId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAuthor(Integer authorId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveAuthor(Integer authorId, String authorName) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //public final void deleteById()
    
    // Test harness - not used in production
    public static void main(String[] args) throws Exception {
        AuthorDao dao = new AuthorDao(new MySqlDbStrategy(),"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
    
        List<Author> authors = dao.getAllAuthors();
        for(Author a : authors) {
            System.out.println(a);
        }
    }

    
}
