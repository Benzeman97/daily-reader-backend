package net.dailyreader.dao;

import net.dailyreader.entity.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParagraphDao extends JpaRepository<Paragraph,Integer> {

    @Query(value = "select * from paragraph p where p.paragraph_num = :paraNum and p.news_id = :newsId",nativeQuery = true)
    Optional<Paragraph> findParagraph(int newsId, String paraNum);

    @Query(value = "delete from paragraph p where p.paragraph_num = :paraNum and p.news_id = :newsId",nativeQuery = true)
    void deleteParagraph(int newsId,String paraNum);
}
