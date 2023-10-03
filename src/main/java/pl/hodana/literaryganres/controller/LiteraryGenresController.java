package pl.hodana.literaryganres.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.hodana.literaryganres.model.LiteraryGenre;

import java.net.MalformedURLException;
import java.net.URL;


@RequestMapping("/genre")
@Controller
public class LiteraryGenresController {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(LiteraryGenresController.class);
    private URL url;

    public LiteraryGenresController() {
        this.restTemplate = new RestTemplate();
    }

    private LiteraryGenre[] getInformationAboutGenre() {
        try {
            url = new URL("https://wolnelektury.pl/api/genres/");

        } catch (MalformedURLException e) {
            logger.error("incorrect url format", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error download date", e.getMessage());
            return null;
        }
        return restTemplate.getForObject(url.toString(), LiteraryGenre[].class);
    }

    @GetMapping
    public String getGenres(Model model) {
        LiteraryGenre[] genres = getInformationAboutGenre();
        if (genres == null)
            model.addAttribute("Empty", "Table is Empty");

        model.addAttribute("genres", genres);
        return "genreView";
    }

}
