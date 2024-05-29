package com.example.membercertification.admin.controller;

import com.example.membercertification.admin.service.RoleService;
import com.example.membercertification.domain.dto.RoleDTO;
import com.example.membercertification.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoleController { // RoleController : 사용자의 권한 정보를 관리하기 위한 클래스

	private final RoleService roleService;

	@GetMapping("/admin/roles")
	public String getRoles(Model model) {

		List<Role> roles = roleService.getRoles();
		model.addAttribute("roles", roles);

		return "admin/roles";
	}

	@GetMapping("/admin/roles/register")
	public String rolesRegister(Model model) {

		RoleDTO role = new RoleDTO();
		model.addAttribute("roles", role);

		return "admin/rolesdetails";
	}

	@PostMapping("/admin/roles")
	public String createRole(RoleDTO roleDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Role role = modelMapper.map(roleDTO, Role.class);
		roleService.createRole(role);

		return "redirect:/admin/roles";
	}

	@GetMapping("/admin/roles/{id}")
	public String getRole(@PathVariable String id, Model model) {
		Role role = roleService.getRole(Long.parseLong(id));

		ModelMapper modelMapper = new ModelMapper();
		RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
		model.addAttribute("roles", roleDTO);

		return "admin/rolesdetails";
	}

	@GetMapping("/admin/roles/delete/{id}")
	public String removeRoles(@PathVariable String id) {

		roleService.deleteRole(Long.parseLong(id));

		return "redirect:/admin/roles";
	}
}
