package com.CursorHomeWorks15.controller;

import com.CursorHomeWorks15.dto.UserDataDTO;
import com.CursorHomeWorks15.dto.UserResponseDTO;
import com.CursorHomeWorks15.model.AppUser;
import com.CursorHomeWorks15.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public String login(String username, String password) {
        return userService.signin(username, password);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDataDTO user) {
        return userService.signup(modelMapper.map(user, AppUser.class));
    }

    @GetMapping(value = "/mydata")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public UserResponseDTO mydata(HttpServletRequest req) {
        return modelMapper.map(userService.mydata(req), UserResponseDTO.class);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponseDTO search(@PathVariable String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }

    @GetMapping("/updatetoken")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }
}
