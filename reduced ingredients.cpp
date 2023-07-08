#include <iostream>
using namespace std;

int min(int a, int b)
{
    if(a<b)
    {
        return a;
    }
    else
    {
        return b;
    }
}

int hcf(int a,int b)
{
    if(a==b)
    {
        return a;
    }
    int m=min(a,b);
    for(int i=m;i>0;i--)
    {
        if(a%i==0 && b%i==0)
        {
            return i;
        }
    }
    return 1;
}

int reduced(int k[], int ing)
{
    int r=k[0];
    for(int i=1;i<ing;i++)
    {
        r=hcf(k[i],r);
    }
    return r;
}

int main() {

    int t,ing,i,p;
    cout<<"enter the no of testcases: ";
    cin>>t;
    while(t>0)
    {
        cout<<"\nenter the no of ingredients: ";
        cin>>ing;
        int k[ing];
        for(i=0;i<ing;i++)
        {
            cout<<"\nenter the quantity of "<<i+1<<" ingredient: ";
            cin>>k[i];
        }
        cout<<"\nreduced quantity of ingredients will be:\n";
        p=reduced(k,ing);
        for(i=0;i<ing;i++)
        {
            cout<<k[i]/p<<" ";
        }
        t--;
    }

    return 0;
}
