/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.kh.mybookapp2.repository;

import edu.wctc.kh.mybookapp2.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Keiji
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
