#include <stdio.h>
#include <stdbool.h>

int isPrime(int);

int main(void)
{
	int max;

	scanf("%d", &max);
	int num = 0;

	while ( num < max )
	{
		if(isPrime(num)) { printf("%d\n", num); }
		num=num+3;
	}
	return 0;
}

int isPrime(int n)
{
		int dev;
        for(dev = 0; dev < n; dev++)
        {
                if((n % dev) == 0) 
				{ return true; }
        }
	return true;
}