<?php

include ('dbcon.php');

$event_id=$_POST['event_id'];
$uid=$_POST['uid'];
$grpname=$_POST['grpname'];
$event_name=$_POST['event_name'];
//$event_image=$_POST['event_image'];
$synopsis=$_POST['synopsis'];
$type=$_POST['type'];

$event_image=mysqli_query($conn,"SELECT `image` FROM `event` WHERE event_id='$event_id'")or die(mysqli_error($conn));
$a=mysqli_fetch_array($event_image,MYSQLI_ASSOC);
$e_image=$a["image"];



$q=mysqli_query($conn,"INSERT INTO `notice`(`uid`, `grpname`, `event_id`, `event_name`, `event_image`, `synopsis`, `type`) VALUES ('$uid','$grpname','$event_id','$event_name','$e_image','$synopsis','$type')")or die(mysqli_error($conn));

if ($q) {

	echo "Added to the notice";
}

else
{
	echo "Not added to the notice";
}

?>