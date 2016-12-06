
    // Теперь эта функция будет принимать указатель на объект, после которого нужно осуществить вставку
    function add_genre(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="genre" class="form-control"><option value="" disabled selected>' +
            '<fmt:message key="admin.add.movie.choosegenre"/></option><c:forEach var="genre" items="${genres}">' +
            '<option>${genre.genreName}</option></c:forEach></select>';
        // new_input.innerHTML='<select name="genre" class="form-control"><option>thriller</option><option>comedy</option><option>drama</option></select>';
// Дописываем рядом с input-ом кнопку, она будет добовлять элемент именно под input, рядом с которым она находится
//        new_input.innerHTML=new_input.innerHTML+'<input type="button" value="+" onclick="add_input(this.parentNode)">';
// И еще одна кнопочка для его удаления.
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_genre(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
//Ищем присутствует ли следующий узел в структуре DOM-а
        if (obj.nextSibling)
        // если да - то создаем после него
            document.getElementById('genres').insertBefore(new_input,obj.nextSibling);
//если такого не нашлось то просто добавляем в конец
        else document.getElementById('genres').appendChild(new_input);
    }
function del_genre(obj)
{
    document.getElementById('genres').removeChild(obj)
}


function add_country(obj)
{
    var new_input=document.createElement('div');
    new_input.innerHTML='<span id="star">*</span><select name="country" class="form-control"><option value="" disabled selected>' +
        '<fmt:message key="admin.add.movie.choosecountry"/></option><c:forEach var="country" items="${countries}">' +
        '<option>${country.countryName}</option> </c:forEach>';
    new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_country(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
    if (obj.nextSibling)
        document.getElementById('countries').insertBefore(new_input,obj.nextSibling);
    else document.getElementById('countries').appendChild(new_input);
}
function del_country(obj)
{
    document.getElementById('countries').removeChild(obj)
}

function add_actor(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="actor" class="form-control"><option value="" disabled selected>' +
            '<fmt:message key="admin.add.movie.chooseactor"/> </option> <c:forEach var="actor" items="${actors}">' +
            '<option>${actor.name}</option> </c:forEach>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_actor(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('actors').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('actors').appendChild(new_input);
    }
    function del_actor(obj)
    {
        document.getElementById('actors').removeChild(obj)
    }

    function add_director(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="director" class="form-control"><option value="" disabled selected>' +
            '<fmt:message key="admin.add.movie.choosedirector"/></option><c:forEach var="director" items="${directors}">' +
            '<option>${director.name}</option> </c:forEach>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_director(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('directors').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('directors').appendChild(new_input);
    }
    function del_director(obj)
    {
        document.getElementById('directors').removeChild(obj)
    }

    function add_new_genre(){
        document.getElementById('for-insert').innerHTML='<form action="/controller" role="form" method="post" name="add-genre">' +
            '<input type="hidden" name="command" value="add_genre">' +
            '<div class="form-group"><input type="text" name="genre" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.genre"/>"></div>' +
            '<div><button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button></div></form>';
    }

    function add_new_participant(){
        document.getElementById('for-insert').innerHTML='<form action="/controller" role="form" method="post" name="add-participant">' +
            '<input type="hidden" name="command" value="add_participant">' +
            '<div class="form-group"><select name="profession" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.participant"/>">' +
            '<option>Actor</option><option>Director</option></select>'+
            '<div class="form-group" style="margin-top: 10px"><input type="text" name="name" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.name"/>"></div>' +

            '<div><button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button></div></form>';
    }


