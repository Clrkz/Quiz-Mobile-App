 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$stdno = $_GET['stdno'];
$fname = $_GET['fname'];
$mname = $_GET['mname'];
$lname = $_GET['lname'];
$pass = $_GET['password'];
$hashpass = md5($pass);

 
	$query = "select std_number from student where std_number = '$stdno' ";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
	if(isset($recordExists)){
		echo 'exist';
	}else{
		$query = "INSERT INTO `student`(`std_number`, `std_fname`, `std_mname`, `std_lname`, `std_password`) VALUES ('$stdno','$fname','$mname','$lname','$hashpass')";
		  if(mysqli_query($con, $query)){ 
			echo 'signup';
		}else{
			echo 'error';
		} 
	} 
	mysqli_close($con);

?>