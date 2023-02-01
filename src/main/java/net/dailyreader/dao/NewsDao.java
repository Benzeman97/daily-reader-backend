package net.dailyreader.dao;

import net.dailyreader.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsDao extends JpaRepository<News,Integer> {

    @Query("from News n where lower(n.title) = :title")
    Optional<News> findNewsByTitle(String title);

    @Query(value = "select news_id from news n order by n.posted_date_time,n.news_id desc limit 1",nativeQuery = true)
    int findLastUpdatedNewsId();

    @Query(value = "select * from news n where lower(n.news_type) not like concat('%',:type,'%') and lower(n.is_affiliated) != :is_affiliated and lower(n.main_news) != :is_main_news order by n.posted_date_time desc limit :limit offset :offset",nativeQuery = true)
    Optional<List<News>> getNewsList(@Param("type")String type,@Param("is_affiliated")String is_affiliated,@Param("is_main_news") String is_main_news,@Param("offset") int offset,@Param("limit") int limit);

    @Query(value = "select count(*) from news",nativeQuery = true)
    long countNews();

    @Query(value = "select * from news n where lower(n.news_type) not like concat('%',:type,'%') and lower(n.is_affiliated) != :is_affiliated and lower(n.main_news) != :is_main_news order by n.updated_date_time desc limit 1",nativeQuery = true)
    Optional<News> getMainNews(@Param("type")String type,@Param("is_affiliated")String is_affiliated,@Param("is_main_news") String is_main_news);
}
