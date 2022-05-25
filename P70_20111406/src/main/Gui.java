
package main;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Gui extends JFrame implements Observer {
// main page
    private JPanel userPanel = new JPanel();
    private JLabel uName = new JLabel("Username: ");
    private JLabel pWord = new JLabel("Password: ");    
    private JButton loginButton = new JButton("Log in");
    private JButton registerButton = new JButton("register");
   
    // input
    public JTextField unInput = new JTextField(8);
    public JTextField pwInput = new JTextField(10);
    private JLabel wrongName = new JLabel("Wrong username or passwork!");
   ////
   ///2nd panel
    private JPanel QuizPanel = new JPanel();
    private JLabel Question = new JLabel();
    private JLabel firstnumber = new JLabel();
    private JLabel secondNumber = new JLabel();
    private JLabel additionLabel = new JLabel();
    //private JTextField secondNumber = new JTextField(10);
    private JButton nextButton = new JButton("Next");
    private JButton quitButton = new JButton("Quit");


    public JLabel message = new JLabel("Welcome!", JLabel.CENTER);
    public JTextField calcSolution = new JTextField(10);

    private boolean started = false; // To identify if the game part starts.

    /**
     * Step 1: The constructor initializes the frame window as well as the login
     * interface.
     *
     * Note: We need to define the events of ActionListener in the Controller
     * class. Go to Model.java for Step 2.
     */
    public Gui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null); // Make the frame located at the absolute center of the screen.
        this.userPanel.add(uName);
        this.userPanel.add(unInput);
        this.userPanel.add(pWord);
        this.userPanel.add(pwInput);
        loginButton.setBounds(100, 100, 300, 300);
        this.userPanel.add(loginButton);
        this.add(this.message, BorderLayout.BEFORE_FIRST_LINE);
        this.add(userPanel);
        this.setVisible(true);
    }

    public void startQuiz() {
        QuizPanel.add(firstnumber);
        QuizPanel.add(additionLabel);
        QuizPanel.add(secondNumber);
        QuizPanel.add(calcSolution);
        QuizPanel.add(nextButton);
        QuizPanel.add(quitButton);

        this.getContentPane().removeAll();
        QuizPanel.setVisible(true);
        this.add(QuizPanel);
        this.revalidate();
        this.repaint();

    }

    public void setQuestion(int num1, int num2) {
        firstnumber.setText(num1 + "");
        secondNumber.setText(num2 + "=");
        calcSolution.setText("");
        QuizPanel.repaint();
    }

    public void addActionListener(ActionListener listener) {
        this.loginButton.addActionListener(listener);
        this.nextButton.addActionListener(listener);
        this.quitButton.addActionListener(listener);
    }

    private void quitGame(int score) {
        JPanel quitPanel = new JPanel();
        JLabel scoreLabel = new JLabel("Your score: " + score);
        quitPanel.add(scoreLabel);
        this.getContentPane().removeAll();
        QuizPanel.setVisible(true);
        this.add(quitPanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Step 7: Define the event when model has been modified.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Account data = (Account) arg; // Obtain the instance of data.
        if (!data.Checker) { // If loginFlage is false, then ask the user to input again.
            this.unInput.setText("");
            this.pwInput.setText("");
            this.message.setText("Invalid username or password.");
        } else if (!this.started) { // If the game has not started, then start the game.
            this.startQuiz(); // Change the interface of the frame.
            this.started = true;
            this.setQuestion(data.num1, data.num2); 
           
        } else if (data.quitFlag) { // If user quits the game, display user's current score.
            this.quitGame(data.currentScore);
        } else { // Otherwise, update a new question for the user.
            this.setQuestion(data.num1, data.num2);
        }
    }
}