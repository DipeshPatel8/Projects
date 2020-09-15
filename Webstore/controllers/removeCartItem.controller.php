<?php

    require_once("rootDBconnection.php");
    session_start();

    $productID = $_POST["remove"];
    $customerID = $_SESSION["customerID"];

    $query = "DELETE FROM cart WHERE product_id=".$productID." AND customer_id=".$customerID;
    
    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->close();    

    header("Location:/Assignment-3-HANN-PATEL/cart.php");
?>