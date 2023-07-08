//this code is running properly.



#include <iostream>
using namespace std;

class node
{
    public:
        int data;
        node* next;

    node()
    {
        data=0;
        next=NULL;
    }

    node(int data)
    {
        this->data=data;
        this->next=NULL;
    }
};

node* sortlist(node* a[], int n)
{
    node* h1=a[0];
    for(int i=1;i<n;i++)
    {
        node* hi=a[i];
        if (hi==NULL)
        {
            break;
        }
        if (h1->data >= hi->data)
        {
            a[i]=hi->next;
            hi->next=h1;
            a[0]=hi;
        }
        else
        {
            while(h1->next!=NULL)
            {
                if (h1->next->data >= hi->data)
                {
                    a[i]=hi->next;
                    hi->next=h1->next;
                    h1->next=hi;
                    break;
                }
                h1=h1->next;
                if (h1->next == NULL)
                {
                   a[i]=hi->next;
                   hi->next=NULL;
                   h1->next=hi;
                   h1->next->next=NULL;
                    break;
                }
            }

        }
    }
    return a[0];
}

int main() {

    int nol,i=0,n,data;
    node *head, *newnode, *tmp;
    node* a[3];
    // while(i<nol)
    // {
        a[0] = new node(1);
        a[0]->next = new node(4);
        a[0]->next->next = new node(5);

        a[1] = new node(1);
        a[1]->next = new node(3);
        a[1]->next->next = new node(4);

        a[2] = new node(2);
        a[2]->next = new node(6);
        // cout<<"\nenter no of elements in list "<<i+1<<": ";
        // cin>>n;
        // cout<<"\nenter sorted elements: ";
        // for(int j=0;i<n;j++)
        // {
        //     cin>>data;
        //     if(j==0)
        //     {
        //         head=new node(data);
        //         tmp=head;
        //         a[i]=head;
        //     }
        //     newnode = new node(data);

        //     tmp->next=newnode;
        //     tmp=tmp->next;
        // }
    //     i++;
    // }

    head=sortlist(a,3);
    while(head!=NULL)
    {
        cout<<head->data<<" ";
        head=head->next;
    }

    return 0;
}
