
//var apiURL = 'http://10.32.96.210:8080/alicerest/bot/query/ask/';
//var apiURL = 'http://alice-alicebot.a3c1.starter-us-west-1.openshiftapps.com/alicerest/bot/query/ask/';

$(document).ready(function (){

    $('#enviar').on('click', function(){
    debug('Iniciando');
    //var that = $('#that').val();
    //var topic = $('#topic').val();
    
    var input = $('#questao').val();

    if(input == '')
    	return;
    
/*    input = input.replace("?","");
    input = input.replace("/","");
    input = input.replace("!","");
    
    that = that.replace("?","");
    that = that.replace("/","");
    that = that.replace("!","");
    
    topic = topic.replace("?","");
    topic = topic.replace("/","");
    topic = topic.replace("!",""); */   
    
    debug('realizou todos os replaces');
    
/*    that = (that == undefined || that == '' || that == "")?"*":that;
    topic = (topic == undefined || topic == '')?"*":topic;
    
    topic = topic.replace("\"", "");*/
    
    //that = '*';
    //topic = that;
    
    debug('input: '+input);//+'| '+that+'| '+topic);
    
    $.ajax({
	        url: 'http://alice-alicebot.a3c1.starter-us-west-1.openshiftapps.com/alicerest/bot/query/ask/' + input + '/*/*',
	        //url: 'http://10.32.96.210:8080/alicerest/bot/query/ask/' + input + '/' + that + '/' + topic,
			//url: this.apiURL + input + '/' + that + '/' + topic,
        	//data:tmp,
		    contentType: "application/json; charset=utf-8",
		    dataType: 'json',
		    type:'GET',
		    crossDomain: true,
			async:true
}).
                        pipe(function(data) { console.log("Successo!!!"); mostrar(data);}).
                        fail(function() { console.log('Falhou!');erro(input);});


    });
});

function debug(msg){
	$("#spandebug").text(msg);;
}

function mostrar(data){
	var questao = "";
	var resposta = "";
	var output = $("<div class='output'/>");

	debug('Retornou sucesso, valores: '+data.questao+'--'+data.resposta+'--'+data.that+'--'+data.topic);
	questao = data.questao;
    resposta = data.conteudo;
    
    $('#that').val(data.that);
    $('#topic').val(data.topic);
    
	var clickableDiv = $("<div class='inputDiv'>"+new Date().toLocaleString()+"<br/>Questão: " + questao + "</div>");
    //clickableDiv.addClass('clickable');
    output.append(clickableDiv);
    
    var tag = $("<span/>");
	tag.html('<p align="justify">Resposta: ' + resposta + '</p>');
	output.append(tag);

	//$('#mainOutput').append(output);
	$('#mainOutput').prepend(output);
	$('#questao').val('');
	//$('#questao').focus();
	debug('terminou de renderizar a resposta');
}
 
function erro(input){
		
		debug('Erro inicio');
		
		var questao = "";
		var output = $("<div class='output'/>");
		questao = $('#questao').val();
	    
		var clickableDiv = $("<div class='inputDiv'>"+new Date().toLocaleString()+"<br/>Questão: " + questao + "</div>");
	    //clickableDiv.addClass('clickable');
	    output.append(clickableDiv);
	    debug('Erro meio');
	    var tag = $("<span/>");
		tag.html('Ocorreu um erro ao processar: ' + input);
		output.append(tag);

		//$('#mainOutput').append(output);
		$('#mainOutput').prepend(output);
		input = '';
		debug('Erro fim');
	}
 
/*
 
function scrollDown(){
	 $("#mainOutput").animate({ scrollTop: $('#mainOutput').prop("scrollHeight")}, 1000);
 }
 
 $("#questao").on('keydown', function(e) {
     if (e.which == 13) {
          $("#enviar").trigger('click');
         return false;
     }
     return true;
 });
 */