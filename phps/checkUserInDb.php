<?php


//connect to the mysqul database
//variables in php are names with a $ as first character
$db = mysql_connect("ADDRESS","USERNAME","PASSWORD") or die ("could not connect");
mysql_select_db('DATABASE');

$name =  $_REQUEST['name'];
//get data from table
$query="select * from TABLENAME WHERE name = '".$name."';";
$result=mysql_query($query, $db);

//check if query worked
if ($result && mysql_num_rows($result)>0) {

	
		echo("USER REGGED");
	} 

	
	
	else {
		echo("USER NOT REGGED"); 
		} // end of the else statement 


	?>