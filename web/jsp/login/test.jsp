<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>Movie rating</title>


</head>
<body></body>

<form  onsubmit="return validateForm()" method="post" name="login_form" class="form-signin">

    <input type="text" class="form-control" name="username"/>

    <span class="error" id="error_login"></span>
    <input type="submit" class="btn btn-lg btn-success btn-block" />

</form>

<script>
    function validateForm() {
        var result = true;
        var pattern = new RegExp("[A-z0-9_]{5,}");
//        var pattern = new RegExp("\w+");
        var error = document.getElementById("error_login");
        error.innerHTML = "";
        var login = document.forms[0]["username"].value;
        if (!login){
            error.innerHTML = "not valid";
            result = false;
        }
        if (!pattern.test(login)){
            result = false;
            error.innerHTML = "not pattern";

        }
        return result;
    }
</script>

</body>