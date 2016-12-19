$(function(){
    $.getJSON('../../json/genres.json', function(data) {
        for(var i=0;i<data.genres.length;i++){
            $('#genresRight').append('<option value='+data.genres[i].name+'>' + data.genres[i].code + '</option>');
        }
    });
});