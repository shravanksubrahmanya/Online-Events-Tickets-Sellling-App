<?php

include 'dbcon.php';

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `event`"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['event_id'] = $row['event_id'];
    $ev_id=$row['event_id'];
    $row_array['event_name'] = $row['event_name'];
     $row_array['image'] = $row['image'];
       $row_array['synopsis'] = $row['synopsis'];

       $sql_likes=mysqli_query($conn,"SELECT count(rid) FROM `reaction` where event_id='$ev_id' ");
       $sql_likes=mysqli_fetch_array($sql_likes);
        $row_array['likes'] = $sql_likes["count(rid)"];

        $sql_comments=mysqli_query($conn,"SELECT count(cid) FROM `comments` where event_id='$ev_id' ");
       $sql_comments=mysqli_fetch_array($sql_comments);
        $row_array['numberofcomments'] = $sql_comments["count(cid)"];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>