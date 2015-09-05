using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib {
    public class AIPlayer01 : Player {
        public AIPlayer01(int score):base(score) {
            
        }
    
        public override Action decide() {
            if (RoundInfo.triangle.getCardType() > RoundInfo.round.turns.Count) {
                Action follow = new Action();
                follow.actionType=ActionType.follow;
                return follow;
            } else {
                Action giveup = new Action();
                giveup.actionType = ActionType.giveup;
                return giveup;
            }
        }
    }
}
