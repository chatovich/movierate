function add_genre(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="genre" class="form-control" required><option value="" disabled selected>' +
            '<fmt:message key="admin.add.movie.choosegenre"/></option><c:forEach var="genre" items="${genres}">' +
            '<option>${genre.genreName}</option></c:forEach></select>';

        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_genre(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('genres').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('genres').appendChild(new_input);
    }
function del_genre(obj)
{
    document.getElementById('genres').removeChild(obj)
}


function add_country(obj)
{
    var new_input=document.createElement('div');
    new_input.innerHTML='<span id="star">*</span><select name="country" class="form-control" required><option value="" disabled selected>' +
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
        new_input.innerHTML='<span id="star">*</span><select name="actor" class="form-control" required><option value="" disabled selected>' +
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
        new_input.innerHTML='<span id="star">*</span><select name="director" class="form-control" required><option value="" disabled selected>' +
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
        document.getElementById('for-insert').innerHTML='<form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="add-genre">' +
            '<input type="hidden" name="command" value="add_genre">' +
            '<div class="form-group"><input type="text" name="genre" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.genre"/>"></div>' +
            '<div><button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button></div></form>';
    }

    function add_new_participant(){
        document.getElementById('for-insert').innerHTML='<form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="add-participant">' +
            '<input type="hidden" name="command" value="add_participant">' +
            '<div class="form-group"><select name="profession" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.participant"/>">' +
            '<option>Actor</option><option>Director</option></select>'+
            '<div class="form-group" style="margin-top: 10px"><input type="text" name="name" class="form-control-static" style="width: 300px; color: black;" required placeholder="<fmt:message key="admin.add.name"/>"></div>' +

            '<div><button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button></div></form>';
    }

    function signed_user_info(){
        document.getElementById('for-insert').innerHTML='<div><img src="${pageContext.request.contextPath}${signedUser.photo}" width="200px" height="200px">' +
            '</div><b><p style="font-size: 18px">${signedUser.login}</p></b><p><fmt:message key="user.since"/> ${signedUser.registrDate}</p>' +
            '<p>${signedUser.email}</p></p><p align="right"><input type="button" value="<fmt:message key="user.info.edit"/>" onclick="edit_user_info()">' +
            '</p>';
    }

    function another_user_info(){
        document.getElementById('for-insert').innerHTML='<div><img src="${pageContext.request.contextPath}${anotherUser.photo}" width="200px" height="200px">' +
            '</div><b><p style="font-size: 18px">${anotherUser.login}</p></b><p><fmt:message key="user.since"/> ${anotherUser.registrDate}</p>';
    }

    function edit_user_info(){
        document.getElementById('for-insert').innerHTML='<div style="text-align:center">' +
            '<form onsubmit="return validateEditForm()" action="${pageContext.request.contextPath}/controller" enctype="multipart/form-data" role="form" method="post" name="edit_user_info">' +
            '<input type="hidden" name="command" value="edit_user_info">' +
            '<input type="hidden" name="login" value="${signedUser.login}">' +

            '<span style="color: red" id="error_email"></span>'+
            '<div class="form-group"><span class="star">* </span><input type="text" name="email" value="${signedUser.email}" style="width: 300px; color: black;display: inline" class="form-control" required placeholder="${signedUser.email}"></div>' +

            '<div class="form-group"><span class="star">* </span><input type="password" name="password" style="width: 300px; color: black;display: inline" class="form-control" required placeholder="<fmt:message key="user.old.password"/>"></div>' +
            '<span style="color: red" id="error_password1"></span>'+
            '<div class="form-group"><input type="password" name="new_password" id="new_password" style="width: 300px; color: black;display: inline" class="form-control" placeholder="<fmt:message key="user.new.password"/>"></div>' +
            '<span style="color: red" id="error_password2"></span>'+
            '<div class="form-group"><input type="password" name="confirm_password" id="confirm_password" style="width: 300px; color: black;display: inline" class="form-control" placeholder="<fmt:message key="user.confirmnew.password"/>"></div>' +

            '<div class="form-group"><input class="form-control" type="file" name="photo" style="width:300px;display:inline"></div>'+
            '<span id="oblig_field">*  <fmt:message key="obligatory.field"/></span><br>'+
            '<div class="form-group"><input type="submit" class="btn btn-primary" value="<fmt:message key="admin.add.movie.submit"/>" style="margin-top:10px"></input></div></form></div>';
    }

    function sure_delete() {
        document.getElementById('sure').innerHTML = 'Are you sure?';
        document.getElementById('del').innerHTML = '';
        document.getElementById('del').innerHTML = '<button class="btn btn-primary" name="action" value="delete"><fmt:message key="admin.add.movie.delete"/></button>';
    }


