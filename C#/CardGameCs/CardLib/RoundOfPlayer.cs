using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib
{
    /// <summary>
    /// Player每一局的信息
    /// </summary>
    /// <remarks>通过此类获取每一局的信息</remarks>
    public class RoundOfPlayer {
        private Player owner;
        public Round round;
        public Triangle triangle=new Triangle();

        public RoundOfPlayer(Player player)
        {
            owner = player;
        }

        public void roundStart(Round round)
        {
            this.round = round;
            triangle.Clear();
        }

        public void addCard(Card card)
        {
            triangle.addCard(card);
        }

        public void isAlive()
        {
            round.isAlive(owner);
        }

       
    }
}
