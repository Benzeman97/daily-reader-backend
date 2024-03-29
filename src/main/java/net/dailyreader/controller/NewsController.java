package net.dailyreader.controller;


import net.dailyreader.dto.response.*;
import net.dailyreader.model.NewsSearch;
import net.dailyreader.service.NewsAdminService;
import net.dailyreader.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://dailyreader.net","https://www.dailyreader.net","http://127.0.0.1:3000","http://127.0.0.1:8045"}, maxAge = 3600)
@RestController
@RequestMapping("/api/news")
public class NewsController {

    final private static Logger LOGGER = LogManager.getLogger(NewsAdminController.class);

    private NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService=newsService;
    }

    @GetMapping(value = "/list",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsListResponse> getNewsList(@RequestParam("page") int page){
        return (page==0) ?
             new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
             new ResponseEntity<>(newsService.getNewsList(page),HttpStatus.OK);
    }

    @GetMapping(value = "/list-type",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsListResponse> getNewsListByType(@RequestParam("type") String type,@RequestParam("page") int page){
        return (page==0 || type.trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getNewsListByType(type,page),HttpStatus.OK);
    }

    @GetMapping(value = "/search",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<NewsSearch>> getNewsBySearch(@RequestParam("q") String name){

        return (name.trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getNewsBySearch(name),HttpStatus.OK);
    }

    @GetMapping(value = "/main",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MainNewsResponse> getMainNews(){
       return new ResponseEntity<>(newsService.getMainNews(),HttpStatus.OK);
    }

    @GetMapping(value = "/trending",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TrendingNewsResponse> getTrendingNews(@RequestParam("id") int id){
        return new ResponseEntity<>(newsService.getTrendingNews(id),HttpStatus.OK);
    }

    @GetMapping(value = "/related",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RelatedNewsResponse> getRelatedNews(@RequestParam("id") int id,@RequestParam("type") String type){
        return (id <=0 || type.trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getRelatedNews(id,type),HttpStatus.OK);
    }

    @GetMapping(value = "/detail",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsResponse> getNews(@RequestParam("id") int id){
        return (id<=0) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getNews(id),HttpStatus.OK);
    }

    @GetMapping(value = "/para",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ParagraphResponse> getParagraphs(@RequestParam("id") int id){
        return (id<=0) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getParagraphs(id),HttpStatus.OK);
    }

    @GetMapping(value = "/media",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MediaResponse> getMedias(@RequestParam("id") int id){
        return (id<=0) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(newsService.getMedias(id),HttpStatus.OK);
    }
}
