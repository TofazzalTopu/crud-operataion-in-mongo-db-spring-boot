package com.info.encrypted.controller;


import com.info.encrypted.model.Tutorial;
import com.info.encrypted.repository.TutorialRepository;
import com.info.encrypted.service.EncryptorDecryptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/")
public class CreateController {
    private static final Logger logger = LoggerFactory.getLogger(CreateController.class);

    @Autowired
    EncryptorDecryptorService encryptorDecryptorService;

    @Autowired
    TutorialRepository tutorialRepository;

    // @Comment: This method will render form to create tutorial.
    @RequestMapping(method = RequestMethod.GET, value = {"/tutorialCreateForm"})
    public String tutorialForm(Model model) {
        Tutorial tutorial = new Tutorial();
        model.addAttribute("tutorial", tutorial);
        return "tutorial/create-tutorial";
    }


    // @Comment: This method will create tutorial and will render tutorials list form after create tutorial.
    @PostMapping("/create")
    public String createTutorial(Model model, @ModelAttribute("tutorial") Tutorial tutorial) throws IOException {
        try {
            tutorial.setTitle(encryptorDecryptorService.setEncryptedValue(tutorial.getTitle()));
            tutorial.setDescription(encryptorDecryptorService.setEncryptedValue(tutorial.getDescription()));
            tutorial.setPublished(true);
            Tutorial _tutorial = tutorialRepository.save(tutorial);

            if (_tutorial == null) {
                logger.info("Tutorials not saved!");
                model.addAttribute("error", "Tutorials not saved!");
                return "tutorial/tutorial-list";
            }

            List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

            List<Tutorial> tutorialList = new ArrayList<>();
            tutorials.forEach(val -> {
                val.setTitle(encryptorDecryptorService.getDecryptedValue(val.getTitle()));
                val.setDescription(encryptorDecryptorService.getDecryptedValue(val.getDescription()));
                tutorialList.add(val);
            });
            model.addAttribute("tutorialList", tutorialList);
            return "tutorial/tutorial-list";
        } catch (Exception e) {
            return "tutorial/tutorial-list";
        }
    }

    // @Comment: This method will render tutorials list form.
    @RequestMapping(method = RequestMethod.GET, value = {"/tutorialList"})
    public String create(Model model) throws IOException {
        List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if (tutorials.isEmpty()) {
            model.addAttribute("error", "No data found.");
        }else {
            List<Tutorial> tutorialList = new ArrayList<>();
            tutorials.forEach(tutorial -> {
                tutorial.setTitle(encryptorDecryptorService.getDecryptedValue(tutorial.getTitle()));
                tutorial.setDescription(encryptorDecryptorService.getDecryptedValue(tutorial.getDescription()));
                tutorialList.add(tutorial);
            });
            model.addAttribute("tutorialList", tutorialList);
        }
        return "tutorial/tutorial-list";
    }





}
