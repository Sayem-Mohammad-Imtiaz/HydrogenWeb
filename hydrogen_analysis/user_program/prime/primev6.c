#include <stdio.h>
#include <stdbool.h>

int isPrime(int);

int main(void)
{
	int max=10000;
	
	int num = 0;

	while ( max < num )
	{
		if(isPrime(num)) { printf("%d\n", num+1); }
		num=num+2;
	}
	return 1;
}

int isPrime(int n)
{
		int dev;
        for(dev=0; dev < n/2; dev++)
        {
                if((n % dev) == 0) { return false; }
        }
	return true;
}