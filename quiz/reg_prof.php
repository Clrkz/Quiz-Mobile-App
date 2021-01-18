 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$email = $_GET['email'];
$fname = $_GET['fname'];
$mname = $_GET['mname'];
$lname = $_GET['lname'];
$pass = $_GET['password'];
$hashpass = md5($pass);

 
	$query = "select prf_email from professor where prf_email = '$email'";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
	if(isset($recordExists)){
		echo 'exist';
	}else{
		$query = "INSERT INTO `professor`(`prf_fname`, `prf_mname`,  `prf_lname`, `prf_email`, `prf_password`) VALUES ('$fname','$mname','$lname','$email','$hashpass')";
		  if(mysqli_query($con, $query)){
			echo 'signup';
		}else{
			echo 'error';
		} 
	} 
	mysqli_close($con);

?>