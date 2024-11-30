package Distributers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Time_textFieild;
	private JTextField DP_Capacity_Field;
	private JTextField DL1_Capacity_Field;
	private JTextField DL2_Capacity_Field;
	private JTextField Home1_Field;
	private JTextField Comapny1_Field;
	private JTextField Home2_Field;
	private JTextField Company2_Field;
	private static Frame instance;
	private JTextField beginingcapacity;
	private JTextField sommeField;
	private JLabel lblSomme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Time_textFieild = new JTextField();
		Time_textFieild.setText("0");
		Time_textFieild.setBounds(622, 25, 86, 20);
		contentPane.add(Time_textFieild);
		Time_textFieild.setColumns(10);
		
		DP_Capacity_Field = new JTextField();
		DP_Capacity_Field.setText("0");
		DP_Capacity_Field.setColumns(10);
		DP_Capacity_Field.setBounds(294, 72, 86, 20);
		contentPane.add(DP_Capacity_Field);
		
		DL1_Capacity_Field = new JTextField();
		DL1_Capacity_Field.setText("0");
		DL1_Capacity_Field.setColumns(10);
		DL1_Capacity_Field.setBounds(138, 165, 86, 20);
		contentPane.add(DL1_Capacity_Field);
		
		JLabel lblNewLabel = new JLabel("Time");
		lblNewLabel.setBounds(543, 28, 46, 14);
		contentPane.add(lblNewLabel);
		
		DL2_Capacity_Field = new JTextField();
		DL2_Capacity_Field.setText("0");
		DL2_Capacity_Field.setColumns(10);
		DL2_Capacity_Field.setBounds(483, 165, 86, 20);
		contentPane.add(DL2_Capacity_Field);
		
		JLabel lblDpcapacity = new JLabel("DP_capacity");
		lblDpcapacity.setBounds(294, 106, 110, 14);
		contentPane.add(lblDpcapacity);
		
		JLabel lblDlcapacity = new JLabel("DL1 capacity");
		lblDlcapacity.setBounds(138, 199, 110, 14);
		contentPane.add(lblDlcapacity);
		
		JLabel lblDlCapacity = new JLabel("DL2 capacity");
		lblDlCapacity.setBounds(483, 199, 96, 14);
		contentPane.add(lblDlCapacity);
		
		Home1_Field = new JTextField();
		Home1_Field.setText("0");
		Home1_Field.setColumns(10);
		Home1_Field.setBounds(44, 297, 86, 20);
		contentPane.add(Home1_Field);
		
		Comapny1_Field = new JTextField();
		Comapny1_Field.setText("0");
		Comapny1_Field.setColumns(10);
		Comapny1_Field.setBounds(237, 297, 86, 20);
		contentPane.add(Comapny1_Field);
		
		Home2_Field = new JTextField();
		Home2_Field.setText("0");
		Home2_Field.setColumns(10);
		Home2_Field.setBounds(397, 297, 86, 20);
		contentPane.add(Home2_Field);
		
		Company2_Field = new JTextField();
		Company2_Field.setText("0");
		Company2_Field.setColumns(10);
		Company2_Field.setBounds(605, 297, 86, 20);
		contentPane.add(Company2_Field);
		
		JLabel Hom = new JLabel("Home 1");
		Hom.setBounds(44, 344, 110, 14);
		contentPane.add(Hom);
		
		JLabel lblCompany = new JLabel("Company 1");
		lblCompany.setBounds(237, 344, 110, 14);
		contentPane.add(lblCompany);
		
		JLabel lblHome = new JLabel("Home 2");
		lblHome.setBounds(397, 344, 110, 14);
		contentPane.add(lblHome);
		
		JLabel lblCompany_1 = new JLabel("Company 2");
		lblCompany_1.setBounds(605, 344, 110, 14);
		contentPane.add(lblCompany_1);
		
		beginingcapacity = new JTextField();
		beginingcapacity.setText("0");
		beginingcapacity.setColumns(10);
		beginingcapacity.setBounds(138, 25, 86, 20);
		contentPane.add(beginingcapacity);
		
		JLabel lblBeginingcap = new JLabel("beginingcap");
		lblBeginingcap.setBounds(20, 28, 110, 14);
		contentPane.add(lblBeginingcap);
		
		sommeField = new JTextField();
		sommeField.setText("0");
		sommeField.setColumns(10);
		sommeField.setBounds(365, 25, 86, 20);
		contentPane.add(sommeField);
		
		lblSomme = new JLabel("somme");
		lblSomme.setBounds(280, 28, 46, 14);
		contentPane.add(lblSomme);
		
		JButton btnNewButton = new JButton("calcule");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int DP = Integer.parseInt(DP_Capacity_Field.getText());
				int DL1 = Integer.parseInt(DL1_Capacity_Field.getText());
				int DL2 = Integer.parseInt(DL2_Capacity_Field.getText());
				int H1 = Integer.parseInt(Home1_Field.getText());
				int H2 = Integer.parseInt(Home2_Field.getText());
				int C1 = Integer.parseInt(Comapny1_Field.getText());
				int C2 = Integer.parseInt(Company2_Field.getText());
				int Somme = DP+DL1+DL2+H1+H2+C1+C2;
				sommeField.setText(""+Somme);
			}
		});
		btnNewButton.setBounds(303, 398, 89, 23);
		contentPane.add(btnNewButton);
		
		
	}
	public void calc() {
		
		int DP = Integer.parseInt(DP_Capacity_Field.getText());
		int DL1 = Integer.parseInt(DL1_Capacity_Field.getText());
		int DL2 = Integer.parseInt(DL2_Capacity_Field.getText());
		int H1 = Integer.parseInt(Home1_Field.getText());
		int H2 = Integer.parseInt(Home2_Field.getText());
		int C1 = Integer.parseInt(Comapny1_Field.getText());
		int C2 = Integer.parseInt(Company2_Field.getText());
		int Somme = DP+DL1+DL2+H1+H2+C1+C2;
		sommeField.setText(""+Somme);
		
	
	}
	
	public void setTimeText(String time) {
        Time_textFieild.setText(time);
    }

    public void setDPCapacityText(String capacity) {
        DP_Capacity_Field.setText(capacity);
    }

    public void setDL1CapacityText(String capacity) {
        DL1_Capacity_Field.setText(capacity);
    }

    public void setDL2CapacityText(String capacity) {
        DL2_Capacity_Field.setText(capacity);
    }

    public void setHome1Text(String home1) {
        Home1_Field.setText(home1);
    }

    public void setCompany1Text(String company1) {
        Comapny1_Field.setText(company1);
    }

    public void setHome2Text(String home2) {
        Home2_Field.setText(home2);
    }

    public void setCompany2Text(String company2) {
        Company2_Field.setText(company2);
    }
    
    public void setbeginingcapacity(String begcap) {
        beginingcapacity.setText(begcap);
    }
    
    public static Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
        }
        return instance;
    }
}
