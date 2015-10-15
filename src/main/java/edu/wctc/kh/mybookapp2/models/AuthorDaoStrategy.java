package edu.wctc.kh.mybookapp2.models;

import java.util.List;

/**
 *
 * @author jlombardo
 */
public interface AuthorDaoStrategy {

    Author getAuthorById(Integer authorId) throws Exception;
    
    List<Author> getAllAuthors() throws Exception;

    void deleteAuthor(Integer authorId) throws Exception;    
    
    void saveAuthor(Integer authorId, String authorName) throws Exception;
    
}
