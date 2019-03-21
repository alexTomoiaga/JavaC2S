package launchcode.org.ballersGuide.controllers;

import launchcode.org.ballersGuide.models.Player;
import launchcode.org.ballersGuide.models.Position;
import launchcode.org.ballersGuide.models.data.PlayerDao;
import launchcode.org.ballersGuide.models.data.PositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private PositionDao positionDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("players", playerDao.findAll());
        model.addAttribute("title", "Ballers Guide");

        return "player/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddPlayerForm(Model model) {
        model.addAttribute("title", "Add Player");
        model.addAttribute(new Player());
        model.addAttribute("positions", positionDao.findAll());
        return "player/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddPlayerForm(@ModelAttribute @Valid Player newPlayer,
                                       @RequestParam int positionId,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "player/add";
        }

        Position pos = positionDao.findOne(positionId);
        newPlayer.setPosition(pos);
        playerDao.save(newPlayer);
        return "redirect:/player";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("players", playerDao.findAll());
        model.addAttribute("title", "Remove Player");
        return "player/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] playerIds) {

        for (int playerId : playerIds) {
            playerDao.delete(playerId);
        }

        return "redirect:/player";
    }

    @RequestMapping(value = "edit/{playerId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int playerId) {

        model.addAttribute("title", "Edit Player");
        model.addAttribute("player", playerDao.findOne(playerId));
        model.addAttribute("positions", positionDao.findAll());
        return "player/edit";
    }

    @RequestMapping(value = "edit/{playerId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int playerId,
                                  @ModelAttribute  @Valid Player newPlayer,
                                  @RequestParam int positionId,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "player/edit";
        }

        Player editedPlayer = playerDao.findOne(playerId);
        editedPlayer.setName(newPlayer.getName());
        editedPlayer.setAgeInLeague(newPlayer.getAgeInLeague());
        editedPlayer.setSize(editedPlayer.getSize());
        editedPlayer.setNumber(newPlayer.getNumber());
        editedPlayer.setPosition(positionDao.findOne(positionId));
        playerDao.save(editedPlayer);

        return "redirect:/player";
    }

    @RequestMapping(value = "position", method = RequestMethod.GET)
    public String position(Model model, @RequestParam int id){

        Position pos = positionDao.findOne(id);
        List<Player> players = pos.getPlayers();
        model.addAttribute("players", players);
        model.addAttribute("title", "Players in Position: " + pos.getName());
        return "player/index";
    }

    @RequestMapping(value = "index-admin")
    public String indexAdmin(Model model) {

        model.addAttribute("players", playerDao.findAll());
        model.addAttribute("title", "Ballers Guide");

        return "player/index-admin";
    }

    @RequestMapping(value = "add-admin", method = RequestMethod.GET)
    public String displayAddPlayerFormAdmin(Model model) {
        model.addAttribute("title", "Add Player");
        model.addAttribute(new Player());
        model.addAttribute("positions", positionDao.findAll());
        return "player/add-admin";
    }

    @RequestMapping(value = "add-Admin", method = RequestMethod.POST)
    public String processAddPlayerFormAdmin(@ModelAttribute @Valid Player newPlayer,
                                       @RequestParam int positionId,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "player/add-admin";
        }

        Position pos = positionDao.findOne(positionId);
        newPlayer.setPosition(pos);
        playerDao.save(newPlayer);
        return "redirect:/player/index-admin";
    }

    @RequestMapping(value = "remove-admin", method = RequestMethod.GET)
    public String displayRemovePlayerFormAdmin(Model model) {
        model.addAttribute("players", playerDao.findAll());
        model.addAttribute("title", "Remove Player");
        return "player/remove-admin";
    }

    @RequestMapping(value = "remove-Admin", method = RequestMethod.POST)
    public String processRemovePlayerFormAdmin(@RequestParam int[] playerIds) {

        for (int playerId : playerIds) {
            playerDao.delete(playerId);
        }

        return "redirect:/player/index-admin";
    }

    @RequestMapping(value = "edit-admin/{playerId}", method = RequestMethod.GET)
    public String displayEditPlayerFormAdmin(Model model, @PathVariable int playerId) {

        model.addAttribute("title", "Edit Player");
        model.addAttribute("player", playerDao.findOne(playerId));
        model.addAttribute("positions", positionDao.findAll());
        return "player/edit-admin";
    }

    @RequestMapping(value = "edit-admin/{playerId}", method = RequestMethod.POST)
    public String processEditFormAdmin(Model model, @PathVariable int playerId,
                                  @ModelAttribute  @Valid Player newPlayer,
                                  @RequestParam int positionId,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "player/edit-admin";
        }

        Player editedPlayer = playerDao.findOne(playerId);
        editedPlayer.setName(newPlayer.getName());
        editedPlayer.setAgeInLeague(newPlayer.getAgeInLeague());
        editedPlayer.setSize(editedPlayer.getSize());
        editedPlayer.setNumber(newPlayer.getNumber());
        editedPlayer.setPosition(positionDao.findOne(positionId));
        playerDao.save(editedPlayer);

        return "redirect:/player/index-admin";
    }

    @RequestMapping(value = "position-admin", method = RequestMethod.GET)
    public String positionAdmin(Model model, @RequestParam int id){

        Position pos = positionDao.findOne(id);
        List<Player> players = pos.getPlayers();
        model.addAttribute("players", players);
        model.addAttribute("title", "Players in Position: " + pos.getName());
        return "player/index-admin";
    }

}
