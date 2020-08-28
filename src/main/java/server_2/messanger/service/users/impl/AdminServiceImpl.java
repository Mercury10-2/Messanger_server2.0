package server_2.messanger.service.users.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import server_2.messanger.dto.UserDto;
import server_2.messanger.service.users.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public ResponseEntity<UserDto> changeRole(Long id) {
        return null;
    }
    
}