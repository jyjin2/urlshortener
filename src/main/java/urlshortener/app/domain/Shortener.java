package urlshortener.app.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

// 1-100 cycle

@Entity
@Table(name = "shortener")
@SequenceGenerator(name="URL_KEY_SEQ_GEN", sequenceName="URL_KEY_SEQ",initialValue=0,allocationSize=1)
public class Shortener {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="URL_KEY_SEQ_GEN")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shortened", length=10)
    private String shortUrl;

    @Column(name = "urlKey", length=255)
    private String urlKey;

//    @Column(name = "regDate", updatable = false)
    @Column(name = "regDate")
    private LocalDateTime regDate;

//    @Column(name = "delDate", updatable = false)
    @Column(name = "delDate")
    private LocalDateTime delDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getDelDate() {
        return delDate;
    }

    public void setDelDate(LocalDateTime delDate) {
        this.delDate = delDate;
    }

}