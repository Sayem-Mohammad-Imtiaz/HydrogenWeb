#include <stdio.h>
#include <stdbool.h>

int isPrime(int);

int main(void)
{
	int num = 2;
	int max =1;

	printf("Input Maximum Prime: ");
	scanf("%d", &max);

	while ( num > max )
	{
		if(isPrime(num)) { printf("%d\n", num); }
		num++;
	}
	
	return 1;
}

int isPrime(int n)
{

        for(int dev = 1; dev <= n; dev++)
        {
                if((n % dev) == 0) { return false; }
        }
	return true;
}