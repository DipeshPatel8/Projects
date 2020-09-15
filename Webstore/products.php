<?php 
    require_once("controllers/rootDBconnection.php");
    include("otherFiles/functions.php");
    session_start();

    $type = key($_POST); //key
    $filter_sort_option = $_POST[$type]; //value
    $query = "";


    if($filter_sort_option == null) {
        $query = "SELECT id, picture, name, price, rating FROM product";
    }
    else{
        if($type == "color") {
            $query = 'SELECT id, picture, name , price, rating FROM product WHERE colour = "'.$filter_sort_option.'"';
        }
        else if ($type == "size") {
            $query = "SELECT id, picture, name, price, rating FROM product WHERE size =".$filter_sort_option;
        }
        else if($type == "rating") {
            $query = "SELECT id, picture, name, price, rating FROM product WHERE rating >=".$filter_sort_option;
       }
        else if($type == "sort") {
            switch($filter_sort_option){
                case "ratings":
                    $query = "SELECT id, picture, name, price, rating FROM product ORDER BY rating";
                    break;
                case "lowTohigh":
                    $query = "SELECT id, picture, name, price, rating FROM product ORDER BY price ASC";
                    break;
                case "highTolow":
                    $query = "SELECT id, picture, name, price, rating FROM product ORDER BY price DESC";
                    break;
            }
        }
    }
?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>All Products</title>

        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/allProductsStyle.css">

    </head>
    <body>

    <?php require_once("includes/header.php") ?>

    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <div class="filters-sorts">
            <div class="dropdown">
                <button class="dropbtn">Colors</button>
                <form method="POST" action="products.php">
                    <div class="dropdown-content">
                        <button type="submit" name="color" value="Red">Red</button>
                        <button type="submit" name="color" value="Blue">Blue</button>
                        <button type="submit" name="color" value="Green">Green</button>
                        <button type="submit" name="color" value="White">White</button>
                        <button type="submit" name="color" value="Purple">Purple</button>
                        <button type="submit" name="color" value="Black">Black</button>
                        <button type="submit" name="color" value="Yellow">Yellow</button>
                        <button type="submit" name="color" value="Gold">Gold</button>
                        <button type="submit" name="color" value="Silver">Silver</button>
                    </div>
                </form>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Size</button>
                <form method="POST" action="products.php">
                    <div class="dropdown-content">
                        <button type="submit" name="size" value="0">0</button>
                        <button type="submit" name="size" value="1">1</button>
                        <button type="submit" name="size" value="2">2</button>
                        <button type="submit" name="size" value="3">3</button>
                        <button type="submit" name="size" value="4">4</button>
                        <button type="submit" name="size" value="5">5</button>
                        <button type="submit" name="size" value="6">6</button>
                        <button type="submit" name="size" value="7">7</button>
                        <button type="submit" name="size" value="8">8</button>
                        <button type="submit" name="size" value="9">9</button>
                        <button type="submit" name="size" value="10">10</button>
                    </div>
                </form>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Ratings</button>
                <form method="POST" action="products.php">
                    <div class="dropdown-content">
                        <button type="submit" name="rating" value="0">all</button>
                        <button type="submit" name="rating" value="1">1+</button>
                        <button type="submit" name="rating" value="2">2+</button>
                        <button type="submit" name="rating" value="3">3+</button>
                        <button type="submit" name="rating" value="4">4+</button>
                        <button type="submit" name="rating" value="5">5+</button>
                    </div>
                </form>
            </div>
            <div class="dropdown">
              <button class="dropbtn">Sort</button>
                <form method="POST" action="products.php">
                    <div class="dropdown-content">
                        <button type="submit" name="sort" value="ratings">Ratings High to Low</button>
                        <button type="submit" name="sort" value="highTolow">Price High to Low</button>
                        <button type="submit" name="sort" value="lowTohigh">Price Low to High</button>
                    </div>
                </form>
            </div>
        </div>
        
        <div id="items">
            <table id="products">  
                <?php
                    $statement = $conn->prepare($query);
                    $statement->execute();
                    $statement->bind_result($id, $picture, $name, $price, $rating);
                    $data = [];
                    
                    for($i = 0; $statement->fetch(); $i++) {
                        $data[$i] = ['id'=> $id,'picture'=> $picture,'name'=> $name, 'price' => $price, 'rating' => $rating ]; 
                    }
                    
                    $statement->close();

                    $productCount = 0;
                    for($rows = 0; $rows < count($data)/4; $rows++ ){
                        echo "<tr>";
                        
                        for($i = 0; $i < 4; $i++){
                            $tmp = $data[$productCount];
                            $productCount++;
                            echo "<td>";
                            echo '<img src="'.$tmp["picture"].'" height="150">';
                            echo "<p>".$tmp["name"]."</p>";
                            echo "<p>".$tmp["price"]."$</p>";
                            echo "<p>".$tmp["rating"]."/5"."</p>";
                            echo "<div class='viewBtn'>";
                            echo '<form method="GET" action="product.php">';
                            echo '<button type="submit" name="selectedProduct" value='.$tmp["id"].'> View Item </button>';
                            echo '</form>';
                            echo "</div>";
                            echo '</td>';
                        }
                        echo "</tr>";
                    }
                ?>    
            </table>
        </div>
    </main>

    <?php require_once("includes/footer.php") ?>

    </body>
</html>