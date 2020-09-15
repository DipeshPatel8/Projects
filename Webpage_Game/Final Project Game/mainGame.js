//Define all canvas and variables needed
let canvasMaze  = document.getElementById('maze');
let contextMaze = canvasMaze.getContext('2d');
let canvasPlayer = document.getElementById('player');
let contextPlayer = canvasPlayer.getContext('2d');
let canvasFloor = document.getElementById('Floor');
let contextFloor = canvasFloor.getContext('2d');
let canvasStatus = document.getElementById('status');
let contextStatus = canvasStatus.getContext('2d');
let keys = {};
let myPlayer = new Player(125,75);
let SoundEffect = new Audio('Walking.mp3');
let motion;
let gameStart = true;

//Set canvas size
canvasMaze.height = 850;
canvasMaze.width = 850;
canvasPlayer.height = 850;
canvasPlayer.width = 850;
canvasFloor.height = 850;
canvasFloor.width = 850;

//Define required events

canvasPlayer.addEventListener('keydown', event => {
     keys[event.key] = true;
});

canvasPlayer.addEventListener('keyup', event => {
     keys[event.key] = false;

});
function start()
{
     //Draw necessary art
     DrawFloor();
     DrawBorder();
     DrawMap(gameMap());
     SpawnDiamonds();
     SetScoreBoard(0,0);
}

function animate()  
{
     //main animation 
     contextPlayer.clearRect(0,0, canvasPlayer.width, canvasPlayer.height);
     motion = requestAnimationFrame(animate);

     //branch to appropriate movement methode
     if(keys.ArrowUp)
     {
          myPlayer.goUp();     
     } 
     if(keys.ArrowDown)
     {
          myPlayer.godown();
     }    
     if(keys.ArrowRight)    
     {
          myPlayer.goright();
     }
     if(keys.ArrowLeft)
     {
          myPlayer.goleft();
     }

     /* glitchy code - incomplete
     if(gameStart == true)
     {
          ShowDiamonds();
     }
     */

     //check diamond found and open exit 
     if(myPlayer.diamondFound == 5)
     {
          OpenExit();
     }
     else
     {
          DrawDiamonds();
     }
     myPlayer.Update();//update game 
}

start();
animate();

