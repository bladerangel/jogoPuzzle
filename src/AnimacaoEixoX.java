
public class AnimacaoEixoX extends Animacao {

	public AnimacaoEixoX(QuebraCabeca quebraCabeca, int fim, boolean imagem) {
		super(quebraCabeca, fim, imagem);

	}

	@Override
	protected Void doInBackground() {
		if (Janela.semaforo.tryAcquire(1)) {
			inicio = quebraCabeca.getIndiceVazio();
			int meta = quebraCabeca.getBotoes().get(fim).getX();
			int x = quebraCabeca.getBotoes().get(inicio).getX();
			int x1 = quebraCabeca.getBotoes().get(fim).getX();
			quebraCabeca.getListDirecoes().select(quebraCabeca.getListDirecoes().getSelectedIndex() + 1);
			if (imagem) {
				if (inicio < fim) {
					rotacionarImagem("imagens/direcao.png", Math.PI);
				} else {
					rotacionarImagem("imagens/direcao.png", 0);
				}
			}
			while (x != meta) {
				try {
					Thread.sleep(1);
					if (inicio < fim) {
						x++;
						x1--;
					} else {
						x--;
						x1++;
					}
					quebraCabeca.getBotoes().get(inicio).setLocation(x, quebraCabeca.getBotoes().get(inicio).getY());
					quebraCabeca.getBotoes().get(fim).setLocation(x1, quebraCabeca.getBotoes().get(fim).getY());
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
