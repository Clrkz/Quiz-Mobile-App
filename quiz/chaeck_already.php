<?php

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){

	$sid = $_POST['sid'];
	$qid = $_POST['qid']; 
 
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
	
	$query = "SELECT * FROM `already_take` WHERE `student_id`='$sid' AND `question_id`='$qid'";
	$result = mysqli_query($con, $query);
	$data = mysqli_fetch_array($result);
  
	if(isset($data)){
  $data["success"] = 1;  
	}else{
		  $data["success"] = 0;
	}

  echo json_encode($data);
	mysqli_close($con);
}



?>