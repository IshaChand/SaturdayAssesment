#include <iostream>
using namespace std;
int main() {

    int n,k,i;
    cout<<"enter the no of elements= ";
    cin>>n;
    int a[n];
    cout<<"enter the elements-\n";
    for(i=0;i<n;i++)
    {
        cin>>a[i];
    }
    cout<<"enter the size of sliding window: ";
    cin>>k;

    for(i=0;i<n;i++)
    {
        int max=a[i];
        for(int j=i;j<k+i;j++)
        {
            if(a[j]>max)
            {
                max=a[j];
            }
        }
        if(k+i<=n)
        {
            cout<<max<<" ";
        }

    }

    return 0;
}
