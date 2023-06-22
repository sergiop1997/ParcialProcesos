package com.Productos.productos.Service;

import com.Productos.productos.Models.User;
import com.Productos.productos.Repository.UserRepository;
import com.Productos.productos.Utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public Boolean createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Boolean delete(Long id){
        try {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean UpdateUser(Long id, User user) {
        try {
            User userBD = userRepository.findById(id).get();
            userBD.setFirstName(user.getFirstName());
            userBD.setLastName(user.getLastName());
            userBD.setBirthday(user.getBirthday());
            userBD.setAddress(user.getAddress());
            User userUp = userRepository.save(userBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public String login(User user) {
        Optional<User>userBd = userRepository.findByEmail(user.getEmail());
        if (userBd.isEmpty()){
            throw new RuntimeException("Usuario no encontrado ");
        }
        if (!passwordEncoder.matches(user.getPassword(),userBd.get().getPassword())){
            throw new RuntimeException("Contraseña incorrecta ");
        }
        return jwtUtil.create(String.valueOf(userBd.get().getId()),
                String.valueOf(userBd.get().getEmail()));
    }

}