using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace CardLib {
    delegate void dispatchCardsFuncDef();
    public class Round {
        public List<Turn> turns = new List<Turn>();
        private int currentMultiple = 1;
        private int totalMultiple = 0;
        readonly private int baseScore;
        private List<Player> players;

        public Round(List<Player> players, int baseScore) {
            this.players = players;
            this.baseScore = baseScore;
        }

        public bool isAlive(Player player) {
            if (turns.Count == 0) {
                return true;//all alive when it is first time.
            }
            Turn t = turns.Last();
            int index = players.IndexOf(player);

            return t.Alive[index];
        }

        public void prepare() {
            for (int i = 0; i < players.Count; i++) {
                players[i].RoundInfo.roundStart(this);
            }

        }
        public void play() {
            //begin to play game
            while (alivedPlayerCount() > 1) {
                Turn t = null;
                if (turns.Count == 0) {//first
                    t = new Turn(players.Count);
                } else {
                    t = new Turn(turns.Last());
                }

                turns.Add(t);
                for (int i = 0; i < players.Count; i++) {
                    Player p = players[i];
                    if (isAlive(p)) {
                        Action a = p.decide();
                        doAction(a, p, t, i);
                        t.Actions[i] = a;
                    } else {
                        t.Actions[i] = null;
                    }

                    if (alivedPlayerCount() == 1) {
                        break;//one player win
                    }

                }
            }
        }
        

        public  void dispatchCards() {
            Deck deck = new Deck();
            deck.Shuffle();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < players.Count; j++) {
                    int index = i * players.Count + j;
                    players[j].RoundInfo.addCard(deck.GetCard(index));
                }
            }
        }

        public int alivedPlayerCount() {
            int count = 0;
            for (int i = 0; i < players.Count; i++) {
                if (isAlive(players[i])) {
                    count++;
                }
            }
            return count;
        }

        public void doAction(Action action, Player player, Turn t, int playerIndex) {
            switch (action.actionType) {
                case ActionType.giveup: {
                        t.Alive[playerIndex] = false;
                        break;
                    }
                case ActionType.follow: {
                        player.decScore(currentMultiple * baseScore);
                        totalMultiple = totalMultiple + currentMultiple;
                        break;
                    }
                case ActionType.promote: {
                        currentMultiple += action.promoteMultiple;
                        player.decScore(currentMultiple * baseScore);
                        totalMultiple = totalMultiple + currentMultiple;
                        break;
                    }
                case ActionType.see: {
                        player.decScore(currentMultiple * baseScore);
                        totalMultiple = totalMultiple + currentMultiple;
                        int i = System.Collections.Comparer.Default.Compare(player.RoundInfo.triangle, action.seeWho.RoundInfo.triangle);
                        if (i > 0) {//win
                            int index = players.IndexOf(action.seeWho);
                            t.Alive[index] = false;
                        } else {
                            t.Alive[playerIndex] = false;
                        }
                        break;
                    }
                default:
                    break;
            }
        }
    }
}
