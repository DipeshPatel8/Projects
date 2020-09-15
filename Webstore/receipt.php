<?php

    require_once("controllers/rootDBconnection.php");
    include("otherFiles/functions.php");
    session_start();

    $discountCode = $_POST["discount"];
    $discount = 0;

    switch($discountCode) {
        case("C00LSH0ES"):
            $discount = 10;
            break;
        case("ALMOSTFREE"):
            $discount = 20;
            break;
        case("IMPRESS123"):
            $discount = 30;
            break;
        default:
            $discount = 0;
            break;
    }

?>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Receipt</title>

        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/receiptStyle.css">

    
    </head>
    <body>

    <?php require_once("includes/header.php") ?>

    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <h2>Receipt</h2>
        <table id="receiptTable">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <?php
                $customer_id = $_SESSION["customerID"];
                $dataCartItems = getCartItems($customer_id);
                $total = 0;
                for($item = 0; $item < count($dataCartItems); $item++){
                    $tmp1 = $dataCartItems[$item];                   
                    $tmp2 = selectProductInfo($tmp1["product_id"]);
                    
                    echo "<tr>";
                    echo "<td>".$tmp2["name"]."</td>";
                    echo "<td>".$tmp2["price"]."$</td>";
                    echo "<td>".$tmp1["quantity"]."</td>";
                    $total = $total + ( (double) $tmp2["price"] * (double) $tmp1["quantity"]);
                    echo "</tr>";                 
                }
                echo "</table><br>";
                $total = $total - $discount;
                echo '<div class="receipt">DISCOUNT: '.$discount.'$</div><br>';
                echo '<div class="receipt">TOTAL: '.$total.'$<div>';
            ?>

    </main>

    <?php 
    insertSingleOrder($customer_id, $total, $discount);
    insertOrderItems($dataCartItems);
    require_once("includes/footer.php");
     ?>

    </body>
</html>