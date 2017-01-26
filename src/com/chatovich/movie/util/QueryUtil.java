package com.chatovich.movie.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * class that contains methods to build queries
 */
public class QueryUtil {

    private static final String QUERY_START = "SELECT SQL_CALC_FOUND_ROWS id_movie, title, poster, year, adding_date FROM movies ";
    private static final String IN_GENRES = " IN(SELECT id_movie FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre='";
    private static final String IN_COUNTRIES = " IN(SELECT id_movie FROM movies_countries JOIN countries ON movies_countries.id_country=countries.id_country WHERE country='";
    private static final String IN_PARTICIPANTS = " IN(SELECT id_movie FROM movies_participants JOIN participants ON movies_participants.id_participant=participants.id_participant WHERE name='";
    private static final String GENRE = "genre";
    private static final String COUNTRY = "country";
    private static final String YEAR = "year";
    private static final String PARTICIPANT = "participant";
    private static final String AND = " AND ";
    private static final String WHERE = "WHERE ";
    private static final String ID_MOVIE = " id_movie ";
    private static final String LIMIT = " LIMIT ?,?;";
    private static final String ORDER = " ORDER BY adding_date";
    private static final String CLOSE_PARENTHESIS = "')";
    private static final String EQUAL = "=";
    private static final String LESS = "<";
    private static final String MORE_OR_EQUAL = ">=";
    private static final String AMPERSAND = "&";
    private static final String INTERROGATION = "?";

    private QueryUtil() {
    }

    /**
     * creates HttpQuery of the previous page
     * @param request HttpServletRequest request
     * @return string path to the previous page
     */
    public static String createHttpQueryString(HttpServletRequest request){

        Enumeration<String> params = request.getParameterNames();
        String query = "";
        String key;
        String value;
        while(params.hasMoreElements()){
            key = params.nextElement();
            value  = request.getParameter(key);
            query = query + AMPERSAND + key + EQUAL + value;
        }

        query = request.getRequestURL() + INTERROGATION + query;

        return query;
    }

    /**
     * builds sql query to search movies according to user's request
     * @param parameters parameters of the user's request
     * @return sql query for the db
     */
    public static String buildMovieSearchQuery(Map<String, String[]> parameters){

        StringBuilder query = new StringBuilder(QUERY_START);
            query.append(WHERE);
            query.append(ID_MOVIE);

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (!entry.getValue()[0].isEmpty()) {
                switch (entry.getKey()) {
                    case GENRE:
                        query.append(IN_GENRES);
                        query.append(entry.getValue()[0]);
                        query.append(CLOSE_PARENTHESIS);
                        query.append(AND);
                        query.append(ID_MOVIE);
                        break;
                    case COUNTRY:
                        query.append(IN_COUNTRIES);
                        query.append(entry.getValue()[0]);
                        query.append(CLOSE_PARENTHESIS);
                        query.append(AND);
                        query.append(ID_MOVIE);
                        break;
                    case YEAR:
                        query.delete(query.lastIndexOf(ID_MOVIE), query.length());
                        query.append(YEAR);
                        if (entry.getValue()[0].charAt(entry.getValue()[0].length() - 1) == 's') {
                            query.append(MORE_OR_EQUAL);
                            int year = Integer.parseInt(entry.getValue()[0].substring(0, entry.getValue()[0].length() - 1));
                            query.append(year);
                            query.append(AND);
                            query.append(YEAR);
                            query.append(LESS);
                            year += 10;
                            query.append(year);
                        } else {
                            query.append(EQUAL);
                            query.append(entry.getValue()[0]);
                        }
                        query.append(AND);
                        query.append(ID_MOVIE);
                        break;
                    case PARTICIPANT:
                        query.append(IN_PARTICIPANTS);
                        query.append(entry.getValue()[0]);
                        query.append(CLOSE_PARENTHESIS);
                        query.append(AND);
                        query.append(ID_MOVIE);
                }
            }
        }
        query.append(ORDER);
        query.append(LIMIT);
        return query.toString();
    }
}
