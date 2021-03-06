
$(document).ready(function (){

    $('#enviar').on('click', function(){
    
    var input = $('#questao').val();

    if(input == '')
    	return;
    
    var apiURL;
    
    apiURL = 'bot/query/ask/';	

    
    input = input.replace("?","");
    input = input.replace("/","");
    input = input.replace("!","");
    
    apiURL = apiURL + input;
    
    var send = apiURL;
    
    $.ajax({
    		url: send,
		    contentType: "application/json; charset=utf-8",
		    dataType: 'json',
		    type:'GET',
		    crossDomain: true,
			async:true
}).
                        pipe(function(data) { console.log("Successo!!!"); mostrar(data);scrollDown();}).
                        fail(function() { console.log('Falhou!');erro(input);scrollDown();});


    });
});

function mostrar(data){
	var questao = "";
	var resposta = "";
	var output = $("<div class='output'/>");

	questao = data.questao;
    resposta = data.conteudo;
    
    questao = questao.replace('<', '').replace('>','').replace('/', '');
    
	var clickableDiv = $("<div class='inputDiv'>"+new Date().toLocaleString()+"<br/>Questão: " + questao + "</div>");
    //clickableDiv.addClass('clickable');
    output.append(clickableDiv);
    
    var tag = $("<span/>");
    var p = $("<p/>");

    p.css("text-align", "justify").html('Resposta: ' + resposta);

	tag.append(p).serialize();

    output.append(tag);

	$('#mainOutput').append(output);
	//$('#mainOutput').prepend(output);
	$('#questao').val('');
	$('#questao').focus();
}
 
function erro(input){
		
		var _questao = "";
		var output = $("<div class='output'/>");
		_questao = $('#questao').val();
	    
		var clickableDiv = $("<div class='inputDiv'>"+new Date().toLocaleString()+"<br/>Questão: " + _questao + "</div>");
	    //clickableDiv.addClass('clickable');
	    output.append(clickableDiv);

	    var tag = $("<span/>");
	    var p = $("<p/>");
	    p.css("text-align", "justify").html('Ocorreu um erro ao processar: ' + input);
	    
	    tag.append(p);
		output.append(tag);

		$('#mainOutput').append(output);

		input = '';
	}
 
/*function isDebugging() {
    if (document.location.hostname == "l" || document.location.hostname == "0.0.0.0") return true;
    return document.location.hostname == "localhost" || document.location.hostname == "127.0.0.1";
}*/

function scrollDown(){
	 $("#mainOutput").animate({ scrollTop: $('#mainOutput').prop("scrollHeight")}, 1000);
 }

 $("#questao").on('keydown', function(e) {
     if (e.which == 13) {
         $("#enviar").trigger('click');
         e.preventDefault();
     }
 });
 