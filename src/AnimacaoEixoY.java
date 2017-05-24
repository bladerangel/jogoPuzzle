
public class AnimacaoEixoY extends Animacao {

	public AnimacaoEixoY(QuebraCabeca quebraCabeca, int fim, boolean imagem) {
		super(quebraCabeca, fim, imagem);

	}

	@Override
	protected Void doInBackground() {
		if (Janela.semaforo.tryAcquire(1)) {
			inicio = quebraCabeca.getIndiceVazio();
			int meta = quebraCabeca.getBotoes().get(fim).getY();
			int y = quebraCabeca.getBotoes().get(inicio).getY();
			int y1 = quebraCabeca.getBotoes().get(fim).getY();
			quebraCabeca.getListDirecoes().select(quebraCabeca.getListDirecoes().getSelectedIndex() + 1);
			if (imagem) {
				if (inicio < fim) {
					rotacionarImagem("imagens/direcao.png", (3 * Math.PI) / 2);
				} else {
					rotacionarImagem("imagens/direcao.png", Math.PI / 2);
				}
			}
			while (y != meta) {
				try {
					Thread.sleep(1);
					if (inicio < fim) {
						y++;
						y1--;
					} else {
						y--;
						y1++;
					}
					quebraCabeca.getBotoes().get(inicio).setLocation(quebraCabeca.getBotoes().get(inicio).getX(), y);
					quebraCabeca.getBotoes().get(fim).setLocation(quebraCabeca.getBotoes().get(fim).getX(), y1);
					quebraCabeca.getPanel().repaint();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			termino();
		}
		return null;
	}
}
