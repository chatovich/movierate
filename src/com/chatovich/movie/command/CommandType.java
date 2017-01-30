package com.chatovich.movie.command;

/**
 * class with command types
 */
public enum CommandType {

    ACCEPT_FEEDBACK (),
    ADD_LIKE(),
    ADD_MOVIE (),
    ADD_FEEDBACK (),
    ADD_PARTICIPANT (),
    CHANGE_LANGUAGE (),
    CHANGE_USER_STATUS (),
    EDIT_USER_INFO (),
    FILTERED_MOVIE_SEARCH (),
    GET_MOVIE_PAGE (),
    GET_ALL_MOVIES (),
    GET_MOVIE_INFO_FOR_UPDATE (),
    GET_INFO_FOR_USER_RATING (),
    GET_INFO_FOR_MOVIE_ADDING(),
    GET_NEW_FEEDBACKS (),
    GET_FEEDBACK (),
    GET_ANOTHER_USER_PAGE (),
    GET_ALL_USERS (),
    GET_ALL_PARTICIPANTS (),
    GET_PARTICIPANT_FOR_UPDATE (),
    LOGIN (),
    LOGOUT (),
    LOAD_MAIN_PAGE (),
    MOVIE_TITLE_SEARCH(),
    REGISTRATION (),
    UPDATE_MOVIE (),
    UPDATE_PARTICIPANT ();
}
