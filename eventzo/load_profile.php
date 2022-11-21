<?php

include 'dbcon.php';


$id=$_POST['uid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `user` where `uid`='$id'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['image'] = $row['user_image'];
    $row_array['lname'] = $row['lname'];
    $row_array['email'] = $row['email'];
     $row_array['gender'] = $row['gender'];
       $row_array['phone'] = $row['phone'];
          $row_array['dob'] = $row['dob'];
          $row_array['password'] = $row['password'];
           $row_array['moreinfo'] = $row['moreinfo'];
     

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);




?>