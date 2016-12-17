package com.movierate.movie.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * class that contains methods to build queries
 */
public class QueryUtil {

    private static final String GENRE = "genre";
    private static final String COUNTRY = "country";
    private static final String YEAR = "year";
    private static final String PARTICIPANT = "participant";
    private static final String AND = " AND ";

    private QueryUtil() {
    }

    public static String createHttpQueryString(HttpServletRequest request){

        Enumeration<String> params = request.getParameterNames();
        String query = "";
        String key;
        String value;
        while(params.hasMoreElements()){
            key = params.nextElement();
            value  = request.getParameter(key);
            query = query + "&" + key + "=" + value;
        }

        query = request.getRequestURL() + "?" + query;

        return query;
    }

    public static String buildMovieSearchQuery(Map<String, String[]> parameters){

        StringBuilder query = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS id_movie, title, poster FROM movies ");
            query.append("WHERE ");
            query.append(" id_movie ");

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (!entry.getValue()[0].isEmpty()) {
                switch (entry.getKey()) {
                    case GENRE:
                        query.append(" IN(SELECT id_movie FROM movies_genres JOIN genres ON movies_genres.id_genre=genres.id_genre WHERE genre='");
                        query.append(entry.getValue()[0]);
                        query.append("')");
                        query.append(" AND id_movie ");
                        break;
                    case COUNTRY:
                        query.append(" IN(SELECT id_movie FROM movies_countries JOIN countries ON movies_countries.id_country=countries.id_country WHERE country='");
                        query.append(entry.getValue()[0]);
                        query.append("')");
                        query.append(" AND id_movie ");
                        break;
                    case YEAR:
                        query.delete(query.lastIndexOf("id_movie"), query.length());
                        query.append("year");
                        if (entry.getValue()[0].charAt(entry.getValue()[0].length() - 1) == 's') {
                            query.append(">=");
                            int year = Integer.parseInt(entry.getValue()[0].substring(0, entry.getValue()[0].length() - 1));
                            query.append(year);
                            query.append(AND);
                            query.append("year");
                            query.append("<");
                            year += 10;
                            query.append(year);
                        } else {
                            query.append("=");
                            query.append(entry.getValue()[0]);
                        }
                        query.append(" AND id_movie ");
                        break;
                    case PARTICIPANT:
                        query.append(" IN(SELECT id_movie FROM movies_participants JOIN participants ON movies_participants.id_participant=participants.id_participant WHERE name='");
                        query.append(entry.getValue()[0]);
                        query.append("')");
                        query.append(" AND id_movie ");
                }
            }
        }
//        query.delete(query.lastIndexOf(AND),query.length());
        query.append(" LIMIT ?,?;");
        return query.toString();
    }
}
