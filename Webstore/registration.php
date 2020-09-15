<?php
    include("otherFiles/functions.php");
    session_start();
?>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Registration</title>

        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/registrationStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/mainBackground.css">
    </head>
    <body>

    <?php require_once("includes/header.php") ?>
    <?php require_once("includes/navigationBar.php") ?> 

    <script>
        function validationForm(){
            var fname = document.getElementById("fname").value;
            if(fname == "") {
            }
        }
    </script>

    <main>
        <div id="registrationBox">
            <div id="transparentLayer">
                <form action="/Assignment-3-HANN-PATEL/controllers/customer.controller.php" method="POST" onsubmit="return ">
                    <label>First Name</label><br>
                    <input type="text" id="fname" name="fname" required><br>
                    <br>
                    <label>Last Name</label><br>
                    <input type="text" id="lname" name="lname" required><br>
                    <br>
                    <label>Email</label><br>
                    <input type="email" id="email" name="email" required><br>
                    <br>
                    <label>Re-Type Email</label><br>
                    <input type="email" id="re-email" name="re-email" required><br>
                    <br>
                    <label>Password</label><br>
                    <input type="password" id="pass" name="pass" required><br>
                    <br>
                    <label>Re-Type Password</label><br>
                    <input type="password" id="re-pass" name="re-pass" required><br>
                    <br>
                    <input type="submit" id="button" value="Register">
                </form>
            </div> 
        </div>
    </main>

    <?php require_once("includes/footer.php") ?>

    </body>
</html>