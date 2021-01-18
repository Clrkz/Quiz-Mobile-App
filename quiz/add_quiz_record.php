 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$sid = $_GET['sid'];
$qid = $_GET['qid'];   
$score = $_GET['score'];   

		$query = "INSERT INTO `quiz_record`(`std_number`, `quiz_name_id`, `score`) VALUES ('$sid','$qid','$score')";
		  if(mysqli_query($con, $query)){ 
			echo 'success';
		}else{
			echo 'error';
		}  
	mysqli_close($con);

?>