<?php

include ('dbcon.php');

$username=$_POST['username'];
$uid=$_POST['uid'];
$eid=$_POST['eid'];
$transactionid=$_POST['transactionid'];
$event_name=$_POST['event_name'];
$phno=$_POST['phno'];
$email=$_POST['email'];
$ticket_type=$_POST['ticket_type'];
$noofticket=$_POST['noofticket'];
$totalamount=$_POST['totalamount'];


$q=mysqli_query($conn,"INSERT INTO `booking`(`event_id`, `user_id`, `transactionid`, `event_name`,'ticket_type','noofticket','totalamount', `uname`, `phone`, `email`, `status`) VALUES ('$eid','$uid','$transactionid','$event_name','$ticket_type','$noofticket','$totalamount','$username','$phno','$email','1')");

if ($q) {

	echo "Purchase is successful";
}

else
{
	echo "Purchasing failed";
}

?>