import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public abstract class Animacao extends SwingWorker<Void, Void> {

	protected QuebraCabeca quebraCabeca;
	protected int fim;
	protected int inicio;
	protected boolean imagem;

	public Animacao(QuebraCabeca quebraCabeca, int fim, boolean imagem) {
		this.quebraCabeca = quebraCabeca;
		this.fim = fim;
		this.imagem = imagem;
	}

	protected void termino() {
		JButton aux = quebraCabeca.getBotoes().get(inicio);
		quebraCabeca.getBotoes().set(inicio, quebraCabeca.getBotoes().get(fim));
		quebraCabeca.getBotoes().set(fim, aux);
		byte aux1 = quebraCabeca.getQuebraCabeca()[inicio];
		quebraCabeca.getQuebraCabeca()[inicio] = quebraCabeca.getQuebraCabeca()[fim];
		quebraCabeca.getQuebraCabeca()[fim] = aux1;
		quebraCabeca.setIndiceVazio(fim);
		Janela.semaforo.release();
		if (!quebraCabeca.getLista().isEmpty())
			quebraCabeca.getLista().poll().execute();
		else if (Arrays.equals(Janela.META, quebraCabeca.getQuebraCabeca())) {
			quebraCabeca.getAcaoEntrada().setEnabled(true);
			quebraCabeca.getAcaoSorteio().setEnabled(true);
			JOptionPane.showMessageDialog(null, "Parabens!");
		}
	}

	protected void rotacionarImagem(String caminho, double angulo) {
		try {
			BufferedImage imagem = ImageIO.read(new File(caminho));
			AffineTransform rotacao = new AffineTransform();
			rotacao.rotate(angulo, imagem.getWidth() / 2, imagem.getHeight() / 2);
			AffineTransformOp filtro = new AffineTransformOp(rotacao, AffineTransformOp.TYPE_BILINEAR);
			imagem = filtro.filter(imagem, null);
			quebraCabeca.getImagemDirecoes().setIcon(new ImageIcon(imagem));
			quebraCabeca.getPanel().removeAll();
			for (int j = 0; j < quebraCabeca.getQuebraCabeca().length; j++) {
				quebraCabeca.getPanel().add(quebraCabeca.getBotoes().get(j));
			}
			quebraCabeca.getPanel().validate();
			quebraCabeca.getPanel().repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
