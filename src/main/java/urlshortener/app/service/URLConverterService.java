package urlshortener.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.app.common.IDConverter;
import urlshortener.app.domain.Shortener;
import urlshortener.app.repository.ShortenerRepository;
import urlshortener.app.repository.URLRepository;

import java.util.Optional;

@Service
public class URLConverterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);
    private final URLRepository urlRepository;

    @Autowired
    ShortenerRepository shortenerRepository;

    @Autowired
    public URLConverterService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(String localURL, String longUrl) {
        LOGGER.info("Shortening {}", longUrl);
//        Long id = urlRepository.incrementID();
        // todo DB
        Long id = shortenerRepository.count();
        String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
//        urlRepository.saveUrl("url:"+id, longUrl);
        //urlRepository2.saveUrl("url:"+id, longUrl);
        Shortener shortener = new Shortener();
        shortener.setUrlKey(longUrl);
        shortener.setShortUrl(uniqueID);
        shortenerRepository.save(shortener);
        String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = baseString + "id/" + uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
//        String longUrl = urlRepository.getUrl(dictionaryKey);
        Optional<Shortener> optional = shortenerRepository.findById(dictionaryKey);
        if(!optional.isPresent()) {
            throw new Exception("shortener is empty");
        }
        String longUrl = optional.get().getUrlKey();
        LOGGER.info("Converting shortened URL back to {}", longUrl);
        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        // remove the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }

}
