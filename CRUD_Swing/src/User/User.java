package User;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class User {

	private JFrame frmCrudSwingMysql;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtCity;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User window = new User();
					window.frmCrudSwingMysql.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public User() throws SQLException  {
		initialize();
		connect();
		
		load();
	}
	
	String query;
	Connection con;
	PreparedStatement pst;
	Statement st;
	ResultSet rs;

	//database connect
	
	public void connect() throws SQLException {
		 String url="jdbc:mysql://localhost:3306/crud";
	        String userName="root";
	        String pas="";
		try {
			con=DriverManager.getConnection(url,userName,pas);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	public void clear() {
		txtID.setText("");
		txtName.setText("");
		txtAge.setText("");
		txtCity.setText("");
		txtName.requestFocus();
	}
	
	// Load table 
	public void load() {
		try {
			pst=con.prepareStatement("select * from user");
			rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrudSwingMysql = new JFrame();
		frmCrudSwingMysql.setTitle("CRUD Swing MySQL");
		frmCrudSwingMysql.setFont(new Font("Dialog", Font.PLAIN, 14));
		frmCrudSwingMysql.setBounds(100, 100, 777, 300);
		frmCrudSwingMysql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCrudSwingMysql.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(32, 50, 46, 20);
		frmCrudSwingMysql.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(32, 81, 46, 20);
		frmCrudSwingMysql.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Age");
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(32, 112, 46, 20);
		frmCrudSwingMysql.getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("City");
		lblNewLabel_1_3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(32, 151, 46, 20);
		frmCrudSwingMysql.getContentPane().add(lblNewLabel_1_3);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(88, 50, 259, 20);
		frmCrudSwingMysql.getContentPane().add(txtID);
		txtID.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(88, 82, 259, 20);
		frmCrudSwingMysql.getContentPane().add(txtName);
		
		txtAge = new JTextField();
		txtAge.setColumns(10);
		txtAge.setBounds(88, 113, 259, 20);
		frmCrudSwingMysql.getContentPane().add(txtAge);
		
		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(88, 150, 259, 20);
		frmCrudSwingMysql.getContentPane().add(txtCity);
		
		JButton btnSave = new JButton("Save");
	
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=txtID.getText();
				String name=txtName.getText();
				String age=txtAge.getText();
				String city=txtCity.getText();
				
				if(name==null || name.isEmpty()||name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter  Name ");
					txtName.requestFocus(); // Focus on Name field 
					return;      // Stops the method execution here  alternative ah nanga if else use pannina ippadi seiya thevai illa 
				}
				if(age==null || age.isEmpty()||age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter Age ");
					txtAge.requestFocus();
					return;
				}
				if(city==null || city.isEmpty()||city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter  City Name ");
					txtCity.requestFocus();
					return;
				}
				
				if(id.isEmpty()) {
					query="insert into User (Name,Age,City) values (?,?,?)";
					try {
						pst=con.prepareStatement(query);
						pst.setString(1, name);
						pst.setString(2,age);
						pst.setString(3, city);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"Data insert Successfully");
						clear();
						load();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		btnSave.setBounds(88, 191, 80, 23);
		frmCrudSwingMysql.getContentPane().add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=txtID.getText();
				String name=txtName.getText();
				String age=txtAge.getText();
				String city=txtCity.getText();
				
				if(name==null || name.isEmpty()||name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter  Name ");
					txtName.requestFocus(); // Focus on Name field 
					return;      // Stops the method execution here  alternative ah nanga if else use pannina ippadi seiya thevai illa 
				}
				if(age==null || age.isEmpty()||age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter Age ");
					txtAge.requestFocus();
					return;
				}
				if(city==null || city.isEmpty()||city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Enter  City Name ");
					txtCity.requestFocus();
					return;
				}
				if(!(id.isEmpty())) {
					query="update user set Name=? ,Age=?,City=? where ID=?";
					try {
						pst=con.prepareStatement(query);
						
						pst.setString(1,name);
						pst.setString(2,age);
						pst.setString(3, city);
						pst.setString(4,id);
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"Data update Successfully");
						clear();
						load();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setBounds(178, 191, 80, 23);
		frmCrudSwingMysql.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id =txtID.getText();
				
				if(!id.isEmpty()) {
					// 5 parameters  
					int result=JOptionPane.showConfirmDialog(null,"Do you want to Delete ?",
					"Delete", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); 
					if(result==JOptionPane.YES_NO_OPTION) {
						try {
							query="delete from User where ID=?";
							pst=con.prepareStatement(query);
							pst.setString(1, id);
							pst.executeUpdate();
							
							JOptionPane.showMessageDialog(null,"Data Deleted Successfully");
							clear();
							load();
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
						
					}
				}
				
				
			}
		});
		btnDelete.setBounds(268, 191, 80, 23);
		frmCrudSwingMysql.getContentPane().add(btnDelete);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, -24, 439, 291);
		frmCrudSwingMysql.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 23, 439, 257);
		panel_1.add(panel);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("User Management System");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(449, 11, 302, 222);
		frmCrudSwingMysql.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model=table.getModel();
				txtID.setText(model.getValueAt(index,0).toString());
				txtName.setText(model.getValueAt(index,1).toString());
				txtAge.setText(model.getValueAt(index,2).toString());
				txtCity.setText(model.getValueAt(index,3).toString());
			}
		});
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
	}
}
