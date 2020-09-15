using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Project
{    
    class Deck
    {
        #region Data Fields - Constants
        //Constants
        private const int DECKSIZE = 52;
        private const int MINDECKLIMIT = 0;
        private const int MAXDECKLIMIT = 51;

        //Data - Fields
        private Card[] fullDeck = new Card[DECKSIZE];
        private int distributedCardIndex=0;
        #endregion

        #region Constructor
        //Constructor
        public Deck()
        {
            int suitCount = 1, valueCount = 1; 
            for (int i = 0; i < DECKSIZE; i++)
            {
                if (valueCount > 13)
                {
                    valueCount = 1;
                    suitCount++;
                }                   
                fullDeck[i] = new Card((Values)valueCount, (Suits)suitCount);
                valueCount++;
            }
        }
        #endregion

        #region Methods
        //Methods
        public Card GetCardFromDeck()
        {
            //give card on top and increase counter
            Card temp = fullDeck[distributedCardIndex];

            //reset counter if limit reached
            if (distributedCardIndex == MAXDECKLIMIT)
                distributedCardIndex = MINDECKLIMIT;
            else
                distributedCardIndex++;
            return temp;
        }
        public void ShuffleDeck()
        {
            //generate random index
            Random r = new Random();
            Card Temp;
            for (int i = 0; i < DECKSIZE; i++)         
            {
                //swap cards with random index
                int index = r.Next(MINDECKLIMIT, MAXDECKLIMIT);
                int index2 = r.Next(MINDECKLIMIT, MAXDECKLIMIT);
                Temp = fullDeck[index];
                fullDeck[index] = fullDeck[index2];
                fullDeck[index2] = Temp;
            }
        }
       
        public override string ToString()
        {
            //display whole deck 
            string DeckDisplay="";
            for (int i = 0; i < DECKSIZE; i++)
            {
                DeckDisplay += fullDeck[i].ToString();
                DeckDisplay += "\n";
            }
            return DeckDisplay; 
        }

        #endregion
    }
}
