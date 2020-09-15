<?php

    require_once("rootDBconnection.php");

    $query = 'SELECT * FROM review WHERE product_id="'.$id.'"';

    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($product_id, $customer_id, $comment, $rating, $picture, $video);
    $dataReview = [];
    
    for($i = 0; $statement->fetch(); $i++) {
        $dataReview[$i] = ['product_id'=>$product_id,'customer_id'=>$customer_id, 'comment'=>$comment, 'rating'=>$rating, 'picture'=>$picture, 'video'=>$video]; 
    }
    
    $statement->close();
?>