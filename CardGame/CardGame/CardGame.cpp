// CardGame.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include "Card.h"
using std::cout;
using std::cin;
using std::endl;

void gameStart(){
	cout<<"gameStart"<<endl;
}
void testCard_1(){
	cout<<"testCard_1"<<endl;
	Card c(70);
	cout<<"type:"<<c.getType()<<",num:"<<(int)c.getNumber()<<endl;

}

int _tmain(int argc, _TCHAR* argv[])
{
	const int max=10;
	char choice[max];
	cin.getline(choice,max,'\n');
	if(strlen(choice)==0)
	{
		gameStart();
	}else
	{
		int intChoice;
		int r = sscanf(choice,"%d",&intChoice);
		if(r==0){
			cout<<"请输入正确的数字"<<endl;
		}else if(intChoice==1)
		{
			testCard_1();
		}

		
	}


	cin.getline(choice,max,'\n');
	return 0;
}

