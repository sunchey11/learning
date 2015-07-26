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
	// ���ӷ���,�������Ӻ�ķ���
	int incScore(int score);
	// ���ٷ���,���ؼ��ٺ�ķ���
	int decScore(int score);
	virtual Action decide(const Round& round)=0;
	virtual bool goOn(const Game & game) = 0;

	//var used by round
	DynaArray<Action> actions;
	void roundStart(Round round);
	bool active(const Round &round);
};

