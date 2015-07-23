#include "StdAfx.h"
#include "Card.h"

Card::Card(void)
	: m_code(0)
{
}
Card::Card(char code)
	: m_code(code)
{
}

Card::~Card(void)
{
}


enum CardType Card::getType(void)
{
	int r=m_code/13;
	return CardType(r);
}


char Card::getNumber(void)
{
	return (m_code%13)+1;
}
ostream& operator<< (ostream &os,Card &st)
{
	os <<"type:"<< st.getType()<<",number:"<<st.getNumber()<<endl;
	return os;
}