function validateForm() {
    var FILL_FIELD = <fmt:message key="reg.fill.field"/>;
    PASSWORDS_NOMATCH = <fmt:message key="reg.passwords.nomatch"/>;
    PATTERN_LOGIN = <fmt:message key="login.pattern"/>;
    PATTERN_EMAIL = <fmt:message key="reg.wrong.email"/>;
    var result = true;
    //get variables from the form
    login = document.forms[0]["username"].value;
    password1 = document.forms[0]["password"].value;
    password2 = document.forms[0]["confirm_password"].value;
    email = document.forms[0]["email"].value;
    pattern_login = new RegExp("[A-z0-9_]{5,}");
    pattern_email = new RegExp("[0-9a-z_]+@[0-9a-z_]+\.[a-z]{2,5}");
//        var pattern = new RegExp("\w+");
    //specify error variables
    var err_login = document.getElementById("error_login");
    err_pass1 = document.getElementById("error_password1");
    err_pass2 = document.getElementById("error_password2");
    err_email = document.getElementById("error_email");
    //clear error messages
    err_login.innerHTML = "";
    err_pass1.innerHTML = "";
    err_pass2.innerHTML = "";
    err_email.innerHTML = "";

//        if (!login){
//            err_login.innerHTML = "not valid";
//            result = false;
//        }
    if (!pattern_login.test(login)){
        result = false;
        err_login.innerHTML = PATTERN_LOGIN;
    }
    if (!pattern_login.test(password1)){
        result = false;
        err_pass1.innerHTML = PATTERN_LOGIN;
    }
    if (!pattern_email.test(email)){
        result = false;
        err_email.innerHTML = PATTERN_EMAIL;
    }
    if(password1!==password2){
        err_pass1.innerHTML = PASSWORDS_NOMATCH;
        err_pass2.innerHTML = PASSWORDS_NOMATCH;
        document.forms[0]["password"].value = "";
        document.forms[0]["confirm_password"].value = "";
        result = false;
    }

    return result;
}

function validateEditForm() {
    PASSWORDS_NOMATCH = <fmt:message key="reg.passwords.nomatch"/>;
    PATTERN_PASSWORD = <fmt:message key="login.pattern"/>;
    PATTERN_EMAIL = <fmt:message key="reg.wrong.email"/>;
    var result = true;
    //get variables from the form
    password1 = document.forms[0]["new_password"].value;
    password2 = document.forms[0]["confirm_password"].value;
    email = document.forms[0]["email"].value;
    pattern_password = new RegExp("[A-z0-9_]{5,}");
    pattern_email = new RegExp("[0-9a-z_]+@[0-9a-z_]+\.[a-z]{2,5}");
    //specify error variables
    err_pass1 = document.getElementById("error_password1");
    err_pass2 = document.getElementById("error_password2");
    err_email = document.getElementById("error_email");
    //clear error messages
    err_pass1.innerHTML = "";

    err_email.innerHTML = "";

    if (!pattern_email.test(email)) {
        result = false;
        err_email.innerHTML = PATTERN_EMAIL;
    }
    if (password1 !== password2) {
        err_pass1.innerHTML = PASSWORDS_NOMATCH;
        err_pass2.innerHTML = PASSWORDS_NOMATCH;
        document.forms[0]["new_password"].value = "";
        document.forms[0]["confirm_password"].value = "";
        result = false;
    }
    if (password1!==""){
        if (password2!=="") {
            if (!pattern_password.test(password1)) {
                result = false;
                err_pass1.innerHTML = PATTERN_PASSWORD;
                err_pass2.innerHTML = "";
            }
        }
    }
    return result;
}