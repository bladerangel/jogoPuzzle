import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import algoritmo.Estado;
import algoritmo.Solucao;

public class Janela extends JFrame implements ActionListener, KeyListener {

	private JPanel contentPane;
	private JPanel panel = new JPanel();

	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollList = new JScrollPane();

	private JLabel log = new JLabel();
	private JLabel imagemQuebraCabeca = new JLabel();
	private JLabel imagemDirecoes = new JLabel();

	private JComboBox<String> comboBox = new JComboBox<String>();
	private java.awt.List listDirecoes = new java.awt.List();

	private JTextArea textArea = new JTextArea();
	private JTextField entradaTexto;

	private JButton acaoSolucao = new JButton();
	private JButton acaoEntrada = new JButton("Entrada");
	private JButton acaoSorteio = new JButton("Embaralhar");

	public static Semaphore semaforo = new Semaphore(1);
	private List<JButton> botoes = new ArrayList<>();
	public static byte[] META = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
	private QuebraCabeca quebraCabeca;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Janela frame = new Janela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void imagemQuebraCabeca(String caminho) {
		try {
			BufferedImage imagem = ImageIO.read(new File(caminho));
			int largura = imagem.getWidth();
			int altura = imagem.getHeight();
			int contador = 1;
			Image imagens[] = new Image[META.length];
			for (int i = 0; i < META.length / 3; i++) {
				for (int j = 0; j < META.length / 3; j++) {
					Image subImagem = imagem.getSubimage(j * largura / 3, i * altura / 3, largura / 3, altura / 3)
							.getScaledInstance(quebraCabeca.getPanel().getWidth() / 3,
									quebraCabeca.getPanel().getHeight() / 3, Image.SCALE_AREA_AVERAGING);
					if (contador < META.length) {
						imagens[contador] = subImagem;
					}
					contador++;
				}
			}
			for (int i = 0; i < quebraCabeca.getQuebraCabeca().length; i++) {
				if (quebraCabeca.getQuebraCabeca()[i] != 0) {
					quebraCabeca.getBotoes().get(i).setIcon(new ImageIcon(imagens[quebraCabeca.getQuebraCabeca()[i]]));
				} else {
					quebraCabeca.getBotoes().get(i).setIcon(null);
				}
			}
			quebraCabeca.getPanel().repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printQuebraCabeca(byte[] quebraCabeca) {
		for (int i = 0; i < quebraCabeca.length; i += 3) {
			textArea.append(quebraCabeca[i] + " " + quebraCabeca[i + 1] + " " + quebraCabeca[i + 2] + "\n");
		}
	}

	public void printSolucao(Estado estadoAtual) {
		if (estadoAtual.getEstadoAnterior() != null) {
			printSolucao(estadoAtual.getEstadoAnterior());
			movimentoSolucao(estadoAtual);
		}
		textArea.append("F(n) = nº movimentos + heuristica = " + estadoAtual.getNumeroMovimentos() + "+"
				+ estadoAtual.getValorHeuristica() + "\n");
		printQuebraCabeca(estadoAtual.getQuebraCabeca());
	}

	public String preencherListDirecoes(Estado estadoAtual, String direcao) {
		return estadoAtual.getNumeroMovimentos() + " - " + direcao + " - " + " f(n) = "
				+ estadoAtual.getNumeroMovimentos() + " + " + estadoAtual.getValorHeuristica() + " = "
				+ estadoAtual.prioridade();
	}

	public void direcoes(String direcao) {
		switch (direcao) {
		case "cima":
			if (quebraCabeca.getIndiceVazio() < 6) {
				new AnimacaoEixoY(quebraCabeca, quebraCabeca.getIndiceVazio() + 3, false).execute();
			}
			break;
		case "baixo":
			if (quebraCabeca.getIndiceVazio() > 2) {
				new AnimacaoEixoY(quebraCabeca, quebraCabeca.getIndiceVazio() - 3, false).execute();
			}
			break;
		case "esquerda":
			if (quebraCabeca.getIndiceVazio() % 3 < 2) {
				new AnimacaoEixoX(quebraCabeca, quebraCabeca.getIndiceVazio() + 1, false).execute();
			}
			break;
		case "direita":
			if (quebraCabeca.getIndiceVazio() % 3 > 0) {
				new AnimacaoEixoX(quebraCabeca, quebraCabeca.getIndiceVazio() - 1, false).execute();
			}
			break;
		default:
			break;
		}
		mudarImagem();
	}

	public void acaoClique(ActionEvent e) {
		if (quebraCabeca.getBotoes().indexOf(e.getSource()) == quebraCabeca.getIndiceVazio() + 3) {
			direcoes("cima");
		}

		else if (quebraCabeca.getBotoes().indexOf(e.getSource()) == quebraCabeca.getIndiceVazio() - 3) {
			direcoes("baixo");
		}

		else if (quebraCabeca.getBotoes().indexOf(e.getSource()) == quebraCabeca.getIndiceVazio() + 1) {
			direcoes("esquerda");
		}

		else if (quebraCabeca.getBotoes().indexOf(e.getSource()) == quebraCabeca.getIndiceVazio() - 1) {
			direcoes("direita");
		}
	}

	public void acaoTeclado(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			direcoes("cima");
			break;
		case KeyEvent.VK_DOWN:
			direcoes("baixo");
			break;
		case KeyEvent.VK_LEFT:
			direcoes("esquerda");
			break;
		case KeyEvent.VK_RIGHT:
			direcoes("direita");
			break;
		default:
			break;
		}
	}

	public void nomesImagens(File folder) {
		for (File fileEntry : folder.listFiles()) {
			quebraCabeca.getCombox().addItem(fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.')));
		}
	}

	public int indexOf(byte[] quebraCabeca, int valor) {
		for (int i = 0; i < quebraCabeca.length; i++)
			if (quebraCabeca[i] == valor)
				return i;
		return -1;
	}

	public void entradaQuebraCabeca() {
		String[] numeros = { "1", "2", "3", "4", "5", "6", "7", "8", "0" };
		String[] numerosEntrada = entradaTexto.getText().split(",");
		String[] numerosEntradaCopia = Arrays.copyOf(numerosEntrada, numerosEntrada.length);
		Arrays.sort(numerosEntrada);
		Arrays.sort(numeros);
		if (Arrays.equals(numeros, numerosEntrada)
				&& entradaTexto.getText().split(",").length == quebraCabeca.getQuebraCabeca().length) {
			for (int i = 0; i < numerosEntradaCopia.length; i++) {
				quebraCabeca.getQuebraCabeca()[i] = Byte.parseByte(numerosEntradaCopia[i]);
				if (quebraCabeca.getQuebraCabeca()[i] == 0) {
					quebraCabeca.setIndiceVazio(i);
				}
			}
			textArea.append("Nova Entrada para o quebra cabeça: \n");
			mudarImagem();
			printQuebraCabeca(quebraCabeca.getQuebraCabeca());
		} else {
			JOptionPane.showMessageDialog(null, "Digite uma entrada válida!");
		}
		requestFocusInWindow();
	}

	public void mudarImagem() {
		requestFocusInWindow();
		quebraCabeca.getPanel().removeAll();
		for (int j = 0; j < quebraCabeca.getQuebraCabeca().length; j++) {
			quebraCabeca.getPanel().add(quebraCabeca.getBotoes().get(j));
		}
		imagemQuebraCabeca("imagens/quebraCabeca/" + quebraCabeca.getCombox().getSelectedItem() + ".png");
		quebraCabeca.getPanel().validate();
		quebraCabeca.getPanel().repaint();
	}

	public void sorteioEntrada() {
		Byte[] numeros = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		Collections.shuffle(Arrays.asList(numeros));
		byte[] numerosRandom = new byte[numeros.length];

		entradaTexto.setText(null);
		for (int i = 0; i < numeros.length; i++) {
			numerosRandom[i] = numeros[i];
			if (numerosRandom[i] == 0) {
				quebraCabeca.setIndiceVazio(i);
			}
			entradaTexto.setText(entradaTexto.getText() + numerosRandom[i] + ",");
		}
		entradaTexto.setText(entradaTexto.getText().substring(0, entradaTexto.getText().lastIndexOf(",")));
		if (new Solucao().solve(numerosRandom) == null) {
			sorteioEntrada();

		} else {
			textArea.append("Nova Entrada para o quebra cabeça: \n");
			quebraCabeca.setQuebraCabeca(numerosRandom);
			mudarImagem();
			printQuebraCabeca(quebraCabeca.getQuebraCabeca());
		}

	}

	public void solucao() {
		quebraCabeca.getAcaoEntrada().setEnabled(false);
		quebraCabeca.getAcaoSorteio().setEnabled(false);
		quebraCabeca.getListDirecoes().removeAll();
		Solucao solucao = new Solucao();
		long tempoInicial = System.currentTimeMillis();
		Estado estado = solucao.solve(quebraCabeca.getQuebraCabeca());
		long tempoFinal = System.currentTimeMillis() - tempoInicial;
		textArea.append("\n\n*SOLUÇÃO USANDO COMO HEURISTICA A MANHATTAN:\n" + "Tempo total: " + tempoFinal + " ms\n");
		if (estado == null) {
			quebraCabeca.getAcaoEntrada().setEnabled(true);
			quebraCabeca.getAcaoSorteio().setEnabled(true);
			textArea.append("Não existe solução!\n");
			JOptionPane.showMessageDialog(null, "Não existe solução!");
		} else {
			printSolucao(estado);
			if (!quebraCabeca.getLista().isEmpty()) {
				quebraCabeca.getLista().poll().execute();
			} else {
				quebraCabeca.getAcaoEntrada().setEnabled(true);
				quebraCabeca.getAcaoSorteio().setEnabled(true);
				JOptionPane.showMessageDialog(null, "Parabens!");
			}

		}
	}

	public void movimentoSolucao(Estado estadoAtual) {
		int inicio = estadoAtual.getEstadoAnterior().getIndiceVazio();
		int fim = estadoAtual.getIndiceVazio();
		switch (fim - inicio) {
		case 3:
			quebraCabeca.getListDirecoes().add(preencherListDirecoes(estadoAtual, "cima"));
			textArea.append("cima->");
			quebraCabeca.getLista().add(new AnimacaoEixoY(quebraCabeca, fim, true));
			break;
		case -3:
			quebraCabeca.getListDirecoes().add(preencherListDirecoes(estadoAtual, "baixo"));
			textArea.append("baixo->");
			quebraCabeca.getLista().add(new AnimacaoEixoY(quebraCabeca, fim, true));
			break;
		case 1:
			quebraCabeca.getListDirecoes().add(preencherListDirecoes(estadoAtual, "esquerda"));
			textArea.append("esquerda->");
			quebraCabeca.getLista().add(new AnimacaoEixoX(quebraCabeca, fim, true));
			break;
		case -1:
			quebraCabeca.getListDirecoes().add(preencherListDirecoes(estadoAtual, "direita"));
			textArea.append("direita->");
			quebraCabeca.getLista().add(new AnimacaoEixoX(quebraCabeca, fim, true));
			break;
		default:
			break;
		}
	}

	public void adicionarBotoes() {
		for (int i = 0; i < META.length; i++) {
			JButton botao = new JButton();
			botao.addActionListener(this);
			quebraCabeca.getBotoes().add(botao);
			quebraCabeca.getPanel().add(botao);
		}
	}

	public void acoesClique() {
		acaoEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entradaQuebraCabeca();
			}
		});
		acaoSolucao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solucao();
			}
		});
		acaoSorteio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorteioEntrada();
			}
		});
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mudarImagem();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Janela() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 921, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		acaoSolucao.setForeground(Color.WHITE);
		acaoSolucao.setText("Solucionar");
		acaoSolucao.setBackground(new Color(255, 69, 0));
		acaoSolucao.setBounds(74, 516, 100, 26);
		contentPane.add(acaoSolucao);
		panel.setBounds(74, 104, 360, 360);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3, 3, 0, 0));
		scrollPane.setBounds(474, 104, 366, 302);
		contentPane.add(scrollPane);
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		scrollPane.setViewportView(textArea);
		comboBox.setBounds(196, 516, 128, 26);
		comboBox.setBackground(new Color(0, 139, 139));
		comboBox.setForeground(Color.WHITE);
		contentPane.add(comboBox);
		scrollList.setBounds(619, 417, 221, 100);
		contentPane.add(scrollList);
		scrollList.setViewportView(listDirecoes);
		imagemDirecoes.setBounds(484, 417, 100, 100);
		contentPane.add(imagemDirecoes);
		imagemDirecoes.setIcon(new ImageIcon("imagens/direcoes.png"));
		log.setBounds(561, 28, 210, 63);
		log.setIcon(new ImageIcon("imagens/log.png"));
		contentPane.add(log);
		imagemQuebraCabeca.setBounds(99, 28, 320, 63);
		imagemQuebraCabeca.setIcon(new ImageIcon("imagens/quebraCabeca.png"));
		contentPane.add(imagemQuebraCabeca);
		entradaTexto = new JTextField();
		entradaTexto.setBounds(74, 478, 242, 26);
		contentPane.add(entradaTexto);
		entradaTexto.setColumns(10);
		acaoEntrada.setForeground(Color.WHITE);
		acaoEntrada.setBackground(new Color(255, 69, 0));
		acaoEntrada.setBounds(334, 475, 100, 26);
		contentPane.add(acaoEntrada);
		acaoSorteio.setForeground(Color.WHITE);
		acaoSorteio.setBackground(new Color(255, 69, 0));
		acaoSorteio.setBounds(334, 518, 100, 23);
		contentPane.add(acaoSorteio);

		byte[] quebraCabecaInicial = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
		quebraCabeca = new QuebraCabeca(panel, botoes, quebraCabecaInicial, indexOf(quebraCabecaInicial, 0),
				imagemDirecoes, listDirecoes, comboBox, acaoEntrada, acaoSorteio);
		nomesImagens(new File("imagens/quebraCabeca/"));
		adicionarBotoes();
		imagemQuebraCabeca("imagens/quebraCabeca/" + quebraCabeca.getCombox().getSelectedItem() + ".png");
		acoesClique();
		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		acaoClique(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		acaoTeclado(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
