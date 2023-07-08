#include <iostream>
using namespace std;

bool check132pattern(int a[],int n)
{
    int sleft=a[0];
    int mright=a[n-1];

    for(int i=1;i<n;i++)
    {
        for(int j=i-1;j<i;j++)
        {
            if(a[j]<sleft)
            {
                sleft=a[j];
            }
        }
        for(int k=i+1;k<n-2;k++)
        {
            if(a[k]>mright)
            {
                mright=a[k];
            }
        }
        if(sleft<a[i]&&a[i]>mright)
        {
            return true;
        }
        else
        {
            continue;
        }
    }

    return false;
}

int main() {

    int n, i;
    cout<<"enter the no of elements in array: ";
    cin>>n;
    int a[n];
    cout<<"enter elements: ";
    for(i=0;i<n;i++)
    {
        cin>>a[i];
    }
    if(check132pattern(a,n))
    {
        cout<<"there is 132 pattern";
    }
    else
    {
        cout<<"there is no pattern";
    }

    return 0;
}
