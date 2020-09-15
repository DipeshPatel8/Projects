<?php
    $key = key($_GET);
    $id = $_GET["selectedProduct"];

    $query = 'SELECT * FROM product WHERE id='.$id;

    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($id, $name, $description, $price, $size, $colour, $picture, $rating);
    $data = [];

    for($i = 0; $statement->fetch(); $i++) {
        $data[$i] = ['id'=> $id,'name'=> $name, 'description'=> $description, 'price'=>$price, 'size'=>$size, 'colour'=>$colour, 'picture'=>$picture, 'rating'=>$rating ]; 
    }

    $productInfo = $data[0];

    $statement->close();    
?>