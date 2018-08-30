
var apiURL = "http://localhost:8080/alicerest/bot";

var input;

$(document).ready(function (){

    $('#enviar').on('click', function(){

    var that = $('#that').val();
    var topic = $('#topic').val();
    
    this.input = $('#questao').val();
    
    this.input = this.input.replace('?','');
    this.input = this.input.replace('/','');
    this.input = this.input.replace('!','');
    
    if(this.input == undefined || this.input == '')
    	return;
    
    that = (that == undefined || that == '')?'*':that;
    topic = (topic == undefined || topic == '')?'*':topic;

    $.ajax({
	        url:'http://10.32.96.210:8080/alicerest/bot/query/ask/' + this.input + '/' + that + '/' + topic.replace('"', ''),
			//url: 'http://alice-alicebot.a3c1.starter-us-west-1.openshiftapps.com/alicerest/bot/query/ask',
        	//data:tmp,
		    contentType: "application/json; charset=utf-8",
		    dataType: 'json',
		    type:'GET',
		    crossDomain: true,
			async:true
}).
                        pipe(function(data) { console.log("Successo!!!"); mostrar(data);scrollDown();}).
                        fail(function() { console.log('Falhou!');erro();scrollDown();});


    });
});


 function mostrar(data){
	var questao = "";
	var resposta = "";
	var output = $("<div class='output'/>");

	questao = data.questao;
    resposta = data.conteudo;
    
    $('#that').val(data.that);
    $('#topic').val(data.topic);
    
	var clickableDiv = $("<div class='inputDiv'>Questão: " + questao + "</div>");
    //clickableDiv.addClass('clickable');
    output.append(clickableDiv);
 
    var tag = $("<span/>");
	tag.html('<p align="justify">Resposta: ' + resposta + '</p>');
	output.append(tag);

	$('#mainOutput').append(output);
	$('#questao').val('');
	$('#questao').focus();
}
 
 function erro(){
		var questao = "";
		var output = $("<div class='output'/>");

		questao = $('#questao').val();
	    
		var clickableDiv = $("<div class='inputDiv'>Questão: " + questao + "</div>");
	    //clickableDiv.addClass('clickable');
	    output.append(clickableDiv);
	 
	    var tag = $("<span/>");
		tag.html('Ocorreu um erro ao processar: ' + this.input);
		output.append(tag);

		$('#mainOutput').append(output);
		this.input = '';
	}
 
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