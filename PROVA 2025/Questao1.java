public static boolean existe(int[] v, int tam, int valor) {
    for (int i = 0; i < tam; i++) {
        if (v[i] == valor) {
            return true;
        }
    }
    return false;
}

public static int uniao(int[] a, int tamA, int[] b, int tamB, int[] u) {
    int tamU = 0;

    for (int i = 0; i < tamA; i++) {
        if (!existe(u, tamU, a[i])) {
            u[tamU++] = a[i];
        }
    }

    for (int i = 0; i < tamB; i++) {
        if (!existe(u, tamU, b[i])) {
            u[tamU++] = b[i];
        }
    }

    return tamU;
}