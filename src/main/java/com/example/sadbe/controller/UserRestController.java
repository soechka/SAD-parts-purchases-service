package com.example.sadbe.controller;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.UserDto;
import com.example.sadbe.service.UsersService;
import com.example.sadbe.tables.pojos.Users;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserRestController {

    private final UsersService usersService;

    @GetMapping("/list")
    public ResponseList<Users> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        return usersService.list(page, pageSize);
    }

    @PostMapping
    public void create(@RequestBody UserDto userDto){
        usersService.create(userDto);
    }

    @PutMapping
    public void update(@RequestBody UserDto userDto){
        usersService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        usersService.delete(id);
    }

    @PutMapping("/{id}")
    public void changeRole(@PathVariable UUID id) { usersService.changeRole(id); }
}
