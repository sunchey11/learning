#pragma once
#include "Player.h"
#include "DynaArray.h"
#include "Round.h"
class Game
{
public:
	Game();
	~Game();
	//正常结束返回0,某人因故退出返回1
	int oneGame();
	void printPlayerInfo();
	DynaArray <Player *> getPlayerByScore();
	Player * getWinner();
	// 所有的玩家,即使已经不玩了,也会在此数组里
	DynaArray <Player *> allPlayers;
};

