package algoritmo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Solucao {

	private Comparator<Estado> comparadorEstado = new Comparator<Estado>() {
		@Override
		public int compare(Estado a, Estado b) {
			return a.prioridade() - b.prioridade();
		}
	};

	private PriorityQueue<Estado> fila = new PriorityQueue<Estado>(comparadorEstado);
	private HashSet<Estado> estadosSucessores = new HashSet<Estado>();

	public void adicionarSucessor(Estado successor) {
		if (successor != null && !estadosSucessores.contains(successor)) {
			fila.add(successor);
		}

	}

	public Estado solve(byte[] inicial) {
		fila.add(new Estado(inicial));
		while (!fila.isEmpty()) {
			Estado estado = fila.poll();
			if (estado.verificaMeta()) {
				return estado;
			}
			estadosSucessores.add(estado);
			adicionarSucessor(estado.moverBaixo());
			adicionarSucessor(estado.moverCima());
			adicionarSucessor(estado.moverEsquerda());
			adicionarSucessor(estado.moverDireita());
		}
		return null;
	}
}
