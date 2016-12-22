function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {

        // Создадим XMLHttpRequest объект для не-Microsoft браузеров
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Создадим XMLHttpRequest с помощью MS ActiveX
        try {
            // Попробуем создать XMLHttpRequest для поздних версий
            // Internet Explorer

            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Не удалось создать требуемый ActiveXObject

            try {
                // Пробуем вариант, который поддержат более старые версии
                //  Internet Explorer

                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Не в состоянии создать XMLHttpRequest с помощью ActiveX
            }
        }
    }

    return xmlreq;
}


function addLike(id_user, id_feedback, likes) {


    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();
    var name = "like_count"+id_feedback;
    // document.getElementById(name).innerHTML="like";
    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    // var handlerFunction = getReadyStateHandler(req, updateCart);
    // req.onreadystatechange = handlerFunction;
    req.onreadystatechange = function() {
        if(this.readyState == 4 ){
            if (this.status == 200){
                    document.getElementById(name).innerHTML=this.responseText;
                    alert("ok");
            }else {
                alert("fail"+this.status);
            }}
        // } else {
        //     alert ("start fail"+this.readyState);
        // }
        // if (this.readyState == 4 && this.status == 200) {
        //     alert(this.responseText);
        //     alert("ok");
        //     document.getElementById('like_count').innerHTML=this.responseText;
        // }
    };

    // Открываем HTTP-соединение с помощью POST-метода к
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "/controller", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded");

    // Посылаем закодированные данные, говорящие о том, что я хочу добавить
    // определенный продукт в корзину.
    req.send("command=add_like&id_user="+id_user+"&id_feedback="+id_feedback+"&likes="+likes);
}

function getReadyStateHandler(req, responseXmlHandler) {

    // Возвращает неопределенную функцию, которая считывает
    // данные XMLHttpRequest return function () {

    // Если требуется статус "закончен"
    if (req.readyState == 4) {

        // Проверяем, пришел ли  успешный ответ сервера
        if (req.status == 200) {

            // Передает  XML оператору
            responseXmlHandler(req.responseXML);

        } else {

            // Возникла ошибка HTTP
            alert("HTTP error: "+req.status);
        }
    }
}

function updateCart(cartXML) {
             document.getElementById('like_count').innerHTML=req.responseText;

    // Получить корневой  &quot;cart&quot; элемент из документа
    var cart = cartXML.getElementsByTagName("cart")[0];

    // Проверим, что более ранний документ корзины не был обработан еще
    var generated = cart.getAttribute("generated");
    if (generated > lastCartUpdate) {
        lastCartUpdate = generated;

        // Очистим список HTML, необходимый для отображения содержимого корзины
        var contents = document.getElementById("cart-contents");
        contents.innerHTML = "";

        // Соединяем продукты в корзине
        var items = cart.getElementsByTagName("item");
        for (var I = 0 ; I < items.length ; I++) {

            var item = items[I];

            // Достаем ключевые понятия из имени и элементов количества
            var name = item.getElementsByTagName("name")[0]
                .firstChild.nodeValue;

            var quantity = item.getElementsByTagName("quantity")[0]
                .firstChild.nodeValue;

            // Создаем и добавляем список продуктов, HTML элемент  для этого продукта
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(name+" x "+quantity));
            contents.appendChild(li);
        }
    }

    // Обновляем итого в корзине, используя значение из документа корзины
    document.getElementById("total").innerHTML =
        cart.getAttribute("total");
}