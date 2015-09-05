using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib {

    public class Triangle:IComparable {
        private List<Card> cards = new List<Card>();
    
        public int CompareTo(object obj) {
            if (obj is Triangle) {
                Triangle otherTriangle = (Triangle)obj;
                int r = System.Collections.Comparer.Default.Compare(this.getCardType(), otherTriangle.getCardType());
                if (r == 0) {
                    if (duizi()>0) {
                        r = System.Collections.Comparer.Default.Compare(this.duizi(), otherTriangle.duizi());
                        if (r == 0) {
                            return System.Collections.Comparer.Default.Compare(this.duizi_san(), otherTriangle.duizi_san()); 
                        } else {
                            return r;
                        }
                    } else {
                        for (int i = 2; i >= 0; i--) {
                            r = System.Collections.Comparer.Default.Compare(this.cards[i], otherTriangle.cards[i]);
                            if (r != 0) {
                                return r;
                            }
                        }
                        return 0;
                    }
                }else{
                    return r;
                }
                
            } else {
                throw new System.Exception("error");
            }
        }

        public void addCard(Card card) {
            if (cards.Count >= 3) {
                throw new System.InvalidOperationException("can't add card more than three.");
            }
            cards.Add(card);
            cards.Sort();
        }
        public void Clear() {
            cards.Clear();
        }
       
        public int getCardType() {
            if (isBaozi()) {// baozi
                return 5;
            }
            //tong hua shun
            if (isShunzi() && isSameSuit()) {
                return 4;
            }
            if (isSameSuit()) {
                return 3;
            }
            if (isShunzi()) {
                return 2;
            }
            if (duizi() > 0) {
                return 1;
            }
            return 0;

        }
        private bool isBaozi() {
            if (cards[0].rank == cards[1].rank && cards[1].rank == cards[2].rank) {
                return true;
            }
            return false;
        }
        private bool isShunzi(){
            if(cards[0].rank==Rank.Ace && cards[1].rank==Rank.Queen&& cards[2].rank== Rank.King ){
                return true;
            }
            if (cards[2].rank == Rank.Ace && cards[0].rank == Rank.Queen && cards[1].rank == Rank.King) {
                return true;
            }
            if (cards[2].rank == Rank.Ace && cards[0].rank == Rank.Deuce && cards[1].rank == Rank.Three) {
                return true;
            }
            if (cards[0].rank == cards[1].rank - 1 && cards[0].rank == cards[2].rank - 2) {
                return true;
            }
            return false;
        }
        private bool isSameSuit() {
            if (cards[0].suit == cards[1].suit && cards[0].suit == cards[2].suit) {
                return true;
            }
            return false;
        }
        /// <summary>
        /// 如果不是对子，返回0
        /// 否则返回对子的序号
        /// </summary>
        private int duizi() {
            if (isBaozi()) {
                return 0;
            }
            if (cards[1].rank == cards[0].rank) {
                return (int)cards[1].rank;
            }
            if (cards[1].rank == cards[2].rank) {
                return (int)cards[1].rank;
            }
            return 0;
        }
        /// <returns>返回对子的散牌</returns>
        private int duizi_san() {
            if (duizi() == 0) {
                throw new System.Exception("not a duizi");
            }
            if (cards[1].rank == cards[0].rank) {
                return (int)cards[2].rank;
            }
            if (cards[1].rank == cards[2].rank) {
                return (int)cards[0].rank;
            }
            throw new System.Exception("can't go here!");

        }
    }
}
