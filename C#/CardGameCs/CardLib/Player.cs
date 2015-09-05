using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib
{
    public abstract class Player
    {
        private RoundOfPlayer roundInfo;
        private int score;

        public Player(int score)
        {
            this.score = score;
            this.roundInfo = new RoundOfPlayer(this);
        }

        public RoundOfPlayer RoundInfo {
            get {
                return roundInfo;
            }
        }

        public void decScore(int scoreToDec) {
            if (score < scoreToDec) {
                throw new System.InvalidOperationException("not enough score");
            }
            score = score - scoreToDec;
        }
        public void incScore(int scoreToInc) {
            score = score + scoreToInc;
        }
        public abstract Action decide();
    }
}
