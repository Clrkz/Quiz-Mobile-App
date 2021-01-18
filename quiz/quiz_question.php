<?php 

include 'db.php';

if($_SERVER['REQUEST_METHOD'] == 'POST'){
$sid 	=$_POST['sid']; 
$qid = $_POST['qid'];  
	$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");

   $exempt = "";
   
	$query = "SELECT * FROM `already_take` WHERE student_id='$sid'";
	$result = $con->query($query);
	$counter = 0;
	$count =  $result->num_rows;
	if($result->num_rows>0){
		while($row=$result->fetch_assoc()){ 
			 $counter++;
			 $exempt =  $exempt.$row['question_id']; 
			 if($count!=$counter){
				  $exempt =  $exempt.",";
			 }
		} 
			$sql= "SELECT * FROM `question` WHERE `quiz_name_id`='$qid' and id NOT IN($exempt) ORDER BY RAND() LIMIT 1"; 
	$result = mysqli_query($con, $sql);
	$data = mysqli_fetch_array($result); 
	if(isset($data)){
  $data["success"] = 1;
  $data["id"];
  $data["question"];  
	}else{
		  $data["success"] = 0;
	} 
  echo json_encode($data); 
	}else{
		echo "no result";
		
	} 
	mysqli_close($con);

}
 ?>