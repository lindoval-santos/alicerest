var apiURL = "http://localhost:8080/alicerest/bot";

$(document).ready(function (){


    $('#enviar').on('click', function(){

    var input = $('#questao').val();

    $.ajax({
		        url:'http://localhost:8080/alicerest/bot/query/ask/'+input,
//			url: 'http://alice-alicebot.a3c1.starter-us-west-1.openshiftapps.com/alicerest/bot/query/ask/' + input,
//        		//data:input,
		        contentType: "application/json; charset=utf-8",
		        dataType: 'json',
		        type:'GET',
		        crossDomain: true,
			async:true
}).
                        pipe(function(data) { console.log("Successo!!!"); mostrar(data);}).
                        fail(function() { console.log('Falhou!');})


    });
});


 function mostrar(data){
	var questao = "";
	var resposta = "";
	var output = $("<div class='output'/>");

	questao = data.questao;
        resposta = data.conteudo;
	var clickableDiv = $("<div class='inputDiv'>Questão: " + questao + "</div>");
        clickableDiv.addClass('clickable');
        output.append(clickableDiv);

    	var tag = $("<span/>");
	tag.html('Resposta: ' + resposta);
	output.append(tag);

	$('#mainOutput').append(output);
	$('#questao').val('');
}


