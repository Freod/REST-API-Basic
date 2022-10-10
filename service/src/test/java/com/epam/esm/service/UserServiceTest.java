package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
// TODO: 06/10/2022

    @Mock
    private UserDao userDao;
    @Mock
    private ObjectConverter objectConverter;
    @InjectMocks
    private UserService userService;
}
