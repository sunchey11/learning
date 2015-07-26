#pragma once
#include "Player.h"
#include "DynaArray.h"
#include "Action.h"
class Round
{
public:
	Round(const DynaArray <Player *> & currentPlayer);
	~Round(void);
	DynaArray <Player *> m_currentPlayer;
	void oneRound();
	
	void doAction(Player * player, const Action &action);
	int currentScore;
	int totalScore;
	int activePlayerCount();
};

