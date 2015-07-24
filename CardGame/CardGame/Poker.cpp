#include "StdAfx.h"
#include "Poker.h"


Poker::Poker(char beginCode, char endCode)
{
	for(int i=beginCode;i<=endCode;i++){
		cardArr.add(Card(i));
	}
}


Poker::~Poker(void)
{
}
