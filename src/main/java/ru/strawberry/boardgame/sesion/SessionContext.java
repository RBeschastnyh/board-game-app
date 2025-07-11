package ru.strawberry.boardgame.sesion;

import org.hibernate.Session;


public interface SessionContext {

    Session getSession();
}
