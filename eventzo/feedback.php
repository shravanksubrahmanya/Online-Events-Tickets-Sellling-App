<?php

include ('dbcon.php');

$feedback=$_POST['feedback'];
$uid=$_POST['uid'];


$q=mysqli_query($conn,"INSERT INTO `feedback`(`uid`, `feedback`, `status`) VALUES ('$uid','$feedback','1')")or die(mysqli_error($conn));

if ($q) {

	echo "Feedback sent";
}

else
{
	echo "Feedback is not sent";
}

?>