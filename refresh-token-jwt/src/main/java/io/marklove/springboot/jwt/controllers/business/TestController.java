package io.marklove.springboot.jwt.controllers.business;

import io.marklove.springboot.jwt.constants.MessageCode;
import io.marklove.springboot.jwt.exceptions.CommonException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

@RestController
@RequestMapping("/api/test")
@Tag(name = "test", description = "APIs test")
public class TestController {

	@Autowired
	LocaleResolver localeResolver;

//	@GetMapping("/all")
//	public String allAccess() {
//		return "Public Content.";
//	}
//
//	@GetMapping("/user")
//	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
//	public String userAccess() {
//		return "User Content.";
//	}
//
//	@GetMapping("/mod")
//	@PreAuthorize("hasRole('MODERATOR')")
//	public String moderatorAccess() {
//		return "Moderator Board.";
//	}
//
//	@GetMapping("/admin")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String adminAccess() {
//		return "Admin Board.";
//	}

	@GetMapping("/multi-language")
	public String multiLanguage() {
		System.out.println(localeResolver.toString());
		throw new CommonException(MessageCode.Error.C5000, null);
	}
}
