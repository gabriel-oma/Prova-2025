import java.util.Scanner;

class Conta {
    int id;
    String cliente;
    double saldo;
    double limite;
}

class Operacao {
    int id;
    int idConta;
    char tipo;
    double valor;
}

public class Prova2025_2 {
    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;
    static Scanner sc = new Scanner(System.in);

    public static int buscaId(Conta[] v, int tam, int x) {
        for (int i = 0; i < tam; i++) {
            if (v[i].id == x) {
                return i;
            }
        }
        return -1;
    }

    // Questao 1
    public static int cadastrarConta(Conta[] v, int tam) {
        if (tam >= TAM_CONTAS || tam >= v.length) {
            System.out.println("Vetor de contas cheio.");
            return tam;
        }

        Conta nova = new Conta();
        System.out.print("ID: ");
        nova.id = sc.nextInt();
        sc.nextLine();
        System.out.print("Cliente: ");
        nova.cliente = sc.nextLine();
        System.out.print("Saldo: ");
        nova.saldo = sc.nextDouble();
        System.out.print("Limite: ");
        nova.limite = sc.nextDouble();

        if (buscaId(v, tam, nova.id) != -1) {
            System.out.println("ID ja cadastrado.");
            return tam;
        }
        if (nova.saldo < 0 || nova.limite < 0) {
            System.out.println("Saldo e limite devem ser maiores ou iguais a zero.");
            return tam;
        }

        v[tam] = nova;
        return tam + 1;
    }

    // Questao 2
    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0;
        int fim = tam - 1;

        while (inicio <= fim) {
            int meio = inicio + (fim - inicio) / 2;
            int comparacao = v[meio].cliente.compareToIgnoreCase(x);

            if (comparacao == 0) {
                return meio;
            }
            if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    // Questao 3
    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i++) {
            Conta chave = v[i];
            int j = i - 1;

            while (j >= 0 &&
                   v[j].cliente.compareToIgnoreCase(chave.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }
        return tam;
    }

    // Questao 4
    public static int filtraOperacoes(Operacao[] vOp, int tamOp,
                                     Operacao[] vOpFiltrado, int x) {
        int quantidade = 0;
        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[quantidade] = vOp[i];
                quantidade++;
            }
        }
        return quantidade;
    }

    // Questao 5
    public static void extrato(Conta[] vContas, int tamConta,
                               Operacao[] vOp, int tamOp) {
        System.out.print("ID da conta: ");
        int idConta = sc.nextInt();
        int posicao = buscaId(vContas, tamConta, idConta);

        if (posicao == -1) {
            System.out.println("Conta nao encontrada.");
            return;
        }

        Conta conta = vContas[posicao];
        Operacao[] filtradas = new Operacao[TAM_OP];
        int quantidade = filtraOperacoes(vOp, tamOp, filtradas, idConta);
        int inicio = quantidade - 5;
        if (inicio < 0) {
            inicio = 0;
        }

        double saldo = 0;
        for (int i = 0; i < inicio; i++) {
            saldo += filtradas[i].valor;
        }

        System.out.printf("CONTA N. %03d%n", conta.id);
        System.out.println("CLIENTE: " + conta.cliente);
        System.out.println("ID | OPERACAO | VALOR | SALDO");
        System.out.println("Saldo Anterior: " + formataValor(saldo));

        for (int i = inicio; i < quantidade; i++) {
            Operacao op = filtradas[i];
            String nome = "";

            if (op.tipo == 'D') {
                nome = "Deposito";
            } else if (op.tipo == 'S') {
                nome = "Saque";
            } else if (op.tipo == 'T') {
                nome = "Transferencia";
            }

            saldo += op.valor;
            System.out.printf("%04d | %s | %s | %s%n",
                              op.id, nome,
                              formataValor(op.valor), formataValor(saldo));
        }
        System.out.println("Saldo Atual: " + formataValor(saldo));
    }

    public static String formataValor(double valor) {
        char indicador = 'C';
        if (valor < 0) {
            indicador = 'D';
        }
        return String.format("%.2f%c", Math.abs(valor), indicador);
    }

    public static void main(String[] args) {
        Conta[] contas = new Conta[TAM_CONTAS];
        Conta conta = new Conta();
        conta.id = 1;
        conta.cliente = "Maria Jose Santos";
        conta.saldo = 424.99;
        conta.limite = 1000;
        contas[0] = conta;

        Operacao[] operacoes = new Operacao[TAM_OP];
        int[] ids = {1, 25, 37, 83, 95, 112};
        char[] tipos = {'D', 'D', 'D', 'T', 'S', 'T'};
        double[] valores = {2437.55, 1000, 237.44, -4000, -250, 1000};

        for (int i = 0; i < valores.length; i++) {
            Operacao op = new Operacao();
            op.id = ids[i];
            op.idConta = conta.id;
            op.tipo = tipos[i];
            op.valor = valores[i];
            operacoes[i] = op;
        }

        insertionSortCliente(contas, 1);
        System.out.println("Posicao de Maria Jose Santos: " +
                           buscaBinariaCliente(contas, 1, "Maria Jose Santos"));
        extrato(contas, 1, operacoes, valores.length);
    }
}
