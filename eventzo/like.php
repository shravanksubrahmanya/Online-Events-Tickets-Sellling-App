<?php 
include ('dbcon.php');
$uid=$_POST['uid'];
$eid=$_POST['eid'];

$q=mysqli_query($conn,"SELECT * FROM `reaction` WHERE event_id='$eid' and u_id='$uid' ");
if (mysqli_num_rows($q)<=0) {
	$q=mysqli_query($conn,"INSERT INTO `reaction`(`event_id`, `u_id`) VALUES ('$eid','$uid')");
	echo "liked";
}
else{
$q=mysqli_query($conn," DELETE FROM `reaction` WHERE event_id='$eid' and u_id='$uid'  ");
	echo "disliked";


}


 ?>