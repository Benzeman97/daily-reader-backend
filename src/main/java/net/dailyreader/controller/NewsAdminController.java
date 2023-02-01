package net.dailyreader.controller;

import net.dailyreader.dto.request.NewsAdminRequest;
import net.dailyreader.dto.response.MediaAdminResponse;
import net.dailyreader.dto.response.NewsAdminResponse;
import net.dailyreader.dto.response.NewsShortResponse;
import net.dailyreader.dto.response.ParagraphAdminResponse;
import net.dailyreader.service.NewsAdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"https://dailyreader.net","https://www.dailyreader.net","http://127.0.0.1:3000"}, maxAge = 3600)
@RestController
@RequestMapping("/api/admin/news")
public class NewsAdminController {

    final private static Logger LOGGER = LogManager.getLogger(NewsAdminController.class);

    private NewsAdminService newsAdminService;

    public NewsAdminController(NewsAdminService newsAdminService){
        this.newsAdminService=newsAdminService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewsAdminResponse> saveNews(@RequestBody NewsAdminRequest request){

         return (request.getTitle().trim().isEmpty() || request.getPreviewImg().trim().isEmpty() ||
                 request.getImgUrl().trim().isEmpty() || request.getNewsType().trim().isEmpty()) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                 new ResponseEntity<>(newsAdminService.saveNews(request),HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<NewsShortResponse> findNews(@RequestParam("title") String title){
         return (title.trim().isEmpty()) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                 new ResponseEntity<>(newsAdminService.findNews(title),HttpStatus.OK);
    }

    @GetMapping("/para/{newsId}/{paraNum}")
    public ResponseEntity<ParagraphAdminResponse> findParagraph(@PathVariable int newsId,@PathVariable String paraNum){
        return (newsId==0 || paraNum.trim().isEmpty()) ?
        new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
        new ResponseEntity<>(newsAdminService.findParagraph(newsId,paraNum),HttpStatus.OK);
    }

    @GetMapping("/media/{newsId}/{mediaType}/{mediaNum}")
   public ResponseEntity<MediaAdminResponse> findMedia(@PathVariable int newsId,@PathVariable String mediaType,@PathVariable String mediaNum){
         return (newsId==0 || mediaType.trim().isEmpty() || mediaNum.trim().isEmpty()) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                 new ResponseEntity<>(newsAdminService.findMedia(newsId,mediaType,mediaNum),HttpStatus.OK);
   }


   @DeleteMapping("/{id}")
   public void deleteNewsById(@PathVariable int id){
        if(id!=0)
            newsAdminService.deleteNewsById(id);
   }



   @DeleteMapping("/delete/para/{newsId}/{paraNum}")
   public void deleteParagraph(@PathVariable int newsId,@PathVariable String paraNum){
        if(newsId!=0 || !paraNum.trim().isEmpty())
            newsAdminService.deleteParagraph(newsId,paraNum);
   }


    @DeleteMapping("/delete/media/{newsId}/{mediaType}/{mediaNum}")
    public void deleteMedia(@PathVariable int newsId,@PathVariable String mediaType,@PathVariable String mediaNum){

        if (newsId!=0 || !mediaType.trim().isEmpty() || !mediaNum.trim().isEmpty())
            newsAdminService.deleteMedia(newsId,mediaType,mediaNum);
   }

}
