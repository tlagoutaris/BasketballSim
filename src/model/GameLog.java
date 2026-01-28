package model;

import result.GameEvent;
import service.GameLogUpdater;

import java.util.ArrayList;

public class GameLog {

    GameLogUpdater updater;

    ArrayList<String> transcript = new ArrayList<>();
    ArrayList<GameEvent> events = new ArrayList<>();

    public GameLog(GameLogUpdater updater) {
        this.updater = updater;
    }

    // Getters
    public ArrayList<String> getTranscript() {
        return transcript;
    }

    public ArrayList<GameEvent> getEvents() {
        return events;
    }
}
