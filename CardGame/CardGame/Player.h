#pragma once
#include "CardSet.h"
#include "Player.h"
class Round;
class Game;
class Action;
class Player
{
public:
	Player(void);
	~Player(void);
	CardSet cardSet;
	// 增加分数,返回增加后的分数
	int incScore(int score);
	// 减少分数,返回减少后的分数
	int decScore(int score);
	virtual Action decide(const Round& round)=0;
	virtual bool goOn(const Game & game) = 0;

	//var used by round
	DynaArray<Action> actions;
	void roundStart(Round round);
	bool active(const Round &round);
};

