package net.dailyreader.dao;

import net.dailyreader.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaDao extends JpaRepository<Media,Integer> {

    @Query(value = "select * from media m where m.media_type = :mediaType and m.media_num = :mediaNum and m.news_id = :newsId",nativeQuery = true)
    Optional<Media> findMedia(int newsId,String mediaType,String mediaNum);

    @Query(value = "delete from media m where m.media_type = :mediaType and m.media_num = :mediaNum and m.news_id = :newsId",nativeQuery = true)
    void deleteMedia(int newsId,String mediaType,String mediaNum);
}
