<?php

include 'dbcon.php';

$event_id=$_POST['event_id'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `tickets` WHERE event_id='$event_id' "); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['type'] = $row['type'];
    $row_array['price'] = $row['price'];
    $row_array['notickets'] = $row['notickets'];
   
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);




?>