 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$sid = $_GET['id'];
$qid = $_GET['qid']; 
   
	$query = "SELECT * FROM `quiz_record` WHERE `std_number`='$sid' AND `quiz_name_id`='$qid' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
	if(isset($recordExists)){
		echo 'exist';
	} else{
		echo 'not';
	}
	
	mysqli_close($con);

?>