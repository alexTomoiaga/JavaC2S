package launchcode.org.ballersGuide.controllers;

import launchcode.org.ballersGuide.models.Player;
import launchcode.org.ballersGuide.models.Position;
import launchcode.org.ballersGuide.models.data.PositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("position")
public class PositionController {

    @Autowired
    private PositionDao positionDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("positions", positionDao.findAll());
        model.addAttribute("title", "Positions");

        return "position/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Position");
        model.addAttribute("position", new Position());
        return "position/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Position position, Errors errors) {

        if (errors.hasErrors()) {
            return "position/add";
        }
        positionDao.save(position);
        return "redirect:/position";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("positions", positionDao.findAll());
        model.addAttribute("title", "Remove Position");
        return "position/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] positionIds) {

        for (int positionId : positionIds) {
            positionDao.delete(positionId);
        }

        return "redirect:/position";
    }

    @RequestMapping(value = "edit/{positionId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int positionId) {

        model.addAttribute("title", "Edit Player");
        model.addAttribute("position", positionDao.findOne(positionId));
        return "position/edit";
    }

    @RequestMapping(value = "edit/{positionId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int positionId,
                                  @ModelAttribute  @Valid Position newPosition,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Position");
            return "position/edit";
        }

        Position editedPosition = positionDao.findOne(positionId);
        editedPosition.setName(newPosition.getName());
        positionDao.save(editedPosition);

        return "redirect:/position";
    }

    @RequestMapping(value = "index-admin")
    public String indexAdmin(Model model) {

        model.addAttribute("positions", positionDao.findAll());
        model.addAttribute("title", "Positions");

        return "position/index-admin";
    }

    @RequestMapping(value = "add-admin", method = RequestMethod.GET)
    public String addAdmin(Model model) {

        model.addAttribute("title", "Position");
        model.addAttribute("position", new Position());
        return "position/add-admin";
    }

    @RequestMapping(value = "add-admin", method = RequestMethod.POST)
    public String addAdmin(Model model,
                      @ModelAttribute @Valid Position position, Errors errors) {

        if (errors.hasErrors()) {
            return "position/add-admin";
        }
        positionDao.save(position);
        return "redirect:/position/index-admin";
    }

    @RequestMapping(value = "remove-admin", method = RequestMethod.GET)
    public String displayRemovePlayerFormAdmin(Model model) {
        model.addAttribute("positions", positionDao.findAll());
        model.addAttribute("title", "Remove Position");
        return "position/remove-admin";
    }

    @RequestMapping(value = "remove-admin", method = RequestMethod.POST)
    public String processRemovePlayerFormAdmin(@RequestParam int[] positionIds) {

        for (int positionId : positionIds) {
            positionDao.delete(positionId);
        }

        return "redirect:/position/index-admin";
    }

    @RequestMapping(value = "edit-admin/{positionId}", method = RequestMethod.GET)
    public String displayEditPlayerFormAdmin(Model model, @PathVariable int positionId) {

        model.addAttribute("title", "Edit Player");
        model.addAttribute("position", positionDao.findOne(positionId));
        return "position/edit-admin";
    }

    @RequestMapping(value = "edit-admin/{positionId}", method = RequestMethod.POST)
    public String processEditFormAdmin(Model model, @PathVariable int positionId,
                                  @ModelAttribute  @Valid Position newPosition,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Position");
            return "position/edit-admin";
        }

        Position editedPosition = positionDao.findOne(positionId);
        editedPosition.setName(newPosition.getName());
        positionDao.save(editedPosition);

        return "redirect:/position/index-admin";
    }
}
