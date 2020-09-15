<?php
    include("otherFiles/functions.php");
    session_start();
?>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Log in</title>

        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/loginStyle.css">
    
    </head>
    <body>

    <?php require_once("includes/header.php") ?>
    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <div id="loginBox">
            <div id="contrast">
                <h3>Login</h3>
                <form action="/Assignment-3-HANN-PATEL/controllers/login.controller.php" method="POST">
                    <label>Email</label><br>
                    <input type="email" id="email" name="email"><br>
                    <br>
                    <label>Password</label><br>
                    <input type="password" id="pass" name="pass"><br><br>
                    <br>
                    <input type="submit" id="button" value="login">
                </form> 
            </div>
        </div>
    </main>

    <?php require_once("includes/footer.php") ?>

    </body>
</html>