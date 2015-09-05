using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CardLib
{
    public class Action
    {
        public ActionType actionType;
        public int promoteMultiple;
        public Player seeWho;
    }

    public enum ActionType
    {
        giveup,
        follow,
        promote,
        see,
    }
}
