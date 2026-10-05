public static void rotacionar(int[] v, int tam, int k) {
    if (tam <= 1) return; 

    k = k % tam;
    
    if (k < 0) {
        k = k + tam;
    }

    reverter(v, 0, k - 1);       
    reverter(v, k, tam - 1);     
    reverter(v, 0, tam - 1);     
}

public static void reverter(int[] v, int inicio, int fim) {
    while (inicio < fim) {
        int temp = v[inicio];
        v[inicio] = v[fim];
        v[fim] = temp;
        inicio++;
        fim--;
    }
}
