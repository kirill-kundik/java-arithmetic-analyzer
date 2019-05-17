package ua.com.kundikprojects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ua.com.kundikprojects.exceptions.BracketsException;
import ua.com.kundikprojects.exceptions.DigitExpectedException;
import ua.com.kundikprojects.exceptions.IllegalMathOperandException;
import ua.com.kundikprojects.exceptions.MathOperandExpectedException;

public class ArithmeticAnalizerTester extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1015278547162860913L;

	private JButton resultButton;
	private JButton helpButton;
	private JButton historyButton;
	private JTextField textField;
	private JOptionPane pane;
	private ArrayList<String> history;
	private JTextArea historyArea;
	private JScrollPane scroll;
	private ArithmeticAnalizer analizer;
	private String firstHelp;
	private Image info;
	private Image welcome;
	private Image error;
	private Image result;
	private Image frameIcon;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	private static final int DURATION = 1000;

	private static final Font FONT = createCool();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new ArithmeticAnalizerTester();

			}
		});
	}

	@SuppressWarnings("static-access")
	public ArithmeticAnalizerTester() {

		super("Arithmetic Analizer 1.0");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT);
		try {
			frameIcon = ImageIO.read(new File(System.getProperty("user.dir") + "\\bin\\Files\\frame.jpg"));
			welcome = ImageIO.read(new File(System.getProperty("user.dir") + "\\bin\\Files\\welcome.jpg"))
					.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
			info = ImageIO.read(new File(System.getProperty("user.dir") + "\\bin\\Files\\help.jpg"))
					.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			error = ImageIO.read(new File(System.getProperty("user.dir") + "\\bin\\Files\\error.png"))
					.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			result = ImageIO.read(new File(System.getProperty("user.dir") + "\\bin\\Files\\result.png"))
					.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
		} catch (IOException e2) {

			e2.getMessage();
		}
		this.setIconImage(frameIcon);

		pane = new JOptionPane();
		history = new ArrayList<String>();

		resultButton = createButton("Get result!", Color.RED, Color.WHITE, Color.WHITE, Color.RED);
		resultButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!textField.getText().equals(""))
					try {
						analizer = new ArithmeticAnalizer(textField.getText());
						history.add(textField.getText() + "=" + Integer.toString(analizer.getResult()));
						pane.showMessageDialog(getParent(),
								textField.getText() + "=" + Integer.toString(analizer.getResult()), "Result",
								pane.INFORMATION_MESSAGE, new ImageIcon(result));
					} catch (ArithmeticException e1) {

						pane.showMessageDialog(getParent(), "Arithmetic Exception!", "Error!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(error));
						e1.getMessage();
					} catch (BracketsException e1) {

						pane.showMessageDialog(getParent(), "Brackets expected!", "Error!", JOptionPane.ERROR_MESSAGE,
								new ImageIcon(error));
						e1.getMessage();
					} catch (IllegalMathOperandException e1) {

						pane.showMessageDialog(getParent(), "Illegal Math Operand!", "Error!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(error));
						e1.getMessage();
					} catch (DigitExpectedException e1) {

						pane.showMessageDialog(getParent(), "Digit Expected!", "Error!", JOptionPane.ERROR_MESSAGE,
								new ImageIcon(error));
						e1.getMessage();
					} catch (MathOperandExpectedException e1) {

						pane.showMessageDialog(getParent(), "Math Operand Expected!", "Error!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(error));
						e1.getMessage();
					}
				else
					pane.showMessageDialog(getParent(), "Please, enter your expression!", "Error!", pane.ERROR_MESSAGE,
							new ImageIcon(error));
			}
		});
		historyButton = createButton("History", Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK);
		historyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame f = new JFrame("History");
				f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				f.setLayout(null);
				f.setResizable(false);
				f.setSize(WIDTH * 2 / 3, HEIGHT * 2 / 3);
				f.setIconImage(frameIcon);

				historyArea = new JTextArea();
				historyArea.setBounds(0, 0, WIDTH * 2 / 3, HEIGHT * 2 / 3);
				historyArea.setFont(FONT.deriveFont(24f));
				historyArea.setLineWrap(true);
				historyArea.setWrapStyleWord(true);
				historyArea.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 3),
						new EmptyBorder(10, 10, 10, 10)));

				String his = new String();
				if (history.isEmpty())
					his = "Your History is Empty. Let's calculate something!";
				else {
					int i = 1;
					for (String expression : history) {
						his += i + ". " + expression + '\n';
						System.out.println(expression);
						i++;
					}
				}

				historyArea.setText(his);

				historyArea.setEditable(false);
				historyArea.setFocusable(false);

				JScrollPane p = new JScrollPane(historyArea);
				p.setBounds(0, 0, WIDTH * 2 / 3, HEIGHT * 2 / 3);

				f.add(p);
				f.setVisible(true);
			}
		});

		helpButton = createButton("Help", Color.BLUE, Color.WHITE, Color.WHITE, Color.BLUE);
		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String help = new String();
				help = "Here is a full list of functional capabilities of this program:" + '\n';
				help += "'+' for addition." + '\n';
				help += "'-' for subtraction." + '\n';
				help += "'*' for multiplication." + '\n';
				help += "'/' for division." + '\n';
				help += "'^' for powering." + '\n';
				help += "'(' or ')' for chaging priority of Math operands." + '\n';

				pane.showMessageDialog(getParent(), help, "Help", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(info));
				// pane.showMessageDialog(getParent(), message, title,
				// messageType, icon);

			}
		});

		historyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		helpButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		resultButton.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFont(FONT.deriveFont(24f));
		textField.setBorder(
				BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK, 3), new EmptyBorder(10, 10, 10, 10)));
		textField.setForeground(Color.BLACK);
		textField.setHorizontalAlignment(SwingConstants.LEFT);

		textField.addFocusListener(new FocusListener() {

			private boolean isNeededToRefresh(String s) {
				if (s.length() != textField.getText().length())
					return false;
				for (int i = 0; i < textField.getText().length(); i++) {
					if (s.charAt(i) != textField.getText().charAt(i))
						return false;

				}
				return true;
			}

			@Override
			public void focusGained(FocusEvent e) {

				if (isNeededToRefresh(new String("Enter your expression..."))) {
					textField.setText("");
					textField.setFont(FONT.deriveFont(24f));
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().length() == 0 || textField.getText() == null) {
					textField.setFont(FONT.deriveFont(20f));
					textField.setForeground(Color.GRAY);
					textField.setText("Enter your expression...");
				}
			}

		});

		textField.setBounds(0, 0, WIDTH - 6, HEIGHT - 150);
		resultButton.setBounds(0, HEIGHT - 150, 198, 150);
		historyButton.setBounds(198, HEIGHT - 150, 198, 150);
		helpButton.setBounds(396, HEIGHT - 150, 198, 150);

		InputMap im = resultButton.getInputMap();
		im.put(KeyStroke.getKeyStroke("ENTER"), "pressed");

		scroll = new JScrollPane(textField);
		scroll.setBounds(0, 0, WIDTH - 6, HEIGHT - 150);
		this.add(scroll);
		this.add(resultButton);
		this.add(historyButton);
		this.add(helpButton);

		this.getRootPane().setDefaultButton(resultButton);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
		// this.pack();
		this.setVisible(true);
		// mainPanel.setBackground(Color.black);

		firstHelp = "Welcome to the Arithmetic Analizer 1.0 program!" + '\n';
		firstHelp += "This is easy-to-use program that can solve your expressions." + '\n';
		firstHelp += "First of all on the main screen of the program you can see 3 buttons: 'Result', 'History', 'Help'."
				+ '\n';
		firstHelp += "'Result' button is for getting result from exprssion that you entered in the text field that is above the button."
				+ '\n';
		firstHelp += "'History' button is for getting all of your results that you entered during the session." + '\n';
		firstHelp += "'Help' button is for getting list of all functional capablities." + '\n';
		firstHelp += "Developed by Kirill Kundik, Student of Software Engineering, NaUKMA." + '\n';
		firstHelp += "(c) All right are reserved!";
		
		JLabel label = new JLabel (firstHelp);
		label.setFont(FONT.deriveFont(26f));

		pane.showMessageDialog(getParent(), label, "Welcome!", pane.INFORMATION_MESSAGE, new ImageIcon(welcome));
	}

	private JButton createButton(String s, Color bgcolor1, Color bgcolor2, Color fgcolor1, Color fgcolor2) {
		JButton myButton = new JButton(s);
		myButton.setFont(FONT.deriveFont(30f));
		myButton.setVerticalTextPosition(SwingConstants.CENTER);
		myButton.setFocusable(false);
		myButton.setBackground(bgcolor1);
		myButton.setForeground(bgcolor2);
		Map<String, Color[]> map = new HashMap<String, Color[]>();
		Color[] color = { bgcolor1, bgcolor2 };
		map.put("background", color);

		Animation buttonAnimation1 = new Animation(myButton, map, DURATION);

		color[0] = fgcolor1;
		color[1] = fgcolor2;
		map.clear();
		map.put("foreground", color);

		Animation buttonAnimation2 = new Animation(myButton, map, DURATION);

		myButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				buttonAnimation1.play();
				buttonAnimation2.play();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				buttonAnimation1.playReverse();
				buttonAnimation2.playReverse();
			}
		});

		return myButton;
	}

	private static final Font createCool() {
		try {
			Font cFont = Font.createFont(Font.TRUETYPE_FONT,
					new File(System.getProperty("user.dir") + "\\bin\\Files\\American Captain.ttf"));
			return cFont;
		} catch (Exception e) {
			System.err.println("Font was not found.");
			return null;
		}

	}
}
