#pragma once
#include "Player.h"
#include "DynaArray.h"
#include "Round.h"
class Game
{
public:
	Game();
	~Game();
	//������������0,ĳ������˳�����1
	int oneGame();
	void printPlayerInfo();
	DynaArray <Player *> getPlayerByScore();
	Player * getWinner();
	// ���е����,��ʹ�Ѿ�������,Ҳ���ڴ�������
	DynaArray <Player *> allPlayers;
};

