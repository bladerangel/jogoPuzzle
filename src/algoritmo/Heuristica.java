package algoritmo;

public class Heuristica {

	public int distanciaManhattan(int a, int b) {
		return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
	}

	public int calculo(byte[] quebraCabeca) {
		int somatorio = 0;
		for (int i = 0; i < quebraCabeca.length; i++) {
			if (quebraCabeca[i] - 1 != -1) {
				somatorio += distanciaManhattan(quebraCabeca[i] - 1, i);
			}
		}
		return somatorio;
	}
}
