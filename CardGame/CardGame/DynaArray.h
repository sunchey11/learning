#pragma once

class IndexOutOfBoundsException
{
};

template <class T>class DynaArray
{
public:
	//只有静态常量整型数据成员才可以在类中初始化
	//const static int MAX=100; //这个可以
	static int MAX;//静态成员必须在外面初始化
	int max;
	DynaArray(int max=MAX);
	DynaArray<T>(const DynaArray<T>& aMess);
	~DynaArray(void);

	DynaArray<T> & operator =(const DynaArray<T> & arr);
	int add(const T& ele);

	//返回值声明为引用，此函数就可以作为lvalue了，不需要再写一个set方法了
	//如果不希望作为lvalue使用，可以增加const关键字
	T& get(int index);
	void remove(int index);
	void debug(){
		for(int i=0;i<currentLength;i++){
			cout<<i<<"          "<<(this->get(i))<<endl;
		}
	}
private:

	int currentLength;
	T* datas;


public:

	int size(void)
	{
		return 0;
	}
};

template <class T> int DynaArray<T>::MAX=100;

template <class T>DynaArray<T>::DynaArray(int max)
	: max(max),currentLength(0)
{
	datas = new T[max];
}

/**
因为有动态内存分配，所以要实现复制构造函数
*/
template <class T> DynaArray<T>::DynaArray(const DynaArray<T>& arr):currentLength(0)
{
	cout<<"Copy onstructor called"<<endl;

	max=arr.max;
	datas = new T[arr.max];
	for(int i=0;i<arr.currentLength;i++){
		//T value = arr.get(i); //这行不能编译,因为arr为const,arr.get(i)为lvalue,编译器认为可以修改arr
		T value = arr.datas[i];
		this->add(value);
	}
}
//为什么返回引用,有a=b=c这种情况和(a=b)=c这种情况
//如果不返回引用,可以编译,但是不能正确的赋值
template <class T> DynaArray<T> & DynaArray<T>::operator=(const DynaArray<T>& arr)
{
	cout<<"Assignment operator called"<<endl;
	if(this == &arr)               // Check addresses, if equal
		return *this;                  // return the 1st operand

	// Release memory for 1st operand
	delete[] datas;
	max=arr.max;
	currentLength=0;
	datas = new T[arr.max];

	for(int i=0;i<arr.currentLength;i++){

		//T value = arr.get(i); //这行不能编译,因为arr为const,arr.get(i)为lvalue,编译器认为可以修改arr
		T value = arr.datas[i];
		add(value);
	}
	

	// Return a reference to 1st operand
	return *this;
}

template <class T>DynaArray<T>::~DynaArray(void)
{
	delete[] datas;
}



template <class T>	int DynaArray<T>::add(const T& ele)
{
	if(currentLength>=max){
		throw IndexOutOfBoundsException();
	}
	datas[currentLength] = ele;
	currentLength++;

	return currentLength;
}
template <class T> T& DynaArray<T>:: get(int index)
{
	if(index>currentLength){
		throw IndexOutOfBoundsException();
	}
	return datas[index];
}


template <class T>	void DynaArray<T>:: remove(int index)
{
	if(index>currentLength){
		throw IndexOutOfBoundsException();
	}
	for(int i=index;i<currentLength;i++){
		datas[i]=datas[i+1];
	}
	currentLength--;
	return;
}