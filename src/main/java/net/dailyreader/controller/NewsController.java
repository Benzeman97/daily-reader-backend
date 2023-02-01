package net.dailyreader.controller;


import net.dailyreader.dto.response.MainNewsResponse;
import net.dailyreader.dto.response.NewsListResponse;
import net.dailyreader.service.NewsAdminService;
import net.dailyreader.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://dailyreader.net","https://www.dailyreader.net","http://127.0.0.1:3000"}, maxAge = 3600)
@RestController
@RequestMapping("/api/news")
public class NewsController {

    final private static Logger LOGGER = LogManager.getLogger(NewsAdminController.class);

    private NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService=newsService;
    }

    @GetMapping(value = "/list/{page}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsListResponse> getNewsList(@PathVariable int page){
        return (page==0) ?
             new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
             new ResponseEntity<>(newsService.getNewsList(page),HttpStatus.OK);
    }

    @GetMapping(value = "/main",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MainNewsResponse> getMainNews(){
        return new ResponseEntity<>(newsService.getMainNews(),HttpStatus.OK);
    }


}
