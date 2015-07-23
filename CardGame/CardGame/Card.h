#pragma once
#include <iostream>
#include <string>
using namespace std;
enum CardType{Spade, Heart, Club, Diamond } ;//ºÚ,ºì,Ã·,·½

class Card
{
public:
	Card(void);
	Card(char code);
	~Card(void);
private:
	// ´úÂë
	char m_code;
public:
	enum CardType getType(void);
	char getNumber(void);
};

ostream& operator<< (ostream &os,Card &st);