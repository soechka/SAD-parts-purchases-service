package com.example.sadbe.service;

import com.example.sadbe.controller.dto.ResponseList;
import com.example.sadbe.dto.UserDto;
import com.example.sadbe.exception.ValidationException;
import com.example.sadbe.repository.UsersRepository;
import com.example.sadbe.tables.daos.UsersDao;
import com.example.sadbe.tables.pojos.Users;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersDao usersDao;
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseList<Users> list(Integer page, Integer pageSize) {
        Condition condition = DSL.noCondition();

        List<Users> items = usersRepository.list(condition, page, pageSize);
        Long total = usersRepository.getCount(condition);
        ResponseList<Users> result = new ResponseList<>();
        result.setItems(items);
        result.setTotal(total);
        return result;
    }

    public void create(UserDto userDto){
        validateUser(userDto);
        Users user = new Users();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsAdmin(userDto.getIsAdmin());
        usersDao.insert(user);
    }

    @Transactional
    public void update(UserDto userDto) {
        Users user = usersRepository.getActual(userDto.getLogin());
        usersDao.update(user);
        create(userDto);
    }

    private void validateUser(UserDto user){
        if(usersRepository.fetchExists(user.getLogin())){
            throw new ValidationException("User already exists");
        }
        // Смешной сложный пароль > 8 символов + разные регистры + цифры + спецсимволы
        if(!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            throw new ValidationException("Password is too easy");
        }
    }

    public void delete(UUID id) {
        usersDao.deleteById(id);
    }

    public void changeRole(UUID id) {
        usersRepository.changeRole(id);
    }
}
