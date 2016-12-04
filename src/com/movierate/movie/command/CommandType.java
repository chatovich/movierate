package com.movierate.movie.command;

/**
 * Created by Yultos_ on 20.11.2016
 */
public enum CommandType {

    CHANGE_LANGUAGE (new ChangeLanguageCommand()),
    REGISTRATION (new RegistrationCommand()),
    GET_MOVIES_BY_GENRE (new FindMoviesByGenreCommand()),
    GET_MOVIE_PAGE (new GetMoviePageCommand()),
    ADD_FEEDBACK (new AddFeedbackCommand());

    private ICommand command;

    CommandType (ICommand command){
        this.command = command;
    }

    public ICommand getCommand(){
        return command;
    }

}
