#include "stdafx.h"
#include "Game.h"


Game::Game()
{
}


Game::~Game()
{
}


int Game::oneGame()
{
	DynaArray <Player *> currentPlayer;
	while (true){

		//看有哪些人要参加
		for (int i = 0; i < allPlayers.size(); i++)
		{
			if (allPlayers.get(i)->goOn(*this)){
				currentPlayer.add(allPlayers.get(i));
			}
		}
		if (currentPlayer.size()>0){
			Round r(currentPlayer);
			r.oneRound();
		}
		else{ 
			break; 
		}
	}
	return 0;
}


void Game::printPlayerInfo()
{
}


DynaArray <Player *> Game::getPlayerByScore()
{
	return 0;
}


Player * Game::getWinner()
{
	return 0;
}
