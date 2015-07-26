#include "StdAfx.h"
#include "Round.h"


Round::Round(const DynaArray <Player *>& currentPlayer) :m_currentPlayer(currentPlayer)
, currentScore(0)
, totalScore(0)
{
}


Round::~Round(void)
{
	cout << "destructor of round called" << endl;
}


void Round::oneRound()
{
	int circel = 1;
	while (activePlayerCount() > 1){
		cout << "circel " << circel << " start:";
		for (int i = 0; i < m_currentPlayer.size(); i++){
			if (m_currentPlayer.get(i)->active(*this)){
				Player *p = m_currentPlayer.get(i);
				Action a = p->decide(*this);
				p->actions.add(a);
				doAction(p,a);
				if (activePlayerCount() == 1){
					break;
				}
			}
		}
		cout << "circel " << circel << " end;";
	}
}


void Round::doAction(Player * player,const Action &action)
{
	switch (action.type)
	{
	case giveup:{

		break;
	}
	case follow:{
		player->decScore(currentScore);
		totalScore += currentScore;
		
		break;
	}
	case promote:{
		currentScore += action.promoteScore;
		player->decScore(currentScore);
		break;
	}
	default:
		break;
	}
	player->actions.add(action);
}


int Round::activePlayerCount()
{
	return 0;
}
