import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class FindPrimes extends JFrame
{
    private final JTextField highestPrimeJTextField = new JTextField();
    private final JButton getPrimesJButton = new JButton("Get Primes");
    private final JTextArea displayPrimesJTextArea = new JTextArea();
    private final JButton cancelJButton = new JButton("Cancel");
    private final JProgressBar progressJProgressBar = new JProgressBar();
    private final JLabel statusJLabel = new JLabel();
    private PrimeCalculator calculator;
    
    
    // construtor
    public FindPrimes()
    {
        super("Finding Primes with SwingWorker");
        setLayout(new BorderLayout());
        
        // inicializa o painel para obter um número a partir do usuário
        JPanel northJPanel = new JPanel();
        northJPanel.add(new JLabel("Find primes less than: "));
        highestPrimeJTextField.setColumns(5);
        northJPanel.add(highestPrimeJTextField);
        getPrimesJButton.addActionListener(
        new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                progressJProgressBar.setValue(0); // redefine a JProgressBar
                displayPrimesJTextArea.setText(""); // limpa a JTextArea
                statusJLabel.setText(""); // limpa o JLabel
                int number; // busca números primos por meio desse valor
                
                try
                {
                    // obtém entrada de usuário
                    number = Integer.parseInt(
                    highestPrimeJTextField.getText());
                }
                catch (NumberFormatException ex)
                {
                    statusJLabel.setText("Enter an integer.");
                    return;
                }
                
                // constrói um novo objeto PrimeCalculator 
                calculator = new PrimeCalculator(number, 
                displayPrimesJTextArea, statusJLabel, getPrimesJButton,
                cancelJButton); 
                
                // ouve alterações de propriedades da barra de progresso
                calculator.addPropertyChangeListener( 
                new PropertyChangeListener() 
                { 
                    public void propertyChange(PropertyChangeEvent e) 
                    { 
                        // se a propriedade alterada for progress, 
                        // atualiza a barra de progresso 
                        if (e.getPropertyName().equals("progress")) 
                        { 
                            int newValue = (Integer) e.getNewValue(); 
                            progressJProgressBar.setValue(newValue); 
                        } 
                    } 
                } // fim da classe interna anônima 
            ); // finaliza a chamada para addPropertyChangeListener 
                
        // desativa o botão Get Primes e ativa o botão Cancel
        getPrimesJButton.setEnabled(false);
        cancelJButton.setEnabled(true);
        calculator.execute(); // executa o objeto PrimeCalculator
    }
  } // fim da classe interna anônima
); // fim da chamada para addActionListener
northJPanel.add(getPrimesJButton);

// adiciona uma JList rolável para exibir os resultados do cálculo
displayPrimesJTextArea.setEditable(false);
add(new JScrollPane(displayPrimesJTextArea,
ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
// inicializa um painel para exibir cancelJButton,
// progressJProgressBar e statusJLabel
JPanel southJPanel = new JPanel(new GridLayout(1, 3, 10, 10));
cancelJButton.setEnabled(false);
cancelJButton.addActionListener(
new ActionListener()
{
    public void actionPerformed(ActionEvent e)
    {
        calculator.cancel(true); // cancela o cálculo
    }
} // fim da classe interna anônima
); // fim da chamada para addActionListener
southJPanel.add(cancelJButton);
progressJProgressBar.setStringPainted(true);
southJPanel.add(progressJProgressBar);
southJPanel.add(statusJLabel);

add(northJPanel, BorderLayout.NORTH);
add(southJPanel, BorderLayout.SOUTH);
setSize(350, 300);
setVisible(true);
} // fim do construtor

 // método main inicia a execução de programa
public static void main(String[] args)
{
    FindPrimes application = new FindPrimes();
    application.setDefaultCloseOperation(EXIT_ON_CLOSE);
} // fim de main
} // fim da classe FindPrimes