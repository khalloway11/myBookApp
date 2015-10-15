package edu.wctc.kh.mybookapp2.models;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

/**
 * This DAO is a strategy object that uses a server-side connection pool to 
 * optimize the creation and use of connections by a web app. 
 * Such connections are VERY performant because they are created in advance, 
 * stored in an in-memory pool and loaned out for quick access. When done the 
 * connection is returned to the pool.
 * 
 * @author jlombardo
 */
public class ConnPoolAuthorDao implements AuthorDaoStrategy {

    private DataSource ds;
    private DBStrategy db;

    public ConnPoolAuthorDao(DataSource ds, DBStrategy db) {
        this.ds = ds;
        this.db = db;
    }

    @Override
    public final List<Author> getAllAuthors() throws Exception {
        // Grabs a connection from the pool
        db.openConnection(ds);
        List<Author> records = new ArrayList<>();

        List<Map<String, Object>> rawData = db.findAllRecords("author");
        for (Map rawRec : rawData) {
            Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));

            String name = rawRec.get("author_name") == null ? "" : rawRec.get("author_name").toString();
            author.setAuthorName(name);

            obj = rawRec.get("date_added");
            Date dateAdded = (obj == null) ? new Date() : (Date) rawRec.get("date_added");
            author.setDateAdded(dateAdded);
            records.add(author);
        }

        // returns connection to pool; doesn't actually close it
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

    // Test harness - not used in production
    // Uses a ad-hoc connection pool and DataSource object to test the code
    public static void main(String[] args) throws Exception {
        
        // Sets up the connection pool and assigns it a JNDI name
        NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

            @Override
            public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException {
                return new InitialContextFactory() {

                    @Override
                    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
                        return new InitialContext(){

                            private Hashtable<String, DataSource> dataSources = new Hashtable<>();

                            @Override
                            public Object lookup(String name) throws NamingException {

                                if (dataSources.isEmpty()) { //init datasources
                                    MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
                                    ds.setURL("jdbc:mysql://localhost:3306/book");
                                    ds.setUser("root");
                                    ds.setPassword("admin");
                                    // Association a JNDI name with the DataSource for our Database
                                    dataSources.put("jdbc/book", ds);

                                    //add more datasources to the list as necessary
                                }

                                if (dataSources.containsKey(name)) {
                                    return dataSources.get(name);
                                }

                                throw new NamingException("Unable to find datasource: "+name);
                            }
                        };
                    }

                };
            }

        });
        
        // Find the connection pool and create the DataSource     
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/book");

        AuthorDaoStrategy dao = new ConnPoolAuthorDao(ds, new MySqlDbStrategy());

        List<Author> authors = dao.getAllAuthors();
        for (Author a : authors) {
            System.out.println(a);
        }
    }

  
}
