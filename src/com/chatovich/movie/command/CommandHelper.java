package com.chatovich.movie.command;

import java.util.HashMap;
import java.util.Map;

/**
 * class that contains instances of classes implementing interface Command
 * corresponding to command names
 */
public class CommandHelper {

    private static final CommandHelper instance = new CommandHelper();

    private Map<CommandType, ICommand> commands = new HashMap<>();

    private CommandHelper() {

        commands.put(CommandType.ACCEPT_FEEDBACK, new AcceptFeedbackCommand());
        commands.put(CommandType.ADD_FEEDBACK, new AddFeedbackCommand());
        commands.put(CommandType.ADD_LIKE, new AddLikeCommand());
        commands.put(CommandType.ADD_MOVIE, new AddMovieCommand());
        commands.put(CommandType.ADD_PARTICIPANT, new AddParticipantCommand());
        commands.put(CommandType.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commands.put(CommandType.CHANGE_USER_STATUS, new ChangeUserStatusCommand());
        commands.put(CommandType.EDIT_USER_INFO, new EditUserInfoCommand());
        commands.put(CommandType.FILTERED_MOVIE_SEARCH, new FilteredMovieSearchCommand());
        commands.put(CommandType.GET_ALL_MOVIES, new GetAllMoviesCommand());
        commands.put(CommandType.GET_ALL_PARTICIPANTS, new GetAllParticipantsCommand());
        commands.put(CommandType.GET_ALL_USERS, new GetAllUsersCommand());
        commands.put(CommandType.GET_ANOTHER_USER_PAGE, new GetAnotherUserPageCommand());
        commands.put(CommandType.GET_FEEDBACK, new GetFeedbackCommand());
        commands.put(CommandType.GET_INFO_FOR_MOVIE_ADDING, new GetInfoForMovieAddingCommand());
        commands.put(CommandType.GET_INFO_FOR_USER_RATING, new GetInfoForUserRatingCommand());
        commands.put(CommandType.GET_MOVIE_INFO_FOR_UPDATE, new GetMovieInfoForUpdateCommand());
        commands.put(CommandType.GET_MOVIE_PAGE, new GetMoviePageCommand());
        commands.put(CommandType.GET_NEW_FEEDBACKS, new GetNewFeedbacksCommand());
        commands.put(CommandType.GET_PARTICIPANT_FOR_UPDATE, new GetParticipantForUpdateCommand());
        commands.put(CommandType.LOAD_MAIN_PAGE, new LoadMainPageCommand());
        commands.put(CommandType.LOGIN, new LoginCommand());
        commands.put(CommandType.LOGOUT, new LogOutCommand());
        commands.put(CommandType.MOVIE_TITLE_SEARCH, new MovieTitleSearchCommand());
        commands.put(CommandType.REGISTRATION, new RegistrationCommand());
        commands.put(CommandType.UPDATE_MOVIE, new UpdateMovieCommand());
        commands.put(CommandType.UPDATE_PARTICIPANT, new UpdateParticipantCommand());
    }


    public static CommandHelper getInstance() {
        return instance;
    }

    public ICommand getCommand(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }
        try {
            return commands.get(CommandType.valueOf(name.toUpperCase()));
        }
        catch (IllegalArgumentException e){
            return null;
        }
    }

    public CommandType getCommandName(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }
        try {
            return CommandType.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e){
            return null;
        }
    }
}
