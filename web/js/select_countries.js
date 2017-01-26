$(function(){
    $.getJSON('../../json/countries.json', function(data) {
        for(var i=0;i<data.countries.length;i++){
            $('#countriesRight').append('<option>' + data.countries[i].name + '</option>');
        }
    });
});