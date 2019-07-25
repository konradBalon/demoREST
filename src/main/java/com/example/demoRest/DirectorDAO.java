package com.example.demoRest.Controllers;

import com.example.demoRest.Model.Director;
import org.springframework.data.repository.CrudRepository;

public class DirectorDAO extends CrudRepository<Director, Long> {
    @Override
    public <S extends Director> S save(S entity) {
        return null;
    }
}
