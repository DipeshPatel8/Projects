<?php 

require_once("rootDBconnection.php");
include("../otherFiles/functions.php");

function insertSingleOrder($customerID, $total, $discount){

    global $conn;

    $query = 'INSERT INTO `order` (`customer_id`, `date`, `discount`, `total`) VALUES (?,?,?,?)';

    $statement = $conn->prepare($query);
    $statement->bind_param("sdsi", $discount, $total, date("Y-m-d h:i:sa"), $customerID);
    $statement->execute();
    $statement->close();

}




?>