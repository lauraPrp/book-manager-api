package com.techreturners.bookmanager.controller;

import com.techreturners.bookmanager.exception.ApiRequestException;
import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        List<Book> books = bookManagerService.getAllBooks();
if(books.isEmpty()){
    return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
}
           return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping({"/{bookId}"})
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        ResponseEntity<Book> result = null;
        try{
             Book retrievedBook =  bookManagerService.getBookById(bookId);
          if(retrievedBook==null) {
             return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
          }
          else {result=new ResponseEntity<>(retrievedBook, HttpStatus.FOUND);}

         }catch(NoSuchElementException nse){

             result= new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
         }
             return result;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        HttpHeaders httpHeaders = null;
            Book newBook = bookManagerService.insertBook(book);
            httpHeaders = new HttpHeaders();
            httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());

           if(newBook.getId()==null)
               return new ResponseEntity<>(book, httpHeaders, HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);

    }

    //User Story 4 - Update Book By Id Solution
    @PutMapping({"/{bookId}"})
    public ResponseEntity<Book> updateBookById(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        try {
            bookManagerService.updateBookById(bookId, book);
        } catch (NoSuchElementException nsee) {
            throw new ApiRequestException("No book found with id "+bookId);
        }
        return new ResponseEntity<>(bookManagerService.getBookById(bookId), HttpStatus.OK);
    }

    @DeleteMapping({"/{bookId}"})
    public ResponseEntity<Book> deleteBookById(@PathVariable("bookId") Long bookId) {
        try {
            bookManagerService.deleteBookById(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception nsee) {
            throw new ApiRequestException("No book found with id "+bookId);
        }
    }

}
