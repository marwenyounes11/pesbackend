package tn.elan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import tn.elan.model.Article;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query(nativeQuery = true,
		   value = "select * from Article a order by a.date desc limit 2")List<Article> listArticle();

}

