#pragma once

enum ActionType{ failure, giveup, follow, promote, see };

class Action
{
public:
	Action();
	~Action();
	int promoteScore;
	int seeWho;
	ActionType type;
};

