package dbProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ProjectWindowContent extends JInternalFrame implements ActionListener {

	// DB Connectivity Attributes
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private Container content;

	private JPanel detailsPanel;
	private JPanel resultsPanel;
	private JPanel exportConceptDataPanel;
	private JScrollPane resultsContentPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;

	// font for textfields
	Font font = new Font("Comic Sans", Font.BOLD, 20);
	
	// font for labels
	Font font2 = new Font("Comic Sans", Font.BOLD, 30);
	
	// combo box
	String classes[] = { "Potions", "Charms", "Defence Against the Dark Arts" };
	private JComboBox<String> subjectAvgBox = new JComboBox<String>(classes);
	String gender[] = { "Male", "Female" };
	private JComboBox<String> genderAvgBox = new JComboBox<String>(gender);
	String house[] = { "Gryffindor", "Ravenclaw", "Hufflepuff", "Slytherin" };
	private JComboBox<String> houseAvgBox = new JComboBox<String>(house);

	// labels
	private JLabel StudentIDLabel = new JLabel("Student ID:              ");
	private JLabel FNLabel = new JLabel("First Name:               ");
	private JLabel LNLabel = new JLabel("Last Name:      ");
	private JLabel ageLabel = new JLabel("Age:        ");
	private JLabel genderLabel = new JLabel("Gender:                 ");
	private JLabel yearLabel = new JLabel("Year:               ");
	private JLabel StudentIDLabel2 = new JLabel("Student ID:     ");
	private JLabel ResultIDLabel = new JLabel("Result ID:     ");
	private JLabel HouseLabel = new JLabel("House:     ");
	private JLabel SubjectLabel = new JLabel("Subject:     ");
	private JLabel GradeLabel = new JLabel("Grade:     ");
	private JLabel avgClassLabel = new JLabel("Avg. by Subject: ");
	private JLabel avgGenderLabel = new JLabel("Avg. by Gender: ");
	private JLabel avgHouseLabel = new JLabel("Avg. by House: ");

	// textfields
	private JTextField StudentIDTF = new JTextField(10);
	private JTextField FNTF = new JTextField(10);
	private JTextField LNTF = new JTextField(10);
	private JTextField ageTF = new JTextField(10);
	private JTextField genderTF = new JTextField(10);
	private JTextField yearTF = new JTextField(10);
	private JTextField avgTF = new JTextField(10);
	private JTextField genderAvgTF = new JTextField(10);
	private JTextField houseAvgTF = new JTextField(10);
	private JTextField resultIDTF = new JTextField(12);
	private JTextField StudentIDTF2 = new JTextField(12);
	private JTextField houseTF = new JTextField(12);
	private JTextField subjectTF = new JTextField(30);
	private JTextField gradeTF = new JTextField(10);

	private static QueryTableModel TableModel = new QueryTableModel();
	private static ResultsTableModel ResultsModel = new ResultsTableModel();

	// Add the models to JTabels
	private JTable TableofDBContents = new JTable(TableModel);
	private JTable ResultsContent = new JTable(ResultsModel);

	// Buttons for inserting, and updating members
	// also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	private JButton newResults = new JButton("New Result");
	private JButton removeResults = new JButton("Remove Result");
	private JButton updateResults = new JButton("Update Result");
	private JButton clearAll = new JButton("Clear All");
	private JButton barChartButton = new JButton("Bar chart");

	public ProjectWindowContent(String aTitle) {
		// setting up the GUI
		super(aTitle, false, false, false, false);
		setEnabled(true);

		initDBConnection();
		
		lineBorder = BorderFactory.createEtchedBorder(15, Color.yellow, Color.black);
		
		//styling combo box
		subjectAvgBox.setFont(font);
		genderAvgBox.setFont(font);
		houseAvgBox.setFont(font);
		
		//styling the buttons
		insertButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		insertButton.setBackground(Color.GRAY);
		insertButton.setForeground(Color.BLACK);
		insertButton.setBorder(lineBorder);
		deleteButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		deleteButton.setBackground(Color.GRAY);
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setBorder(lineBorder);
		updateButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		updateButton.setBackground(Color.GRAY);
		updateButton.setForeground(Color.BLACK);
		updateButton.setBorder(lineBorder);
		clearButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		clearButton.setBackground(Color.GRAY);
		clearButton.setForeground(Color.BLACK);
		clearButton.setBorder(lineBorder);
		newResults.setFont(new Font("Comic Sans", Font.BOLD, 30));
		newResults.setBackground(Color.GRAY);
		newResults.setForeground(Color.BLACK);
		newResults.setBorder(lineBorder);
		removeResults.setFont(new Font("Comic Sans", Font.BOLD, 30));
		removeResults.setBackground(Color.GRAY);
		removeResults.setForeground(Color.BLACK);
		removeResults.setBorder(lineBorder);
		updateResults.setFont(new Font("Comic Sans", Font.BOLD, 30));
		updateResults.setBackground(Color.GRAY);
		updateResults.setForeground(Color.BLACK);
		updateResults.setBorder(lineBorder);
		clearAll.setFont(new Font("Comic Sans", Font.BOLD, 30));
		clearAll.setBackground(Color.GRAY);
		clearAll.setForeground(Color.BLACK);
		clearAll.setBorder(lineBorder);
		barChartButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
		barChartButton.setBackground(Color.GRAY);
		barChartButton.setForeground(Color.BLACK);
		barChartButton.setBorder(lineBorder);
		
		// setting font in textfields
		FNTF.setFont(font);
		StudentIDTF.setFont(font);
		FNTF.setFont(font);
		LNTF.setFont(font);
		ageTF.setFont(font);
		genderTF.setFont(font);
		yearTF.setFont(font);
		avgTF.setFont(font);
		genderAvgTF.setFont(font);
		houseAvgTF.setFont(font);
		resultIDTF.setFont(font);
		StudentIDTF2.setFont(font);
		houseTF.setFont(font);
		subjectTF.setFont(font);
		gradeTF.setFont(font);
		
		// setting font for labels
		StudentIDLabel.setFont(font2);
		FNLabel.setFont(font2);
		LNLabel.setFont(font2);
		ageLabel.setFont(font2);
		genderLabel.setFont(font2);
		yearLabel.setFont(font2);
		StudentIDLabel2.setFont(font2);
		ResultIDLabel.setFont(font2);
		HouseLabel.setFont(font2);
		SubjectLabel.setFont(font2);
		GradeLabel.setFont(font2);
		avgClassLabel.setFont(font2);
		avgGenderLabel.setFont(font2);
		avgHouseLabel.setFont(font2);
		
		// add the 'main' panel to the Internal Frame
		content = getContentPane();
		content.setLayout(null);
		content.setBackground(Color.GRAY);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		// setup details panel and add the components to it
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new GridLayout(6, 2));
		detailsPanel.setBackground(Color.GRAY);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Student Details"));

		detailsPanel.add(StudentIDLabel);
		detailsPanel.add(StudentIDTF);
		detailsPanel.add(FNLabel);
		detailsPanel.add(FNTF);
		detailsPanel.add(LNLabel);
		detailsPanel.add(LNTF);
		detailsPanel.add(ageLabel);
		detailsPanel.add(ageTF);
		detailsPanel.add(genderLabel);
		detailsPanel.add(genderTF);
		detailsPanel.add(yearLabel);
		detailsPanel.add(yearTF);

		// adds the position and size of the panel to the gui
		detailsPanel.setSize(600, 400);
		detailsPanel.setLocation(3, 0);
		content.add(detailsPanel);

		// setup results panel and add the components to it
		resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(8, 3));
		resultsPanel.setBackground(Color.GRAY);
		resultsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Results Data"));
		resultsPanel.add(ResultIDLabel);
		resultsPanel.add(resultIDTF);
		resultsPanel.add(newResults);
		resultsPanel.add(StudentIDLabel2);
		resultsPanel.add(StudentIDTF2);
		resultsPanel.add(removeResults);
		resultsPanel.add(HouseLabel);
		resultsPanel.add(houseTF);
		resultsPanel.add(updateResults);
		resultsPanel.add(SubjectLabel);
		resultsPanel.add(subjectTF);
		resultsPanel.add(clearAll);
		resultsPanel.add(GradeLabel);
		resultsPanel.add(gradeTF);
		resultsPanel.add(barChartButton);
		resultsPanel.add(avgClassLabel);
		resultsPanel.add(subjectAvgBox);
		resultsPanel.add(avgTF);
		resultsPanel.add(avgGenderLabel);
		resultsPanel.add(genderAvgBox);
		resultsPanel.add(genderAvgTF);
		resultsPanel.add(avgHouseLabel);
		resultsPanel.add(houseAvgBox);
		resultsPanel.add(houseAvgTF);

		resultsPanel.setSize(800, 500);
		resultsPanel.setLocation(3, 400);
		content.add(resultsPanel);

		insertButton.setSize(300, 75);
		updateButton.setSize(300, 75);
		deleteButton.setSize(300, 75);
		clearButton.setSize(300, 75);

		insertButton.setLocation(603, 30);
		updateButton.setLocation(603, 210);
		deleteButton.setLocation(603, 120);
		clearButton.setLocation(603, 300);

		// action listeners
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		newResults.addActionListener(this);
		removeResults.addActionListener(this);
		updateResults.addActionListener(this);
		clearAll.addActionListener(this);
		subjectAvgBox.addActionListener(this);
		genderAvgBox.addActionListener(this);
		houseAvgBox.addActionListener(this);
		barChartButton.addActionListener(this);

		content.add(insertButton);
		content.add(updateButton);
		content.add(deleteButton);
		content.add(clearButton);

		// create the panel for the database content
		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));
		ResultsContent.setPreferredScrollableViewportSize(new Dimension(82, 345));
		
		TableofDBContents.setFont(new Font("Comic Sans", Font.BOLD, 20));
		TableofDBContents.setRowHeight(25);
		ResultsContent.setFont(new Font("Comic Sans", Font.BOLD, 20));
		ResultsContent.setRowHeight(25);
		
		resultsContentPanel = new JScrollPane(ResultsContent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultsContentPanel.setBackground(Color.GRAY);
		resultsContentPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Results Content"));
		resultsContentPanel.setSize(1100, 500);
		resultsContentPanel.setLocation(800, 400);

		dbContentsPanel = new JScrollPane(TableofDBContents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.GRAY);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Database Content"));
		dbContentsPanel.setSize(1000, 400);
		dbContentsPanel.setLocation(901, 0);
		content.add(dbContentsPanel);
		content.add(resultsContentPanel);

		setSize(1200, 800);
		setVisible(true);

		TableModel.refreshFromDB(stmt);
		ResultsModel.refreshFromDB(stmt);
	}

	private void initDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/hogwarts?verifyServerCertificate=false&useSSL=true";
			con = DriverManager.getConnection(url, "root", "admin");
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.print("Failed to initialise DB Connection");
			System.out.print(e);
		}
	}

	public void BarChart(ResultSet rs, String title) {
		try {
			// Create dataset
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			String series1 = houseAvgBox.getSelectedItem().toString();
			while (rs.next()) {
				String category = rs.getString(1);
				Number value = rs.getDouble(2);
				dataset.addValue(value, series1, category);
			}
			// Create chart
			JFreeChart chart = ChartFactory.createBarChart("Grades By House", // Chart title
					"Subjects", // X-Axis Label
					"Grades", // Y-Axis Label
					dataset, PlotOrientation.VERTICAL, true, true, false);

			ChartFrame frame = new ChartFrame(title, chart);
			chart.setBackgroundPaint(Color.WHITE);
			frame.pack();
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		Object target = e.getSource();
		if (target == barChartButton) {
			String updateTemp = "SELECT subjects, avg(grade) " +
					"FROM results "+
					"WHERE house = " + "'" + houseAvgBox.getSelectedItem().toString() + "' " + 
					"GROUP BY subjects;";
			try {
				rs = stmt.executeQuery(updateTemp);
				BarChart(rs, houseAvgBox.getSelectedItem().toString());
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			}

		}
		
		if (target == insertButton) {

			try {
				String updateTemp = "INSERT INTO students VALUES ('" + StudentIDTF.getText() + "','" + FNTF.getText()
						+ "','" + LNTF.getText() + "','" + ageTF.getText() + "','" + genderTF.getText() + "','"
						+ yearTF.getText() + "');";
				stmt.executeUpdate(updateTemp);
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
			}
		}

		if (target == clearButton) {
			StudentIDTF.setText("");
			FNTF.setText("");
			LNTF.setText("");
			ageTF.setText("");
			genderTF.setText("");
			yearTF.setText("");

		}

		if (target == deleteButton) {
			try {
				String updateTemp = "DELETE FROM students WHERE student_id = " + StudentIDTF.getText() + ";";
				stmt.executeUpdate(updateTemp);
			} catch (SQLException sqle) {
				System.err.println("Error with delete:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
				ResultsModel.refreshFromDB(stmt);
			}
		}

		if (target == updateButton) {
			try {
				String updateTemp = "UPDATE students SET firstName = '" + FNTF.getText() + "', lastName = " + "'"
						+ LNTF.getText() + "'" + ", age = " + ageTF.getText() + ", gender = " + "'" + genderTF.getText()
						+ "'" + ", study_year = " + "'" + yearTF.getText() + "'" + " where student_id = "
						+ StudentIDTF.getText();

				stmt.executeUpdate(updateTemp);
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				TableModel.refreshFromDB(stmt);
			}
		}

		if (target == newResults) {
			try {
				String updateTemp = "INSERT INTO results VALUES ('" + resultIDTF.getText() + "','"
						+ StudentIDTF2.getText() + "','" + houseTF.getText() + "','" + subjectTF.getText() + "','"
						+ gradeTF.getText() + "');";

				stmt.executeUpdate(updateTemp);

			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				ResultsModel.refreshFromDB(stmt);
			}
		}

		if (target == removeResults) {
			try {
				String updateTemp = "DELETE FROM results WHERE result_id = " + resultIDTF.getText() + ";";
				stmt.executeUpdate(updateTemp);
			} catch (SQLException sqle) {
				System.err.println("Error with delete:\n" + sqle.toString());
			} finally {
				ResultsModel.refreshFromDB(stmt);
			}
		}

		if (target == updateResults) {
			try {
				String updateTemp = "UPDATE results SET grade = '" + gradeTF.getText() + "'" + " WHERE result_id = '"
						+ resultIDTF.getText() + "'";

				stmt.executeUpdate(updateTemp);
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} finally {
				ResultsModel.refreshFromDB(stmt);
			}
		}

		if (target == clearAll) {
			resultIDTF.setText("");
			StudentIDTF2.setText("");
			houseTF.setText("");
			subjectTF.setText("");
			gradeTF.setText("");
			avgTF.setText("");
			genderAvgTF.setText("");
			houseAvgTF.setText("");

		}

		if (target == subjectAvgBox)
			try {
				if (subjectAvgBox.getSelectedItem().equals("Potions")) {
					String updateTemp = "SELECT avg(grade) AS average FROM results WHERE subjects = 'Potions'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						avgTF.setText(rs.getString("average"));
					}

				} else if (subjectAvgBox.getSelectedItem().equals("Charms")) {
					String updateTemp = "SELECT avg(grade) AS average FROM results WHERE subjects = 'Charms'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						avgTF.setText(rs.getString("average"));
					}
				} else if (subjectAvgBox.getSelectedItem().equals("Defence Against the Dark Arts")) {
					String updateTemp = "SELECT avg(grade) AS average FROM results WHERE subjects = 'Defence Against the Dark Arts'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						avgTF.setText(rs.getString("average"));
					}
				}
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} // end combo box 1

		if (target == genderAvgBox)
			try {
				if (genderAvgBox.getSelectedItem().equals("Male")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE gender = 'M'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						genderAvgTF.setText(rs.getString("average"));
					}

				} else if (genderAvgBox.getSelectedItem().equals("Female")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE gender = 'F'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						genderAvgTF.setText(rs.getString("average"));
					}
				}
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} // end combo box 2

		// if (target == drop box name)
		if (target == houseAvgBox)
			try {
				// if(drop box name.getSelectedItem().equals("name in array")
				if (houseAvgBox.getSelectedItem().equals("Gryffindor")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE house = 'Gryffindor'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						houseAvgTF.setText(rs.getString("average"));
					}

				} else if (houseAvgBox.getSelectedItem().equals("Ravenclaw")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE house = 'Ravenclaw'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						houseAvgTF.setText(rs.getString("average"));
					}
				} else if (houseAvgBox.getSelectedItem().equals("Hufflepuff")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE house = 'Hufflepuff'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						houseAvgTF.setText(rs.getString("average"));
					}
				} else if (houseAvgBox.getSelectedItem().equals("Slytherin")) {
					String updateTemp = "SELECT avg(grade) " + "AS average " + "FROM results " + "INNER JOIN students "
							+ "ON results.student_id " + "=" + " students.student_id " + "WHERE house = 'Slytherin'"
							+ "AND subjects = '" + subjectTF.getText() + "'";
					rs = stmt.executeQuery(updateTemp);
					while (rs.next()) {
						houseAvgTF.setText(rs.getString("average"));
					}
				}
			} catch (SQLException sqle) {
				System.err.println("Error with members insert:\n" + sqle.toString());
			} // end combo box 3

	}// end of action performed
}
