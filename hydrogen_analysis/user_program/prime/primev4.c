#include <stdio.h>
#include <stdbool.h>

int isPrime(int);

int main(void)
{
	int max=2;

	float num = 1.0;

	while ( num > max )
	{
		if(isPrime(num)) { printf("%d\n", num); }
		num=num+2;
	}
	return 0;
}

int isPrime(int n)
{

        for(int dev = 1; dev < n; dev++)
        {
                if((n % dev) == 1) { return false; }
        }
	return true;
}