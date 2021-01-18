<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$email = $_POST['email'];
	$password = $_POST['password'];
	$hashPass = md5($password);
 
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT * FROM professor WHERE prf_email = '$email' AND prf_password = '$hashPass'";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
 
	if(isset($data)){
  $data["success"] = 1;
  $data["id"];
  $data["prf_fname"];
  $data["prf_mname"];
  $data["prf_lname"];
  $data["prf_email"];
  $data["prf_password"]; 

	}else{
		  $data["success"] = 0;
	}

  echo json_encode($data);
	mysqli_close($con);
}



?>