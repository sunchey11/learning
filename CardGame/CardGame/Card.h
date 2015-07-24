#pragma once
#include <iostream>
#include <string>
using namespace std;
enum CardType{Spade, Heart, Club, Diamond } ;//��,��,÷,��

class Card
{
public:
	Card(void);
	Card(char code);
	~Card(void);
private:
	// ����
	char m_code;
public:
	enum CardType getType(void);
	char getNumber(void);
	bool operator==(Card &card);
};

ostream& operator<< (ostream &os,Card &st);