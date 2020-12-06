package kz.iitu.end;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Service
public class BookInfoService {

    @Autowired
    private RestTemplate restTemplate;

//        @GetMapping("/books/")
//    public ModelAndView getAllBooks() { ResponseEntity<List<Book>> response = restTemplate.exchange(
//            "http://book-service/books/list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() { });
//        List<Book> books = response.getBody();
//            ModelAndView modelAndView = new ModelAndView("books");
//            modelAndView.addObject("booklist", books);
//        return modelAndView;
//    }

    @GetMapping("/books/")
    @HystrixCommand
    public List<Book> getAllBooks() {
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "http://book-service/books/list", HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {
                });
        List<Book> books = response.getBody();
        return books;
    }


    @RequestMapping("/books/{id}")
    @HystrixCommand(fallbackMethod = "getBookByIdFallback",
            threadPoolKey = "BookById",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "100"),
                    @HystrixProperty(name = "maxQueueSize", value = "50"),
            })
    public Book getBookById(@PathVariable("id") Long id) {
        Book book = restTemplate.getForObject("http://book-service/books/" + id, Book.class);
        return book;
    }

    public Book getBookByIdFallback(@PathVariable("id") Long id) {
        return new Book("No book", (double) 0);
    }

}
