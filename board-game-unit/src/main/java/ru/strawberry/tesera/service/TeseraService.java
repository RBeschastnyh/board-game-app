package ru.strawberry.tesera.service;


import ru.strawberry.tesera.dto.AuthResponse;

import java.util.List;

public interface TeseraService {

    List<String> getUserGames();

    AuthResponse auth();
}
