$(function(){
	changeTime();  
});
var i=3;  

function changeTime(){

   document.getElementById("noPerm").innerHTML=i;   

   i--;   

}

var inId=window.setInterval("changeTime()",1000);
	
	
  	function autoRedirect(){   
	
		if(history.length>1){
			history.go(-1);  
		}else{
			top.window.opener = null;
			top.window.close();
		}
  		
	
	}  

window.setInterval("autoRedirect()",3000);