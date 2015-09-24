package edu.wctc.kh.mybookapp2.models;

import java.util.List;

/**
 *
 * @author jlombardo
 */
public interface AuthorDaoStrategy {

    List<Author> getAllAuthors() throws Exception;
    
}
