package urlshortener.app.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import urlshortener.app.common.URLValidator;
import urlshortener.app.service.URLConverterService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
//@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final URLConverterService urlConverterService;

    public MainController(URLConverterService urlConverterService) {
        this.urlConverterService = urlConverterService;
    }

//    @GetMapping("/")
//    public String indexDefalut(){
//        return "index";
//    }

//    @GetMapping("/")
//    public String index2(Model model){
//        model.addAttribute("test", "good job");
//        return "index";
//    }

//    @GetMapping("/index3")
//    public String index3(){
//        return "invalidURL";
//    }


    @RequestMapping(value = "/sUrl", method= {RequestMethod.GET, RequestMethod.POST}
            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
            , produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) // shortener
    public String shortenUrl(@Valid final @NotNull ShortenRequest shortenRequest, HttpServletRequest request, Model model) throws Exception {
        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
        String longUrl = shortenRequest.getUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl)) {
            String localURL = request.getRequestURL().toString();

            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
            LOGGER.info("Shortened url to: " + shortenedUrl);
//            return shortenedUrl; // index shortenedUrl
            model.addAttribute("longurl", longUrl);
            model.addAttribute("surl", shortenedUrl);
            return shortenedUrl;
        } else {
            return "error";
//        throw new Exception("Please enter a valid URL.");
        }
    }
}
