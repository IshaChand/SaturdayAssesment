#include <iostream>
using namespace std;

bool isPrime(int n)
{
    if(n<=1)
    {
        return false;
    }
    for (int i=2;i<n/2;i++)
    {
        if (n%i==0)
        {
            return false;
        }
    }
    return true;
}

int field3potato(int x, int y)
{
    int p=1;
    while(!isPrime(x+y+p))
    {
        p++;
    }
    return p;
}


int main() {

    int x,y,z,t,s2;
    cout<<"enter the no of testcases: ";
    cin>>t;
    while(t>0)
    {
        cout<<"enter the no of potatoes harvested from field1: ";
        cin>>x;
        cout<<"enter the no of potatoes harvested from field2: ";
        cin>>y;

        z=field3potato(x,y);
        cout<<"min no of potato: "<<z<<"\n";
        t--;
    }
    return 0;
}
