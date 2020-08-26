#include <stdlib.h>
#include <stdio.h>
#define tam 30 //número máximo de processos

int processos[tam]; //Fila de processos
int numProcesso;
int quantum; //quantidade de tempo disponível pra cada processso

void time()
{
    int tmp;
    int i;
    for (i = 1; i <= numProcesso; i++)
    {
        printf("\nDigite o tempo de cada processo %d:", i);
        scanf("%d", tmp);
        processos[i] = tmp;
    }
}

void fila()
{
    int countFila = numProcesso; //contagem de processos que estão na fila
    int frenteFila = 1;
    while (countFila != 0)
    {
        while (processos[frenteFila] <= 0)
        { //Tira processos que não tem time da primeira posição
            frenteFila++;
            if (frenteFila >= numProcesso) //Faz a rotação
                frenteFila = 0;
        }
        printf("\n\nO processo %d vai para o cpu com %d tempo", frenteFila, processos[frenteFila]);
        printf("\nProcessa ate %d time", quantum);
        processos[frenteFila] = processos[frenteFila] - quantum;
        if (processos[frenteFila] <= 0)
        {
            printf("\nE sai da fila.\n");
            countFila--;
        }
        else
        {
            printf("\nE vai pro final da fila com %d tempo restantes.\n", processos[frenteFila]);
        }
        frenteFila++;
        if (frenteFila > numProcesso)
            frenteFila = 0;
        system("PAUSE");
    }
}

int main()
{
    printf("\nDigite o numero maximo do quantum: ");
    scanf("%d", &quantum);
    printf("\nDigite o numero de processos na fila: ");
    scanf("%d", &numProcesso);
    time();
    fila();
    printf("\n\n::::::::: Fim ::::::::::\n");
    getchar();
}