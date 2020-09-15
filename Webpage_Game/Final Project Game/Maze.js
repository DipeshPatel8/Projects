class Cell{
    //Value order = TOP-RIGHT-BOTTOM-LEFT
    // 0 = CLOSED
    // 1 = OPENED

    constructor(top, right, bot, left)
    {
        this.T = top;
        this.R = right;
        this.B = bot;
        this.L = left;
    }
}

//different types of cells 
//See TypesOfCell.png for details
const a = new Cell(0,1,1,1);
const b = new Cell(1,0,1,1);
const c = new Cell(1,1,0,1);
const d = new Cell(1,1,1,0);
const e = new Cell(1,1,0,0);
const f = new Cell(1,0,0,1);
const g = new Cell(0,1,1,0);
const h = new Cell(0,0,1,1);
const i = new Cell(0,0,1,0);
const j = new Cell(0,0,0,1);
const k = new Cell(1,0,0,0);
const l = new Cell(0,1,0,0);
const m = new Cell(1,1,1,1);
const n = new Cell(0,1,0,1);
const o = new Cell(1,0,1,0);



function gameMap()
{
    //map layout based upon MazeLayout.png
    const maze = [
        [g,b,g,h,l,n,h,g,n,n,n,n,h,g,h],//Row 0
        [o,o,o,e,n,n,f,o,g,h,g,h,o,o,o],//Row 1
        [o,o,o,g,n,n,h,o,o,e,f,o,o,k,o],//Row 2
        [o,e,c,f,g,h,o,o,d,h,i,o,e,h,o],//Row 3
        [e,n,n,h,o,o,o,o,o,o,o,e,h,o,o],//Row 4
        [g,n,h,o,o,e,f,e,f,o,d,n,f,o,o],//Row 5
        [d,h,o,o,e,a,a,a,a,f,o,g,n,f,o],//Row 6
        [o,o,o,e,h,d,m,m,b,i,o,o,g,h,o],//Row 7
        [o,o,e,n,f,d,m,m,b,e,f,o,o,e,f],//Row 8
        [o,e,n,j,g,c,c,c,m,n,n,f,e,n,h],//Row 9
        [e,n,h,i,o,g,n,j,o,g,h,g,n,n,f],//Row 10
        [g,n,f,o,o,e,n,n,f,o,o,o,g,a,h],//Row 11
        [o,g,n,b,e,n,n,n,n,f,o,o,o,o,o],//Row 12
        [o,o,g,f,g,n,h,g,n,n,c,f,o,k,o],//Row 13
        [e,f,e,n,f,l,f,e,n,n,n,n,f,g,f]];//Row 14

    return maze;
}

function DrawBorder()
{
    //draw outer borders => big square 
    contextMaze.strokeStyle = "black";
    contextMaze.lineWidth = 5;
    contextMaze.moveTo(50,50);
    contextMaze.lineTo(50,800);
    contextMaze.stroke();
    contextMaze.moveTo(50,800);
    contextMaze.lineTo(800,800);
    contextMaze.stroke();
    contextMaze.moveTo(800,800);
    contextMaze.lineTo(800,50);
    contextMaze.stroke();
    contextMaze.moveTo(800,50);
    contextMaze.lineTo(50,50);
    contextMaze.stroke();
}
 
function DrawMap(maze)
{
    //draw inner borders

    //top corner 
    let XCoord = 50;
    let YCoords = 50;
    //line setting
    contextMaze.strokeStyle = "black";
    contextMaze.lineWidth = 3;
    //maze is the array of cells
    for(let i = 0; i<maze.length; i++)//for every row
    {
        for(let j = 0; j<maze.length; j++)//for every columns
        {
            let currentCell = maze[i][j];
            let tempX = XCoord;
            let tempY = YCoords;
            if(currentCell.T == 0)
            {
                contextMaze.moveTo(tempX, tempY);
                //top open
                contextMaze.lineTo(tempX+50, tempY);
                contextMaze.stroke();
            }
            if(currentCell.R == 0)
            {
                contextMaze.moveTo(tempX+50, tempY);
                //right open
                contextMaze.lineTo(tempX+50, tempY+50);
                contextMaze.stroke();
            }
            if(currentCell.B == 0)
            {
                contextMaze.moveTo(tempX, tempY+50);
                //bottom open
                contextMaze.lineTo(tempX+50, tempY+50);
                contextMaze.stroke();
            }
            if(currentCell.L == 0)
            {
                contextMaze.moveTo(tempX, tempY)
                //left open
                contextMaze.lineTo(tempX, tempY+50);
                contextMaze.stroke();
            }
            XCoord = XCoord+50;//cells are 50 width
        }
        XCoord=50;
        YCoords = YCoords+50;//cells have 50 height
    }
}

function DrawFloor()
{
    const floor = new Image();
    let x = 50;
    let y = 50;
    
    //image is a pic of 3x3 tiles
    //game maze is 15x15 cells
    //https://stackoverflow.com/questions/14757659/loading-an-image-onto-a-canvas-with-javascript
    floor.onload = () => {
        for(let i = 0; i<5; i++)
        {
            for(let j = 0; j<5; j++)
            {
                contextFloor.drawImage(floor, x, y, 150, 150);//one pic covers 150x150
                x = x+150;
            }
            x=50;
            y = y+150;
        }
    }

    floor.src = 'FloorTheme.png';
}

let diamonds = new Array;
//Create 5 diamonds to place randomly on map
function SpawnDiamonds()
{
    //diamonds can be in cell 1 to 15
    let max = 15;
    let min = 1;
    for(let i = 0; i<5; i++)
    {
        //https://stackoverflow.com/questions/1527803/generating-random-whole-numbers-in-javascript-in-a-specific-range
        let x = Math.floor(Math.random()*(max - min+1))+min;
        let y = Math.floor(Math.random()*(max - min+1))+min;
        //put diamond in the middle of appropriate cell
        x = x*50+25;
        y = y*50+25;
        let newDiamond = [x,y];
        diamonds[i] = newDiamond;//add to diamonds array
    }
    DrawDiamonds();
}

function DrawDiamonds()
{
    let Img = new Image();
    Img.src = 'Diamond.png';
    for(let i = 0; i<diamonds.length; i++)
    {
        //draw each diamond on appropriate location with size of 10x10
        contextMaze.drawImage(Img, diamonds[i][0], diamonds[i][1], 10, 10);
    }
}

function ShowDiamonds()
{
    //glitchy code non used because not working
    let i = 0;
    while(i<diamonds.length)
    {
        contextPlayer.globalCompositeOperation = 'xor';
        contextPlayer.beginPath();
        contextPlayer.arc(diamonds[i][0], diamonds[i][1], 25, 0, 2*Math.PI);
        contextPlayer.fillStyle = 'rgba(0, 0, 0, 0)';
        contextPlayer.fill();
        contextPlayer.stroke();
        i = setTimeout(function(i){i++;
            return i;
        }, 1000);
    }
    setTimeout(function(){gameStart = false;}, 1000);
}

function SetScoreBoard(time, d)
{
    //display score board 
    contextStatus.clearRect(0,0,canvasStatus.width, canvasStatus.clientHeight);
    contextStatus.font = "30px Arial";
    contextStatus.fillText(`Time: ${time}sec`, 10, 35);
    contextStatus.fillText(`Diamonds: ${d} of 5`, 10, 70);
}

function OpenExit()
{
    //make whole map visiable
    contextPlayer.clearRect(0,0,canvasPlayer.width, canvasPlayer.height);

    //draw ecit portal
    let exit = new Image();
    exit.src = 'Portal.png';
    contextMaze.drawImage(exit, 350,400 , 100, 100);

    myPlayer.exit = true;
}

function EndScreen(time)
{
    //stop animation
    cancelAnimationFrame(motion);
    SoundEffect.muted = true;
    SoundEffect  = new Audio("EndMusic.mp3");//play ending music
    SoundEffect.play();

    //remoce scoreboard
    canvasStatus.width = 0;
    canvasStatus.height = 0;
    contextPlayer.clearRect(0,0,canvasPlayer.width, canvasPlayer.height);

    let canvasBack = document.createElement('canvas');
    let contextBack = canvasBack.getContext('2d');
    let canvasText = document.createElement('canvas');;
    let contextText = canvasText.getContext('2d');;

    //set ending canvas
    canvasBack.width = 750;
    canvasBack.height = 500;
    canvasText.width = 750;
    canvasText.height = 500;

    //calculate time of gameplay
    let hours = Math.floor(time/3600);
    time = time%3600;
    let minutes = Math.floor(time/60);
    let seconds = time%60;

    //image not working
    let img = new Image();
    img.src = 'EndScreen.png';
    contextBack.drawImage(img, 0,0,canvasBack.width, canvasBack.height);

    //display ending text
    contextText.font = "30px Arial";
    contextText.fillStyle = 'white';
    contextText.textAlign = "center";
    contextText.fillText('You Win', canvasText.width/2, 100);
    contextText.fillText('Thanks for playing', canvasText.width/2, 150);
    contextText.fillText(`You finished the game in ${hours}:${minutes}:${seconds}`, canvasText.width/2, 200);
    contextText.fillText("Press 'y' to play again" , canvasText.width/2, 250);

    //add canvas to body
    document.body.appendChild(canvasBack);
    document.body.appendChild(canvasText);

    //player replay event
    window.addEventListener("keydown", event => {
        if(event.keyCode == 89)
        {
            //stop music and reset game
            SoundEffect.muted = true;
            location.reload();
        }
    })
}
