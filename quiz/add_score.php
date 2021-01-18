 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$sid = $_GET['sid'];
$question = $_GET['qtnid']; 
$quiz = $_GET['qid']; 
$ans = $_GET['ans']; 
$exempt = 0; 
	$query = "SELECT * FROM `answer` WHERE answer='$ans' and question_id='$question '";
	$result = $con->query($query);  
	if($result->num_rows>0){
		while($row=$result->fetch_assoc()){ 
			 $exempt =  $row['correct'];  
		}
			$query = "INSERT INTO `quiz_score`(`student_id`, `quiz_name_id`, `correct`) VALUES ('$sid','$quiz','$exempt')";
		  if(mysqli_query($con, $query)){ 
			echo 'success';
		}else{
			echo 'error';
		}   
	}else{
		echo "no result"; 
	} 
	mysqli_close($con); 
?>