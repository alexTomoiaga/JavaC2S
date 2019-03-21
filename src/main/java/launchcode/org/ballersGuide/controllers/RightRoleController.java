package launchcode.org.ballersGuide.controllers;

import launchcode.org.ballersGuide.models.RightRole;
import launchcode.org.ballersGuide.models.data.RightRoleDao;
import launchcode.org.ballersGuide.models.data.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("rightRole")
public class RightRoleController {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RightRoleDao rightRoleDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("rightRoles", rightRoleDao.findAll());
        model.addAttribute("title", "Right");

        return "rightRole/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Right");
        model.addAttribute("rightRole", new RightRole());
        return "rightRole/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid RightRole rightRole, Errors errors) {

        if (errors.hasErrors()) {
            return "rightRole/add";
        }
        rightRoleDao.save(rightRole);
        return "redirect:/rightRole";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("rightRoles", rightRoleDao.findAll());
        model.addAttribute("title", "Remove Right");
        return "rightRole/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] rightRoleIds) {

        for (int rightRoleId : rightRoleIds) {
            rightRoleDao.delete(rightRoleId);
        }

        return "redirect:/rightRole";
    }

    @RequestMapping(value = "edit/{rightRolesId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int rightRoleId) {

        model.addAttribute("title", "Edit Role");
        model.addAttribute("rightRole", roleDao.findOne(rightRoleId));
        return "rightRole/edit";
    }

    @RequestMapping(value = "edit/{rightRolesId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int rightRoleId,
                                  @ModelAttribute  @Valid RightRole newRightRole,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Right");
            return "rightRole/edit";
        }

        RightRole editedRightRole = rightRoleDao.findOne(rightRoleId);
        editedRightRole.setName(newRightRole.getName());
        rightRoleDao.save(editedRightRole);

        return "redirect:/rightRole";
    }
}
