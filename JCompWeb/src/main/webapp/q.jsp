<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<%String questionCode = request.getParameter("q");


String context = request.getContextPath();
%> 
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
  
    <title>Wite Code </title>
    <link href="res/css/style.css" rel="stylesheet">
    <script src="res/js/jquery.min.js"></script>
    <script src="res/js/jquery-ui.js"></script>

    <!--[if lt IE 9]>
    <script src="res/js/html5shiv372.min.js"></script>
    <![endif]-->
  </head>
	
  <body onload="fetchQuestionDetails()">
    <div class="warpper">
      <div class="container">
        <header><% // TODO move to header
        %>
          <div class="site-logo div-l"> <img src="res/img/ThemeProg.jpg" alt="Logo" border="0">
          <br>Click 'Check Ajax' and then scroll down.
          </div>


        </header>
		<section>
		<div class="content">
			<div id="question_header" class="question_header"></div>
			<div id="body1"></div>
			<div id=question_footer></div>		
		</div> 
		</section>	
		
		<section>
		<hr><br>
          <div class="content">
            
            <form name=userInput action="<%= context %>/api/ans"  method=POST >
              <div class="form-content">
                <div class="form-l">
                  <div class="form-control">
                    Class name of code <input size="50" type="text" name="clzName"  placeholder="Full class name like com.my.Adder"
                     value="">
                  </div>
                 
                  <div class="form-control">
                    Code : <textarea name="ans" id="ans" placeholder="Your code" rows=6 cols=210></textarea>
                    <br>Sample
                    <pre>
                                     
public class Adder{ 
	
    public int add(int i, int y){
    	
    	return i+y;
    	
    }
 }</pre>
                  </div>
                </div>
                
              </div>
              <div class="submit-success"><span>Thank You <br></span></div>
              <br>
               <input type=button onclick='submitQuestion()' value="Check Ajax"> 
               &nbsp;  <input type=submit  value="See raw Json response"> 
              </div>
            </form>
          </div>
          <br>Result:
          <div id='question_thankyou' class='question_thankyou' ></div>
          <br>
          <div id='dbg1' style="display : none;"></div>
        </section>
        
        
		
		<footer>	
          <div class="footer">
            <div class="div-l">Copyright © 2015 All rights reserved by Tushar Kapila, sel2in</div>
            <div class="div-r"><a href="">Terms of Use</a> | <a href="">Privacy Statement</a> 
            <br>
            Build 13 Facade : <%= s2n.jComp.facade.impl.DefaultClzApiFacade.getVersion() %>.
                        </div>
          </div>
        </footer>
       </div>
      </div>
      
      <%
	
	if(questionCode == null || questionCode.length() ==0){
%>
	
	<div id="errorNoSid" style="display: none">

	Bad or missing html <a href=http://sel2in.com />Support</a>
	</div>
	<%
	return;
}
%>

<script>


var questionObj = JSON.parse("{\"errno\": \"4\",\"error\": \"Init error\"}"); 
function dbg(s, jj){
	d = document.getElementById("dbg1");
	s = s + "";
	x =  s.replace("</", "-");
	x = x.replace("<", "--");
	x = x.replace(">", "--");
	if(jj==2){
		alert(x);
	}
	//d.innerHTML = d.innerHTML + "<br>" + x	;
}

function setInnerHtmlToObj(s, tos){
	d = document.getElementById(tos);
	d.innerHTML = s	;
}

// checkif question exists. if not show error. if yes server will return info about it, which we will use to display this form
// todo error handling , timeout etc

var questionCode = "<%= questionCode %>";
var contextPath = "<%= context %>";

function fetchQuestionDetails(){
	$.ajax({
	url: contextPath + "/api/q/" + questionCode,
	context: document.body,
	data : "questionCode=" + questionCode ,
	dataType : "json"
	}).success(function(data, txtSt, jqHr) {
		try{
			questionObj = data;
			
				//alert(questionObj.body2);
				setInnerHtmlToObj(questionObj.summary, "question_header");
				setInnerHtmlToObj(questionObj.details, "body1");
				//setInnerHtmlToObj(questionObj.footer, "question_footer");
				//setInnerHtmlToObj(questionObj.logo, "company_Logo");
				
			
		}catch(e){
			$('.question_header').css("color","red");				
			setInnerHtmlToObj("Could not load", "question_header");
		
		}
	}).error(function (jqXHR, textStatus, errorThrown){
		setInnerHtmlToObj("Network or Server Error", "question_header");
		document.userInput.style.display = "none";

		dbg("get suvery details error :  " + errorThrown + ", " + textStatus + ", " + jqXHR, 0);
	
	});
}


function submitQuestion(){

	var ans = document.userInput.ans.value;
	var cName = document.userInput.clzName.value;
	dbg("cName " + cName, 0);
	
	var dataString =  'name=' + escape(cName) + '&code=' + escape(questionCode) +  '&src='+ encodeURIComponent(ans) + '&';
	//alert(dataString);
	$.ajax({
	method : "POST",	
	url: contextPath + "/api/ans",
	context: document.body,
	data : dataString,
	dataType : "json"
	}).success(function(data, txtSt, jqHr) {
		try{
			r = data;
			s = "At " + new Date() 
			+ "<br>Status : " + r.status
			+ "<br>Compile :" + r.compileStatus + 
			"<br>Compile errors :" + r.compileErrors + "<br>Compile Msg :" +  r.compileMsgs +
			  "<hr><br>Test  :" + r.testStatus + " errors " + r.testErrors + "<br>Msgs " + r.testMsgs + "<hr>";
			s = s.replace("\n", "<br>\n" );//replace all TODO
			setInnerHtmlToObj(s , "question_thankyou");
			if(r.status) {
				//document.userInput.reset();
				$('.question_thankyou').css("color","green");

			} else {
				$('.question_thankyou').css("color","red");
				//setInnerHtmlToObj(r.error, "question_thankyou");
			}
			
		}catch(e){
			dbg("get suvery details err :  " + e, 0);
		}
		
	}).error(function (jqXHR, textStatus, errorThrown){
		setInnerHtmlToObj("Network or Server Error in Submitting Question", "question_thankyou");
		dbg("get suvery details error :  " + errorThrown + ", " + textStatus + ", " + jqXHR, 0);
	
	});
}


</script>
<br>

  </body>
</html>