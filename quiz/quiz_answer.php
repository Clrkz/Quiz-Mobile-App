<?php 

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){
$qtnid = $_POST['qtnid']; 
 $letter = $_POST['letter']; 
 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect"); 
	$sql= "SELECT * FROM `answer` WHERE `question_id` = '$qtnid' ORDER BY `id` ASC LIMIT $letter,1";
	
	$result = mysqli_query($con, $sql);
	$data = mysqli_fetch_array($result);
  
	if(isset($data)){
  $data["success"] = 1;
  $data["id"];
  $data["answer"];
  $data["correct"]; 

	}else{
		  $data["success"] = 0;
	}

  echo json_encode($data);
	mysqli_close($con);

}
 ?>