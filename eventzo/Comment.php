<?php

include ('dbcon.php');

$eid=$_POST['event_id'];
$uid=$_POST['uid'];
$comment=$_POST['comment'];
$fname=$_POST['fname'];
$lname=$_POST['lname'];
$image=$_POST['image'];

//$fn=mysqli_query($conn,"SELECT 'fname' FROM `user` WHERE uid='$uid'")or die(mysqli_error($conn));

//$ln=mysqli_query($conn,"SELECT 'lname' FROM `user` WHERE uid='$uid'")or die(mysqli_error($conn));

//$i=mysqli_query($conn,"SELECT 'user_image' FROM `user` WHERE uid='$uid'")or die(mysqli_error($conn));

$q=mysqli_query($conn,"INSERT INTO `comments`(`user_id`, `event_id`, `comments`, `fname`, `lname`, `image`) VALUES ('$uid','$eid','$comment','$fname','$lname','$image')")or die(mysqli_error($conn));

if ($q) {

	echo "Comment added successfully";
}

else
{
	echo "Comment is not added";
}

?>