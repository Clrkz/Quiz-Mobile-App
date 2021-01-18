<?php 
	//Creating a connection
	include 'db.php'; 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB); 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }  
	$sql= "SELECT *,(SELECT count(*) FROM `question` where quiz_name_id=qn.id ) as item FROM `quiz_name` qn ORDER BY quiz_name ASC";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con); 
 ?>