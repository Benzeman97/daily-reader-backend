package net.dailyreader.dao;

import net.dailyreader.entity.News;
import net.dailyreader.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParagraphDao extends JpaRepository<Paragraph,Integer> {

    @Query("from Paragraph p where p.newsId = :id")
    Optional<List<Paragraph>> findNewsById(@Param("id") int id);

    @Query(value = "select * from paragraph p where p.paragraph_num = :paraNum and p.news_id = :newsId",nativeQuery = true)
    Optional<Paragraph> findParagraph(int newsId, String paraNum);

    @Query(value = "delete from paragraph p where p.paragraph_num = :paraNum and p.news_id = :newsId",nativeQuery = true)
    void deleteParagraph(int newsId,String paraNum);
}
