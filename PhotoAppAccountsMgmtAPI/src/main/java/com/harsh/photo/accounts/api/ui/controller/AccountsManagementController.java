package com.harsh.photo.accounts.api.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountmgmt")
public class AccountsManagementController {
	
	@GetMapping("/status/check")
	public String checkStatus() {
		return "working";
	}

}
