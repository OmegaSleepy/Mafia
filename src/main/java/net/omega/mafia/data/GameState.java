package net.omega.mafia.data;

public enum GameState {
    MORNING, //reporting killed
    AFTERNOON, //free roam
    DINNER, //discussion time
    NIGHT, //role time
    VOTE //allow voting, if the vote is tied, reset the votes and announce new timer
}
