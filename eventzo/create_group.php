<?php

include ('dbcon.php');


$uid=$_POST['uid'];
$connectedid=$_POST['connectedid'];
$grpname=$_POST['grpname'];
$fname=$_POST['fname'];
$lname=$_POST['lname'];
$image=$_POST['image'];
$gender=$_POST['gender'];
$dob=$_POST['dob'];
$moreinfo=$_POST['moreinfo'];


 $q=mysqli_query($conn,"INSERT INTO `groups`(`uid`, `connectedid`, `grpname`, `fname`, `lname`, `image`, `gender`, `dob`, `moreinfo`) VALUES ('$uid','$connectedid','$grpname','$fname','$lname','$image','$gender','$dob','$moreinfo') ") or die(mysqli_error($conn));

 if ($q) {

 		echo "Group created";
 }

 else
 {
 	echo "Group is not created";
 }

?>