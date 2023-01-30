package net.dailyreader.dao;

import net.dailyreader.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsDao extends JpaRepository<News,Integer> {

    @Query("from News n where lower(n.title) = :title")
    Optional<News> findNewsByTitle(String title);

    @Query(value = "select news_id from news n order by n.posted_date_time,n.news_id desc limit 1",nativeQuery = true)
    int findLastUpdatedNewsId();
}
