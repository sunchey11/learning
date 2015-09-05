using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CardLib;
namespace CardGameCs {
    class Program {
        static void Main(string[] args) {
            string line = Console.ReadLine();
            if (line.Count() == 0) {
                gameStart();
            } else {
                int choice = Convert.ToInt32(line);
                switch (choice) {
                    case 0: {
                            testRound_0();
                            break;
                        }
                    case 1: {
                            break;
                        }
                    default:
                        break;
                }
            }
            Console.ReadKey();
        }
        static void gameStart() {
            testRound_0();
        }
        static void testRound_0() {
            List<Player> players = new List<Player>();
            players.Add(new AIPlayer01(10000));
            players.Add(new AIPlayer01(10000));
            Round r = new Round(players, 100);
            r.prepare();
            r.dispatchCards();
            players[0].RoundInfo.triangle.Clear();
            players[1].RoundInfo.triangle.Clear();

            players[0].RoundInfo.triangle.addCard(new Card(Suit.Club,Rank.Ace));
            players[0].RoundInfo.triangle.addCard(new Card(Suit.Diamond, Rank.Ace));
            players[0].RoundInfo.triangle.addCard(new Card(Suit.Heart, Rank.Ace));

            players[1].RoundInfo.triangle.addCard(new Card(Suit.Club, Rank.Ace));
            players[1].RoundInfo.triangle.addCard(new Card(Suit.Diamond, Rank.Deuce));
            players[1].RoundInfo.triangle.addCard(new Card(Suit.Heart, Rank.Three));
            
            r.play();
            
        }
    }
}
