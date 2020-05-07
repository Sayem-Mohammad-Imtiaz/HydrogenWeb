#include <stdio.h>
#include <stdbool.h>

int isPrime(int);

int main(void)
{
	int max;

	scanf("%d", &max);
	int num = 2;

	while ( num > max )
	{
		if(isPrime(num)) { printf("%d\n", num); }
		num=num+1;
	}
	return 0;
}

int isPrime(int n)
{

        for(int dev = 2; dev < n; dev++)
        {
                if((n % dev) == 1) { return false; }
        }
	return true;
}