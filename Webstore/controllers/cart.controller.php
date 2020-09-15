<?php

require_once("rootDBconnection.php");
session_start();

$product = $_POST["addToCart"];
$quantity = $_POST["quantity"];
$customer_id = $_SESSION['customerID'];

$query = "INSERT INTO cart (product_id, customer_id, quantity) VALUES (?,?,?)";

$statement = $conn->prepare($query);
$statement->bind_param("iii", $product, $customer_id, $quantity);
$statement->execute();
$statement->close();

header("Location:/Assignment-3-HANN-PATEL/products.php");
?>