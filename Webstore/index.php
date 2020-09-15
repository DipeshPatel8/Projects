<?php 
    require_once("controllers/rootDBconnection.php");
    include("otherFiles/functions.php");
    session_start();
?>

<!DOCTYPE HTML>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/mainBackground.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/headerNavigationFooterStyle.css">
        <link rel="stylesheet" href="/Assignment-3-HANN-PATEL/cssFiles/indexStyle.css">    
    </head>
    <body>

    <?php require_once("includes/header.php") ?>

    <?php require_once("includes/navigationBar.php") ?>

    <main>
        <h1>Home Page</h1>

        <div class="displaySection">
            <div class="pic">
                <img src="/Assignment-3-HANN-PATEL/images/nature.jpg" width="700" height="400">
            </div>

            <br>

            <div class="info">
                <p>
                Wacky Walk is a web store specialized in funky and out of the normal footwear. 
                While most people want simple everyday shoes, few geniuses realised that some 
                celebrity brand shoes looked like they were something from out of this world, 
                like back to the future type shoes. So these masterminds understood that this 
                is the next generation of shoes and made a prototype which was a huge success. 
                That leads to this company which sells shoes of all sorts; shoes with horns, 
                buttons, zippers, colorful, gigantesque, anything you imagine we have. These 
                shoes seem childish .but don’t worry we have for all ages, from baby to elderly.
                You can bet we are currently in the process of designing Ronald McDonald's new shoes.
                </p>
            </div>

            <div class="slideshow">
                <div class="mySlides fade">
                    <img src="images/banana.jpg" style="width:100%">
                    <div class="text">WOW BANANA</div>
                </div>

                <div class="mySlides fade">
                    <img src="images/duck.jpg" width="824" height="465">
                    <div class="text">DUCK DUCK</div>
                </div>

                <div class="mySlides fade">
                    <img src="images/horn.jpg" width="824" height="465">
                    <div class="text">HORN AND HEEL</div>
                </div>

                <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                <a class="next" onclick="plusSlides(1)">&#10095;</a> 
            </div>
            <br>
        </div>
        
    </main>
    <script src="/Assignment-3-HANN-PATEL/otherFiles/indexScript.js"></script>

    <?php 
        require_once("includes/footer.php"); 
    ?>
    </body>
</html>