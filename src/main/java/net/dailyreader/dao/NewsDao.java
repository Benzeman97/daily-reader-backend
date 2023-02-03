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
    Optional<News> findNewsByTitle(@Param("title") String title);

    @Query("from News n where n.newsId = :id")
    Optional<News> findNewsById(@Param("id") int id);

    @Query(value = "select news_id from news n order by n.posted_date_time,n.news_id desc limit 1",nativeQuery = true)
    int findLastUpdatedNewsId();

    @Query(value = "select * from news n where lower(n.main_news) = :is_main_news and lower(n.is_published) = :is_published order by n.posted_date_time desc limit :limit offset :offset",nativeQuery = true)
    Optional<List<News>> getNewsList(@Param("is_main_news") String is_main_news,@Param("is_published") String is_published,@Param("offset") int offset,@Param("limit") int limit);


    @Query(value = "select * from news n where lower(n.is_published) = :is_published and lower(n.news_type) like concat('%',lower(:type),'%') order by n.posted_date_time desc limit :limit offset :offset",nativeQuery = true)
    Optional<List<News>> getNewsListByType(@Param("type") String type,@Param("is_published") String is_published,@Param("offset") int offset,@Param("limit") int limit);
    @Query(value = "select count(*) from news n where lower(n.is_published) = :is_published",nativeQuery = true)
    long countNews(@Param("is_published") String is_published);

    @Query(value = "select count(*) from news n where lower(n.is_published) = :is_published and lower(n.news_type) like concat('%',lower(:type),'%')",nativeQuery = true)
    long countNewsByType(@Param("type") String type,@Param("is_published") String is_published);

    @Query(value = "select * from news n where lower(n.is_published) = :is_published and lower(n.title) like concat('%',lower(:name),'%') limit 8",nativeQuery = true)
    Optional<List<News>> getNewsBySearch(@Param("name") String name,@Param("is_published") String is_published);

    @Query(value = "select * from news n where lower(n.is_affiliated) = :is_affiliated and lower(n.main_news) = :is_main_news and lower(n.is_published) = :is_published order by n.updated_date_time desc limit 1",nativeQuery = true)
    Optional<News> getMainNews(@Param("is_affiliated") String is_affiliated,@Param("is_main_news") String is_main_news,@Param("is_published") String is_published);

    @Query(value = "select * from news n where lower(n.is_published) = :is_published and lower(n.is_affiliated) = :is_affiliated and n.posted_date_time between CURDATE() - interval 10 day and CURDATE() order by n.views desc limit 4",nativeQuery = true)
    Optional<List<News>> getTrendingNewsList(@Param("is_affiliated") String is_affiliated,@Param("is_published") String is_published);

    @Query(value = "select * from news n where lower(n.news_type) like concat('%',lower(:type),'%') and lower(n.is_published) = :is_published and n.posted_date_time between CURDATE() - interval 30 day and CURDATE() limit 8",nativeQuery = true)
    Optional<List<News>> getRelatedNews(@Param("type") String type,@Param("is_published") String is_published);

}
