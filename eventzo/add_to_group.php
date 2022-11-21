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


 $q=mysqli_query($conn,"INSERT INTO `groups`(`uid`, `connectedid`, `grpname`, `fname`, `lname`, `image`, `gender`, `dob`, `moreinfo`) VALUES ('$uid','$connectedid','$grpname','$fname','$lname','$image','$gender','$dob','$moreinfo')");

 if ($q) {

 		echo "Added to the group";
 }

 else
 {
 	echo "Not added to the group";
 }

?>