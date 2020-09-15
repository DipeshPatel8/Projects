using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using System.Threading;

namespace Game
{
    class Program
    {
        static void Main()
        {
            bool game=false;

            // main menu code/art
            mainmenu(ref game);

            //player chooses to play the game
            if(game==true)
            {
                //remove art from main menu
                Console.Clear();

                //game codes

                //invisible cursor
                Console.CursorVisible = false;

                //create all necessary varibales
                int height, width, mey, mex, potx = 0, poty = 0, score = 0, enemyx = 0, enemyy = 0;
                bool i = true, m = true;
                ConsoleKey key;

                //do certain adjust and variable filling
                height = (int)Console.WindowHeight;
                width = (int)Console.WindowWidth;

                //left side of the map
                mey = 10;
                mex = 10;

                //displaying a game map/area 
                screen(height, width);

                //spawn player on map
                Console.SetCursorPosition(mex, mey);
                Console.ForegroundColor = ConsoleColor.Red;
                Console.Write("^");

                //once a key is hit the game will launch
                key = Console.ReadKey().Key;

                while (gameover(ref mex, ref mey, ref enemyx, ref enemyy, ref score) == false)
                {

                    //keep the previous key remembered which makes it glide
                    if (Console.KeyAvailable) key = Console.ReadKey().Key;
                    //make sures he glides, doesn't go out of control and makes the game run smooth
                    System.Threading.Thread.Sleep(Convert.ToInt32(50));

                    //redraw or update certain things on map
                    draw(width, height, ref i, ref mex, ref mey, ref potx, ref poty, ref score, ref enemyx, ref enemyy, ref m);

                    //making the player move and everything else
                    mouvement(ref key, ref mex, ref mey, ref enemyx, ref enemyy);

                }
                //ask if user wants to replay
                replayscreen();
            }
            //if user doesn't want to replay say bye
            endgamescreen();
        }

        static void screen(int screeny, int screenx)
        {
            int i, dx, dy;

            //top border
            dx = 0;
            dy = 0;
            for (i = 0; i < screenx - 20; i++)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.SetCursorPosition(dx, dy);
                Console.Write("═");
                dx = dx + 1;
            }

            //left border
            dx = 0;
            dy = 0;
            for (i = 0; i < screeny - 7; i++)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.SetCursorPosition(dx, dy);
                Console.Write("║");
                dy = dy + 1;
            }

            //right border
            dx = screenx - 20;
            dy = 0;
            for (i = 0; i < screeny - 7; i++)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.SetCursorPosition(dx, dy);
                Console.Write("║");
                dy = dy + 1;
            }

            //bottom border
            dx = 0;
            dy = screeny - 7;
            for (i = 0; i < screenx - 20; i++)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.SetCursorPosition(dx, dy);
                Console.Write("═");
                dx = dx + 1;
            }

            //corner design
            Console.ForegroundColor = ConsoleColor.Green;
            Console.SetCursorPosition(0, 0);
            Console.Write("╔");
            Console.SetCursorPosition(0, screeny - 7);
            Console.Write("╚");
            Console.SetCursorPosition(screenx - 20, 0);
            Console.Write("╗");
            Console.SetCursorPosition(screenx - 20, screeny - 7);
            Console.Write("╝");

            //add the scoreboard
            Console.SetCursorPosition(105, 2);
            Console.CursorVisible = false;
            Console.ForegroundColor = ConsoleColor.Red;
            Console.Write("Score:0");
        }

        static bool gameover(ref int playerx, ref int playery, ref int enemyx, ref int enemyy, ref int score)
        {
            //if enemy touches the player, game ends
            if((enemyy == playery)&&(enemyx == playerx))
            {
                enemykillyou();
                return true;
            }

            //if score goes under 0, game ends
            else if(score < 0)
            {
                scoretoolow();
                return true;
            }

            //if nothing keep game going
            else
            {
            return false;
            }
        }

        static void mouvement(ref ConsoleKey key, ref int x, ref int y, ref int enemyx, ref int enemyy)
        {
            //player mouvement
            switch (key)
            {
                //going top side
                case ConsoleKey.UpArrow:
                    Console.SetCursorPosition(x, y);
                    Console.Write(" ");//erase the previous position
                    y = y - 1;//increase the slot it's in
                    if (y <= 0)//when it hits the top border
                    {
                        //bring it back from the bottom
                        y = Console.WindowHeight - 8;                                     
                    }
                    Thread.Sleep(10);//slow down the vertical mouvement
                    Console.SetCursorPosition(x, y);//put player on new position
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("^");
                    break;

                //going bottom side
                case ConsoleKey.DownArrow:
                    Console.SetCursorPosition(x, y);
                    Console.Write(" ");//erase the previous position
                    y = y + 1;//increase the slot it's in
                    if (y >= Console.WindowHeight - 7)//when it hits the bottom border
                    {
                        //bring it back from top
                        y = 1;
                    }
                    Thread.Sleep(10);//slow down vertical mouvement
                    Console.SetCursorPosition(x, y);//put player on new position
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("v");
                    break;

                //going right side
                case ConsoleKey.RightArrow:
                    Console.SetCursorPosition(x, y);
                    Console.Write(" ");//erase the previous position
                    x = x + 1;//increase the slot it's in
                    if (x >= Console.WindowWidth - 20)//when it hits the right border
                    {
                        //bring it back from left side
                        x = 1;
                    }
                    Console.SetCursorPosition(x, y);//put player on new position
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write(">");
                    break;

                //going left side
                case ConsoleKey.LeftArrow:
                    Console.SetCursorPosition(x, y);
                    Console.Write(" ");//erase the previous position 
                    x = x - 1;//increase the slot it's in
                    if (x <= 0)//when it hits the left border
                    {
                        //bring it back from right side
                        x = Console.WindowWidth - 21;
                    }
                    Console.SetCursorPosition(x, y);//put player on new position
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("<");
                    break;
            }

            //move the enemy
            enemymouvement(ref x, ref y, ref enemyx, ref enemyy);
        }            

        static void draw(int xway, int yway, ref bool i, ref int myx, ref int myy, ref int potx, ref int poty, ref int score, ref int enemyx, ref int enemyy, ref bool m)
        {
            //create random variable to spawn pot at random space
            Random pot = new Random();
            int rndx = 0, rndy = 0;
            
            //only spawn one pot beginning
            while (i==true)
            {
                rndx = pot.Next(1, xway - 21);
                rndy = pot.Next(1, yway - 8);
                Console.SetCursorPosition(rndx, rndy);//put pot at spot generated
                Console.ForegroundColor = ConsoleColor.DarkYellow;
                Console.Write("∩");
                potx = rndx;
                poty = rndy;
                i = false;
            }

            //respawn pot if destroyed
            if (myx == potx && myy == poty)
            {
                rndx = pot.Next(1, xway - 21);
                rndy = pot.Next(1, yway - 8);
                Console.SetCursorPosition(rndx, rndy);//put pot at new spot generated
                Console.ForegroundColor = ConsoleColor.DarkYellow;
                Console.Write("∩");
                potx = rndx;
                poty = rndy;

                //add points for every destroyed pot by player
                score = score + 10;
            }

            //respawn pot somewhere else if enemy destroys it
            if(enemyx == potx && enemyy == poty)
            {
                rndx = pot.Next(1, xway - 21);
                rndy = pot.Next(1, yway - 8);
                Console.SetCursorPosition(rndx, rndy);//respwan pot at new spot generated
                Console.ForegroundColor = ConsoleColor.DarkYellow;
                Console.Write("∩");
                potx = rndx;
                poty = rndy;

                //lose points upon letting enemy destroy pot
                score = score - 10;
            }

            //display score top right corner
            Console.SetCursorPosition(105, 2);
            Console.CursorVisible = false;
            Console.ForegroundColor = ConsoleColor.Red;
            Console.Write("Score:" + score);

            //spawn enemies
            spawnenemy(ref enemyx, ref enemyy, ref m, ref score, ref xway, ref yway, ref score);

        }

        static void spawnenemy(ref int enemyx, ref int enemyy, ref bool m, ref int points, ref int screenx, ref int screeny, ref int score)
        {
            //spawn the enemy only once, stop it from looping 
            while (m == true)
            {
                //spawn the enemy ont he left side of the map
                enemyx = 70;
                enemyy = 10;
                Console.SetCursorPosition(enemyx, enemyy);
                Console.CursorVisible = false;
                Console.ForegroundColor = ConsoleColor.Blue;
                Console.Write("☼");
                m = false;
            }
        }

        static void enemymouvement(ref int myx, ref int myy, ref int enemyx, ref int enemyy)
        {
            //player is below and left to enemy
            if (myy > enemyy && myx > enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy++;
                enemyx++;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }

            //player is below and right to enemy
            else if(myy > enemyy && myx < enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy++;
                enemyx--;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }
            
            //player is above and left to enemy
            else if(myy < enemyy && myx > enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy--;
                enemyx++;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }

            //pplayer is above and right to enemy
            else if(myy < enemyy && myx < enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy--;
                enemyx--;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }
            
            //plaer is exactly below enemy
            else if (myy > enemyy && myx == enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy++;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }
            
            //player is exactly above enemy
            else if (myy < enemyy && myx == enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyy--;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }
            
            //player is exactly to the left of enemy
            else if (myy == enemyy && myx < enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyx--;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }

            //player is exactly to the right of enemy
            else if (myy == enemyy && myx > enemyx)
            {
                Console.SetCursorPosition(enemyx, enemyy);
                Console.Write(" ");//erase old position
                enemyx++;
                Thread.Sleep(10);//keep game slow
                Console.SetCursorPosition(enemyx, enemyy);//put enemy on new position
                Console.ForegroundColor = ConsoleColor.Cyan;
                Console.Write("Θ");
            }
        }

        static void mainmenu(ref bool game)
        {
            //clear previous screen
            Console.Clear();
            Console.CursorVisible = false;

            //the big title *BREAK THE POT*
            Console.ForegroundColor = ConsoleColor.DarkCyan;
            Console.SetCursorPosition(10,2);
            Console.WriteLine("██████╗ ██████╗ ███████╗ █████╗ ██╗  ██╗    ████████╗██╗  ██╗███████╗    ██████╗  ██████╗ ████████╗");
            Console.SetCursorPosition(10, 3);
            Console.WriteLine("██╔══██╗██╔══██╗██╔════╝██╔══██╗██║ ██╔╝    ╚══██╔══╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗╚══██╔══╝");
            Console.SetCursorPosition(10, 4);
            Console.WriteLine("██████╔╝██████╔╝█████╗  ███████║█████╔╝        ██║   ███████║█████╗      ██████╔╝██║   ██║   ██║   ");
            Console.SetCursorPosition(10, 5);
            Console.WriteLine("██╔══██╗██╔══██╗██╔══╝  ██╔══██║██╔═██╗        ██║   ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║   ██║   ");
            Console.SetCursorPosition(10, 6);
            Console.WriteLine("██████╔╝██║  ██║███████╗██║  ██║██║  ██╗       ██║   ██║  ██║███████╗    ██║     ╚██████╔╝   ██║   ");
            Console.SetCursorPosition(10, 7);
            Console.WriteLine("╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝       ╚═╝   ╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝    ╚═╝   ");

            //menu title
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.SetCursorPosition(50, 10);
            Console.Write(" +-+-+-+-+ +-+-+-+-+");
            Console.SetCursorPosition(50, 11);
            Console.Write(" |M|a|i|n| |M|e|n|u|");
            Console.SetCursorPosition(50, 12);
            Console.Write(" +-+-+-+-+ +-+-+-+-+");

            //options
            Console.ForegroundColor = ConsoleColor.White;
            Console.SetCursorPosition(57, 15);
            Console.Write("1.Play");
            Console.SetCursorPosition(57, 17);
            Console.Write("2.Rules");
            Console.SetCursorPosition(57, 19);
            Console.Write("3.Exit");

            //read the players option
            ConsoleKey option;
            option = Console.ReadKey().Key;
            if(option == ConsoleKey.NumPad1)
            {
                //start the game
                game = true;
            }
            else if(option == ConsoleKey.NumPad2)
            {
                //show rules
                rules();
            }
            else if(option == ConsoleKey.NumPad3)
            {
                //don't go into the game
                game = false;
            }
            else
            {
                //if any other key press nothing happend, come back on this page
                Main();
            }
        }

        static void enemykillyou()
        {
            //clear previous screen
            Console.Clear();

            //make the screen dark red
            Console.ForegroundColor = ConsoleColor.DarkRed;

            //display death message

            //THE GUARDIAN 
            Console.SetCursorPosition(10, 3);
            Console.Write("▄▄▄█████▓ ██░ ██ ▓█████      ▄████  █    ██  ▄▄▄       ██▀███  ▓█████▄  ██▓ ▄▄▄       ███▄    █ ");
            Console.SetCursorPosition(10, 4);
            Console.Write("▓  ██▒ ▓▒▓██░ ██▒▓█   ▀     ██▒ ▀█▒ ██  ▓██▒▒████▄    ▓██ ▒ ██▒▒██▀ ██▌▓██▒▒████▄     ██ ▀█   █ ");
            Console.SetCursorPosition(10, 5);
            Console.Write("▒ ▓██░ ▒░▒██▀▀██░▒███      ▒██░▄▄▄░▓██  ▒██░▒██  ▀█▄  ▓██ ░▄█ ▒░██   █▌▒██▒▒██  ▀█▄  ▓██  ▀█ ██▒");
            Console.SetCursorPosition(10, 6);
            Console.Write("░ ▓██▓ ░ ░▓█ ░██ ▒▓█  ▄    ░▓█  ██▓▓▓█  ░██░░██▄▄▄▄██ ▒██▀▀█▄  ░▓█▄   ▌░██░░██▄▄▄▄██ ▓██▒  ▐▌██▒");
            Console.SetCursorPosition(10, 7);
            Console.Write("  ▒██▒ ░ ░▓█▒░██▓░▒████▒   ░▒▓███▀▒▒▒█████▓  ▓█   ▓██▒░██▓ ▒██▒░▒████▓ ░██░ ▓█   ▓██▒▒██░   ▓██░");
            Console.SetCursorPosition(10, 8);
            Console.Write("  ▒ ░░    ▒ ░░▒░▒░░ ▒░ ░    ░▒   ▒ ░▒▓▒ ▒ ▒  ▒▒   ▓▒█░░ ▒▓ ░▒▓░ ▒▒▓  ▒ ░▓   ▒▒   ▓▒█░░ ▒░   ▒ ▒ ");
            Console.SetCursorPosition(10, 9);
            Console.Write("    ░     ▒ ░▒░ ░ ░ ░  ░     ░   ░ ░░▒░ ░ ░   ▒   ▒▒ ░  ░▒ ░ ▒░ ░ ▒  ▒  ▒ ░  ▒   ▒▒ ░░ ░░   ░ ▒░");
            Console.SetCursorPosition(10, 10);
            Console.Write("  ░       ░  ░░ ░   ░      ░ ░   ░  ░░░ ░ ░   ░   ▒     ░░   ░  ░ ░  ░  ▒ ░  ░   ▒      ░   ░ ░ ");
            Console.SetCursorPosition(10, 11);
            Console.Write("          ░  ░  ░   ░  ░         ░    ░           ░  ░   ░        ░     ░        ░  ░         ░ ");
            Console.SetCursorPosition(10, 12);
            Console.Write("                                                                ░                               ");

            //KILLED YOU        
            Console.SetCursorPosition(20, 14);
            Console.Write(" ██ ▄█▀ ██▓ ██▓     ██▓    ▓█████ ▓█████▄    ▓██   ██▓ ▒█████   █    ██ ");
            Console.SetCursorPosition(20, 15);
            Console.Write(" ██▄█▒ ▓██▒▓██▒    ▓██▒    ▓█   ▀ ▒██▀ ██▌    ▒██  ██▒▒██▒  ██▒ ██  ▓██▒");
            Console.SetCursorPosition(20, 16);
            Console.Write("▓███▄░ ▒██▒▒██░    ▒██░    ▒███   ░██   █▌     ▒██ ██░▒██░  ██▒▓██  ▒██░");
            Console.SetCursorPosition(20, 17);
            Console.Write("▓██ █▄ ░██░▒██░    ▒██░    ▒▓█  ▄ ░▓█▄   ▌     ░ ▐██▓░▒██   ██░▓▓█  ░██░");
            Console.SetCursorPosition(20, 18);
            Console.Write("▒██▒ █▄░██░░██████▒░██████▒░▒████▒░▒████▓      ░ ██▒▓░░ ████▓▒░▒▒█████▓ ");
            Console.SetCursorPosition(20, 19);
            Console.Write("▒ ▒▒ ▓▒░▓  ░ ▒░▓  ░░ ▒░▓  ░░░ ▒░ ░ ▒▒▓  ▒       ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒ ");
            Console.SetCursorPosition(20, 20);
            Console.Write("░ ░▒ ▒░ ▒ ░░ ░ ▒  ░░ ░ ▒  ░ ░ ░  ░ ░ ▒  ▒     ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░ ");
            Console.SetCursorPosition(20, 21);
            Console.Write("░ ░░ ░  ▒ ░  ░ ░     ░ ░      ░    ░ ░  ░     ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░ ");
            Console.SetCursorPosition(20, 22);
            Console.Write("░  ░    ░      ░  ░    ░  ░   ░  ░   ░        ░ ░         ░ ░     ░     ");
            Console.SetCursorPosition(20, 23);
            Console.Write("                                   ░          ░ ░                       ");

            //continue message
            Console.SetCursorPosition(45, 25);
            Console.Write(" +-+-+-+-+-+ +-+-+-+-+-+ ");
            Console.SetCursorPosition(45, 26);
            Console.Write(" |p|r|e|s|s| |e|n|t|e|r| ");
            Console.SetCursorPosition(45, 27);
            Console.Write(" +-+-+-+-+-+ +-+-+-+-+-+ ");

            //make user press enter
            ConsoleKey hit;
            hit = Console.ReadKey().Key;
            if(hit == ConsoleKey.Enter)
            {
            }
            else
            {
                enemykillyou();
            }
        }

        static void scoretoolow()
        {
            //clear previous screen
            Console.Clear();

            //make this screen magenta
            Console.ForegroundColor = ConsoleColor.Magenta;

            //Display loss message

            //YOU LOST
            Console.SetCursorPosition(20, 3);
            Console.Write("▓██   ██▓ ▒█████   █    ██     ██▓     ▒█████    ██████ ▄▄▄█████▓");
            Console.SetCursorPosition(20, 4);
            Console.Write(" ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██▒    ▒██▒  ██▒▒██    ▒ ▓  ██▒ ▓▒");
            Console.SetCursorPosition(20, 5);
            Console.Write("  ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██░    ▒██░  ██▒░ ▓██▄   ▒ ▓██░ ▒░");
            Console.SetCursorPosition(20, 6);
            Console.Write("  ░ ▐██▓░▒██   ██░▓▓█  ░██░   ▒██░    ▒██   ██░  ▒   ██▒░ ▓██▓ ░ ");
            Console.SetCursorPosition(20, 7);
            Console.Write("  ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░██████▒░ ████▓▒░▒██████▒▒  ▒██▒ ░ ");
            Console.SetCursorPosition(20, 8);
            Console.Write("   ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒    ░ ▒░▓  ░░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░  ▒ ░░   ");
            Console.SetCursorPosition(20, 9);
            Console.Write(" ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░    ░ ░ ▒  ░  ░ ▒ ▒░ ░ ░▒  ░ ░    ░    ");
            Console.SetCursorPosition(20, 10);
            Console.Write(" ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░      ░ ░   ░ ░ ░ ▒  ░  ░  ░    ░      ");
            Console.SetCursorPosition(20, 11);
            Console.Write(" ░ ░         ░ ░     ░            ░  ░    ░ ░        ░           ");
            Console.SetCursorPosition(20, 12);
            Console.Write(" ░ ░                                                             ");

            //TOO MANY POTS 
            Console.SetCursorPosition(7, 14);
            Console.Write("▄▄▄█████▓ ▒█████   ▒█████      ███▄ ▄███▓ ▄▄▄       ███▄    █▓██   ██▓    ██▓███   ▒█████  ▄▄▄█████▓  ██████ ");
            Console.SetCursorPosition(7, 15);
            Console.Write("▓  ██▒ ▓▒▒██▒  ██▒▒██▒  ██▒   ▓██▒▀█▀ ██▒▒████▄     ██ ▀█   █ ▒██  ██▒   ▓██░  ██▒▒██▒  ██▒▓  ██▒ ▓▒▒██    ▒ ");
            Console.SetCursorPosition(7, 16);
            Console.Write("▒ ▓██░ ▒░▒██░  ██▒▒██░  ██▒   ▓██    ▓██░▒██  ▀█▄  ▓██  ▀█ ██▒ ▒██ ██░   ▓██░ ██▓▒▒██░  ██▒▒ ▓██░ ▒░░ ▓██▄   ");
            Console.SetCursorPosition(7, 17);
            Console.Write("░ ▓██▓ ░ ▒██   ██░▒██   ██░   ▒██    ▒██ ░██▄▄▄▄██ ▓██▒  ▐▌██▒ ░ ▐██▓░   ▒██▄█▓▒ ▒▒██   ██░░ ▓██▓ ░   ▒   ██▒");
            Console.SetCursorPosition(7, 18);
            Console.Write("  ▒██▒ ░ ░ ████▓▒░░ ████▓▒░   ▒██▒   ░██▒ ▓█   ▓██▒▒██░   ▓██░ ░ ██▒▓░   ▒██▒ ░  ░░ ████▓▒░  ▒██▒ ░ ▒██████▒▒    ");
            Console.SetCursorPosition(7, 19);
            Console.Write("  ▒ ░░   ░ ▒░▒░▒░ ░ ▒░▒░▒░    ░ ▒░   ░  ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒   ██▒▒▒    ▒▓▒░ ░  ░░ ▒░▒░▒░   ▒ ░░   ▒ ▒▓▒ ▒ ░");
            Console.SetCursorPosition(7, 20);
            Console.Write("    ░      ░ ▒ ▒░   ░ ▒ ▒░    ░  ░      ░  ▒   ▒▒ ░░ ░░   ░ ▒░▓██ ░▒░    ░▒ ░       ░ ▒ ▒░     ░    ░ ░▒  ░ ░");
            Console.SetCursorPosition(7, 21);
            Console.Write("  ░      ░ ░ ░ ▒  ░ ░ ░ ▒     ░      ░     ░   ▒      ░   ░ ░ ▒ ▒ ░░     ░░       ░ ░ ░ ▒    ░      ░  ░  ░  ");
            Console.SetCursorPosition(7, 22);
            Console.Write("             ░ ░      ░ ░            ░         ░  ░         ░ ░ ░                     ░ ░                 ░  ");
            Console.SetCursorPosition(7, 23);
            Console.Write("                                                              ░ ░                                            ");

            //continue message
            Console.SetCursorPosition(45, 25);
            Console.Write(" +-+-+-+-+-+ +-+-+-+-+-+ ");
            Console.SetCursorPosition(45, 26);
            Console.Write(" |p|r|e|s|s| |e|n|t|e|r| ");
            Console.SetCursorPosition(45, 27);
            Console.Write(" +-+-+-+-+-+ +-+-+-+-+-+ ");

            //make user press enter
            ConsoleKey hit;
            hit = Console.ReadKey().Key;
            if (hit == ConsoleKey.Enter)
            {
            }
            else
            {
                enemykillyou();
            }
        }

        static void rules()
        {
            //clear previous screen
            Console.Clear();

            //create variables to help draw a box for rules
            int x, y, i;

            //top border
            x = 20;
            y = 5;
            for(i=0;i<70;i++)
            {
                Console.ForegroundColor = ConsoleColor.DarkGreen;
                Console.SetCursorPosition(x, y);
                Console.Write("▓");
                x = x + 1;
            }

            //bottom border
            x = 20;
            y = 25;
            for(i=0;i<71;i++)
            {
                Console.ForegroundColor = ConsoleColor.DarkGreen;
                Console.SetCursorPosition(x, y);
                Console.Write("▓");
                x = x + 1;
            }

            //left border
            x = 20;
            y = 5;
            for(i=0;i<20;i++)
            {
                Console.ForegroundColor = ConsoleColor.DarkGreen;
                Console.SetCursorPosition(x, y);
                Console.Write("▓");
                y = y + 1;
            }

            //right border
            x = 90;
            y = 5;
            for (i = 0; i < 20; i++)
            {
                Console.ForegroundColor = ConsoleColor.DarkGreen;
                Console.SetCursorPosition(x, y);
                Console.Write("▓");
                y = y + 1;
            }

            //explain rules
            Console.ForegroundColor = ConsoleColor.White;

            //titles *RULES*
            Console.SetCursorPosition(55, 7);
            Console.Write("RULES");

            //RULE 1
            Console.SetCursorPosition(22,9);
            Console.Write("1.Use the arrow keys to move around.");

            //RULE 2
            Console.SetCursorPosition(22, 11);
            Console.Write("2.Destroy the most pots possible before the Guardian kills you.");
            Console.SetCursorPosition(22, 13);

            //RULE 3
            Console.Write("3.You get 10 points per pot.");
            Console.SetCursorPosition(22, 15);

            //RULE 4
            Console.Write("4.If the Guardian takes a pot, you lose 10 points.");
            Console.SetCursorPosition(22, 17);

            //RULE 5
            Console.Write("5.If the Guardian kills you or your points go below 0, you LOSE.");
            Console.SetCursorPosition(45, 20);

            //GOOD LUCK MESAGE
            Console.Write("GOOD LUCK! Have Fun!");
            Console.SetCursorPosition(43, 22);

            //make user return to main menu
            Console.Write("Press any key to return");
            Console.ReadKey();

            //clear screen
            Console.Clear();

            //go back to main menu
            Main();
        }

        static void replayscreen()
        {
            //clear previous screen
            Console.Clear();

            //make this screen white
            Console.ForegroundColor = ConsoleColor.White;

            //display replay message

            //WOULD YOU LIKE
            Console.SetCursorPosition(2, 2);
            Console.Write("##......##..#######..##.....##.##.......########.....##....##..#######..##.....##....##.......####.##....##.########");
            Console.SetCursorPosition(2, 3);
            Console.Write("##..##..##.##.....##.##.....##.##.......##.....##.....##..##..##.....##.##.....##....##........##..##...##..##......");
            Console.SetCursorPosition(2, 4);
            Console.Write("##..##..##.##.....##.##.....##.##.......##.....##......####...##.....##.##.....##....##........##..##..##...##......");
            Console.SetCursorPosition(2, 5);
            Console.Write("##..##..##.##.....##.##.....##.##.......##.....##.......##....##.....##.##.....##....##........##..#####....######..");
            Console.SetCursorPosition(2, 6);
            Console.Write("##..##..##.##.....##.##.....##.##.......##.....##.......##....##.....##.##.....##....##........##..##..##...##......");
            Console.SetCursorPosition(2, 7);
            Console.Write("##..##..##.##.....##.##.....##.##.......##.....##.......##....##.....##.##.....##....##........##..##...##..##......");
            Console.SetCursorPosition(2, 8);
            Console.Write(".###..###...#######...#######..########.########........##.....#######...#######.....########.####.##....##.########");

            //TO REPLAY?
            Console.SetCursorPosition(17, 10);
            Console.Write("########..#######.....########..########.########..##..........###....##....##..#######");
            Console.SetCursorPosition(17, 11);
            Console.Write("...##....##.....##....##.....##.##.......##.....##.##.........##.##....##..##..##.....##");
            Console.SetCursorPosition(17, 12);
            Console.Write("...##....##.....##....##.....##.##.......##.....##.##........##...##....####.........##.");
            Console.SetCursorPosition(17, 13);
            Console.Write("...##....##.....##....########..######...########..##.......##.....##....##........###..");
            Console.SetCursorPosition(17, 14);
            Console.Write("...##....##.....##....##...##...##.......##........##.......#########....##.......##....");
            Console.SetCursorPosition(17, 15);
            Console.Write("...##....##.....##....##....##..##.......##........##.......##.....##....##.............");
            Console.SetCursorPosition(17, 16);
            Console.Write("...##.....#######.....##.....##.########.##........########.##.....##....##.......##....");

            //ask for user choice
            Console.SetCursorPosition(50, 20);
            Console.Write("Press Y for yes or N for no");
            ConsoleKey input;
            input = Console.ReadKey().Key;
            if(input == ConsoleKey.Y)
            {
                //if user wants to replay
                Main();
            }
            else if( input == ConsoleKey.N)
            {
                //if user says no
            }
            else
            {
                //if any other key pressed do nothing show this screen again
                replayscreen();
            }
        }

        static void endgamescreen()
        {
            //clear preious screen
            Console.Clear();

            //make this screen blue
            Console.ForegroundColor = ConsoleColor.Blue;

            //display leaving message

            //SEE YOU
            Console.SetCursorPosition(27,7);
            Console.Write(" .|'''.|  '||''''|  '||''''|  '||' '|'  ..|''||   '||'  '|'");
            Console.SetCursorPosition(27, 8);
            Console.Write(" ||..  '   ||  .     ||  .      || |   .|'    ||   ||    |  ");
            Console.SetCursorPosition(27, 9);
            Console.Write("  ''|||.   ||''|     ||''|       ||    ||      ||  ||    | ");
            Console.SetCursorPosition(27, 10);
            Console.Write(".     '||  ||        ||          ||    '|.     ||  ||    | ");
            Console.SetCursorPosition(27, 11);
            Console.Write("|'....|'  .||.....| .||.....|   .||.    ''|...|'    '|..'   ");

            //NEXT TIME
            Console.SetCursorPosition(22, 13);
            Console.Write("'|.   '|' '||''''|  '||' '|' |''||''|  |''||''| '||' '||    ||' '||''''| ");
            Console.SetCursorPosition(22, 14);
            Console.Write(" |'|   |   ||  .      || |      ||        ||     ||   |||  |||   ||  .  ");
            Console.SetCursorPosition(22, 15);
            Console.Write(" | '|. |   ||''|       ||       ||        ||     ||   |'|..'||   ||''|   ");
            Console.SetCursorPosition(22, 16);
            Console.Write(" |   |||   ||         | ||      ||        ||     ||   | '|' ||   ||      ");
            Console.SetCursorPosition(22, 17);
            Console.Write(".|.   '|  .||.....| .|   ||.   .||.      .||.   .||. .|. | .||. .||.....|");

            //make user press any key to leave
            Console.ForegroundColor = ConsoleColor.White;
            Console.SetCursorPosition(54, 25);
            Console.Write("Press Any Key");
            Console.ReadKey();
        }
    }
}



