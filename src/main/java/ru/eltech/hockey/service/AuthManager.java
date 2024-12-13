package ru.eltech.hockey.service;

import org.springframework.stereotype.Component;

@Component
public class AuthManager {

    private boolean loggedIn = false;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
