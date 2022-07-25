package urlshortener.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import urlshortener.app.domain.Shortener;
import urlshortener.app.repository.ShortenerRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
//@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ShortenerRepository shortenerRepository;

//    private final URLConverterService urlConverterService;
//
//    public MainController(URLConverterService urlConverterService) {
//        this.urlConverterService = urlConverterService;
//    }
//
//    @GetMapping("/")
//    public String indexDefalut(){
//        return "index";
//    }

    //    @GetMapping("/")
//    public String index2(Model model){
//        model.addAttribute("test", "good job");
//        return "index";
//    }
//
    @GetMapping("/history")
    public String history(Model model) {
        // todo get history data
        // todo set model

        model.addAttribute("tasks", shortenerRepository.findByDelDateIsNull()); // ShortenerRepository.findAll()
        return "history";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id, Model model) {

        LOGGER.info("test {}", shortenerRepository.findByDelDateIsNull());

        Optional<Shortener> optionalShortener = shortenerRepository.findById(id);
        if (optionalShortener.isPresent()) {
            Shortener shortener = optionalShortener.get();
            shortener.setDelDate(LocalDateTime.now());
            shortenerRepository.save(shortener);

            model.addAttribute("tasks", shortenerRepository.findByDelDateIsNull());

//            Shortener fromShortener = optionalShortener.get();
//            Shortener toShortener = new Shortener();
//            toShortener.setId(fromShortener.getId());
//            toShortener.setDelDate(LocalDateTime.now());
//            toShortener.setRegDate(fromShortener.getRegDate());
//            toShortener.setShortUrl(fromShortener.getShortUrl());
//            toShortener.setUrlKey(fromShortener.getUrlKey());
//            shortenerRepository.save(toShortener);
            return "history";
        }

        model.addAttribute("errorMsg", "error");
        return "history";
    }
}




//
//
//    @RequestMapping(value = "/sUrl", method= {RequestMethod.GET, RequestMethod.POST}
//            , consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
//            , produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) // shortener
//    public String shortenUrl(@Valid final @NotNull ShortenRequest shortenRequest, HttpServletRequest request, Model model) throws Exception {
//        LOGGER.info("Received url to shorten: " + shortenRequest.getUrl());
//        String longUrl = shortenRequest.getUrl();
//        if (URLValidator.INSTANCE.validateURL(longUrl)) {
//            String localURL = request.getRequestURL().toString();
//
//            String shortenedUrl = urlConverterService.shortenURL(localURL, shortenRequest.getUrl());
//            LOGGER.info("Shortened url to: " + shortenedUrl);
////            return shortenedUrl; // index shortenedUrl
//            model.addAttribute("longurl", longUrl);
//            model.addAttribute("surl", shortenedUrl);
//            return shortenedUrl;
//        } else {
//            return "error";
////        throw new Exception("Please enter a valid URL.");
//        }
//    }
//}
