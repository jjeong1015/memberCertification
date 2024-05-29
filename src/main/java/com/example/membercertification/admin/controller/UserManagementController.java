package com.example.membercertification.admin.controller;

import com.example.membercertification.admin.service.RoleService;
import com.example.membercertification.admin.service.UserManagementService;
import com.example.membercertification.domain.dto.AccountDTO;
import com.example.membercertification.domain.entity.Account;
import com.example.membercertification.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserManagementController { // UserManagementController : 사용자 정보를 관리하기 위한 클래스

	private final UserManagementService userManagementService;
	private final RoleService roleService;
	@GetMapping("/admin/users")
	public String getUsers(Model model) {

		List<Account> users = userManagementService.getUsers();
		model.addAttribute("users", users);

		return "admin/users";
	}

	@PostMapping("/admin/users")
	public String modifyUser(AccountDTO accountDTO) {

		userManagementService.modifyUser(accountDTO);

		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/{id}")
	public String getUser(@PathVariable(value = "id") Long id, Model model) {

		AccountDTO accountDTO = userManagementService.getUser(id);
		List<Role> roleList = roleService.getRolesWithoutExpression();

		model.addAttribute("user", accountDTO);
		model.addAttribute("roleList", roleList);

		return "admin/userdetails";
	}

	@GetMapping( "/admin/users/delete/{id}")
	public String removeUser(@PathVariable(value = "id") Long id) {

		userManagementService.deleteUser(id);

		return "redirect:admin/users";
	}
}
