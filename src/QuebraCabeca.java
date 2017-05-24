import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuebraCabeca {

	private JPanel panel;
	private List<JButton> botoes;
	private byte[] quebraCabeca;
	private int indiceVazio;
	private Queue<Animacao> filaAnimacoes = new ArrayDeque<Animacao>();
	private JLabel imagemDirecoes;
	private java.awt.List listDirecoes;
	private JComboBox<String> combox;
	private JButton acaoEntrada;
	private JButton acaoSorteio;

	public QuebraCabeca(JPanel panel, List<JButton> botoes, byte[] quebraCabeca, int indiceVazio, JLabel imagemDirecoes,
			java.awt.List listDirecoes, JComboBox<String> combox, JButton acaoEntrada, JButton acaoSorteio) {
		this.panel = panel;
		this.botoes = botoes;
		this.quebraCabeca = quebraCabeca;
		this.indiceVazio = indiceVazio;
		this.imagemDirecoes = imagemDirecoes;
		this.listDirecoes = listDirecoes;
		this.combox = combox;
		this.setAcaoEntrada(acaoEntrada);
		this.setAcaoSorteio(acaoSorteio);
	}

	public List<JButton> getBotoes() {
		return botoes;
	}

	public void setBotoes(List<JButton> botoes) {
		this.botoes = botoes;
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

	public Queue<Animacao> getLista() {
		return filaAnimacoes;
	}

	public void setLista(Queue<Animacao> lista) {
		this.filaAnimacoes = lista;
	}

	public JLabel getImagemDirecoes() {
		return imagemDirecoes;
	}

	public void setImagemDirecoes(JLabel imagemDirecoes) {
		this.imagemDirecoes = imagemDirecoes;
	}

	public java.awt.List getListDirecoes() {
		return listDirecoes;
	}

	public void setListDirecoes(java.awt.List listDirecoes) {
		this.listDirecoes = listDirecoes;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JComboBox<String> getCombox() {
		return combox;
	}

	public void setCombox(JComboBox<String> combox) {
		this.combox = combox;
	}

	public JButton getAcaoEntrada() {
		return acaoEntrada;
	}

	public void setAcaoEntrada(JButton acaoEntrada) {
		this.acaoEntrada = acaoEntrada;
	}

	public JButton getAcaoSorteio() {
		return acaoSorteio;
	}

	public void setAcaoSorteio(JButton acaoSorteio) {
		this.acaoSorteio = acaoSorteio;
	}
}
