package urlshortener.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urlshortener.app.domain.Shortener;

import java.util.List;

@Repository
public interface ShortenerRepository extends JpaRepository<Shortener, Long> {

//    Shortener findByIdKey(String idKey);
    public List<Shortener> findByDelDateIsNull();


}
