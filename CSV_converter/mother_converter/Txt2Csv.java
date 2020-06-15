package mother_converter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.awt.Font;
import javax.swing.JRadioButton;


public class Txt2Csv extends JFrame implements ActionListener {

	JButton btn = new JButton("Convert");
	JButton btncancel = new JButton("Close");
	JButton TXTload = new JButton("txt Load");
	JRadioButton rdbtnNewRadioButton = new JRadioButton("Test time enable");
	
	JLabel noticeTXT = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	StringBuilder TXTloadbuffer = new StringBuilder(); // MAX cap is 12383875 line

	String[] dTXTfName = new String[32];
	String[] dTXTfdirectory = new String[32];
	String[] dTXTffile = new String[32];
	String[] dTXTSavefName = new String[32];
	String[][] dTXTSavefName_split = new String[32][];

	File[] select_multi_files;

	int rows;
	
	public Txt2Csv() {
		super("TXTtoCSV V04");

		int jb;

		for (jb = 0; jb < 32; jb++) {

			dTXTfName[jb] = "";
			dTXTfdirectory[jb] = "";
			dTXTffile[jb] = "";
			dTXTSavefName[jb] = "";

		}

		TXTloadbuffer.setLength(0);
		btn.setFont(new Font("Arial", Font.PLAIN, 12));

		btn.setBounds(26, 112, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel TXTfile = new JLabel("TXTfile");
		TXTfile.setFont(new Font("Arial", Font.PLAIN, 12));
		TXTfile.setBounds(23, 28, 120, 15);
		getContentPane().add(TXTfile);
		btncancel.setFont(new Font("Arial", Font.PLAIN, 12));

		btncancel.setBounds(136, 111, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(29, 71, 204, 15);
		getContentPane().add(noticeTXT);
		TXTload.setFont(new Font("Arial", Font.PLAIN, 12));

		TXTload.setBounds(133, 24, 97, 23);
		getContentPane().add(TXTload);
		TXTload.addActionListener(this);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(26, 93, 207, 15);
		getContentPane().add(fileEmpty);

		JLabel lblMainProgramJb = new JLabel("Main program: JB JEON");
		lblMainProgramJb.setFont(new Font("Arial", Font.PLAIN, 10));
		lblMainProgramJb.setBounds(105, 144, 123, 15);
		getContentPane().add(lblMainProgramJb);

		JLabel lblSubProgramJh = new JLabel("Sub program: JH KANG, JH CHOI");
		lblSubProgramJh.setFont(new Font("Arial", Font.PLAIN, 10));
		lblSubProgramJh.setBounds(67, 159, 158, 15);
		getContentPane().add(lblSubProgramJh);
		

		rdbtnNewRadioButton.setBounds(65, 48, 121, 23);
		getContentPane().add(rdbtnNewRadioButton);
		
		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 350, 275, 223);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == TXTload) {

			int jb;

			for (jb = 0; jb < 32; jb++) {

				dTXTfName[jb] = "";
				dTXTfdirectory[jb] = "";
				dTXTffile[jb] = "";
				dTXTSavefName[jb] = "";

			}

			try {

				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);

				dialog.setDirectory(".");
				dialog.setMultipleMode(true);
				dialog.setVisible(true);

				String directory = dialog.getDirectory();

				select_multi_files = dialog.getFiles();

				if (select_multi_files != null && select_multi_files.length > 0) {

					int i;

					for (i = 0; i < select_multi_files.length; i++) {

						dTXTfdirectory[i] = directory;
						dTXTffile[i] = select_multi_files[i].getName();

						if (select_multi_files.length == 1) {

							//dTXTfName[i] = dTXTfdirectory[i] + dTXTffile[i];
							
							dTXTfName[i] = dTXTfdirectory[i].concat(dTXTffile[i]);
							//dTXTfName[i].concat(dTXTffile[i]);

						} else {

							//dTXTfName[i] = dTXTfdirectory[i] + "\\" + dTXTffile[i];
							
							dTXTfName[i] = dTXTfdirectory[i].concat("\\").concat(dTXTffile[i]);
							//dTXTfName[i].concat("\\");
							//dTXTfName[i].concat(dTXTffile[i]);
							
						}

						//System.out.println("dTXTffile[i]:" + i + " " + dTXTffile[i]);
						//System.out.println("dTXTfdirectory[i]:" + i + " " + dTXTfdirectory[i]);
						//System.out.println("dTXTfName[i]:" + i + " " + dTXTfName[i]);

						if (dTXTfName[i].isEmpty()) {

							noticeTXT.setText("");
							fileEmpty.setText("");
							noticeTXT.setText("        not loaded TXT File");

						} else {

							noticeTXT.setText("");
							fileEmpty.setText("");
							noticeTXT.setText("            loaded TXT File");

						}

					}
				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}

			// File f = new File(dfName);

			// long fileSize = (long) f.length();
			// if too big file size is need long integer

			// Giga byte file load on PC memory is impossible, change method to data
			// streaming.

		} else if (e.getSource() == btn) {

			int i;
			int j;
			int x;
			int y;
			int z;
			int l;
			int k;
			int cnt;


			int head_flag;
			int loopcount;
			int linesplit_count;
			int head_end_count;
			int body_end_count;
			int ex_body_end_count;
			int body_loop_count;
			int mainbody_end_count;
			int engage_main_body;
			int bin_engage;
			int ignore_item;
			int bining_end_count;
			int line_done;
			int block_end;
			int data_align;
			int first_data_en;
			int tail_count;
			int date_and_time_flag;

			String hadler_id;
			String ATE_id;
			String date_data;
			String Lot_id;
			
			String line;
			String line_sub_replace;			

			int[] site_counter = new int[16];
			String[] SITE32_array = new String[16];
			int[] first_head_information = new int[16];

			int isite;
			int bin_isite;
			long diff;
			long sec;
			int time_count;
			
			String[] site_Hbin_counter = new String[16];
			String[] site_Sbin_counter = new String[16];
			String[] site_PF_counter = new String[16];
			
			SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
			Date d1 = null;
			Date d2 = null;

			Date[] time = null;
			time = new Date[10000];

			String[][] head_data = null;
			head_data = new String[100][35];

			String[][] body_data = null;
			body_data = new String[10000][35];

			String[][] main_body_data = null;
			main_body_data = new String[10000][35];
			
			String[] linesplit = new String[35];
		
			try {

				for (j = 0; j < select_multi_files.length; j++) {

					//dTXTSavefName[j] = "";
					TXTloadbuffer.setLength(0);

					for (i = 0; i < 10000; i++) {
						
						for(k = 0 ; k < 35; k++)  body_data[i][k] = null;
						
					}


					if (dTXTfName[j].isEmpty()) {

						noticeTXT.setText("");
						fileEmpty.setText("");
						fileEmpty.setText("   TXT file is empty.");

					} else {

						noticeTXT.setText("");
						fileEmpty.setText("");
						//noticeTXT.setText(" processing..............");

						dTXTSavefName[j] = dTXTfName[j];

						//setTitle(dTXTffile[j] + " - TXT save..");
						//System.out.println("dTXTSavefName[j]:" + j + " " + dTXTSavefName[j]);

						setTitle("Processing...");

						FileInputStream ftstream = new FileInputStream(dTXTfName[j]);
						BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

						
						cnt = 0;
						rows = 0;

						head_flag = 0;
						loopcount = 1;
						linesplit_count = 0;
						head_end_count = 0;
						body_end_count = 0;
						ex_body_end_count = 0;
						body_loop_count = 0;
						mainbody_end_count = 0;
						engage_main_body = 0;
						bin_engage = 0;
						ignore_item = 0;
						bining_end_count = 0;
						line_done = 0;
						block_end = 0;
						data_align = 0;
						first_data_en = 0;
						date_and_time_flag = 0;
						
						Lot_id = "Unknown";
						date_data = "Unknown";
						hadler_id = "Unknown";
						ATE_id = "Unknown";

						isite = 0;
						bin_isite = 0;
						diff = 0;
						sec = 0;
						time_count = 0;
						tail_count = 30;
						

						for (i = 0; i < SITE32_array.length; i++) {

							SITE32_array[i] = String.valueOf(i);

						}

						for (i = 0; i < site_counter.length; i++) {

							site_counter[i] = 999;
							first_head_information[i] = 0;
						}

						 //System.out.println("NEW ERR A-3");
						
						BufferedWriter writer = new BufferedWriter(new FileWriter(dTXTSavefName[j] + ".csv"));
					
						while ((line = reader.readLine()) != null) {
		
							//System.out.println("NEW ERR A-4");
							

							
							if (line.contains("Device#")) {
								
								head_flag = 1;

								if (head_end_count == 0) {
									
									head_end_count = rows;
									
								}

							}

							if(rdbtnNewRadioButton.isSelected()) {
								
								//System.out.println("NEW ERR A-5-1");
								
								if (line.contains("Date:") && line.contains("Time:")) {
															
									
									 //System.out.println("previous row : "+rows);
									 //System.out.println("previous head_end_count : "+head_end_count);
									 //System.out.println("previous exbody_end_count : "+ex_body_end_count);
									 //System.out.println("previous body_end_count : "+body_end_count);
									 //System.out.println("previous body_loop_count : "+body_loop_count);
									 //System.out.println("previous engage_main_body : "+engage_main_body);
									 //System.out.println("previous line_done : "+line_done);
									 							
									 writer.append(TXTloadbuffer.toString());;
									 writer.flush();
									 TXTloadbuffer.setLength(0);
									
									date_and_time_flag = 1; 
									 
									line_done = 1;
									engage_main_body = engage_main_body + 1;
									data_align = 1;
									ex_body_end_count = body_end_count;
									body_loop_count = rows - ex_body_end_count;
									body_end_count = rows;

									 //System.out.println("post row : "+rows);
									 //System.out.println("post head_end_count : "+head_end_count);
									 //System.out.println("post exbody_end_count : "+ex_body_end_count);
									 //System.out.println("post body_end_count : "+body_end_count);
									 //System.out.println("post body_loop_count : "+body_loop_count);
									 //System.out.println("post engage_main_body : "+engage_main_body);
									 //System.out.println("post line_done : "+line_done);
									
								}
								
							} else {
								
								//System.out.println("NEW ERR A-5-2");
							
								if (line.contains("=========================================================================")) {

									 //System.out.println("previous row : "+rows);
									 //System.out.println("previous head_end_count : "+head_end_count);
									 //System.out.println("previous exbody_end_count : "+ex_body_end_count);
									 //System.out.println("previous body_end_count : "+body_end_count);
									 //System.out.println("previous body_loop_count : "+body_loop_count);
									 //System.out.println("previous engage_main_body : "+engage_main_body);
									 //System.out.println("previous line_done : "+line_done);
									 							
									 writer.append(TXTloadbuffer.toString());;
									 writer.flush();
									 TXTloadbuffer.setLength(0);
									 
									 
									line_done = 1;
									engage_main_body = engage_main_body + 1;
									data_align = 1;
									ex_body_end_count = body_end_count;
									body_loop_count = rows - ex_body_end_count;
									body_end_count = rows;

									 //System.out.println("post row : "+rows);
									 //System.out.println("post head_end_count : "+head_end_count);
									 //System.out.println("post exbody_end_count : "+ex_body_end_count);
									 //System.out.println("post body_end_count : "+body_end_count);
									 //System.out.println("post body_loop_count : "+body_loop_count);
									 //System.out.println("post engage_main_body : "+engage_main_body);
									 //System.out.println("post line_done : "+line_done);
									
								}
								
							}
							


							 //System.out.println("NEW ERR A-6");
							
							for (i = 0; i < line.length(); i++) {

								if (line.contains("  ")) {

									line = line.replace("  ", " ");

								} else {

									break;

								}

							}
							
							//System.out.println("NEW ERR A-7");
							
							//for (i = 0; i < line.length(); i++) {
							//	if (line.charAt(i) == ' ' || line.charAt(i) == ',')
							//		linesplit_count = linesplit_count + 1;
							//}

							 //System.out.println("NEW ERR A-8");
							//String[] linesplit = new String[linesplit_count];
							
							 //System.out.println("NEW ERR A-9");
							 
							linesplit = line.split("\\s|,");
							
							//for(i = 0; i < linesplit.length - 12; i++) {
							
							//	System.out.println("line_split "+i+" : "+linesplit[i]);

							//}
							
							//System.out.println("rows : "+rows);
							
							for (y = 0; y < linesplit.length; y++) {

								if (head_flag == 0) {

									head_data[rows][y] = linesplit[y];

								} else {

									if (engage_main_body >= 1) {

										if (first_data_en == 0) {

											body_data[rows - head_end_count][y] = linesplit[y];

										} else {

											if (data_align == 1) {

												body_data[rows - ex_body_end_count][y] = linesplit[y];

											} else {

												body_data[rows - body_end_count][y] = linesplit[y];

											}

										}

									} else {

										body_data[rows - head_end_count][y] = linesplit[y];

									}

								}

							}

							//System.out.println("NEW ERR A-10");
							
							line = null;
							
							for(i = 0; i < linesplit.length ; i++) {
								
								linesplit[i] = null;
								
							}
							
							 //System.out.println("NEW ERR A");

							if (rows != 0 && rows == head_end_count) {

								TXTloadbuffer.append("PGM Name");
								TXTloadbuffer.append(",");
								TXTloadbuffer.append(head_data[2][3]);
								TXTloadbuffer.append("\n");
								
								//System.out.println("NEW ERR B");

							} else if (rows != 0 && rows == body_loop_count && engage_main_body == 1
									&& line_done == 1) {

								//System.out.println("NEW ERR C");
							
								TXTloadbuffer.append("SerialNumber"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Test Pass/Fail Status"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "SBIN"); TXTloadbuffer.append( ",");
								TXTloadbuffer.append( "HBIN");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Site");TXTloadbuffer.append( ",");TXTloadbuffer.append( "TesterID");TXTloadbuffer.append( ",");TXTloadbuffer.append( "HanderID");TXTloadbuffer.append(",");
								TXTloadbuffer.append( "StartTime");TXTloadbuffer.append( ",");TXTloadbuffer.append( "EndTime");TXTloadbuffer.append( ",");TXTloadbuffer.append( "TestTime");TXTloadbuffer.append( ",");TXTloadbuffer.append( "LotNumber");
								
								
								for (i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][4] == null || body_data[i][4].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")
											|| body_data[i][1].equals("Device#:")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										TXTloadbuffer.append("\n");
										break;

									} else {

										if (i == 0) {

											TXTloadbuffer.append(",");
											TXTloadbuffer.append(body_data[i][3]);
											
										} else {

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												TXTloadbuffer.append("," + body_data[i][3] + " " + body_data[i][4]);

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append("," + body_data[i][3] + " " + body_data[i][4]);

												}

											}

										}
									}

								}

								//System.out.println("NEW ERR D");

								TXTloadbuffer.append("Upper Limit ----->");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");
								TXTloadbuffer.append( ",");

								for (i = 0; i < body_loop_count + 1; i++) {

									//System.out.println("NEW ERR D-1");

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][4] == null || body_data[i][4].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")
											|| body_data[i][1].equals("Device#:")) {

										//System.out.println("NEW ERR D-2");

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										//System.out.println("NEW ERR D-3");

										TXTloadbuffer.append("\n");
										break;

									} else {

										//System.out.println("NEW ERR D-4");

										if (i == 0) {

											//System.out.println("NEW ERR D-5");

											if (body_data[i][10].equals("(F)")) {

												TXTloadbuffer.append(",");
												TXTloadbuffer.append(body_data[i][11]);
												
											} else {

												TXTloadbuffer.append(",");
												TXTloadbuffer.append(body_data[i][10]);
												
												}

										} else {

											//System.out.println("body_data[i][1]:" + body_data[i][1]);
											//System.out.println("body_data[i][2]:" + body_data[i][2]);
											//System.out.println("body_data[i][3]:" + body_data[i][3]);
											//System.out.println("body_data[i][4]:" + body_data[i][4]);
											
											//System.out.println("NEW ERR D-6");

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												//System.out.println("NEW ERR D-6-1");

												if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
																													// "
																													// "
																													// exeception
																													// for
																													// cygnus
																													// diode
													//System.out.println("NEW ERR D-6-2"); // item

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][11].equals("(F)")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][12]);
															
														} else {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][11]);
															
														}

													}

												} else if (body_data[i][4].equals("-1")) {

													//System.out.println("NEW ERR D-6-3");

													if (body_data[i][12] == null) {

														//System.out.println("NEW ERR D-6-4");

														if (body_data[i][5].equals("N/A")) {

															for (x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][8]);
																	
																} else {

																	if ((body_data[i][7].contains("u")
																			|| body_data[i][7].contains("m")
																			|| body_data[i][7].contains("A")
																			|| body_data[i][7].contains("V"))
																			&& !body_data[i][7].contains("N/A")) {

																		TXTloadbuffer.append(",");
																		TXTloadbuffer.append(body_data[i][8]);
																		
																	} else {

																		TXTloadbuffer.append(",");
																		TXTloadbuffer.append(body_data[i][7]);
																		
																	}

																}

															}

														} else {

															for (x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][8]);
																	
																} else {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][7]);
																	
																}

															}

														}

													} else {

														//System.out.println("NEW ERR D-6-5");

														if (body_data[i][5].equals("N/A")) {

															for (x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][8].equals("(F)")) {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][9]);
																	
																} else {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][8]);
																	
																}

															}

														} else {

															for (x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][10]);
																	
																} else {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][9]);
																	
																}

															}

														}

													}

												} else if (body_data[i][6].equals("N/A")) {

													//System.out.println("NEW ERR D-7");

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][9].equals("(F)")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][10]);
															
														} else {

															if (body_data[i][8].equals("N/A")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][8]);
																
															} else {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][9]);
																
															}

														}

													}

												} else {

													//System.out.println("NEW ERR D-7-1");

													for (x = i - 2; x < i; x++) {
														
														//System.out.println("NEW ERR D-7-2");

														//System.out.println("body_data[i][3]:" + body_data[i][3]);
														//System.out.println("body_data[x][3]:" + body_data[x][3]);
														//System.out.println("body_data[i][4]:" + body_data[i][4]);
														//System.out.println("body_data[x][4]:" + body_data[x][4]);

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {
															
															//System.out.println("NEW ERR D-7-3");
															
															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														//System.out.println("body_data[i][10]:" + body_data[i][10]);

														if (body_data[i][10] == null) {
															
															//System.out.println("NEW ERR D-7-4");
															
															TXTloadbuffer.append(",");

														} else if (body_data[i][10].equals("(F)")) {
															
															//System.out.println("NEW ERR D-7-5");
															
															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][11]);
															
														} else {
															
															//System.out.println("NEW ERR D-7-6");
															
															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][10]);
														}

													}

												}

												//System.out.println("NEW ERR D-6-1-end");
												
											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												//System.out.println("NEW ERR D-8");
												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												//System.out.println("NEW ERR D-9");

												if (body_data[i][12] == null) {

													if (body_data[i][5].equals("N/A")) {

														for (x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][8]);
																
															} else {

																if ((body_data[i][7].contains("u")
																		|| body_data[i][7].contains("m")
																		|| body_data[i][7].contains("A")
																		|| body_data[i][7].contains("V"))
																		&& !body_data[i][7].contains("N/A")) {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][8]);
																	
																} else {

																	TXTloadbuffer.append(",");
																	TXTloadbuffer.append(body_data[i][7]);
																	
																}

															}

														}

													} else {

														for (x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][8]);
																
															} else {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][7]);
																
															}

														}

													}

												} else {

													if (body_data[i][5].equals("N/A")) {

														for (x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][8].equals("(F)")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][9]);
																
															} else {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][8]);
																
															}

														}

													} else {

														for (x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][10]);
																
															} else {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][9]);
																
															}

														}

													}

												}

											} else if (body_data[i][6].equals("N/A")) {

												//System.out.println("NEW ERR D-10");

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][9].equals("(F)")) {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][10]);
														
													} else {

														if (body_data[i][8].equals("N/A")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][8]);
															
														} else {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][9]);
															
														}

													}

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
																														// "
																														// "
																														// exeception
																														// for
																														// cygnus
																														// diode
																														// item

												//System.out.println("NEW ERR D-11");

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][11].equals("(F)")) {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][12]);
														
													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][11]);
														
													}

												}

											} else {

												//System.out.println("NEW ERR D-12");

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][10].equals("(F)")) {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][11]);

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][10]);
													}

												}

											}

											
										}
									
			
									}

							
								}

								//System.out.println("NEW ERR E");

								TXTloadbuffer.append("Lower Limit ----->");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");
								TXTloadbuffer.append( ",");

								for (i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][4] == null || body_data[i][4].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")
											|| body_data[i][1].equals("Device#:")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										TXTloadbuffer.append("\n");
										break;

									} else {

										if (i == 0) {

											TXTloadbuffer.append(",");
											TXTloadbuffer.append(body_data[i][6]);
											
										} else {

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
													// "
													// "
													// exeception
													// for
													// cygnus
													// diode
													// item

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][8]);
														
													}

												} else if (body_data[i][4].equals("-1")) {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][5]);
														
													}

												} else if (body_data[i][6].equals("N/A")) {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][6]);
														
													}

												} else {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][6]);
														
													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][5]);
													
												}

											} else if (body_data[i][6].equals("N/A")) {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][6]);												

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
												// "
												// "
												// exeception
												// for
												// cygnus
												// diode
												// item

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][8]);
												}

											} else {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][6]);
												}

											}

										}
									}

								}

								//System.out.println("NEW ERR F");

								TXTloadbuffer.append("Measurement Limit ----->");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");TXTloadbuffer.append( ",");
								TXTloadbuffer.append( ",");TXTloadbuffer.append( "sec");TXTloadbuffer.append( ",");
								
								
								for (i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][4] == null || body_data[i][4].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")
											|| body_data[i][1].equals("Device#:")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										TXTloadbuffer.append("\n");
										break;

									} else {

										if (i == 0) {

											TXTloadbuffer.append(",");
											TXTloadbuffer.append(body_data[i][7]);
											
										} else {

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												if (body_data[i][4].equals("-1")) {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][7].equals("N/A")
																|| body_data[i][8].equals("N/A")) {

															TXTloadbuffer.append(",");

														} else {

															if (body_data[i][6].equals("ohm")
																	|| body_data[i][6].equals("mohm")
																	|| body_data[i][6].equals("%")
																	|| body_data[i][6].equals("pA")
																	|| body_data[i][6].equals("nA")
																	|| body_data[i][6].equals("uA")
																	|| body_data[i][6].equals("mA")
																	|| body_data[i][6].equals("A")
																	|| body_data[i][6].equals("pV")
																	|| body_data[i][6].equals("nV")
																	|| body_data[i][6].equals("uV")
																	|| body_data[i][6].equals("mV")
																	|| body_data[i][6].equals("V")
																	|| body_data[i][6].equals("P/F")
																	|| body_data[i][6].equals("mOhm")
																	|| body_data[i][6].equals("S")
																	|| body_data[i][6].equals("mS")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][6]);
																
															} else if (body_data[i][9].equals("ohm")
																	|| body_data[i][9].equals("mohm")
																	|| body_data[i][9].equals("%")
																	|| body_data[i][9].equals("pA")
																	|| body_data[i][9].equals("nA")
																	|| body_data[i][9].equals("uA")
																	|| body_data[i][9].equals("mA")
																	|| body_data[i][9].equals("A")
																	|| body_data[i][9].equals("pV")
																	|| body_data[i][9].equals("nV")
																	|| body_data[i][9].equals("uV")
																	|| body_data[i][9].equals("mV")
																	|| body_data[i][9].equals("V")
																	|| body_data[i][9].equals("P/F")
																	|| body_data[i][9].equals("mOhm")
																	|| body_data[i][9].equals("S")
																	|| body_data[i][9].equals("mS")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][9]);
																
															} else {

																TXTloadbuffer.append(",");

															}

														}

													}

												} else if (body_data[i][6].equals("N/A")) {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][8].equals("N/A")
																|| body_data[i][9].equals("N/A")) {

															TXTloadbuffer.append(",");

														} else {

															if (body_data[i][6].equals("ohm")
																	|| body_data[i][6].equals("mohm")
																	|| body_data[i][6].equals("%")
																	|| body_data[i][6].equals("pA")
																	|| body_data[i][6].equals("nA")
																	|| body_data[i][6].equals("uA")
																	|| body_data[i][6].equals("mA")
																	|| body_data[i][6].equals("A")
																	|| body_data[i][6].equals("pV")
																	|| body_data[i][6].equals("nV")
																	|| body_data[i][6].equals("uV")
																	|| body_data[i][6].equals("mV")
																	|| body_data[i][6].equals("V")
																	|| body_data[i][6].equals("P/F")
																	|| body_data[i][6].equals("mOhm")
																	|| body_data[i][6].equals("S")
																	|| body_data[i][6].equals("mS")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][6]);
																
															} else if (body_data[i][10].equals("ohm")
																	|| body_data[i][10].equals("mohm")
																	|| body_data[i][10].equals("%")
																	|| body_data[i][10].equals("pA")
																	|| body_data[i][10].equals("nA")
																	|| body_data[i][10].equals("uA")
																	|| body_data[i][10].equals("mA")
																	|| body_data[i][10].equals("A")
																	|| body_data[i][10].equals("pV")
																	|| body_data[i][10].equals("nV")
																	|| body_data[i][10].equals("uV")
																	|| body_data[i][10].equals("mV")
																	|| body_data[i][10].equals("V")
																	|| body_data[i][10].equals("P/F")
																	|| body_data[i][10].equals("mOhm")
																	|| body_data[i][10].equals("S")
																	|| body_data[i][10].equals("mS")) {

																TXTloadbuffer.append(",");
																TXTloadbuffer.append(body_data[i][10]);
																
															} else {

																TXTloadbuffer.append(",");

															}

														}

													}

												} else if (body_data[i][3].equals("D1")
														|| body_data[i][3].equals("D2")) { // added
													// "
													// "
													// exeception
													// for
													// cygnus
													// diode
													// item

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][12]);
													}

												} else {

													for (x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[i][7]);

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][7].equals("N/A")
															|| body_data[i][8].equals("N/A")) {

														TXTloadbuffer.append(",");

													} else {

														if (body_data[i][6].equals("ohm")
																|| body_data[i][6].equals("mohm")
																|| body_data[i][6].equals("%")
																|| body_data[i][6].equals("pA")
																|| body_data[i][6].equals("nA")
																|| body_data[i][6].equals("uA")
																|| body_data[i][6].equals("mA")
																|| body_data[i][6].equals("A")
																|| body_data[i][6].equals("pV")
																|| body_data[i][6].equals("nV")
																|| body_data[i][6].equals("uV")
																|| body_data[i][6].equals("mV")
																|| body_data[i][6].equals("V")
																|| body_data[i][6].equals("P/F")
																|| body_data[i][6].equals("mOhm")
																|| body_data[i][6].equals("S")
																|| body_data[i][6].equals("mS")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][6]);

														} else if (body_data[i][9].equals("ohm")
																|| body_data[i][9].equals("mohm")
																|| body_data[i][9].equals("%")
																|| body_data[i][9].equals("pA")
																|| body_data[i][9].equals("nA")
																|| body_data[i][9].equals("uA")
																|| body_data[i][9].equals("mA")
																|| body_data[i][9].equals("A")
																|| body_data[i][9].equals("pV")
																|| body_data[i][9].equals("nV")
																|| body_data[i][9].equals("uV")
																|| body_data[i][9].equals("mV")
																|| body_data[i][9].equals("V")
																|| body_data[i][9].equals("P/F")
																|| body_data[i][9].equals("mOhm")
																|| body_data[i][9].equals("S")
																|| body_data[i][9].equals("mS")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][9]);
															
														} else {

															TXTloadbuffer.append(",");

														}

													}

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][8].equals("N/A")
															|| body_data[i][9].equals("N/A")) {

														TXTloadbuffer.append(",");

													} else {

														if (body_data[i][6].equals("ohm")
																|| body_data[i][6].equals("mohm")
																|| body_data[i][6].equals("%")
																|| body_data[i][6].equals("pA")
																|| body_data[i][6].equals("nA")
																|| body_data[i][6].equals("uA")
																|| body_data[i][6].equals("mA")
																|| body_data[i][6].equals("A")
																|| body_data[i][6].equals("pV")
																|| body_data[i][6].equals("nV")
																|| body_data[i][6].equals("uV")
																|| body_data[i][6].equals("mV")
																|| body_data[i][6].equals("V")
																|| body_data[i][6].equals("P/F")
																|| body_data[i][6].equals("mOhm")
																|| body_data[i][6].equals("S")
																|| body_data[i][6].equals("mS")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][6]);

														} else if (body_data[i][10].equals("ohm")
																|| body_data[i][10].equals("mohm")
																|| body_data[i][10].equals("%")
																|| body_data[i][10].equals("pA")
																|| body_data[i][10].equals("nA")
																|| body_data[i][10].equals("uA")
																|| body_data[i][10].equals("mA")
																|| body_data[i][10].equals("A")
																|| body_data[i][10].equals("pV")
																|| body_data[i][10].equals("nV")
																|| body_data[i][10].equals("uV")
																|| body_data[i][10].equals("mV")
																|| body_data[i][10].equals("V")
																|| body_data[i][10].equals("P/F")
																|| body_data[i][10].equals("mOhm")
																|| body_data[i][10].equals("S")
																|| body_data[i][10].equals("mS")) {

															TXTloadbuffer.append(",");
															TXTloadbuffer.append(body_data[i][10]);
															
														} else {

															TXTloadbuffer.append(",");

														}

													}

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
												// "
												// "
												// exeception
												// for
												// cygnus
												// diode
												// item

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][12]);
													
												}

											} else {

												for (x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[i][7]);
													
												}

											}

										}
									}

								}

								//System.out.println("NO HEAD A?");

								for (i = 0; i < head_end_count; i++) {

									// System.out.println("NO HEAD A-1?");
									// System.out.println("head_end_count:"+head_end_count+":i:"+i);
									// System.out.println("head_data[i].length:"+head_data[i].length+":i:"+i);
									// System.out.println("head_data[i][1]:"+head_data[i][1]);

									if (head_data[i][1] == null) {

										// do nothing

									} else if (head_data[i][0].equals("Datalog") && head_data[i][1].equals("report")) {

										d1 = f.parse(head_data[i + 1][1]);

									} else if (head_data[i][1].equals("Lot:")) {

										Lot_id = head_data[i][2];

									} else if (head_data[i][1].equals("Node") && head_data[i][2].equals("Name:")) {

										ATE_id = head_data[i][3];

									} else if (head_data[i][1].equals("HandID:")) {

										hadler_id = head_data[i][2];

									} else {

										// do nothing
									}

								}

								//System.out.println("NO HEAD B?");

								for (i = 0; i < 70; i++) {

									// System.out.println("NO HEAD B-1?");

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin") || body_data[i][1].equals("Device#:")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										break;

									} else {

										// if (body_data[i][0].equals("BARCODE") && !body_data[i][3].equals("0")) {
										if (body_data[i][0].equals("BARCODE")) {
											// site_counter[isite] = Integer.parseInt(body_data[i][2]);

											if (body_data[i][3].equals("0")) {

												site_counter[Integer.parseInt(body_data[i][2])] = 999;

											} else {

												site_counter[Integer.parseInt(body_data[i][2])] = Integer
														.parseInt(body_data[i][2]);

											}

											// isite = isite + 1;

										} else { // added for non site information case

											for (z = 0; z < site_counter.length; z++) {

												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {

													site_counter[z] = Integer.parseInt(body_data[i][2]);

												}

											}

										}
									}

								}

								//System.out.println("NO HEAD C?");
								
								if((body_loop_count - head_end_count) < tail_count) tail_count = 10;
								
								
								for (i = (body_loop_count - head_end_count) - tail_count; i < (body_loop_count - head_end_count) + 1; i++) {

									//System.out.println("NO HEAD C-1?");

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin") || body_data[i][1].equals("Device#:")) {
										//System.out.println("NO HEAD C-3?");
										// do nothing

									} else {
										//System.out.println("NO HEAD C-4?");
										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {
											//System.out.println("NO HEAD C-5?");
											bin_engage = 1;

										}

										if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {
											//System.out.println("NO HEAD C-9?");
											date_data = body_data[i][1];

											d2 = f.parse(body_data[i][3]);
										}

										for (bin_isite = 0; bin_isite < site_counter.length; bin_isite++) {

											if (bin_engage == 1 && body_data[i][1]
													.equals(String.valueOf(site_counter[bin_isite]))) {
												//System.out.println("NO HEAD C-6?");
												site_Hbin_counter[bin_isite] = body_data[i][2];
												site_Sbin_counter[bin_isite] = body_data[i][3];

												if (site_Hbin_counter[bin_isite].equals("1")) {
													//System.out.println("NO HEAD C-7?");
													site_PF_counter[bin_isite] = "PASS";

												} else {
													//System.out.println("NO HEAD C-8?");
													site_PF_counter[bin_isite] = "FAIL";

												}

												// bin_isite = bin_isite + 1;

											}

										}

									}

									if (i == (body_loop_count - head_end_count))
										bin_engage = 0;
									//System.out.println("NO HEAD C-10?");
								}

								//System.out.println("NO HEAD D?");

								for (i = 0; i < site_counter.length; i++) {

									//System.out.println("NO HEAD D-1?");

									if (site_counter[i] == 999) {
										//System.out.println("NO HEAD D-2?");
									} else {

										for (x = 0; x < body_loop_count + 1; x++) {

											//System.out.println("NO HEAD D-3?");

											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")
													|| body_data[x][1].equals("Device#:")) {

												//System.out.println("NO HEAD D-3?");

												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {

												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												//System.out.println("d1:"+f.format(d1));
												//System.out.println("d2:"+f.format(d2));
												//System.out.println("d1.getTime():"+d1.getTime());
												//System.out.println("d2.getTime():"+d2.getTime());

												//System.out.println("NO HEAD D-4?");

												if (d1 == null || d2 == null) {
												
													TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_Sbin_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ",");TXTloadbuffer.append( hadler_id); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");
													TXTloadbuffer.append( Lot_id);
													

													first_head_information[i] = 1;

												} else {

													diff = d1.getTime() - d2.getTime();
													sec = diff / 1000;

													TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_Sbin_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ","); TXTloadbuffer.append( hadler_id); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( date_data); TXTloadbuffer.append( " "); TXTloadbuffer.append( f.format(d1)); TXTloadbuffer.append( ","); TXTloadbuffer.append( date_data); TXTloadbuffer.append( " ");
													TXTloadbuffer.append( f.format(d2)); TXTloadbuffer.append( ","); TXTloadbuffer.append( Math.abs(sec)); TXTloadbuffer.append( ","); TXTloadbuffer.append( Lot_id);
													
													
													first_head_information[i] = 1;
												}

											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												//System.out.println("NO HEAD D-5?");

												if (first_head_information[i] == 0) {

													//System.out.println("NO HEAD D-6?");

													TXTloadbuffer.append("Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_Sbin_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");
													TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ");"); TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( " ");
													TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown");
			
													first_head_information[i] = 1;
												}

												if (body_data[x][4].equals("-1")) {

													//System.out.println("NO HEAD D-7?");

													if (body_data[x][5].equals("N/A")) {

														//System.out.println("NO HEAD D-8?");

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][6]);
														
													} else if (body_data[x][6].equals("mohm")
															|| body_data[x][6].equals("ohm")
															|| body_data[x][6].equals("%")
															|| body_data[x][6].equals("m")
															|| body_data[x][6].equals("mA")
															|| body_data[x][6].equals("mV")
															|| body_data[x][6].equals("V")
															|| body_data[x][6].equals("A")
															|| body_data[x][6].equals("uA")
															|| body_data[x][6].equals("uV")
															|| body_data[x][6].equals("nA")
															|| body_data[x][6].equals("nV")
															|| body_data[x][6].equals("P/F")
															|| body_data[x][6].equals("mOhm")
															|| body_data[x][6].equals("S")
															|| body_data[x][6].equals("mS")) {

														//System.out.println("NO HEAD D-9?");

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][7]);
														
													} else {

														//System.out.println("NO HEAD D-10?");

														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][6]);
													}

												} else if (body_data[x][6].equals("N/A")) {

													//System.out.println("NO HEAD D-11?");

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][7]);
													
												} else if (body_data[x][3].equals("D1")
														|| body_data[x][3].equals("D2")) {

													//System.out.println("NO HEAD D-12?");

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][9]);
													
												} else {

													//System.out.println("NO HEAD D-13?");

													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][8]);
												}

											}

										}

										//System.out.println("NO HEAD D-14?");

										TXTloadbuffer.append("\n");

									}

								}

								//System.out.println("NO HEAD E?");

								for (l = 0; l < body_loop_count + 1; l++) {

									//System.out.println("NO HEAD E-1?");
									
									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;

									}
								}

								for (l = 0; l < site_counter.length; l++) {

									//System.out.println("NO HEAD E-2?");
									
									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (i = 0; i < site_Hbin_counter.length; i++) {

									//System.out.println("NO HEAD E-3?");
									
									site_Hbin_counter[i] = null;

								}

								for (i = 0; i < site_Sbin_counter.length; i++) {

									//System.out.println("NO HEAD E-4?");
									
									site_Sbin_counter[i] = null;

								}

								for (i = 0; i < site_PF_counter.length; i++) {

									//System.out.println("NO HEAD E-5?");
									
									site_PF_counter[i] = null;

								}
					
								//System.out.println("NO HEAD E-6?");
								
								// isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;
								tail_count = 30;
								
								//System.out.println("NO HEAD E-7?");
					

							} else if (rows != 0 && engage_main_body > 1 && line_done == 1) {

								//System.out.println("NO HEAD F?");

								for (i = 0; i < head_end_count; i++) {

									//System.out.println("head_end_count:"+head_end_count+":i:"+i);
									//System.out.println("head_data[i].length:"+head_data[i].length+":i:"+i);
									//System.out.println("head_data[i][1]:"+head_data[i][1]);

									if (head_data[i][1] == null) {

										// do nothing

									} else if (head_data[i][0].equals("Datalog") && head_data[i][1].equals("report")) {

										d1 = f.parse(head_data[i + 1][1]);

									} else if (head_data[i][1].equals("Lot:")) {

										Lot_id = head_data[i][2];

									} else if (head_data[i][1].equals("Node") && head_data[i][2].equals("Name:")) {

										ATE_id = head_data[i][3];

									} else if (head_data[i][1].equals("HandID:")) {

										hadler_id = head_data[i][2];

									} else {

										// do nothing
									}

								}

								//System.out.println("NO HEAD G?");

								for (i = 0; i < 70; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin") || body_data[i][1].equals("Device#:")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										break;

									} else {

										if (body_data[i][0].equals("BARCODE")) {

											if (body_data[i][3].equals("0")) {

												site_counter[Integer.parseInt(body_data[i][2])] = 999;

											} else {

												site_counter[Integer.parseInt(body_data[i][2])] = Integer
														.parseInt(body_data[i][2]);

											}

											// isite = isite + 1;

										} else { // added for non site information case

											for (z = 0; z < site_counter.length; z++) {

												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {

													site_counter[z] = Integer.parseInt(body_data[i][2]);

												}

											}

										}
									}

								}

								//System.out.println("NO HEAD H?");
								
								if(body_loop_count < tail_count) tail_count = 10;
								
								for (i = body_loop_count - tail_count; i < body_loop_count + 1; i++) {

									// System.out.println("NO HEAD H-1?");

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin") || body_data[i][1].equals("Device#:")) {

										// do nothing

									} else {

										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {

											bin_engage = 1;

										}

										if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {

											// System.out.println("ftime_count:"+time_count);

											date_data = body_data[i][1];

											time[time_count] = f.parse(body_data[i][3]);
											time_count = time_count + 1;

											// System.out.println("atime_count:"+time_count);
										}

										for (bin_isite = 0; bin_isite < site_counter.length; bin_isite++) {

											if (bin_engage == 1 && body_data[i][1]
													.equals(String.valueOf(site_counter[bin_isite]))) {

												site_Hbin_counter[bin_isite] = body_data[i][2];
												site_Sbin_counter[bin_isite] = body_data[i][3];

												if (site_Hbin_counter[bin_isite].equals("1")) {

													site_PF_counter[bin_isite] = "PASS";

													// System.out.println("site_counter[bin_isite]:"+String.valueOf(site_counter[bin_isite]));
													// System.out.println("site_PF_counter[bin_isite]:"+site_PF_counter[bin_isite]);
												} else {

													site_PF_counter[bin_isite] = "FAIL";

													// System.out.println("site_counter[bin_isite]:"+String.valueOf(site_counter[bin_isite]));
													// System.out.println("site_PF_counter[bin_isite]:"+site_PF_counter[bin_isite]);
												}

												// bin_isite = bin_isite + 1;

											}

										}

									}

									if (i == body_loop_count)
										bin_engage = 0;

								}

								//System.out.println("NO HEAD I?");

								for (i = 0; i < site_counter.length; i++) {

									//System.out.println("NO HEAD I-1?");
									// System.out.println("site_counter[i] "+i+" "+site_counter[i]);
									// System.out.println("SITE32_array[i] "+i+" "+SITE32_array[i]);

									if (site_counter[i] == 999) {

									//System.out.println("NO HEAD I-2?");

									} else {
										//System.out.println("NO HEAD I-3?");
										for (x = 0; x < body_loop_count + 1; x++) {
											//System.out.println("NO HEAD I-4?");

											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")
													|| body_data[x][1].equals("Device#:")) {
												//System.out.println("NO HEAD I-5?");
												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {
												//System.out.println("NO HEAD I-6?");
												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												 //System.out.println("NO HEAD I-7?");
												 //System.out.println("d1:"+f.format(d1));
												 //System.out.println("d2:"+f.format(d2));
												 //System.out.println("time_count:"+(time_count-1));
												 //System.out.println("time[time_count-1]:"+f.format(time[time_count-1]));
												 //System.out.println("d1.getTime():"+d1.getTime());
												 //System.out.println("d2.getTime():"+d2.getTime());
												 //System.out.println("time[time_count-1].getTime():"+time[time_count-1].getTime());

												if (engage_main_body == 2) {
													//System.out.println("NO HEAD I-8?");
													if (d2 == null || time[0] == null) {
														//System.out.println("NO HEAD I-9?");
														
														TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
														TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( site_Sbin_counter[i]);
														TXTloadbuffer.append( ","); TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ","); TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ",");TXTloadbuffer.append( hadler_id);
														TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown"); TXTloadbuffer.append( ","); TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");
														TXTloadbuffer.append( ","); TXTloadbuffer.append( Lot_id);														
														
														first_head_information[i] = 1;

													} else {
														//System.out.println("NO HEAD I-10?");
														diff = d2.getTime() - time[time_count - 1].getTime();
														sec = diff / 1000;


														TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
																TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_Sbin_counter[i]);
																TXTloadbuffer.append( ",");TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ",");TXTloadbuffer.append( hadler_id);
																TXTloadbuffer.append( ",");TXTloadbuffer.append( date_data); TXTloadbuffer.append( " ");TXTloadbuffer.append( f.format(d2)); TXTloadbuffer.append( ",");TXTloadbuffer.append( date_data);
																TXTloadbuffer.append( " ");TXTloadbuffer.append( f.format(time[time_count - 1])); TXTloadbuffer.append( ",");
																TXTloadbuffer.append( Math.abs(sec)); TXTloadbuffer.append( ",");TXTloadbuffer.append( Lot_id);														
														
														
														first_head_information[i] = 1;

													}

												} else {
													//System.out.println("NO HEAD I-11?");
													if (d2 == null || time[0] == null) {

														TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
																TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_Sbin_counter[i]);
																TXTloadbuffer.append( ",");TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ",");TXTloadbuffer.append( hadler_id);
																TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");
																TXTloadbuffer.append( ",");TXTloadbuffer.append( Lot_id);
														
														
														first_head_information[i] = 1;

													} else {
														//System.out.println("NO HEAD I-12?");
														diff = time[time_count - 2].getTime()
																- time[time_count - 1].getTime();
														sec = diff / 1000;


														TXTloadbuffer.append(body_data[x][3]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
														TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_Sbin_counter[i]);
														TXTloadbuffer.append( ",");TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( ATE_id); TXTloadbuffer.append( ",");TXTloadbuffer.append( hadler_id);
														TXTloadbuffer.append( ",");TXTloadbuffer.append( date_data); TXTloadbuffer.append( " ");TXTloadbuffer.append( f.format(time[time_count - 2]));
														TXTloadbuffer.append( ",");TXTloadbuffer.append( date_data); TXTloadbuffer.append( " ");TXTloadbuffer.append( f.format(time[time_count - 1]));
														TXTloadbuffer.append( ",");TXTloadbuffer.append( Math.abs(sec)); TXTloadbuffer.append( ",");TXTloadbuffer.append( Lot_id);
														
														first_head_information[i] = 1;

													}

												}

											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {
												//System.out.println("NO HEAD I-13?");

												if (first_head_information[i] == 0) {

													//System.out.println("NO HEAD I-14?");

													//System.out.println("site_PF_counter[i]:" + site_PF_counter[i]);
													//System.out.println("site_Hbin_counter[i]:" + site_Hbin_counter[i]);
													//System.out.println("site_Sbin_counter[i]:" + site_Sbin_counter[i]);
													//System.out.println("site_counter[i]:" + site_counter[i]);
													//System.out.println("body_data[x][0]:" + body_data[x][0]);
													//System.out.println("body_data[x][1]:" + body_data[x][1]);
													//System.out.println("body_data[x][2]:" + body_data[x][2]);
													//System.out.println("body_data[x][3]:" + body_data[x][3]);

													TXTloadbuffer.append("Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( site_PF_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_Hbin_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( site_Sbin_counter[i]); TXTloadbuffer.append( ",");
													TXTloadbuffer.append( site_counter[i]); TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");
													TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( " ");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( " ");
													TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");TXTloadbuffer.append( ",");TXTloadbuffer.append( "Unknown");
													
													first_head_information[i] = 1;
												}

												if (body_data[x][4].equals("-1")) {
													//System.out.println("NO HEAD I-15?");
													if (body_data[x][5].equals("N/A")) {
														//System.out.println("NO HEAD I-16?");
														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][6]);
														
													} else if (body_data[x][6].equals("mohm")
															|| body_data[x][6].equals("ohm")
															|| body_data[x][6].equals("%")
															|| body_data[x][6].equals("m")
															|| body_data[x][6].equals("mA")
															|| body_data[x][6].equals("mV")
															|| body_data[x][6].equals("V")
															|| body_data[x][6].equals("A")
															|| body_data[x][6].equals("uA")
															|| body_data[x][6].equals("uV")
															|| body_data[x][6].equals("nA")
															|| body_data[x][6].equals("nV")
															|| body_data[x][6].equals("P/F")
															|| body_data[x][6].equals("mOhm")
															|| body_data[x][6].equals("S")
															|| body_data[x][6].equals("mS")) {
														//System.out.println("NO HEAD I-17?");
														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][7]);
														
													} else {
														//System.out.println("NO HEAD I-18?");
														TXTloadbuffer.append(",");
														TXTloadbuffer.append(body_data[x][6]);
													}

												} else if (body_data[x][6].equals("N/A")) {
													//System.out.println("NO HEAD I-19?");
													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][7]);
													
												} else if (body_data[x][3].equals("D1")
														|| body_data[x][3].equals("D2")) {
													//System.out.println("NO HEAD I-20?");
													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][9]);
													
												} else {
													//System.out.println("NO HEAD I-21?");
													TXTloadbuffer.append(",");
													TXTloadbuffer.append(body_data[x][8]);
												}

											}

										}
										//System.out.println("NO HEAD I-22?");
										TXTloadbuffer.append("\n");

									}

								}

								//System.out.println("NO HEAD J?");

								
								for (l = 0; l < body_loop_count + 1; l++) {

									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;

									}
								}

								for (l = 0; l < site_counter.length; l++) {

									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (i = 0; i < site_Hbin_counter.length; i++) {

									site_Hbin_counter[i] = null;

								}

								for (i = 0; i < site_Sbin_counter.length; i++) {

									site_Sbin_counter[i] = null;

								}

								for (i = 0; i < site_PF_counter.length; i++) {

									site_PF_counter[i] = null;

								}

								// isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;
								tail_count = 30;
								

							}

							//System.out.println("NO HEAD M?");
							rows = rows + 1;
							//TXTloadbuffer.flush();

							
						}

	
						if(rdbtnNewRadioButton.isSelected()) {
							
							//System.out.println("rows && date_and_time_flag:"+rows+":"+date_and_time_flag);
							if(date_and_time_flag == 0) {
								//System.out.println("Run this error?");
								JOptionPane.showMessageDialog(this, "Data log not include date and time");
							
							}
						}
						
						//System.out.println("NO HEAD L?");

						setTitle(dTXTffile[j] + ".csv saved..");

						noticeTXT.setText("");
						fileEmpty.setText("");
						fileEmpty.setText("   processing..........done");

						reader.close();
						 
						writer.append(TXTloadbuffer.toString());
						writer.flush();
						writer.close();
						TXTloadbuffer.setLength(0);
						
					}

					// dispose(); // do not close for checking result.

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Converting Error : Please See row "+(rows+1));
				
					
			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}

	private String cstr(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(final String[] args) throws InterruptedException {
		
		EventQueue.invokeLater(new Runnable() {
			public synchronized void run() {
				
				new Txt2Csv();
				
			}
		});
	}
}
