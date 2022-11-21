<?php

include ('dbcon.php');

$message=$_POST['message'];
$uid=$_POST['uid'];
$eid=$_POST['event_id'];

$q=mysqli_query($conn,"INSERT INTO `enews`(`event_id`, `user_id`, `messages`) VALUES ('$eid','$uid','$message')")or die(mysqli_error($conn));

if ($q) {

	echo "Message added";
}

else
{
	echo "Message is not added";
}

?>