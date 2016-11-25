package com.movierate.movie.command;

/**
 * Created by Yultos_ on 20.11.2016
 */
public enum CommandType {

    CHANGE_LANGUAGE (new ChangeLanguageCommand());

    private ICommand command;

    CommandType (ICommand command){
        this.command = command;
    }

    public ICommand getCommand(){
        return command;
    }

}
