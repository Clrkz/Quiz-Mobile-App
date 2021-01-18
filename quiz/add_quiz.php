 
<?php
include 'db.php';

$con = mysqli_connect(HOST, USER, PASSWORD, DB) or die("Unable to Connect");
$qname = $_GET['name'];
$qquestion = $_GET['question'];
$ans[0] = $_GET['A'];
$ans[1] = $_GET['B'];
$ans[2] = $_GET['C'];
$ans[3] = $_GET['D']; 
$correct = $_GET['correct'];
$lid = $_GET['lid'];
$right = $_GET[$correct];
 $lastID;
$lastIDQ; 
$last = $con->insert_id;
		   

 if($lid!='0'){//


	$query = "INSERT INTO `question`(  `quiz_name_id`, `question`) VALUES ('$lid','$qquestion')";
  if(mysqli_query($con, $query)){//
  	 $lastIDQ = $con->insert_id; 
for($i=0; $i<4; $i++){//
$query = "INSERT INTO `answer`( `question_id`, `answer`, `correct`) VALUES ('$lastIDQ','$ans[$i]',0)"; 
mysqli_query($con, $query);
}//
$query = "UPDATE `answer` SET  `correct`=1 WHERE question_id='$lastIDQ' and answer='$right' ";
mysqli_query($con, $query);
echo 'signup';
  }//
else{
			echo 'error';
		}





 }else{
	$query = "select quiz_name from quiz_name where quiz_name = '$qname'";
	$recordExists = mysqli_fetch_array(mysqli_query($con, $query)); 
	if(isset($recordExists)){
		echo 'exist';
	}else{
			$query = "INSERT INTO `quiz_name`(`quiz_name`,status) VALUES ('$qname',1)";
				  if(mysqli_query($con, $query)){
				  		 $lastID = $con->insert_id; 
	$query = "INSERT INTO `question`(  `quiz_name_id`, `question`) VALUES ('$lastID','$qquestion')";
  if(mysqli_query($con, $query)){
  	 $lastIDQ = $con->insert_id; 
for($i=0; $i<4; $i++){
$query = "INSERT INTO `answer`( `question_id`, `answer`, `correct`) VALUES ('$lastIDQ','$ans[$i]',0)"; 
mysqli_query($con, $query);
}
$query = "UPDATE `answer` SET  `correct`=1 WHERE question_id='$lastIDQ' and answer='$right' ";
mysqli_query($con, $query);
echo 'signup';
  }

				}else{
			echo 'error';
		}




		 
	}
}
	mysqli_close($con);

?>