package urlshortener.app.domain;

import javax.persistence.*;

// 1-100 cycle

@Entity
@Table(name = "shortener")
//@SequenceGenerator(name="URL_KEY_SEQ_GEN", sequenceName="URL_KEY_SEQ",initialValue=1,allocationSize=1)
public class Shortener {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="URL_KEY_SEQ_GEN")
    @Column(name = "idKey", length=10)
    private String idKey;

//    @Column(name = "shortened", length=25)
//    private String shortUrl;

    @Column(name = "urlKey", length=255)
    private String urlKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

//    public String getShortUrl() {
//        return shortUrl;
//    }
//
//    public void setShortUrl() {
//        this.shortUrl = shortUrl;
//    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

}