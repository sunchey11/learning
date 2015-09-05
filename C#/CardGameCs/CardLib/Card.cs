using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib
{
    public class Card : ICloneable,IComparable<Card>
    {


        /// <summary>
        /// Flag that determines whether aces are higher than kings or lower
        /// than deuces.
        /// </summary>
        public static bool isAceHigh = true;

        public readonly Suit suit;
        public readonly Rank rank;

        public Card(Suit newSuit, Rank newRank)
        {
            suit = newSuit;
            rank = newRank;
        }

        private Card()
        {

        }

        public override string ToString()
        {
            return "The " + rank + " of " + suit + "s";
        }

        public object Clone()
        {
            return MemberwiseClone();
        }

        public static bool operator ==(Card card1, Card card2)
        {
            return (card1.suit == card2.suit) && (card1.rank == card2.rank);
        }

        public static bool operator !=(Card card1, Card card2)
        {
            return !(card1 == card2);
        }

        public override bool Equals(object card)
        {
            return this == (Card)card;
        }
        public override int GetHashCode()
        {
            return 13 * (int)rank + (int)suit;
        }

        public static bool operator >(Card card1, Card card2)
        {

            if (isAceHigh)
            {
                if (card1.rank == Rank.Ace)
                {
                    if (card2.rank == Rank.Ace)
                        return false;
                    else
                        return true;
                }
                else
                {
                    if (card2.rank == Rank.Ace)
                        return false;
                    else
                        return (card1.rank > card2.rank);
                }
            }
            else
            {
                return (card1.rank > card2.rank);
            }

        }

        public static bool operator <(Card card1, Card card2)
        {
            return !(card1 >= card2);
        }

        public static bool operator >=(Card card1, Card card2)
        {

            if (isAceHigh)
            {
                if (card1.rank == Rank.Ace)
                {
                    return true;
                }
                else
                {
                    if (card2.rank == Rank.Ace)
                        return false;
                    else
                        return (card1.rank >= card2.rank);
                }
            }
            else
            {
                return (card1.rank >= card2.rank);
            }

        }

        public static bool operator <=(Card card1, Card card2)
        {
            return !(card1 > card2);
        }

        public int CompareTo(Card other) {
            if (this == other) {
                return 0;
            } else if (this > other) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
