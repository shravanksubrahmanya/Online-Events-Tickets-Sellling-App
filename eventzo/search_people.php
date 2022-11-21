<?php

include 'dbcon.php';

$searchitem=$_POST['searchitem'];
$uid=$_POST['uid'];
$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `user` WHERE fname='$searchitem' OR lname='$searchitem' OR moreinfo='$searchitem' OR dob='$searchitem'AND uid!='$uid'"); 
if ($fetch) {

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['uid'] = $row['uid'];

    $row_array['lname'] = $row['lname'];
    $row_array['image'] = $row['user_image'];
     $row_array['dob'] = $row['dob'];
       $row_array['gender'] = $row['gender'];
       $row_array['moreinfo'] = $row['moreinfo'];

    array_push($return_arr,$row_array);
}
echo json_encode($return_arr);
}
else
{
	echo "No search results found";
}
?>