/*
 * 算术工具类
 */

//减法函数  
function subtract(arg1, arg2) { 
	if(arg1 == null)arg1 = 0;
	if(arg2 == null)arg2 = 0;
    var r1, r2, m, n;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));  
     //last modify by deeka  
     //动态控制精度长度  
    n = (r1 >= r2) ? r1 : r2;  
    return ((arg1 * m - arg2 * m) / m).toFixed(n);  
}
//乘法函数
function multiply(arg1,arg2){
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{
    	m+=s1.split(".")[1].length;
    	}catch(e){
    		
    	}
    try{
    	m+=s2.split(".")[1].length;
    	}catch(e){
    		
    	}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
//除法函数  
function divide(arg1, arg2) {  
	if(arg1 == null)arg1 = 0;
	if(arg2 == null)arg2 = 0;
    var t1 = 0, t2 = 0, r1, r2;  
    try {  
        t1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        t2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    with (Math) {  
        r1 = Number(arg1.toString().replace(".", ""));  
        r2 = Number(arg2.toString().replace(".", ""));  
        return (r1 / r2) * pow(10, t2 - t1);  
    }  
}
//加法函数  
function add(arg1, arg2) {  
    var r1, r2, m;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));
    
    var result = (arg1 * m + arg2 * m) / m;
    
    result = Math.round(multiply(result, Math.pow(10, 2))) / Math.pow(10, 2);
    return result;  
}