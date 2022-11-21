<?php

include ('dbcon.php');

$eid=$_POST['eid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `comments` WHERE event_id='$eid'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['lname'] = $row['lname'];
    $row_array['comment'] = $row['comments'];
     $row_array['image'] = $row['image'];
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>