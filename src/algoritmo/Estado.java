package algoritmo;

import java.util.Arrays;

public class Estado {
	public static final byte[] META = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
	private byte[] quebraCabeca;
	private int indiceVazio;
	private int numeroMovimentos;
	private int valorHeuristica;
	private Estado estadoAnterior;

	public int prioridade() {
		return numeroMovimentos + valorHeuristica;
	}

	public Estado(byte[] inicial) {
		quebraCabeca = inicial;
		numeroMovimentos = 0;
		valorHeuristica = new Heuristica().calculo(inicial);
		indiceVazio = index(inicial, 0);
		estadoAnterior = null;
	}

	public Estado(Estado estadoAnterior, int indiceVazio) {
		quebraCabeca = Arrays.copyOf(estadoAnterior.quebraCabeca, estadoAnterior.quebraCabeca.length);
		quebraCabeca[estadoAnterior.indiceVazio] = quebraCabeca[indiceVazio];
		quebraCabeca[indiceVazio] = 0;
		this.indiceVazio = indiceVazio;
		numeroMovimentos = estadoAnterior.numeroMovimentos + 1;
		valorHeuristica = new Heuristica().calculo(quebraCabeca);
		this.estadoAnterior = estadoAnterior;
	}

	public int index(byte[] quebraCabeca, int val) {
		for (int i = 0; i < quebraCabeca.length; i++)
			if (quebraCabeca[i] == val)
				return i;
		return -1;
	}

	public boolean verificaMeta() {
		return Arrays.equals(quebraCabeca, META);
	}

	public Estado moverBaixo() {
		if (indiceVazio > 2)
			return new Estado(this, indiceVazio - 3);
		return null;
	}

	public Estado moverCima() {
		if (indiceVazio < 6)
			return new Estado(this, indiceVazio + 3);
		return null;
	}

	public Estado moverDireita() {
		if (indiceVazio % 3 > 0)
			return new Estado(this, indiceVazio - 1);
		return null;
	}

	public Estado moverEsquerda() {
		if (indiceVazio % 3 < 2)
			return new Estado(this, indiceVazio + 1);
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Estado) {
			Estado outro = (Estado) obj;
			return Arrays.equals(quebraCabeca, outro.quebraCabeca);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(quebraCabeca);
	}

	public byte[] getQuebraCabeca() {
		return quebraCabeca;
	}

	public void setQuebraCabeca(byte[] quebraCabeca) {
		this.quebraCabeca = quebraCabeca;
	}

	public int getIndiceVazio() {
		return indiceVazio;
	}

	public void setIndiceVazio(int indiceVazio) {
		this.indiceVazio = indiceVazio;
	}

	public int getNumeroMovimentos() {
		return numeroMovimentos;
	}

	public void setNumeroMovimentos(int numeroMovimentos) {
		this.numeroMovimentos = numeroMovimentos;
	}

	public int getValorHeuristica() {
		return valorHeuristica;
	}

	public void setValorHeuristica(int valorHeuristica) {
		this.valorHeuristica = valorHeuristica;
	}

	public Estado getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(Estado estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public static byte[] getMeta() {
		return META;
	}
	
	
}
