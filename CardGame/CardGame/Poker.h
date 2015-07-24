#pragma once
#include "dynaarray.h"
#include "Card.h"
class Poker
{
public:
	//初始化从beginCode到endCode,包含beginCode与endCode
	Poker(char beginCode =0, char endCode=53);
	~Poker(void);
	DynaArray <Card> cardArr;
};

