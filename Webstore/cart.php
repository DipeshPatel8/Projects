<?php
    include("controllers/rootDBconnection.php");
    include("otherFiles/functions.php");
    session_start();

    $productID = [];
    $counter = 0;
    $customer_id = $_SESSION["customerID"];
    $dataCartItems = getCartItems($customer_id);
    if($customer_id == null){
        echo '<script>alert("Please log in to use cart feature")</script>';
        echo "<script>";
        echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/index.php'";
        echo "</script>";
    }
       
?>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Cart</title>

        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/mainBackground.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/cartStyle.css">
    
    </head>
    <body>

    <?php require_once("includes/header.php") ?>

    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <h2>Shopping Cart</h2>
        <table id="cartTable">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th></th>
            </tr>
            <?php
                $customer_id = $_SESSION["customerID"];
                $dataCartItems = getCartItems($customer_id);
                for($item = 0; $item < count($dataCartItems); $item++){
                    $tmp1 = $dataCartItems[$item];                   
                    $tmp2 = selectProductInfo($tmp1["product_id"]);
                    
                    echo "<tr>";
                    echo "<td>".$tmp2["name"]."</td>";
                    echo "<td>".$tmp2["price"]."$</td>";
                    echo '<td><input type="number" value='.$tmp1["quantity"].'></input>';
                    echo '<td><form method="POST" action="/Assignment-3-HANN-PATEL/controllers/removeCartItem.controller.php" >
                                <button type="submit" name="remove" value="'.$tmp1["product_id"].'">remove item</button>
                                </form></td>';
                    echo "</tr>";                 
                }
            ?>
        </table>

        <br>

        <div class="total"></div>
        <form method="POST" action="/Assignment-3-HANN-PATEL/receipt.php">
            <label>Discount Code</label>
            <input type="text" name="discount"></input>
            <button id="checkOutBtn" class="checkout" name="check" value="customerID">Check out</button>
        </form>
    </main>

    <?php require_once("includes/footer.php") ?>

    </body>
</html>