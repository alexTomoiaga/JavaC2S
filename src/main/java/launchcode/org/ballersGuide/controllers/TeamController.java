package launchcode.org.ballersGuide.controllers;


import launchcode.org.ballersGuide.models.Player;
import launchcode.org.ballersGuide.models.Team;
import launchcode.org.ballersGuide.models.data.PlayerDao;
import launchcode.org.ballersGuide.models.data.TeamDao;
import launchcode.org.ballersGuide.models.forms.AddTeamItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;

@Controller
@RequestMapping(value = "team")
public class TeamController {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private PlayerDao playerDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Teams");
        model.addAttribute("teams", teamDao.findAll());

        return "team/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add team");
        model.addAttribute(new Team());
        return "team/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Team newTeam,
                      Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add team");
            return "team/add";
        }

        teamDao.save(newTeam);
        return "redirect:view/" + newTeam.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable int id) {

        Team team = teamDao.findOne(id);

        model.addAttribute("title", team.getName());
        model.addAttribute("team", team);
        return "team/view";
    }

    @RequestMapping(value = "add-player/{teamId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int teamId) {

        Team team = teamDao.findOne(teamId);

        AddTeamItemForm itemForm = new AddTeamItemForm(team, playerDao.findAll());

        model.addAttribute("title", "Add player to team: " + team.getName());
        model.addAttribute("form", itemForm);

        return "team/add-player";
    }

    @RequestMapping(value = "add-player/{teamId}", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddTeamItemForm itemForm,
                          Errors errors, @PathVariable int teamId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "team/add-player/" + teamId;
        }

        Team team = teamDao.findOne(itemForm.getTeamId());
        Player player = playerDao.findOne(itemForm.getPlayerId());

        team.addPlayer(player);
        teamDao.save(team);

        return "redirect:../view/" + team.getId();
    }

    @RequestMapping(value = "remove-player/{teamId}", method = RequestMethod.GET)
    public String removeItem(Model model, @PathVariable int teamId) {

        Team team = teamDao.findOne(teamId);

        AddTeamItemForm itemForm = new AddTeamItemForm(team, team.getPlayers());

        model.addAttribute("title", "Remove player From team: " + team.getName());
        model.addAttribute("form", itemForm);

        return "team/remove-player";
    }

    @RequestMapping(value = "remove-player/{teamId}", method = RequestMethod.POST)
    public String removeItem(Model model, @ModelAttribute @Valid AddTeamItemForm itemForm,
                             Errors errors, @PathVariable int teamId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Remove Player");
            return "team/add-player/" + teamId;
        }

        Team team = teamDao.findOne(itemForm.getTeamId());
        Player player = playerDao.findOne(itemForm.getPlayerId());

        team.removePlayer(player);
        teamDao.save(team);

        return "redirect:../view/" + team.getId();
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("teams", teamDao.findAll());
        model.addAttribute("title", "Remove team");
        return "team/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] teamIds) {

        for (int teamId : teamIds) {
            teamDao.delete(teamId);
        }

        return "redirect:/team";
    }

    @RequestMapping(value = "edit/{teamId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int teamId) {

        model.addAttribute("title", "Edit team");
        model.addAttribute("team", teamDao.findOne(teamId));
        model.addAttribute("players", playerDao.findAll());
        return "team/edit";
    }

    @RequestMapping(value = "edit/{teamId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int teamId,
                                  @ModelAttribute  @Valid Team newTeam,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit team");
            return "team/edit";
        }

        Team editedTeam = teamDao.findOne(teamId);
        editedTeam.setName(newTeam.getName());
        teamDao.save(editedTeam);

        return "redirect:/team";
    }

    @RequestMapping(value = "index-admin")
    public String indexAdmin(Model model) {

        model.addAttribute("title", "Teams");
        model.addAttribute("teams", teamDao.findAll());

        return "team/index-admin";
    }

    @RequestMapping(value = "add-admin", method = RequestMethod.GET)
    public String addAdmin(Model model) {
        model.addAttribute("title", "Add team");
        model.addAttribute(new Team());
        return "team/add-admin";
    }

    @RequestMapping(value = "add-admin", method = RequestMethod.POST)
    public String addAdmin(Model model, @ModelAttribute @Valid Team newTeam,
                      Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add team");
            return "team/add-admin";
        }

        teamDao.save(newTeam);
        return "redirect:view-admin/" + newTeam.getId();
    }

    @RequestMapping(value = "view-admin/{id}", method = RequestMethod.GET)
    public String viewAdmin(Model model, @PathVariable int id) {

        Team team = teamDao.findOne(id);

        model.addAttribute("title", team.getName());
        model.addAttribute("team", team);
        return "team/view-admin";
    }

    @RequestMapping(value = "add-player-admin/{teamId}", method = RequestMethod.GET)
    public String addItemAdmin(Model model, @PathVariable int teamId) {

        Team team = teamDao.findOne(teamId);

        AddTeamItemForm itemForm = new AddTeamItemForm(team, playerDao.findAll());

        model.addAttribute("title", "Add player to team: " + team.getName());
        model.addAttribute("form", itemForm);

        return "team/add-player-admin";
    }

    @RequestMapping(value = "add-player-admin/{teamId}", method = RequestMethod.POST)
    public String addItemAdmin(Model model, @ModelAttribute @Valid AddTeamItemForm itemForm,
                          Errors errors, @PathVariable int teamId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Player");
            return "team/add-player-admin/" + teamId;
        }

        Team team = teamDao.findOne(itemForm.getTeamId());
        Player player = playerDao.findOne(itemForm.getPlayerId());

        team.addPlayer(player);
        teamDao.save(team);

        return "redirect:../view-admin/" + team.getId();
    }

    @RequestMapping(value = "remove-player-admin/{teamId}", method = RequestMethod.GET)
    public String removeItemAdmin(Model model, @PathVariable int teamId) {

        Team team = teamDao.findOne(teamId);

        AddTeamItemForm itemForm = new AddTeamItemForm(team, team.getPlayers());

        model.addAttribute("title", "Remove player From team: " + team.getName());
        model.addAttribute("form", itemForm);

        return "team/remove-player-admin";
    }

    @RequestMapping(value = "remove-player-admin/{teamId}", method = RequestMethod.POST)
    public String removeItemAdmin(Model model, @ModelAttribute @Valid AddTeamItemForm itemForm,
                             Errors errors, @PathVariable int teamId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Remove Player");
            return "team/add-player-admin/" + teamId;
        }

        Team team = teamDao.findOne(itemForm.getTeamId());
        Player player = playerDao.findOne(itemForm.getPlayerId());

        team.removePlayer(player);
        teamDao.save(team);

        return "redirect:../view-admin/" + team.getId();
    }

    @RequestMapping(value = "remove-admin", method = RequestMethod.GET)
    public String displayRemovePlayerFormAdmin(Model model) {
        model.addAttribute("teams", teamDao.findAll());
        model.addAttribute("title", "Remove team");
        return "team/remove-admin";
    }

    @RequestMapping(value = "remove-admin", method = RequestMethod.POST)
    public String processRemovePlayerFormAdmin(@RequestParam int[] teamIds) {

        for (int teamId : teamIds) {
            teamDao.delete(teamId);
        }

        return "redirect:/team/index-admin";
    }

    @RequestMapping(value = "edit-admin/{teamId}", method = RequestMethod.GET)
    public String displayEditPlayerFormAdmin(Model model, @PathVariable int teamId) {

        model.addAttribute("title", "Edit team");
        model.addAttribute("team", teamDao.findOne(teamId));
        model.addAttribute("players", playerDao.findAll());
        return "team/edit-admin";
    }

    @RequestMapping(value = "edit-admin/{teamId}", method = RequestMethod.POST)
    public String processEditFormAdmin(Model model, @PathVariable int teamId,
                                  @ModelAttribute  @Valid Team newTeam,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit team");
            return "team/edit-admin";
        }

        Team editedTeam = teamDao.findOne(teamId);
        editedTeam.setName(newTeam.getName());
        teamDao.save(editedTeam);

        return "redirect:/team/index-admin";
    }
}
