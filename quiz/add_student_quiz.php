 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$sid = $_GET['sid'];
$qid = $_GET['qid'];   
		$query = "INSERT INTO `already_take`(`student_id`, `question_id`) VALUES ('$sid','$qid')";
		  if(mysqli_query($con, $query)){ 
			echo 'success';
		}else{
			echo 'error';
		}  
	mysqli_close($con);

?>