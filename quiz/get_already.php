 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$sid = $_GET['id'];
$qid = $_GET['qid']; 
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
		echo $exempt;
		
		
		
		
		
	}else{
		echo "no result";
		
	}
	
	
	/*
	
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
	if(isset($recordExists)){
		echo 'exist';
	} else{
		echo 'not';
	}
	*/
	mysqli_close($con);

?>