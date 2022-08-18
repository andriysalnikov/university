package ua.com.foxminded.andriysalnikov.university.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityController.class);

    @GetMapping("/")
    public String universityStartPage() {
        return "university";
    }

}


