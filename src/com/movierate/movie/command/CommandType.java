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
    GET_INFO_FOR_MOVIE_ADDING(new GetInfoForMovieAddingCommand());

    private ICommand command;

    CommandType (ICommand command){
        this.command = command;
    }

    public ICommand getCommand(){
        return command;
    }

}
