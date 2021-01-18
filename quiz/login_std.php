<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$stdno = $_POST['stdno'];
	$password = $_POST['password'];
	$hashPass = md5($password);
 
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT * FROM student WHERE std_number = '$stdno' AND std_password = '$hashPass'";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
  
	if(isset($data)){
  $data["success"] = 1;
  $data["std_number"];
  $data["std_fname"];
  $data["std_mname"];
  $data["std_lname"];
  $data["std_password"]; 

	}else{
		  $data["success"] = 0;
	}

  echo json_encode($data);
	mysqli_close($con);
}



?>