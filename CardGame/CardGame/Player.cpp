#include "StdAfx.h"
#include "Player.h"

#include "Round.h"

Player::Player(void)
{
}


Player::~Player(void)
{
}


// ���ӷ���,�������Ӻ�ķ���
int Player::incScore(int score)
{
	return 0;
}


// ���ٷ���,���ؼ��ٺ�ķ���
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
