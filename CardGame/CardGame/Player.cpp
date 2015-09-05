#include "StdAfx.h"
#include "Player.h"

#include "Round.h"

Player::Player(void)
{
}


Player::~Player(void)
{
}


// 增加分数,返回增加后的分数
int Player::incScore(int score)
{
	return 0;
}


// 减少分数,返回减少后的分数
int Player::decScore(int score)
{
	return 0;
}



void Player::roundStart(Round round)
{
	actions.clearAll();
}


bool Player::active(const Round& round)
{
	if (actions.size() == 0){
		return true;
	}
	Action * lastAction = &(actions.get(actions.size() - 1));
	if (lastAction->type == giveup || lastAction->type == failure){
		return false;
	}
	return true;
}
