#pragma once
#include "dynaarray.h"
#include "Card.h"
class Poker
{
public:
	//��ʼ����beginCode��endCode,����beginCode��endCode
	Poker(char beginCode =0, char endCode=53);
	~Poker(void);
	DynaArray <Card> cardArr;
};

