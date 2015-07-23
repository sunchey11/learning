// CardGame.cpp : �������̨Ӧ�ó������ڵ㡣
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include "Card.h"
#include "DynaArray.h"
using std::cout;
using std::cin;
using std::endl;


void testCard_1(){
	cout<<"testCard_1"<<endl;
	Card c(70);
	cout<<"type:"<<c.getType()<<",num:"<<(int)c.getNumber()<<endl;

}
void testDynaArray_2(){
	cout<<"testDynaArray_2"<<endl;

	//������ͨ���캯��
	DynaArray <int> arr1;
	cout<<arr1.max<<endl;
	arr1.add(5);

	//������ͨ���캯��
	DynaArray <int> arr2(10);
	cout<<arr2.max<<endl;
	arr2.add(15);

	//���Ը��ƹ��캯��
	DynaArray <int> arr3(arr1);
	cout<<arr3.max<<endl;

	//���Ը�ֵ�����
	arr3 = arr2;
	cout<<arr3.max<<endl;

	//�������
	try
	{
		for(int i=0;i<10;i++){

			arr3.add(100+i);

		}
	}
	catch(IndexOutOfBoundsException e)
	{
		cout<<"exxxxxxxxxxxxxxxxxxxxxxxx:"<<&e<<endl;
	}

	cout<<"okkk"<<endl;
	arr3.debug();


}
void gameStart(){
	cout<<"gameStart"<<endl;

	testDynaArray_2();
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
			cout<<"��������ȷ������"<<endl;
		}else if(intChoice==1)
		{
			testCard_1();
		}else if(intChoice==2)
		{
			testDynaArray_2();
		}


	}


	cin.getline(choice,max,'\n');
	return 0;
}

