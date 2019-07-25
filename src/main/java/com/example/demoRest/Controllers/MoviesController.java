package com.example.demoRest;


import com.example.demoRest.Excepitons.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MoviesController {
    private MovieDAO movieDAO;

    @Autowired
    public MoviesController(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }




    @RequestMapping(value = "/movies",
            method = RequestMethod.GET)
    public List<Movie> getMovies() {
        //  return movieDAO.findMoviesByIsValidIsTrue();
        return movieDAO.findAll();
    }
    @RequestMapping(value = "/title/{title}",
            method = RequestMethod.GET)
    public Movie findByTitle( @PathVariable("title") String title) {

        return movieDAO.findMovieByTitle(title);
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)

     public Movie getMovieByID(@PathVariable("id") long id){


            if (movieDAO.findMovieById(id) == null)
                throw new NotFoundException();


            return
                    movieDAO.findMovieById(id);
        }


//       public void zapisz(){movieDAO.save(
//
//
//
//               new Movie("asd","sd",232))     ;
//        }


//    //lista powitan
//    //po post dodaje sie do listy
//    //przy put podmienia sie element z listy
//    List<Movie> listaUserow = new ArrayList<>();
//
//    @RequestMapping("/helloworld")
//    public String helloWorld() {
//        return "Hello World";
//    }
//
//
//    @RequestMapping("/user/{number}")
//    public String sayHello(@PathVariable("name") String name) {
//        return "Hello " + name;
//    }

    @RequestMapping(value = "/movie",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void dodaj(@RequestBody Movie movie) {

        movieDAO.save(movie);

        System.out.println("zapisałem film " + movie);
    }
//
//    @RequestMapping(value = "/dodajKilka",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void dodajKilka(@RequestBody List<Movie> movies) {
//        listaUserow.addAll(movies);
//        System.out.println("dostalem od usera:" + movies.toString());
//    }
//
//    @GetMapping(value = "/dajListe", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void dajListe() {
//        System.out.println(listaUserow.toString());
//    }
//
//    @PutMapping(value = "/zmien/{id}",
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void change(@PathVariable("id") int id,
//                       @RequestBody Movie movie) {
//
//        System.out.println("zmieniono wpis o id: " + id + "\n" + "wpis po edycji: " + movie.toString());
//    }

}