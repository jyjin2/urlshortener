package urlshortener.app.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import urlshortener.app.common.URLValidator;
import urlshortener.app.service.URLConverterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;


@RestController
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;

    public URLController(URLConverterService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

//    @RequestMapping(value = "/shortenUrl", method=RequestMethod.POST
//            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
//            , produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) // shortener
    @RequestMapping(value = "/shortenUrl", method=RequestMethod.POST) // consumes = {"application/json"})
    public String shortenUrl(@Valid final @NotNull ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
        String longUrl = shortenRequest.getUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl)) {
            String localURL = request.getRequestURL().toString();

            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
            LOGGER.info("Shortened url to: " + shortenedUrl);
            return shortenedUrl; // index shortenedUrl
        }
        throw new Exception("Please enter a valid URL.");
    }


//    @PostMapping("/shortener")
//    public String shortenUrl(@Valid final @NotNull ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
//        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
//        String longUrl = shortenRequest.getUrl();
//        if (URLValidator.INSTANCE.validateURL(longUrl)) {
//            String localURL = request.getRequestURL().toString();
//
//            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
//            LOGGER.info("Shortened url to: " + shortenedUrl);
//            return shortenedUrl; // index shortenedUrl
//        }
//        throw new Exception("Please enter a valid URL.");
//    }

    @RequestMapping(value = "/test", method=RequestMethod.POST, consumes = {"application/json"})
    public String test(ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
        LOGGER.info("Received url to test : " + shortenRequest.getUrl());
      return shortenRequest.getUrl();
    }

    @PostMapping("/post1")
    public void post1(@RequestBody ShortenRequest shortenRequest){
        LOGGER.info("Received url to test : " + shortenRequest.getUrl());
    }

    @RequestMapping(value = "/id/{id}", method=RequestMethod.GET)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        LOGGER.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlConverterService.getLongURLFromID(id);
        LOGGER.info("Original URL: " + redirectUrlString);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrlString);
        return redirectView;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //
}