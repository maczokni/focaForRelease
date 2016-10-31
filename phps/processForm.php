<?php



// now connect to the database
$db = mysql_connect("ADDRESS","USERNAME","PASSWORD") or die ("could not connect");
mysql_select_db('DATABASE');


// extract the values from the form
$VARNAME_1 = $_REQUEST['VARNAME_1'];
$VARNAME_2 = $_REQUEST['VARNAME_2'];
$VARNAME_3 = $_REQUEST['VARNAME_3'];
$coords ="geomfromtext('POINT(".$latitude." ".$longitude.")')";


// write the SQL insert script for the point
$query = "insert into TABLENAME (VARNAME_1, VARNAME_2, VARNAME_3, coords) values (";
$query = $query."'".$VARNAME_1."','".$VARNAME_2."','".$VARNAME_3."',".$coords."');";

// run the insert query
// if the result is TRUE it has worked
if (mysql_query($query,$db)) {
	echo("Your data has been saved to the databse");
}
else {
	echo("There was an error saving your data");
}


?>