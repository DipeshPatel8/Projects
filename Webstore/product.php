<?php
    require_once("controllers/rootDBconnection.php");
    require_once("controllers/product.controller.php");
    require_once("controllers/review.controller.php");
    include("otherFiles/functions.php");
    session_start();

    global $conn;
    global $dataReview;
?>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Product</title>
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/mainBackground.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/singleProductStyle.css">
    
    </head>
    <body>

    <?php require_once("includes/header.php") ?>

    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <div class="pic">
            <?php
                echo '<img src="'.$productInfo["picture"].'"></img>';
            ?>
        </div>
        
        <div class="info">
            <?php
                echo '<p> Name: '.$productInfo["name"].'</p><br>';
                echo '<p> Price: '.$productInfo["price"].'$</p><br>';
                echo '<p> Size: '.$productInfo["size"].'</p><br>';
                echo '<p> Colour: '.$productInfo["colour"].'</p><br>';
                echo '<p> Ratings: '.$productInfo["rating"].'/5 </p>';

                echo '<form method="POST" action="controllers/cart.controller.php">
                <button id="btn" name="addToCart" value='.$productInfo["id"].'>Add to Cart</button>
                <input type="number" min="1" max="100" value="1" id="numb" name="quantity"></input>
                </form>';
            ?>
        </div>

        <div class="description">
            <h3>Description</h3>
            <?php
                echo "<p>".$productInfo["description"]."</p>";
            ?>
        </div>
        
        <div class="reviews">
            <h3>Reviews</h3>
            <?php
                for($i=0; $i < count($dataReview); $i++) {
                    $tmp = $dataReview[$i];
                    $customerInfo = getCustomerInfo($tmp["customer_id"]);
                    echo "<div>";
                    echo "<h5>". $customerInfo['fname']. " " . $customerInfo['lname']."</h5>";
                    echo "<p>".$tmp["rating"]."/5</p>";
                    echo "<p>".$tmp["comment"]."</p>";
                    echo '<img src="'.$tmp["picture"].'">';
                    echo '<source src="'.$tmp["video"].'" type="video/mp3">';
                    echo "</div>";
                }
                
            ?>
        </div>
  
    </main>

    <?php require_once("includes/footer.php") ?>

    </body>
</html>