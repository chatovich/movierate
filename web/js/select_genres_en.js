$(function(){
    $.getJSON('../../json/genres.json', function(data) {
        for(var i=0;i<data.genres.length;i++){
            $('#genresRight').append('<option>' + data.genres[i].name + '</option>');
        }
    });
});

$(function(){
    $.getJSON('../../json/genres.json', function(data) {
        for(var i=0;i<data.genres.length;i++){
            var s = "${pageContext.request.contextPath}/controller?command=filtered_movie_search&genre="+data.genres[i].name;
            $('#topGenres').append('<li><a style="padding: 5px 5px" href='+s+'>' + data.genres[i].name + '</a></li>');
        }
    });
});