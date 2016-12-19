package com.movierate.movie.command;

/**
 * Created by Yultos_ on 20.11.2016
 */
public enum CommandType {

    CHANGE_LANGUAGE (new ChangeLanguageCommand()),
    REGISTRATION (new RegistrationCommand()),
    GET_MOVIES_BY_GENRE (new GetMoviesByGenreCommand()),
    GET_MOVIE_PAGE (new GetMoviePageCommand()),
    ADD_MOVIE (new AddMovieCommand()),
    ADD_FEEDBACK (new AddFeedbackCommand()),
    ADD_GENRE (new AddGenreCommand()),
    ADD_PARTICIPANT (new AddParticipantCommand ()),
    GET_ALL_MOVIES (new GetAllMoviesCommand()),
    GET_MOVIE_INFO_FOR_UPDATE (new GetMovieInfoForUpdateCommand()),
    UPDATE_MOVIE (new UpdateMovieCommand()),
    GET_ALL_PARTICIPANTS (new GetAllParticipantsCommand()),
    GET_PARTICIPANT_FOR_UPDATE (new GetParticipantForUpdateCommand()),
    UPDATE_PARTICIPANT (new UpdateParticipantCommand()),
    LOGIN (new LoginCommand()),
    LOGOUT (new LogOutCommand()),
    GET_NEW_FEEDBACKS (new GetNewFeedbacksCommand()),
    GET_FEEDBACK (new GetFeedbackCommand()),
    ACCEPT_FEEDBACK (new AcceptFeedbackCommand()),
    EDIT_USER_INFO (new EditUserInfoCommand()),
    GET_INFO_FOR_USER_RATING (new GetInfoForUserRatingCommand()),
    GET_ANOTHER_USER_PAGE (new GetAnotherUserPageCommand()),
    FILTERED_MOVIE_SEARCH (new FilteredMovieSearchCommand()),
    LOAD_MAIN_PAGE (new LoadMainPageCommand()),
    GET_ALL_USERS (new GetAllUsersCommand()),
    CHANGE_USER_STATUS (new ChangeUserStatusCommand()),
    GET_INFO_FOR_MOVIE_ADDING(new GetInfoForMovieAddingCommand());

    private ICommand command;

    CommandType (ICommand command){
        this.command = command;
    }

    public ICommand getCommand(){
        return command;
    }

}
