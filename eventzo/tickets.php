<?php

include ('dbcon.php');

$uid=$_POST['uid'];
$eid=$_POST['event_id'];
$tickettype=$_POST['tickettype'];
$price=$_POST['price'];
$noticket=$_POST['noticket'];


$q=mysqli_query($conn,"INSERT INTO `tickets`(`uid`, `event_id`, `type`, `price`, `notickets`) VALUES ('$uid','$eid','$tickettype','$price','$noticket')");



if ($q) {

	echo "Tickets information uploaded";
}

else
{
	echo "Tickets information is not uploaded";
}

?>