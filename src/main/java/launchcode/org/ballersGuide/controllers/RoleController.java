package launchcode.org.ballersGuide.controllers;

import launchcode.org.ballersGuide.models.*;
import launchcode.org.ballersGuide.models.data.RightRoleDao;
import launchcode.org.ballersGuide.models.data.RoleDao;
import launchcode.org.ballersGuide.models.forms.AddRoleItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RightRoleDao rightRoleDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("roles", roleDao.findAll());
        model.addAttribute("title", "Role");

        return "role/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("title", "Role");
        model.addAttribute("role", new Role());
        return "role/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Role role, Errors errors) {

        if (errors.hasErrors()) {
            return "role/add";
        }
        roleDao.save(role);
        return "redirect:/role";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemovePlayerForm(Model model) {
        model.addAttribute("roles", roleDao.findAll());
        model.addAttribute("title", "Remove Role");
        return "role/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemovePlayerForm(@RequestParam int[] roleIds) {

        for (int roleId : roleIds) {
            roleDao.delete(roleId);
        }

        return "redirect:/role";
    }

    @RequestMapping(value = "edit/{roleId}", method = RequestMethod.GET)
    public String displayEditPlayerForm(Model model, @PathVariable int roleId) {

        model.addAttribute("title", "Edit Role");
        model.addAttribute("role", roleDao.findOne(roleId));
        model.addAttribute("rightRoles", rightRoleDao.findAll());
        return "role/edit";
    }

    @RequestMapping(value = "edit/{roleId}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int roleId,
                                  @ModelAttribute  @Valid Role newRole,
                                  Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Role");
            return "role/edit";
        }

        Role editedRole = roleDao.findOne(roleId);
        editedRole.setName(newRole.getName());
        roleDao.save(editedRole);

        return "redirect:/role";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable int id) {

        Role role = roleDao.findOne(id);

        model.addAttribute("title", role.getName());
        model.addAttribute("role", role);
        return "role/view";
    }

    @RequestMapping(value = "add-right/{roleId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int roleId) {

        Role role = roleDao.findOne(roleId);

        AddRoleItemForm itemForm = new AddRoleItemForm(role, rightRoleDao.findAll());

        model.addAttribute("title", "Add rightRole to role: " + role.getName());
        model.addAttribute("form", itemForm);

        return "role/add-right";
    }

    @RequestMapping(value = "add-right/{roleId}", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddRoleItemForm itemForm,
                          Errors errors, @PathVariable int roleId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add rightRole");
            return "role/add-right/" + roleId;
        }

        Role role = roleDao.findOne(itemForm.getRoleId());
        RightRole rightRole = rightRoleDao.findOne(itemForm.getRightId());

        role.addRight(rightRole);
        roleDao.save(role);

        return "redirect:../view/" + role.getId();
    }

    @RequestMapping(value = "remove-right/{roleId}", method = RequestMethod.GET)
    public String removeItem(Model model, @PathVariable int roleId) {

        Role role = roleDao.findOne(roleId);

        AddRoleItemForm itemForm = new AddRoleItemForm(role, rightRoleDao.findAll());

        model.addAttribute("title", "Remove rightRole from role: " + role.getName());
        model.addAttribute("form", itemForm);

        return "role/remove-right";
    }

    @RequestMapping(value = "remove-right/{roleId}", method = RequestMethod.POST)
    public String removeItem(Model model, @ModelAttribute @Valid AddRoleItemForm itemForm,
                             Errors errors, @PathVariable int roleId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add rightRole");
            return "role/add-right/" + roleId;
        }

        Role role = roleDao.findOne(itemForm.getRoleId());
        RightRole rightRole = rightRoleDao.findOne(itemForm.getRightId());

        role.deleteRight(rightRole);
        roleDao.save(role);

        return "redirect:../view/" + role.getId();
    }
}
