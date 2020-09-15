<?php
    session_start();

    $_SESSION["loggedIn"] = false;
    $_SESSION["customerID"] = null;

    echo "<script>";
    echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/index.php'";
    echo "</script>";
?>