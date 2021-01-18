<?php 
	//Creating a connection
	include 'db.php'; 
	$con = mysqli_connect(HOST, USER, PASSWORD, DB); 
    if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }  
	
	$sid = $_GET['sid'];
	$sql= "SELECT qr.std_number as stdno,`quiz_name` as quiz,(select count(*) from quiz_score where quiz_name_id=qn.id and correct=1) as score 
FROM `quiz_name` qn
INNER JOIN quiz_record qr 
ON qn.id=qr.quiz_name_id 
where qr.std_number='$sid'
order by qr.id DESC";
	
	$result = mysqli_query($con ,$sql);
	
	while ($row = mysqli_fetch_assoc($result)) {
		
		$array[] = $row;
		
	}
	header('Content-Type:Application/json');
	
	echo json_encode($array);
 
    mysqli_free_result($result);
 
    mysqli_close($con); 
 ?>