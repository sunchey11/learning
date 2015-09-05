using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib
{
    public class Turn
    {
        private List<Action> actions = new List<Action>();
        private List<bool> alive = new List<bool>();
        private List<bool> black = new List<bool>();

        public Turn(int playerCount) {
            for (int i = 0; i < playerCount; i++) {
                actions.Add(null);
                alive.Add(true);
                black.Add(true);
            }
        }
        public Turn(Turn lastTurn) {
            for (int i = 0; i < lastTurn.Actions.Count; i++) {
                //init actions
                actions.Add(null);

                //copy alive and black
                alive.Add(lastTurn.alive[i]);
                black.Add(lastTurn.black[i]);
            }
        }

        public List<Action> Actions {
            get {
                return actions;
            }
            
        }
        public List<bool> Alive {
            get {
                return alive;
            }

        }
        public List<bool> Black {
            get {
                return black;
            }

        }
    }
}
