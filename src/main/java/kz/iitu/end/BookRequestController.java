package kz.iitu.end;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book/request")
public class BookRequestController {

    private final Producer producer;
    private BookInfoService bookInfoService;

    @Autowired
    public BookRequestController(Producer producer, BookInfoService bookInfoService) {
        this.producer = producer;
        this.bookInfoService = bookInfoService;
    }

    // TODO Ideally there should POST request
    @GetMapping
    public String sendMessageToKafkaTopic2(@RequestParam("userId") String userId,
                                           @RequestParam("bookId") Long bookId) {

        BookRequest bookRequest = new BookRequest(userId, bookInfoService.getBookById(bookId));
        this.producer.bookRequestNotify(bookRequest);
        return "Your request sent successful!";
    }
}