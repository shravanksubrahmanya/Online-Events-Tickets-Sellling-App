<?php

include ('dbcon.php');

$fname=$_POST['fname'];
$lname=$_POST['lname'];
$uid=$_POST['uid'];
$eid=$_POST['eid'];
$email=$_POST['email'];
$transactionid=$_POST['transactionid'];
$event_name=$_POST['event_name'];
$ticket_type=$_POST['ticket_type'];
$nooftickets=$_POST['nooftickets'];
$phno=$_POST['phno'];
$totalamount=$_POST['totalamount'];
$tl=$_POST['tickets_left'];



$s=mysqli_query($conn,"UPDATE `tickets` SET `notickets`='$tl' WHERE event_id='$eid'")or die(mysqli_error($conn));

$q=mysqli_query($conn,"INSERT INTO `booking`(`event_id`,`user_id`,`transactionid`,`event_name`,`ticket_type`,`noofticket`,`totalamount`,`fname`,`lname`,`phone`,`email`,`status`) VALUES ('$eid','$uid','$transactionid','$event_name','$ticket_type','$nooftickets','$totalamount','$fname','$lname','$phno','$email','1')")or die(mysqli_error($conn));

if ($q) {

	echo "Purchase is successful";
}

else
{
	echo "Purchase failed";
}

?>UPDATE `tickets` SET `tid`=[value-1],`uid`=[value-2],`event_id`=[value-3],`type`=[value-4],`price`=[value-5],`notickets`=[value-6] WHERE 1