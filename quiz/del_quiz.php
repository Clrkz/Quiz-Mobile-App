<?php 
	//Creating a connection
	include 'db.php';
	
	$con = mysqli_connect(HOST, USER, PASSWORD, DB);
	 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
	/*Get the id of the last visible item in the RecyclerView from the request and store it in a variable. For            the first request id will be zero.*/	
	$id = $_GET["id"];
		$status = $_GET["status"];
	 
	$sql= "UPDATE `quiz_name` SET status = '$status' WHERE `id`='$id'"; 
	
	  if(mysqli_query($con, $sql)){
			echo 'success';
		}else{
			echo 'error';
		} 
	  
    mysqli_close($con); 
 ?>