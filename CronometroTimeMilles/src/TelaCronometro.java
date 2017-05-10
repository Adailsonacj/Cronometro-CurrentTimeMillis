import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaCronometro extends JFrame implements ActionListener,Runnable {

	/**
	 * 
	 */
	Thread inicia = new Thread(this);
	
	private static final long serialVersionUID = 1L;
	private JMenuBar barraMenu = null;
	private JMenu config = null;
	private JMenuItem tempo = null, sair = null;
	private JButton iniciar = null, pausar = null, zerar = null;
	private JPanel painelRelogio = null, painelBotoes = null;
	
	private boolean rodando = false;
	private boolean pausado = false;
	
	public static int hora ;
	public static int min ;
	public static int seg ;
	private long mils;
	private long milisegundoIncial;
	private long milisegundoAtual;


	TelaCronometro() {
		setTitle("CronoFacto");
		setSize(400, 250);
		// setAlwaysOnTop(true);
		setResizable(false);
		setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initComponentes();

		setVisible(true);
	}

	private void initComponentes() {
		barraMenu = new JMenuBar();
		config = new JMenu("Configura��o");
		tempo = new JMenuItem("Tempo");
		sair = new JMenuItem("Sair");

		setJMenuBar(barraMenu);
		barraMenu.add(config);
		config.add(tempo);
		tempo.addActionListener(this);
		config.addSeparator();
		config.add(sair);
		sair.addActionListener(this);


		iniciar = new JButton("Iniciar");
		iniciar.addActionListener(this);
		pausar = new JButton("Pausar");
		pausar.addActionListener(this);
		zerar = new JButton("Zerar");
		zerar.addActionListener(this);

		painelRelogio = new JPanel();
		painelBotoes = new JPanel();
		add(painelRelogio, BorderLayout.CENTER);
		add(painelBotoes, BorderLayout.SOUTH);

		painelBotoes.add(iniciar);
		painelBotoes.add(pausar);
		painelBotoes.add(zerar);

	}
	public void run(){
			
		//Guardo o milisegundo de quando come�ou contar
		
					milisegundoIncial = System.currentTimeMillis();
					while(true){
					if (rodando==true) {	
					if(pausado==false){
						//Calcular milisegundo atual
						milisegundoAtual = System.currentTimeMillis()-milisegundoIncial;
					
						
						//Verificar se j� passou 1000 milisegundo - ou melhor: 1 segundo
						if(milisegundoAtual>=1000){
							
							if(seg>0&&min==0&&hora==0){
								seg--;
								if(seg==0){
								}
							}else
							if(seg>=0&&min>0&&hora>=0){
								seg--;

								if(seg == -1){
									seg = 59;
									if(min>0){
										min--;
									} 
								}
							}else
								if(seg>=0&&min>=0&&hora>0){
									seg--;
									if(seg == -1){
										seg = 59;
										if(min>0){
											min--;
										}
										if(min==0&&seg==59){
											min = 59;
											hora--;
										}
									}
								}
					//Atualizamos o milisegundoIncial, para recome�ar a contagem do segundo
							milisegundoIncial = System.currentTimeMillis();
							System.out.println(hora +":"+min+":"+seg+":"+mils);
						
							}
						if(rodando ==true){
							mils = milisegundoAtual;
							if(seg==0&&min==0&&hora==0&&mils>0){
								if(mils==0){
									rodando = false;
								}
							}
						}
						repaint();
						}
					}
				}
			}

	@Override
	public void paint(Graphics g)
	{	
		super.paint(g);
		g.drawString(hora+":"+min+":"+seg+":"+mils, 200, 125);
		
		
		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sair) {
			System.exit(0);
		} else if (e.getSource() == tempo) {
			try{
			hora=(Integer.parseInt(JOptionPane.showInputDialog("Digite hora")));

			min=(Integer.parseInt(JOptionPane.showInputDialog("Digite minuto")));

			seg=(Integer.parseInt(JOptionPane.showInputDialog("Digite segundo")));
			
			
			repaint();
		} catch (Exception error) {
			JOptionPane.showMessageDialog(null, "Você inseriu carácteres não permitidos!");
		}
			
		} else if (e.getSource() == iniciar) {
			if(rodando==false){
			rodando = true;
			inicia.start();
			}
			if(rodando==true){
			if(pausado==true){
				pausado = false;
				}
			}
		}
		else if (e.getSource() == pausar) {
			//pausado = true;
			rodando = false;
		}
		else if (e.getSource() == zerar) {
			rodando = false;
			hora = 0; min = 0; seg = 0; mils = 0;
			repaint();
			}
	}
	

}