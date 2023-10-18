package academy.devdojo.springboot2essentials.client;

import academy.devdojo.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);

        log.info(Arrays.toString(animes));

        //GET
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        log.info(exchange.getBody());

//        Anime test1 = Anime.builder().name("test1").build();
//        Anime animeSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", test1, Anime.class);
//        log.info("Saved Anime {}", animeSaved);


        //POST
        Anime test2 = Anime.builder().name("test2").build();
        ResponseEntity<Anime> testExchange = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(test2, createJsonHeader()),
                Anime.class);
        log.info("saved animed {}", testExchange);


        //PUT
        Anime animeToBeUpdated = testExchange.getBody();
        animeToBeUpdated.setName("Samurai champloo 2");

        ResponseEntity<Void> testExchangeUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info("Updated {}", testExchangeUpdated);


        //DELETE
        ResponseEntity<Void> testExchangeDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());

        log.info("Delete animed {}", testExchangeDeleted);
    }
    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
