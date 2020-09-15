class Player
{
    constructor(x,y)
    {
        //player variables
        this.width = 30;
        this.height = 30;
        this.Img = new Image();
        this.Img.src = 'playerModelDown.png';//inital start look
        this.posx = x;
        this.posy = y;
        this.diamondFound = 0;
        this.radius = 50;
        this.loop = 0;
        this.time = 0;
        this.exit = false;
    }

    Update()
    {  
        if(this.exit == false)
        {
            this.DrawLight();//cricle around player
        }
        else
        {
            OpenExit();//open exit portal
            this.checkExit();//check if player on portal
        }
        this.checkInnerBorders();//maze collision 
        this.checkDiamond();//diamond found checking
        this.DrawPlayer();//display character
        this.UpdateStatus();//update scoreboard
    }

    DrawPlayer()
    {
        contextPlayer.drawImage(this.Img, this.posx-15, this.posy-15, this.width, this.height);
    }

    UpdateStatus()
    {   
        this.loop++;
        if(this.loop%60==0)//increase time every second
        {
            this.time++;
        }
        SetScoreBoard(this.time,this.diamondFound);
    }

    DrawLight()
    {
        //https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/globalCompositeOperation
        contextPlayer.globalCompositeOperation = 'xor';

        //cover in black
        contextPlayer.fillStyle = "black";
        contextPlayer.fillRect(50,50,750,750)
        contextPlayer.fill();

        //"clear arc" clear circle around player
        contextPlayer.beginPath();
        contextPlayer.arc(this.posx, this.posy, this.radius, 0, 2*Math.PI);
        contextPlayer.fillStyle = 'rgba(0, 0, 0, 0)';
        contextPlayer.fill();
        contextPlayer.stroke();
    }

    goUp()
    {
        SoundEffect.play();
        this.Img.src = 'playerModelUp.png';
        this.posy = this.posy-1;
        if((this.posy-this.height/2) < 50)
        {
            this.posy = 50+this.width/2+1;
        }
    }

    godown()
    {   
        SoundEffect.play();
        this.Img.src = 'playerModelDown.png';
        this.posy=this.posy+1;
        if((this.posy+this.height/2) > 800)
        {
            this.posy = 800-this.height/2-1;
        }
    }

    goright()
    {
        SoundEffect.play();
        this.Img.src = 'playerModelRight.png';
        this.posx=this.posx+1;
        if((this.posx+this.width/2) > 800)
        {
            this.posx = 800-this.width/2-1;
        }
    }

    goleft()
    {
        SoundEffect.play();
        this.Img.src = 'playerModelleft.png';
        this.posx=this.posx-1;
        if((this.posx-this.width/2) < 50)
        {
            this.posx = 50+this.width/2+1;
        }
    }

    checkInnerBorders()
    {
        if(this.innerLineTouch() == true)
        {
            //find which cell its in currently
            let locationX = Math.floor(this.posx/50);
            let locationY = Math.floor(this.posy/50);

            //get maze
            let maze = gameMap();
            //get maze cell type
            let cell = maze[locationY-1][locationX-1];

            //set cell as a 0-50 plan itself
            let innerX = this.posx-locationX*50;
            let innerY = this.posy-locationY*50;

            if((innerX+this.width/2>=50) && cell.R == 0)
            {
                //right side closed
                this.posx = locationX*50+(49-this.width/2);
            }
            if((innerX-this.width/2<=0) && cell.L == 0)
            {
                //left side closed
                this.posx = locationX*50+(this.width/2+1);
            }
            if((innerY+this.height/2>=50) && cell.B == 0)
            {
                //bottom side closed
                this.posy = locationY*50+(49-this.height/2);
            }
            if((innerY-this.height/2<=0) && cell.T == 0)
            {
                //top side closed
                this.posy = locationY*50+(this.height/2+1);
            }
        }
    }

    innerLineTouch()
    {
        let value = false;
        if((this.posx + this.width/2)%50 == 0)
        {
            //right side touched
            value = true;
        }
        else if((this.posx - this.width/2)%50 == 0)
        {
            //left side touched
            value = true;
        }
        else if((this.posy + this.height/2)%50 == 0)
        {
            //botton side touched
            value = true;
        }
        else if((this.posy - this.height/2)%50 == 0)
        {
            //top side touched
            value = true;
        }

        return value;
    }

    checkDiamond()
    {
        for(let i = 0; i< diamonds.length; i++)
        {
            let currentDiamond = diamonds[i]; //diamond to check
            if((this.posx >= currentDiamond[0]-5) && (this.posx <= currentDiamond[0]+5))
            {
                // x position matched
                if((this.posy >= currentDiamond[1]-5) && (this.posy <= currentDiamond[1]+5))
                {
                    // y position matched
                    //play diamond collection soundeffect
                    SoundEffect = new Audio('Found.mp3');
                    SoundEffect.play();
                    SoundEffect = new Audio('Walking.mp3');
                    //remove diamond from map
                    contextMaze.clearRect(currentDiamond[0], currentDiamond[1], 10, 10);
                    //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/splice
                    diamonds.splice(i,1);//remove diamond from array
                    this.diamondFound++;//increase score
                    this.radius = this.radius+25;//increase radius
                }        
            }
        }
    }

    checkExit()
    {
        if(this.posx>=350 && this.posx<=450)
        {
            // x position valid
            if(this.posy>=400 && this.posy<=500)
            {
                // y position valid
                EndScreen(this.time);//end game
            }
        }
    }
}