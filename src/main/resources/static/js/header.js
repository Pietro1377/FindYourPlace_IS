function actionOpen(drpName) {
    let drpCnt= document.getElementById("drpCnt"+drpName);
    if (drpCnt.className === "dropdown-content")
        drpCnt.className = "dropdown-content dropdown-content--active";
    else
        drpCnt.className = "dropdown-content";

    let closeAct= document.getElementById("closeAct");
    if (closeAct.className === "closeAction")
        closeAct.className = "closeAction closeAction--appear";
    else
        closeAct.className = "closeAction";
}
function actionClose() {
    let drpCntArr= document.getElementsByClassName("dropdown-content");

    for (let drpCnt of drpCntArr) {
        if (drpCnt.className === "dropdown-content dropdown-content--active")
            drpCnt.className = "dropdown-content";
    }

    let closeAct= document.getElementById("closeAct");
    if (closeAct.className === "closeAction closeAction--appear")
        closeAct.className = "closeAction";
}

function actionOpenNot() {
    let drpCnt= document.getElementById("drpCntNotification");
    if (drpCnt.className === "dropdown-contentNot")
        drpCnt.className = "dropdown-contentNot dropdown-contentNot--active";
    else
        drpCnt.className = "dropdown-contentNot";
}
function actionCloseNot() {
    let drpCnt= document.getElementById("drpCntNotification");
    drpCnt.className = "dropdown-contentNot";
}


window.addEventListener("scroll", function() {
    let body=  document.getElementsByTagName("body")[0];
    let head=  document.getElementsByClassName("mainHeader")[0];
    if (window.scrollY!==0){
        if (window.innerWidth>=1024){
            body.style.paddingTop="80px";
        }
        else{
            body.style.paddingTop="62px";
        }
        head.style.position="fixed";
        head.style.top="0px";
        head.style.left="0px";
        head.style.width="100%";
    } else{
        body.style.removeProperty("padding-top");
        head.style.removeProperty("position");
        head.style.removeProperty("top");
        head.style.removeProperty("left");
        head.style.removeProperty("width");
    }

});

const CONTEXT_PATH = $('#contextPathHolder').attr('href');

$(document).ready(function (){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: CONTEXT_PATH + 'retrieveNot',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(response, textStatus, xhr) {
            if(xhr.status === 200) {
                let listaNot=$('#notify-item-list');
                listaNot.html('');
                $("#countNot").text(response.notifiche.length);
                if (response.notifiche.length>=1){
                    response.notifiche.forEach(function (notifica){
                        let itemNot=`
                    <li id="notify-item`+notifica.idNotifica+`">
                        <div class="notify-item-wrapper">
                        <div class="notify-item-readbtn">
                            <a onclick="readNot(`+notifica.idNotifica+`)">Segna come letta</a>
                        </div>
                            <div class="notify-item-author">`+notifica.autore+ `</div>
                            <div class="notify-item-content">`+notifica.testo+ `</div>
                            <div class="notify-item-dates">
                                <span class="notify-send-date">`+notifica.dataInvio+ `</span>
                                <span class="notify-expire-date">`+notifica.dataScadenza+ `</span>
                            </div>
                        </div>
                    </li>
                    `
                        listaNot.append(itemNot);
                    })
                }else{
                    let alertNot=$("#alert-no-notify");
                    alertNot.text("Attualmente non hai nessuna notifica!");
                    alertNot.show();
                }

            } else {
                console.log("HTTP Status imprevisto: " + xhr.status)
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if(xhr.status === 401) {
                console.log("L'utente non è loggato")
            }
            else {
                console.log("Errore HTTP Status imprevisto: " + textStatus + errorThrown)
            }
        }
    });
})

function readNot(idNot){
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: 'POST',
        url: CONTEXT_PATH + 'isReadNot',
        data: {
            "idNotifica" : idNot
        },
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(response, textStatus, xhr) {
            if(xhr.status === 200) {
                let notItem=$('#notify-item'+idNot);
                let updateCount=$("#countNot");
                notItem.remove();
                updateCount.text(parseInt(updateCount.text())-1);
                if (updateCount.text()==="0" || document.getElementById("#alert-no-notify").innerText.trim().length === 0 ){
                    let alertNot=$("#alert-no-notify");
                    alertNot.text("Attualmente non hai nessuna notifica!");
                    alertNot.show();
                }
            } else {
                console.log("HTTP Status imprevisto: " + xhr.status)
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            if(xhr.status === 401) {
                console.log("L'utente non è loggato")
            }
            else {
                console.log("Errore HTTP Status imprevisto: " + textStatus + errorThrown)
            }
        }
    });

}

$(function() {
        $('#drpCntAccount, .account-btn, .notify-btn, #drpCntNotification').click(function(e) {
        e.stopPropagation();
    });
});

$(function(){
    $(document).click(function(){
        actionClose();
        actionCloseNot();
    });
});

