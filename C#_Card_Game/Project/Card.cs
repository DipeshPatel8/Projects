using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Project
{
    class Card
    {
        #region Data Fields
        //Data - Fields
        private Values value;
        private Suits suit; //1=Spades, 2=Diamond, 3=Clubs, 4=Heart
        #endregion

        #region Constructor
        //Constructor
        public Card(Values _value, Suits _suit)
        {
            value = _value;
            suit = _suit;
        }
        #endregion

        #region Methods
        //Methods
        public Values Value
        {
            get
            {
                return value;                       
            }
        }

        public Suits Suit
        {
            get
            {
                return suit;
            }
        }

        public override string ToString()
        {
            //create a string 
            string theCard="";
            string[] SuitShape = new string[] { "♠", "♦", "♣", "♥"};

            string FORMAT = "{0,-1} {1,-5} {2} {3,-1} {4}";
            theCard += string.Format(FORMAT, "|", value, "|", SuitShape[(int)suit-1], "|");

            //return the full string
            return theCard;
        }
        #endregion
    }

    #region Enums
    //Enums for suit and value
    enum Suits
    {
        Spades = 1,
        Diamonds,
        Clubs,
        Heart
    }
    enum Values
    {
        Ace = 1,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King
    }
    #endregion
}
