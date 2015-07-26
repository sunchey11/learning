#pragma once

class IndexOutOfBoundsException
{
};

template <class T>class DynaArray
{
public:
	//ֻ�о�̬�����������ݳ�Ա�ſ��������г�ʼ��
	//const static int MAX=100; //�������
	static int MAX;//��̬��Ա�����������ʼ��
	int max;
	DynaArray(int max=MAX);
	DynaArray<T>(const DynaArray<T>& aMess);
	~DynaArray(void);

	DynaArray<T> & operator =(const DynaArray<T> & arr);
	int add(const T& ele);

	//����ֵ����Ϊ���ã��˺����Ϳ�����Ϊlvalue�ˣ�����Ҫ��дһ��set������
	//�����ϣ����Ϊlvalueʹ�ã���������const�ؼ���
	T& get(int index);
	void removeAt(int index);
	void remove(T v);
	void swap(T &a,T &b);
	void random();
	void debug(){
		for(int i=0;i<currentLength;i++){
			T v = this->get(i);
			
			cout<<i<<"          "<<v<<endl;
		}
	}
private:

	int currentLength;
	T* datas;


public:

	int size(void)
	{
		return currentLength;
	}

	void clearAll()
	{
		currentLength = 0;
	}
};

template <class T> int DynaArray<T>::MAX=100;

template <class T>DynaArray<T>::DynaArray(int max)
	: max(max),currentLength(0)
{
	datas = new T[max];
}

/**
��Ϊ�ж�̬�ڴ���䣬����Ҫʵ�ָ��ƹ��캯��
*/
template <class T> DynaArray<T>::DynaArray(const DynaArray<T>& arr):currentLength(0)
{
	cout<<"Copy onstructor called"<<endl;

	max=arr.max;
	datas = new T[arr.max];
	for(int i=0;i<arr.currentLength;i++){
		//T value = arr.get(i); //���в��ܱ���,��ΪarrΪconst,arr.get(i)Ϊlvalue,��������Ϊ�����޸�arr
		T value = arr.datas[i];
		this->add(value);
	}
}
//Ϊʲô��������,��a=b=c���������(a=b)=c�������
//�������������,���Ա���,���ǲ�����ȷ�ĸ�ֵ
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

		//T value = arr.get(i); //���в��ܱ���,��ΪarrΪconst,arr.get(i)Ϊlvalue,��������Ϊ�����޸�arr
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
template <class T>	void DynaArray<T>::swap(T &a,T &b)
{
	T temp = a;
	a=b;
	b=temp;
}

template <class T>	void DynaArray<T>:: random(){
		
	for(int i=0;i<currentLength;i++)
	{
		/*����i��n֮���һ������*/
		int index=rand()%(currentLength-i)+i;
		swap(get(i),get(index));
	}
}
template <class T>	void DynaArray<T>:: remove(T v){
	for(int i=0;i<currentLength;i++){
		if(get(i)==v){
			removeAt(i);
			break;
		}
	}
}
template <class T>	void DynaArray<T>:: removeAt(int index)
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