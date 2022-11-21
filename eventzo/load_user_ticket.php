<?php

include 'dbcon.php';

$uid=$_POST['uid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT fname,lname,email,phone FROM `user` WHERE `uid`='$uid'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['lname'] = $row['lname'];
    $row_array['email'] = $row['email'];
     $row_array['phno'] = $row['phone'];
      
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>