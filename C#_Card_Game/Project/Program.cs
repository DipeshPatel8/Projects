using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Project
{
    class Program
    {
        /// <summary>
        /// 
        ///     Tested by Philip Rechtacek
        ///  
        /// </summary>
       
        #region Constants
        //Game constants
        public const int MINDECKLIMIT = 0;
        public const int MAXDECKLIMIT = 51;
        public const int HANDSIZE = 5;
        public const int ZERO = 0;
        public const int MAXDISCARDROUNDS = 3;
        public const int STARTSCORE = 1000;
        public const int MAX_DISCARDABLE_CARDS = 4;
        public const int TESTINGOPTIONS = 8;
        public const int MAINMENUOPTIONS = 3;
        public const int MINOPTION = 1;
        public const string TITLEFORMAT = "{0} {1,43} {2,30}";

        //Combination constants
        public const int FIRSTCARDINDEX = 0;
        public const int SECONDCARDINDEX = 1;
        public const int THIRDCARDINDEX = 2;
        public const int FOURTHCARDINDEX = 3;
        public const int FIFTHCARDINDEX = 4;
        public const int TWIN = 2;

        //Points Constants
        public const int HIGHFIVEPOINTS = 800;
        public const int SEQUENCEPOINTS = 600;
        public const int QUADRUPLETSPOINTS = 400;
        public const int FAMILYPOINTS = 200;
        public const int MIXEDSEQUENCEPOINTS = 100;
        public const int DOUBLETWINSPOINTS = 50;
        public const int NOCOMBINATIONPOINTS = -100;
        #endregion

        #region Main
        static void Main(string[] args)
        {
            int UserChoice = 0;
            //keep looping until user wants to leave
            do
            {
                //display title and menu options
                Console.Clear();
                Console.WriteLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                Console.WriteLine(TITLEFORMAT, "|", "Combine and Win","|");
                Console.WriteLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                Console.WriteLine();
                Console.WriteLine("1. Play");
                Console.WriteLine("2. Automatic Testing");
                Console.WriteLine("3. Exit");
                Console.WriteLine();
                //get user input and execute the proper code
                UserChoice = GetUserInput("Please enter your option", MAINMENUOPTIONS, MINOPTION);
                if (UserChoice == 1)
                    MainGame();
                else if (UserChoice == 2)
                    Testing();
                else if (UserChoice == 3)
                {
                    break;
                }
            } while (UserChoice != 3);             
        }
        #endregion

        #region Main Game Methods
        static void MainGame()
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8;
            //try catch to avoid random errors
            try
            {
                bool ContinueChoice;
                int DiscardRounds, CardSwitched, PlayerPoints = STARTSCORE;       
                //loop until user wants to leave or points below 0
                do
                {
                    //clear previous screen and display title
                    Console.Clear();
                    Console.WriteLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    Console.WriteLine(TITLEFORMAT, "|", "Combine and Win", "|");
                    Console.WriteLine("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    //create deck and other required variables
                    Deck mydeck = new Deck();
                    DiscardRounds = 1;
                    CardSwitched = 0;

                    //shuffle deck
                    mydeck.ShuffleDeck();

                    //display current score and all cards in hand
                    Console.WriteLine();
                    Console.WriteLine(">>> Game Score: {0} points. <<<", PlayerPoints);
                    Console.WriteLine();                   
                    Card[] MyHand = GenerateFiveCard(mydeck);
                    SortCardsByValue(ref MyHand);//sort cards by value
                    PrintHand(MyHand);
                    Console.WriteLine();

                    //loop max 3 time (max 3 discard rounds)
                    do
                    {
                        //required variables
                        ContinueChoice = false;
                        CardSwitched = ZERO;
                        int[] cardsChangedIndex = new int[MAX_DISCARDABLE_CARDS] { -1, -1, -1, -1 };

                        //display title
                        Console.BackgroundColor = ConsoleColor.White;
                        Console.ForegroundColor = ConsoleColor.Black;
                        Console.WriteLine(">>> Discarding Round {0} <<<", DiscardRounds);
                        Console.BackgroundColor = ConsoleColor.Black;
                        Console.ForegroundColor = ConsoleColor.White;

                        //loop max 4 times(max 4/5 cards discradables or user dosnt want to change
                        while (CardSwitched < HANDSIZE && ContinueChoice == false)
                        {
                            //discard card and get user choice to discard more and not
                            ContinueChoice = DiscardCard(ref MyHand, mydeck, ref CardSwitched, ref cardsChangedIndex);
                        }

                        //if no cards were discards, skip other rounds and display results
                        if (CardSwitched == ZERO)
                            break;

                        //sort hand and display after the discard
                        Console.WriteLine();
                        SortCardsByValue(ref MyHand);
                        PrintHand(MyHand);
                        Console.WriteLine();

                        //increase discard round count
                        DiscardRounds++;
                    } while (DiscardRounds <= MAXDISCARDROUNDS);

                    //check which combination the hand matchs and add or remove points
                    Console.WriteLine();
                    PlayerPoints = PlayerPoints + PointsEarned(MyHand);
                    Console.WriteLine();

                    //display result and points
                    Console.WriteLine("{0,10} {1, -5} {2}",">>> Game Score", ":", PlayerPoints+" points. <<<");
                    Console.WriteLine();
                    
                    //ask user if he/she wants to continue or not if points above 0
                    if(PlayerPoints>ZERO)
                        ContinueChoice = Replay();
                } while (ContinueChoice == true&&PlayerPoints>ZERO);

                //display lose message if points below 0
                if(PlayerPoints<=ZERO)
                {
                    Console.WriteLine();
                    Console.WriteLine("Sorry you have lost. Better luck next time :)");
                    Console.WriteLine("Press any key to return to the main menu.");
                    Console.ReadKey();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        static Card[] GenerateFiveCard(Deck myDeck)
        {
            //create 5 card array and fill with cards from deck
            Card[] HandOfFive = new Card[HANDSIZE];
            for (int i = 0; i < HANDSIZE; i++)
            {
                HandOfFive[i] = myDeck.GetCardFromDeck();
            }
            return HandOfFive;
        }

        static bool DiscardCard(ref Card[] MyHand, Deck myDeck, ref int cardsSwitched, ref int[] cardsChangedIndexes)
        {
            //get user choice 
            int UserChoice;
            UserChoice = GetUserInput("Choose card to replace, or 0 to continue", HANDSIZE, ZERO);

            //leave if no cards selected
            if (UserChoice == ZERO)
                return true;

            //check if card was already changed
            while(CheckIfCardAlreadyChanged(UserChoice, cardsChangedIndexes)==true)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                UserChoice = GetUserInput("Card already changed. Try again", HANDSIZE, ZERO);
                Console.ForegroundColor = ConsoleColor.White;
                if (UserChoice == ZERO)
                    return true;
            }

            //change desired card with a new one from deck
            MyHand[UserChoice-1] = myDeck.GetCardFromDeck();

            //add changed card index to changeCards array
            cardsChangedIndexes[cardsSwitched] = UserChoice;

            //increase amount of card changed
            cardsSwitched++;

            //print message and return 
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("Card has been Changed");
            Console.ForegroundColor = ConsoleColor.White;
            return false;
        }
            
        static bool CheckIfCardAlreadyChanged(int choice, int[]changedCards)
        {
            bool changed=true;
            //go through all index in array 
            for (int i = 0; i < changedCards.Length; i++)
            {
                //check selected index is in array of not
                if (choice==changedCards[i])
                {
                    //if index in array return true
                    changed = true;
                    break;
                }
                else
                    //return false if not in array
                    changed = false;
            }
            return changed;
        }

        static void PrintHand(Card[] MyHand)
        {
            //print all 5 cards in array with title
            Console.WriteLine(">>> Current Hand <<<");
            for (int i = 0; i < HANDSIZE; i++)
            {
                Console.WriteLine("[{0}] {1} ",i+1, MyHand[i].ToString()+" ");
            }
        }

        static int GetUserInput(string msg, int Max, int Min)
        {
            bool valid;
            int value;
            Console.Write(msg+": ");

            //get user input
            valid = int.TryParse(Console.ReadLine(), out value);

            //validate if proper type and within range
            while (!valid || value<Min || value>Max)
            {
                //display proper error message 
                if (!valid)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("Error: Only a number can be entered. Try again: ");
                    Console.ForegroundColor = ConsoleColor.White;
                }
                else if(value<Min)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("Error: Number can't be below {0}. Try again: ", Min);
                    Console.ForegroundColor = ConsoleColor.White;
                }
                else if(value>Max)
                {
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.Write("Error: Number can't be more than {0}. Try again: ", Max);
                    Console.ForegroundColor = ConsoleColor.White;
                } 
                
                //get new input
                valid = int.TryParse(Console.ReadLine(), out value);
            }
            return value;
        }

        static bool Replay()
        {
            //ask user if he/she wants to replay or not 
            ConsoleKey Input;
            Console.WriteLine("Press any key to continue (replay) or press N to quit");
            Input = Console.ReadKey().Key;
            if (Input == ConsoleKey.N)
                return false;
            else
                return true;              
        }

        static int PointsEarned(Card[] myHand)
        {
            Console.WriteLine();
            //find matching combination, display winning combination name and points earned
            //return points earned
            if (HighFive(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: High Five",":", "+800 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return HIGHFIVEPOINTS;
            }
            else if (Sequence(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: Sequence", ":","+600 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return SEQUENCEPOINTS;
            }
            else if (Quadruplets(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: Quadruplets",":","400 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return QUADRUPLETSPOINTS;
            }
            else if (Family(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: Family", ":", "+200 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return FAMILYPOINTS;
            }
            else if (MixedSequence(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: Mixed Sequence", ":", "+100 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return MIXEDSEQUENCEPOINTS;
            }
            else if (DoubleTwins(myHand) == true)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("{0,10} {1, -5} {2}", "Winning Combination: Double Twin", ":","+50 points.");
                Console.ForegroundColor = ConsoleColor.White;
                return DOUBLETWINSPOINTS;
            }
            else
            {
                //return negative points if no combination matched
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("{0,10} {1, -5} {2}", ">>> No Winning Combination",":", "-100 points. <<<");
                Console.ForegroundColor = ConsoleColor.White;
                return NOCOMBINATIONPOINTS;
            }
        }

        static void SortCardsByValue(ref Card[] myHand)
        {
            Card temp;
            //for each card
            for (int i = 0; i < myHand.Length; i++)
            {
                //compare with each card minus 1
                for (int j = 0; j < myHand.Length-1; j++)
                {
                    //if current card is bigger than next card 
                    if(myHand[j].Value>myHand[j+1].Value)
                    {
                        //swap
                        temp = myHand[j + 1];
                        myHand[j + 1] = myHand[j];
                        myHand[j] = temp;
                    }
                }
            }
        }
        #endregion

        #region Combinations
        static bool HighFive(Card[] myHand)
        {
            bool valid;
            //first card validation
            if (myHand[FIRSTCARDINDEX].Value == Values.Ten)
            {
                //second card validation
                if (myHand[SECONDCARDINDEX].Value == Values.Jack && myHand[SECONDCARDINDEX].Suit == myHand[FIRSTCARDINDEX].Suit)
                {
                    //third card validation
                    if (myHand[THIRDCARDINDEX].Value == Values.Queen && myHand[THIRDCARDINDEX].Suit == myHand[SECONDCARDINDEX].Suit)
                    {
                        //fourth card validation
                        if (myHand[FOURTHCARDINDEX].Value == Values.King && myHand[FOURTHCARDINDEX].Suit == myHand[THIRDCARDINDEX].Suit)
                        {
                            //fifth card validation
                            if (myHand[FIFTHCARDINDEX].Value == Values.Ace && myHand[FIFTHCARDINDEX].Suit == myHand[FOURTHCARDINDEX].Suit)
                            {
                                valid = true;
                            }
                            else
                                valid = false;
                        }
                        else
                            valid = false;
                    }
                    else
                        valid = false;
                }
                else
                    valid = false;
            }
            else
                valid = false;
            return valid;
        }

        static bool Sequence(Card[] myHand)
        {
            bool valid=false;
            for (int i = 0; i < myHand.Length; i++)
            {
                //if the first card is Ten, Jack, Queen or King, it cannot be a sequence
                if(i==0)
                {
                    if(myHand[i].Value==Values.Ten || myHand[i].Value == Values.Jack || myHand[i].Value == Values.Queen || myHand[i].Value == Values.King)
                    {
                        valid = false;
                        break;
                    }
                }
                else
                {
                    //check if current card value is previous card value + 1 and matching suits
                    if (myHand[i].Value == myHand[i - 1].Value + 1 && myHand[i].Suit == myHand[i - 1].Suit)
                        valid = true;
                    else
                    {
                        valid = false;
                        break;
                    }                        
                }
            }
            return valid;
        }

        static bool Quadruplets(Card[] myHand)
        {
            int total = 0;
            int ExpectedTotal = 4;
            //compare all card to the first cards
            for (int i = 0; i < myHand.Length; i++)
            {
                //four additions if quadruplets
                if (myHand[FIRSTCARDINDEX].Value == myHand[i].Value)
                    total++;
            }
            //if the first card was the different card he total should still 0, so check with second card
            if(total==0)
            {
                for (int i = 0; i < myHand.Length; i++)
                {
                    //four additions if quadruplets
                    if (myHand[SECONDCARDINDEX].Value == myHand[i].Value)
                        total++;
                }
            }
            //if total was 4 it means there are 4 values of same kind
            if (total == ExpectedTotal)
                return true;
            else
                return false;
        }

        static bool Family(Card[] myHand)
        {
            bool valid=false;
            for (int i = 0; i < myHand.Length; i++)
            {
                //compare all cards to first card, if all suits matched it stays true
                if (myHand[FIRSTCARDINDEX].Suit == myHand[i].Suit)
                    valid = true;
                else
                {
                    //if one card different suit, break loop and return false
                    valid = false;
                    break;
                }
            }
            return valid;
        }

        static bool MixedSequence(Card[] myHand)
        {
            bool valid = false;
            for (int i = 0; i < myHand.Length; i++)
            {
                //if the first card is Ten, Jack, Queen or King, it cannot be a sequence
                if (i == 0)
                {
                    if (myHand[i].Value == Values.Ten || myHand[i].Value == Values.Jack || myHand[i].Value == Values.Queen || myHand[i].Value == Values.King)
                    {
                        valid = false;
                        break;
                    }
                }
                else
                {
                    //check if current card value is previous card value + 1 
                    if (myHand[i].Value == myHand[i - 1].Value + 1)
                        valid = true;
                    else
                    {
                        valid = false;
                        break;
                    }                       
                }
            }
            return valid;
        }

        static bool DoubleTwins(Card[] myHand)
        {
            int count1, twinCount = 0;
            //compare each card to everycard
            for (int z = 0; z < myHand.Length; z++)
            {
                count1 = 0;
                for (int x = 0; x < myHand.Length; x++)
                {
                    //if a two cards matched, up the counter
                    if (myHand[z].Value == myHand[x].Value)
                        count1++;
                }
                //increase the twin counter if a twin was found
                if (count1 == TWIN)
                    twinCount++;                    
            }
            //remove 2 because there will be a double count 
            twinCount -= 2;
            //return true if 2 twins were found,
            if (twinCount == TWIN)
                return true;
            else
                return false;
        }
        #endregion

        #region Testing Methods
        static void Testing()
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8;
            //display title 
            Console.WriteLine();
            Console.WriteLine(">>> Automatic Testing <<<");
            int userChoice = 0;

            //keep looping until user wants to leave
            do
            {
                //display choices
                int playerPoints = STARTSCORE;
                Console.WriteLine();
                Console.WriteLine("1. HighFive");
                Console.WriteLine("2. Sequence");
                Console.WriteLine("3. Quadruplets");
                Console.WriteLine("4. Family");
                Console.WriteLine("5. Mixed Sequence");
                Console.WriteLine("6. Double Twins");
                Console.WriteLine("7. None");
                Console.WriteLine("8. Quit");
                Console.WriteLine();

                //get user input and validat it
                userChoice = GetUserInput("Enter combination to test", TESTINGOPTIONS, MINOPTION);
                Card[] customHand = null;

                //fill array of cards according the selection of the user
                switch (userChoice)
                {
                    case 1:
                        customHand = GetHighFiveHand();
                        break;
                    case 2:
                        customHand = GetSequenceHand();
                        break;
                    case 3:
                        customHand = GetQuadrupletsHand();
                        break;
                    case 4:
                        customHand = GetFamilyHand();
                        break;
                    case 5:
                        customHand = GetMixedSequenceHand();
                        break;
                    case 6:
                        customHand = GetDoubleTwinHand();
                        break;
                    case 7:
                        customHand = GetNotWinningCombination();
                        break;
                    case 8:
                        break;
                }
                //if user want to leave don't display and leave
                if (userChoice == 8)
                    break;

                //display hand and points earned
                PrintHand(customHand);
                playerPoints = playerPoints + PointsEarned(customHand);
            } while (userChoice!=8);          
        }

        static Card[] GetHighFiveHand()
        {
            //custom high five hand 
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Ten, Suits.Heart);
            customHand[1] = new Card(Values.Jack, Suits.Heart);
            customHand[2] = new Card(Values.Queen, Suits.Heart);
            customHand[3] = new Card(Values.King, Suits.Heart);
            customHand[4] = new Card(Values.Ace, Suits.Heart);

            return customHand;
        }

        static Card[] GetSequenceHand()
        {
            //custom sequence hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Four, Suits.Clubs);
            customHand[1] = new Card(Values.Five, Suits.Clubs);
            customHand[2] = new Card(Values.Six, Suits.Clubs);
            customHand[3] = new Card(Values.Seven, Suits.Clubs);
            customHand[4] = new Card(Values.Eight, Suits.Clubs);

            return customHand;
        }

        static Card[] GetQuadrupletsHand()
        {
            //custom Quadruplet hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Queen, Suits.Heart);
            customHand[1] = new Card(Values.Queen, Suits.Spades);
            customHand[2] = new Card(Values.Queen, Suits.Diamonds);
            customHand[3] = new Card(Values.Queen, Suits.Clubs);
            customHand[4] = new Card(Values.Ace, Suits.Heart);

            return customHand;
        }

        static Card[] GetFamilyHand()
        {
            //custom family hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Four, Suits.Diamonds);
            customHand[1] = new Card(Values.Three, Suits.Diamonds);
            customHand[2] = new Card(Values.Eight, Suits.Diamonds);
            customHand[3] = new Card(Values.Jack, Suits.Diamonds);
            customHand[4] = new Card(Values.Ten, Suits.Diamonds);

            return customHand;
        }

        static Card[] GetMixedSequenceHand()
        {
            //custom mixed sequence hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Six, Suits.Clubs);
            customHand[1] = new Card(Values.Seven, Suits.Heart);
            customHand[2] = new Card(Values.Eight, Suits.Spades);
            customHand[3] = new Card(Values.Nine, Suits.Spades);
            customHand[4] = new Card(Values.Ten, Suits.Heart);

            return customHand;
        }

        static Card[] GetDoubleTwinHand()
        {
            //custom double twin hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Four, Suits.Spades);
            customHand[1] = new Card(Values.Four, Suits.Clubs);
            customHand[2] = new Card(Values.Jack, Suits.Heart);
            customHand[3] = new Card(Values.Jack, Suits.Spades);
            customHand[4] = new Card(Values.Ace, Suits.Heart);

            return customHand;
        }

        static Card[] GetNotWinningCombination()
        {
            //custom no combination hand
            Card[] customHand = new Card[HANDSIZE];
            customHand[0] = new Card(Values.Ten, Suits.Spades);
            customHand[1] = new Card(Values.Ace, Suits.Clubs);
            customHand[2] = new Card(Values.Two, Suits.Heart);
            customHand[3] = new Card(Values.Jack, Suits.Spades);
            customHand[4] = new Card(Values.King, Suits.Heart);

            return customHand;
        }
        #endregion
    }
}
