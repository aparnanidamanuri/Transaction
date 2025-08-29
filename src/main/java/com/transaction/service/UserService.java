package com.transaction.service;

import com.transaction.dto.UserDTO;
import com.transaction.entity.UserEntity;
import com.transaction.exception.UserException;
import com.transaction.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> addUsers(List<UserDTO> userDTOList) {
        List<UserEntity> usersToSave = new ArrayList<>();
        List<String> duplicateUserIds = new ArrayList<>();

        Map<String, String> errors = new LinkedHashMap<>();
        for (UserDTO userDTO : userDTOList) {
            if(userDTO.getUserId() == null || userDTO.getUserId().isEmpty())
                errors.put("User ID:","User ID can't be null");
            if(userDTO.getName().isEmpty() || userDTO.getName().isBlank())
                errors.put("User Name:","User name can't be blank");
            if(userDTO.getEmail().isEmpty() || userDTO.getEmail() == null || !userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
                errors.put("Email","Invalid Email format");

            if(!errors.isEmpty()){
                throw new UserException(errors, HttpStatus.OK);
            }

            if (userRepository.findByUserId(userDTO.getUserId()).isPresent()) {
                duplicateUserIds.add(userDTO.getUserId());
                continue;
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userDTO.getUserId());
            userEntity.setName(userDTO.getName());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setRegisteredAt(Timestamp.from(Instant.now()));
            usersToSave.add(userEntity);
        }

        List<UserEntity> savedUsers = userRepository.saveAll(usersToSave);

        Map<String, Object> result = new HashMap<>();
        result.put("savedUsers", savedUsers);
        result.put("skippedDuplicates", duplicateUserIds);

        return result;
    }

    public List<UserEntity> searchUsers(String userId, String name, String email, Timestamp registeredAt) {
        return userRepository.search(userId, name, email, registeredAt);
    }

    public UserEntity updateUser(UserDTO userDTO, Integer id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User with ID: " + id + " not found", HttpStatus.NOT_FOUND));

        if (userDTO.getUserId() != null) user.setUserId(userDTO.getUserId());
        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getRegisteredAt() != null) user.setRegisteredAt(userDTO.getRegisteredAt());

        return userRepository.save(user);
    }

    public String deleteById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "Successfully deleted user with id: " + id;
        }
        return "No user found with id: " + id;
    }

    public String deleteAll() {
        userRepository.deleteAll();
        return "Successfully deleted all the users";
    }

    //Scheduled Tasks
//    @Scheduled(fixedDelay = 60000)
//    public void reportUserCountWithFixedDelay(){
//        reportUserCount("Auto Scheduled Task");
//    }
//
//    public Map<String, Object> reportUserCount(String source){
//        Map<String, Object> map = new HashMap<>();
//        map.put("source", source);
//        map.put("userCount", userRepository.count());
//        map.put("timeStamp", LocalDateTime.now());
//        return map;
//    }

    @Scheduled(fixedDelay = 60000, initialDelay = 15000)
    public void reportUserCountWithFixedDelay2() {
        System.out.println("[FixedDelay with InitialDelay] Users: " + userRepository.count() + " at " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 20000)
    public void reportUserCountWithFixedRate() {
        System.out.println("[FixedRate] Users: " + userRepository.count() + " at " + LocalDateTime.now());
    }

    @Scheduled(cron = "* 2 * * * *")
    public void cronReportUserCount() {
        System.out.println("[Cron] (2m mark) Users: " + userRepository.count() + " at " + LocalDateTime.now());
    }

    @Scheduled(fixedRateString = "${scheduler.user.fixedRate:10000}")
    public void dynamicFixedRateReport() {
        System.out.println("[FixedRateString] Users: " + userRepository.count() + " at " + LocalDateTime.now());
    }
}
