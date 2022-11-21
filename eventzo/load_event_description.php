<?php

include 'dbcon.php';

$event_id=$_POST['event_id'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `event` WHERE `event_id`='$event_id'"); 


$sql_comments=mysqli_query($conn,"SELECT count(cid) FROM `comments` where event_id='$event_id' ");
       $sql_comments=mysqli_fetch_array($sql_comments);
        $row_array['numberofcomments'] = $sql_comments["count(cid)"];

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['event_id'] = $row['event_id'];
    $row_array['event_name'] = $row['event_name'];
    $row_array['description'] = $row['description'];
     $row_array['image'] = $row['image'];
      $row_array['synopsis'] = $row['synopsis'];
       $row_array['type'] = $row['type'];
       $row_array['date'] = $row['date']; 
        $row_array['time'] = $row['time']; 
         $row_array['hosttype'] = $row['hosttype']; 
         $row_array['videolink'] = $row['video']; 


    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);




?>