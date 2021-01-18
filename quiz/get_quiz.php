<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$qname = $_POST['quiz_name'];  
 
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT id FROM quiz_name WHERE quiz_name='$qname' ORDER BY id DESC LIMIT 0,1";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
  
	if(isset($data)){
  $data["success"] = 1;
  $data["id"]; 
	}else{
		  $data["success"] = 0;
	}

  echo json_encode($data);
	mysqli_close($con);
}
 
?>