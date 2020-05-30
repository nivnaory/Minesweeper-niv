package com.afeka.sm.Minesweeper;

public interface Finals {
    char MINE = 'X';
    char EMPTY = ' ';
    char RED_MINE = 'R';
    char FLAG = 'F';

    String GAME_STATUS_PLAY = "play";
    String GAME_STATUS_WIN = "Win";
    String GAME_STATUS_LOSE = "Lose";

    int EASY_LEVEL = 1;
    int MEDIUM_LEVEL = 2;
    int HARD_LEVEL = 3;

    int EASY_BOARD_SIZE = 4;
    int MEDIUM_BOARD_SIZE = 6;
    int HARD_BOARD_SIZE = 8;

    int BOARD_MINES_RATIO = 7;

    int INITIAL_RECORD_VALUE = 1000;

    int INITIAL_VALUE_OF_CHOSEN_LEVEL_BY_THE_USER = 0;

    int NUM_OF_RECORDS_TO_SAVE = 3;

    String LEVEL_ACTIVITY_KEY = "level Activity";
    String GAME_RESULT = "GameResult";

    String TIME_PASSED = "timePassed";

    String APP_CHOSEN_NAME = "myPreferencesFile";

    String HAS_THE_USER_BROKEN_A_RECORD = "hasTheUserBrokenARecord";
}
