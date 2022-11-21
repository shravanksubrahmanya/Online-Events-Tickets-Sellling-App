<?php
 
include ('dbcon.php');

 
    $json = json_decode(file_get_contents('php://input'),true);
    
    
   
    $name = $json["name"].".JPG"; //within square bracket should be same as Utils.imageName & Utils.image
    $image = $json["image"];
    $type = $json["type"];
    $uid = $json["uid"];
    $ename = $json["ename"];
    $synopsis = $json["synopsis"];
    $date = $json["date"];
    $time = $json["time"];
    $location = $json["location"];
    $images = $json["images"];
    $description = $json["description"];
    $videopicker = $json["videopicker"];
    $hosttype = $json["hosttype"];

    // $sender_id = $json["sender_id"];
    // $recipient_id = $json["recipient_id"];
 
    $response = array();
 
    $decodedImage = base64_decode("$image");
 
    $return = file_put_contents("images/".$name, $decodedImage);
 
    if($return !== false){
        $response['success'] = 1;
    $q=mysqli_query($conn,"INSERT INTO `event`(`user_id`, `event_name`, `description`, `synopsis`, `type`, `date`, `time`,`location`, `image`,`video`,`hosttype`,status) VALUES ('$uid','$ename','$description','$synopsis','$type','$date','$time','$location','$name','$videopicker','$hosttype','1')")or die(mysqli_error($conn));
    $event_id=mysqli_insert_id($conn);

        $response['message'] = "Image Uploaded Successfully";
        $response['e_id'] = $event_id;

    }else{
        $response['success'] = 0;
        $response['message'] = "Image Uploaded Failed";
    }
 
    echo json_encode($response);
?>