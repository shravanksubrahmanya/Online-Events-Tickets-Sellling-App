<?php

include ('dbcon.php');

$fname=$_POST['fname'];
$lname=$_POST['lname'];
$mailid=$_POST['mailid'];
$password=$_POST['password'];
$selectedgender=$_POST['selectedgender'];
$dateofbirth=$_POST['dateofbirth'];
$mobileno=$_POST['mobileno'];
$userimage=$_POST['imagetoupload'];
$moreinfo=$_POST['moreinfo'];

$q=mysqli_query($conn,"UPDATE user SET fname='$fname' & lname='$lname' & email='$mailid' & gender='$selectedgender' & phone='$mobileno' & dob='$dateofbirth' & password='$password' & user_image='$userimage' & moreinfo='$moreinfo' WHERE email='$mailid'");


$res=mysqli_fetch_array($q,MYSQLI_ASSOC);

if ($res) {

	echo "Updation is successful";
}

?>