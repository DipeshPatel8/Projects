<?php
    
?>
<head>
    <link rel="stylesheet" href="../cssFiles/headerNavigationFooterStyle.css">
</head>

<nav>
    <div class="navBar">
        <a href="/Assignment-3-HANN-PATEL/index.php">Home Page</a>
        <a href="/Assignment-3-HANN-PATEL/products.php">All products</a>
        <a href="/Assignment-3-HANN-PATEL/registration.php">Register</a>
        <?php
            if($_SESSION['loggedIn'] == true){
                echo'<a href="/Assignment-3-HANN-PATEL/controllers/logout.controller.php">Sign out</a>';
            }
            else
            {
                echo'<a href="/Assignment-3-HANN-PATEL/login.php">Sign in</a>';
            }
        ?>
        <form methods="POST" action="/Assignment-3-HANN-PATEL/cart.php">
            <?php echo '<button id="cart" type="submit">Cart</button>' ?>
        </form>
    </div>
</nav>