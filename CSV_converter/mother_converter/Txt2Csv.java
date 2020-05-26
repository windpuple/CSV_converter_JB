package mother_converter;

import java.awt.Color;

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

public class Txt2Csv extends JFrame implements ActionListener {

	JButton btn = new JButton("Convert");
	JButton btncancel = new JButton("Close");
	JButton TXTload = new JButton("txt Load");

	JLabel noticeTXT = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static StringBuffer TXTloadbuffer = new StringBuffer(); // MAX cap is 12383875 line

	static int p = 0;

	static String[] dTXTfName = new String[256];
	static String[] dTXTfdirectory = new String[256];
	static String[] dTXTffile = new String[256];
	static String[] dTXTSavefName = new String[256];
	static String[][] dTXTSavefName_split = new String[256][];
	static String swavheader;

	File[] select_multi_files;

	public Txt2Csv() {
		super("TXTtoCSV V01");

		int jb;

		for (jb = 0; jb < 256; jb++) {

			dTXTfName[jb] = "";
			dTXTfdirectory[jb] = "";
			dTXTffile[jb] = "";
			dTXTSavefName[jb] = "";

		}

		swavheader = "";

		TXTloadbuffer.setLength(0);
		btn.setFont(new Font("Arial", Font.PLAIN, 12));

		btn.setBounds(23, 94, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel TXTfile = new JLabel("TXTfile");
		TXTfile.setFont(new Font("Arial", Font.PLAIN, 12));
		TXTfile.setBounds(23, 28, 120, 15);
		getContentPane().add(TXTfile);
		btncancel.setFont(new Font("Arial", Font.PLAIN, 12));

		btncancel.setBounds(133, 93, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(26, 53, 204, 15);
		getContentPane().add(noticeTXT);
		TXTload.setFont(new Font("Arial", Font.PLAIN, 12));

		TXTload.setBounds(133, 24, 97, 23);
		getContentPane().add(TXTload);
		TXTload.addActionListener(this);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(23, 75, 207, 15);
		getContentPane().add(fileEmpty);

		JLabel lblMainProgramJb = new JLabel("Main program: JB JEON");
		lblMainProgramJb.setFont(new Font("Arial", Font.PLAIN, 10));
		lblMainProgramJb.setBounds(102, 126, 123, 15);
		getContentPane().add(lblMainProgramJb);

		JLabel lblSubProgramJh = new JLabel("Sub program: JH KANG, JH CHOI");
		lblSubProgramJh.setFont(new Font("Arial", Font.PLAIN, 10));
		lblSubProgramJh.setBounds(64, 141, 158, 15);
		getContentPane().add(lblSubProgramJh);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 202);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == TXTload) {

			int jb;

			for (jb = 0; jb < 256; jb++) {

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
						
						if(select_multi_files.length == 1) {
							
							dTXTfName[i] = dTXTfdirectory[i] + dTXTffile[i];
							
						} else {
							
							dTXTfName[i] = dTXTfdirectory[i] + "\\"+dTXTffile[i];
							
						}
						

						System.out.println("dTXTffile[i]:"+i+" "+dTXTffile[i]);				
						System.out.println("dTXTfdirectory[i]:"+i+" "+dTXTfdirectory[i]);	
						System.out.println("dTXTfName[i]:"+i+" "+dTXTfName[i]);	
						
						
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

			int j;
			
			
			try {

				for (j = 0; j < select_multi_files.length; j++) {

					dTXTSavefName[j] = "";
					swavheader = "";
					TXTloadbuffer.setLength(0);

					int cnt = 0;

					if (dTXTfName[j].isEmpty()) {

						noticeTXT.setText("");
						fileEmpty.setText("");
						fileEmpty.setText("   TXT file is empty.");

					} else {

						noticeTXT.setText("");
						fileEmpty.setText("");
						//noticeTXT.setText("   processing..............");

						dTXTSavefName[j] = dTXTfName[j];
					
						//setTitle(dTXTffile[j] + " - TXT save..");
						
						System.out.println("dTXTSavefName[j]:"+j+" "+dTXTSavefName[j]);
						
						setTitle("Processing...");

						FileInputStream ftstream = new FileInputStream(dTXTfName[j]);
						BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

						String line;
						String line_sub_replace;

						int rows = 0;

						int head_flag = 0;
						int loopcount = 1;
						int linesplit_count = 0;
						int head_end_count = 0;
						int body_end_count = 0;
						int ex_body_end_count = 0;
						int body_loop_count = 0;
						int mainbody_end_count = 0;
						int engage_main_body = 0;
						int bin_engage = 0;
						int ignore_item = 0;
						int bining_end_count = 0;
						int line_done = 0;
						int block_end = 0;
						int data_align = 0;
						int first_data_en = 0;

						String hadler_id;
						String ATE_id;
						String date_data;
						String Lot_id;

						Lot_id = "Unknown";
						date_data = "Unknown";
						hadler_id = "Unknown";
						ATE_id = "Unknown";

						int isite = 0;
						int[] site_counter = new int[32];
						String[] SITE32_array = new String[32];
						int[] first_head_information = new int[32];

						for (int i = 0; i < SITE32_array.length; i++) {

							SITE32_array[i] = String.valueOf(i);

						}

						for (int i = 0; i < site_counter.length; i++) {

							site_counter[i] = 999;
							first_head_information[i] = 0;
						}

						int bin_isite = 0;
						String[] site_Hbin_counter = new String[64];

						for (int i = 0; i < site_Hbin_counter.length; i++) {

							site_Hbin_counter[i] = null;

						}

						String[] site_Sbin_counter = new String[64];

						for (int i = 0; i < site_Sbin_counter.length; i++) {

							site_Sbin_counter[i] = null;

						}

						String[] site_PF_counter = new String[64];

						for (int i = 0; i < site_PF_counter.length; i++) {

							site_PF_counter[i] = null;

						}

						long diff = 0;
						long sec = 0;

						SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
						Date d1 = null;
						Date d2 = null;

						Date[] time = null;
						time = new Date[172032];

						String[][] head_data = null;
						head_data = new String[100][1024];

						String[][] body_data = null;
						body_data = new String[172032][1024];

						String[][] main_body_data = null;
						main_body_data = new String[172032][1024];

						int time_count = 0;

						BufferedWriter writer = new BufferedWriter(new FileWriter(dTXTSavefName[j] + ".csv"));

						while ((line = reader.readLine()) != null) {

							line_sub_replace = line;

							if (line_sub_replace.contains("Device#")) {
								head_flag = 1;

								if (head_end_count == 0) {
									head_end_count = rows;
								}

							}

							if (line_sub_replace.contains("Date:") && line_sub_replace.contains("Time:") || line_sub_replace.contains("=========================================================================")) {

								line_done = 1;
								engage_main_body = engage_main_body + 1;
								data_align = 1;
								ex_body_end_count = body_end_count;
								body_loop_count = rows - ex_body_end_count;
								body_end_count = rows;

								if(body_loop_count < 50) {
									
									body_loop_count = 50;
									
								}
								
							}

							for (int i = 0; i < line.length(); i++) {

								if (line_sub_replace.contains("  ")) {

									line_sub_replace = line_sub_replace.replace("  ", " ");

								} else {

									break;

								}

							}

							for (int i = 0; i < line_sub_replace.length(); i++) {
								if (line_sub_replace.charAt(i) == ' ' || line_sub_replace.charAt(i) == ',')

									linesplit_count = linesplit_count + 1;

							}

							String[] linesplit = new String[linesplit_count];

							linesplit = line_sub_replace.split("\\s|,");

							for (int y = 0; y < linesplit.length; y++) {

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
							
							//System.out.println("row, head_end_count, body_loop_count, engage_main_body, line_done : "+rows+" "+head_end_count+" "+body_loop_count+" "+engage_main_body+" "+line_done);
							
							System.out.println("NEW ERR A");
							
							if (rows != 0 && rows == head_end_count) {

								writer.append("PGM Name" + "," + head_data[2][3] + "\n");

								//System.out.println("NEW ERR B");
								
							} else if (rows != 0 && rows == body_loop_count && engage_main_body == 1
									&& line_done == 1) {

								//System.out.println("NEW ERR C");
								
								writer.append("SerialNumber" + "," + "Test Pass/Fail Status" + "," + "HBIN" + ","
										+ "SBIN" + "," + "Site" + "," + "TesterID" + "," + "HanderID" + ","
										+ "StartTime" + "," + "EndTime" + "," + "TestTime" + "," + "LotNumber");

								for (int i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											writer.append("," + body_data[i][3]);

										} else {

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												writer.append("," + body_data[i][3] + " " + body_data[i][4]);

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else {

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][3] + " " + body_data[i][4]);

												}

											}

										}
									}

								}

								writer.append("Upper Limit ----->" + "," + "," + "," + "," + "," + "," + "," + "," + ","
										+ ",");

								for (int i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											if (body_data[i][10].equals("(F)")) {

												writer.append("," + body_data[i][11]);

											} else {

												writer.append("," + body_data[i][10]);
											}

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

													for (int x = i - 2; x < i; x++) {

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

															writer.append("," + body_data[i][12]);

														} else {

															writer.append("," + body_data[i][11]);

														}

													}

												} else if (body_data[i][4].equals("-1")) {

													if (body_data[i][12] == null) {

														if (body_data[i][5].equals("N/A")) {

															for (int x = i - 2; x < i; x++) {

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

																	writer.append("," + body_data[i][8]);

																} else {

																	if ((body_data[i][7].contains("u")
																			|| body_data[i][7].contains("m")
																			|| body_data[i][7].contains("A")
																			|| body_data[i][7].contains("V"))
																			&& !body_data[i][7].contains("N/A")) {

																		writer.append("," + body_data[i][8]);

																	} else {

																		writer.append("," + body_data[i][7]);

																	}

																}

															}

														} else {

															for (int x = i - 2; x < i; x++) {

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

																	writer.append("," + body_data[i][8]);

																} else {

																	writer.append("," + body_data[i][7]);

																}

															}

														}

													} else {

														if (body_data[i][5].equals("N/A")) {

															for (int x = i - 2; x < i; x++) {

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

																	writer.append("," + body_data[i][9]);

																} else {

																	writer.append("," + body_data[i][8]);

																}

															}

														} else {

															for (int x = i - 2; x < i; x++) {

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

																	writer.append("," + body_data[i][10]);

																} else {

																	writer.append("," + body_data[i][9]);

																}

															}

														}

													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

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

															writer.append("," + body_data[i][10]);

														} else {

															if (body_data[i][8].equals("N/A")) {

																writer.append("," + body_data[i][8]);

															} else {

																writer.append("," + body_data[i][9]);

															}

														}

													}

												} else {

													for (int x = i - 2; x < i; x++) {

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

															writer.append("," + body_data[i][11]);

														} else {

															writer.append("," + body_data[i][10]);

														}

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												if (body_data[i][12] == null) {

													if (body_data[i][5].equals("N/A")) {

														for (int x = i - 2; x < i; x++) {

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

																writer.append("," + body_data[i][8]);

															} else {

																if ((body_data[i][7].contains("u")
																		|| body_data[i][7].contains("m")
																		|| body_data[i][7].contains("A")
																		|| body_data[i][7].contains("V"))
																		&& !body_data[i][7].contains("N/A")) {

																	writer.append("," + body_data[i][8]);

																} else {

																	writer.append("," + body_data[i][7]);

																}

															}

														}

													} else {

														for (int x = i - 2; x < i; x++) {

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

																writer.append("," + body_data[i][8]);

															} else {

																writer.append("," + body_data[i][7]);

															}

														}

													}

												} else {

													if (body_data[i][5].equals("N/A")) {

														for (int x = i - 2; x < i; x++) {

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

																writer.append("," + body_data[i][9]);

															} else {

																writer.append("," + body_data[i][8]);

															}

														}

													} else {

														for (int x = i - 2; x < i; x++) {

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

																writer.append("," + body_data[i][10]);

															} else {

																writer.append("," + body_data[i][9]);

															}

														}

													}

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][10]);

													} else {

														if (body_data[i][8].equals("N/A")) {

															writer.append("," + body_data[i][8]);

														} else {

															writer.append("," + body_data[i][9]);

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

												for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][12]);

													} else {

														writer.append("," + body_data[i][11]);

													}

												}

											}

											else {

												for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][11]);

													} else {

														writer.append("," + body_data[i][10]);
													}

												}

											}

										}
									}

								}

								writer.append("Lower Limit ----->" + "," + "," + "," + "," + "," + "," + "," + "," + ","
										+ ",");

								for (int i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											writer.append("," + body_data[i][6]);

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

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][8]);

													}

												} else if (body_data[i][4].equals("-1")) {

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][5]);

													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][6]);

													}

												} else {

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][6]);

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][5]);

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][6]);
													;

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
												// "
												// "
												// exeception
												// for
												// cygnus
												// diode
												// item

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][8]);

												}

											} else {

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][6]);

												}

											}

										}
									}

								}

								writer.append("Measurement Limit ----->" + "," + "," + "," + "," + "," + "," + "," + ","
										+ "," + "sec" + ",");

								for (int i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											writer.append("," + body_data[i][7]);

										} else {

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												if (body_data[i][4].equals("-1")) {

													for (int x = i - 2; x < i; x++) {

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

															writer.append(",");

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

																writer.append("," + body_data[i][6]);

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

																writer.append("," + body_data[i][9]);

															} else {

																writer.append(",");

															}

														}

													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

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

															writer.append(",");

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

																writer.append("," + body_data[i][6]);

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

																writer.append("," + body_data[i][10]);

															} else {

																writer.append(",");

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

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][12]);

													}

												} else {

													for (int x = i - 2; x < i; x++) {

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

														writer.append("," + body_data[i][7]);

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (int x = i - 2; x < i; x++) {

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

														writer.append(",");

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

															writer.append("," + body_data[i][6]);

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

															writer.append("," + body_data[i][9]);

														} else {

															writer.append(",");

														}

													}

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

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

														writer.append(",");

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

															writer.append("," + body_data[i][6]);

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

															writer.append("," + body_data[i][10]);

														} else {

															writer.append(",");

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

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][12]);

												}

											} else {

												for (int x = i - 2; x < i; x++) {

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

													writer.append("," + body_data[i][7]);

												}

											}

										}
									}

								}

								System.out.println("NO HEAD A?");

								for (int i = 0; i < head_end_count; i++) {

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

								System.out.println("NO HEAD B?");

								for (int i = 0; i < 100; i++) {

									// System.out.println("NO HEAD B-1?");

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										break;

									} else {

										//if (body_data[i][0].equals("BARCODE") && !body_data[i][3].equals("0")) {
										if (body_data[i][0].equals("BARCODE") ) {
											//site_counter[isite] = Integer.parseInt(body_data[i][2]);
											
											if(body_data[i][3].equals("0")) {
								
												site_counter[Integer.parseInt(body_data[i][2])] = 999;
		
												
											} else {
											
												site_counter[Integer.parseInt(body_data[i][2])] = Integer.parseInt(body_data[i][2]);
							
											}
										
					
											
		
											//isite = isite + 1;

										} else { // added for non site information case

											for (int z = 0; z < site_counter.length; z++) {

												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {

													site_counter[z] = Integer.parseInt(body_data[i][2]);

												}

											}

										}
									}

								}

								System.out.println("NO HEAD C?");

								for (int i = body_loop_count - 50; i < body_loop_count + 1; i++) {

									//System.out.println("NO HEAD C-1?");
									//System.out.println("site_counter.length: "+site_counter.length);
									//System.out.println("site_counter[bin_isite]"+" "+bin_isite+" "+site_counter[bin_isite]+" "+i+" "+body_loop_count);
									
									if (site_counter[bin_isite] == 999) {
										//System.out.println("NO HEAD C-2?");
										// do nothing
										
										if(bin_isite == site_counter.length-1) {
											
											// do nothing
											
										} else {
										
											bin_isite = bin_isite + 1;
											
										}
										

									}

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {
										//System.out.println("NO HEAD C-3?");
										// do nothing

									} else {
										//System.out.println("NO HEAD C-4?");
										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {
											//System.out.println("NO HEAD C-5?");
											bin_engage = 1;

										} else if (bin_engage == 1
												&& body_data[i][1].equals(String.valueOf(site_counter[bin_isite]))) {
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

											bin_isite = bin_isite + 1;

										} else if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {
											//System.out.println("NO HEAD C-9?");
											date_data = body_data[i][1];

											d2 = f.parse(body_data[i][3]);
										}
									}

									if (i == body_loop_count)
										bin_engage = 0;
									//System.out.println("NO HEAD C-10?");
								}

								System.out.println("NO HEAD D?");

								for (int i = 0; i < site_counter.length; i++) {

									System.out.println("NO HEAD D-1?");

									if (site_counter[i] == 999) {
										System.out.println("NO HEAD D-2?");
									} else {

										for (int x = 0; x < body_loop_count + 1; x++) {
											
											System.out.println("NO HEAD D-3?");
											
											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")) {
												
												System.out.println("NO HEAD D-3?");
												
												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {

												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												// System.out.println("d1:"+f.format(d1));
												// System.out.println("d2:"+f.format(d2));
												// System.out.println("d1.getTime():"+d1.getTime());
												// System.out.println("d2.getTime():"+d2.getTime());

												System.out.println("NO HEAD D-4?");
												
												if(d1 == null || d2 == null ) {
													
													writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
															+ "Unknown" + "," + "Unknown" + "," + "Unknown" + ","
															+ Lot_id);
													
													first_head_information[i] = 1;
													
												} else {
												
													diff = d1.getTime() - d2.getTime();
													sec = diff / 1000;
												
													writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
															+ date_data + " " + f.format(d1) + "," + date_data + " "
															+ f.format(d2) + "," + Math.abs(sec) + "," + Lot_id);
													
													first_head_information[i] = 1;
												}
												


											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												System.out.println("NO HEAD D-5?");
												
												if (first_head_information[i] == 0) {

													System.out.println("NO HEAD D-6?");
													
													writer.append("Unknown" + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + "Unknown" + "," + "Unknown" + ","
															+ "Unknown" + " " + "Unknown" + "," + "Unknown" + " "
															+ "Unknown" + "," + "Unknown" + "," + "Unknown");

													first_head_information[i] = 1;
												}

												if (body_data[x][4].equals("-1")) {

													System.out.println("NO HEAD D-7?");
													
													if (body_data[x][5].equals("N/A")) {
														
														System.out.println("NO HEAD D-8?");
														
														writer.append("," + body_data[x][6]);

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

														System.out.println("NO HEAD D-9?");

														writer.append("," + body_data[x][7]);

													} else {

														System.out.println("NO HEAD D-10?");
														
														writer.append("," + body_data[x][6]);

													}

												} else if (body_data[x][6].equals("N/A")) {

													System.out.println("NO HEAD D-11?");
													
													writer.append("," + body_data[x][7]);

												} else if (body_data[x][3].equals("D1")
														|| body_data[x][3].equals("D2")) {
													
													System.out.println("NO HEAD D-12?");
													
													writer.append("," + body_data[x][9]);

												} else {
													
													System.out.println("NO HEAD D-13?");
													
													writer.append("," + body_data[x][8]);

												}

											}

										}

										System.out.println("NO HEAD D-14?");
										
										writer.append("\n");

									}

								}

								System.out.println("NO HEAD E?");

								for (int l = 0; l < body_loop_count + 1; l++) {

									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;

									}
								}

								for (int l = 0; l < site_counter.length; l++) {

									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (int i = 0; i < site_Hbin_counter.length; i++) {

									site_Hbin_counter[i] = null;

								}

								for (int i = 0; i < site_Sbin_counter.length; i++) {

									site_Sbin_counter[i] = null;

								}

								for (int i = 0; i < site_PF_counter.length; i++) {

									site_PF_counter[i] = null;

								}

								//isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;

							} else if (rows != 0 && engage_main_body > 1 && line_done == 1) {

								System.out.println("NO HEAD F?");

								for (int i = 0; i < head_end_count; i++) {

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

								System.out.println("NO HEAD G?");

								for (int i = 0; i < 100; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {

										break;

									} else {

										if (body_data[i][0].equals("BARCODE")) {

											if(body_data[i][3].equals("0")) {
												
												site_counter[Integer.parseInt(body_data[i][2])] = 999;
		
												
											} else {
											
												site_counter[Integer.parseInt(body_data[i][2])] = Integer.parseInt(body_data[i][2]);
							
											}
											
											//isite = isite + 1;

										} else { // added for non site information case

											for (int z = 0; z < site_counter.length; z++) {

												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {

													site_counter[z] = Integer.parseInt(body_data[i][2]);

												}

											}

										}
									}

								}

								System.out.println("NO HEAD H?");

								for (int i = body_loop_count - 50; i < body_loop_count + 1; i++) {

									//System.out.println("NO HEAD H-1?");
									//System.out.println("site_counter.length: "+site_counter.length);
									//System.out.println("site_counter[bin_isite]"+" "+bin_isite+" "+site_counter[bin_isite]+" "+i+" "+body_loop_count);


									if (site_counter[bin_isite] == 999) {

										if(bin_isite == site_counter.length-1) {
											
											// do nothing
											
										} else {
										
											bin_isite = bin_isite + 1;
											
										}

									}

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// do nothing

									} else {

										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {

											bin_engage = 1;

										} else if (bin_engage == 1
												&& body_data[i][1].equals(String.valueOf(site_counter[bin_isite]))) {

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

											bin_isite = bin_isite + 1;

										} else if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {

											// System.out.println("ftime_count:"+time_count);

											date_data = body_data[i][1];

											time[time_count] = f.parse(body_data[i][3]);
											time_count = time_count + 1;

											// System.out.println("atime_count:"+time_count);
										}
									}

									if (i == body_loop_count)
										bin_engage = 0;

								}

								System.out.println("NO HEAD I?");

								for (int i = 0; i < site_counter.length; i++) {

									// System.out.println("site_counter[i] "+i+" "+site_counter[i]);
									// System.out.println("SITE32_array[i] "+i+" "+SITE32_array[i]);

									if (site_counter[i] == 999) {

									} else {
										//System.out.println("NO HEAD I-2?");
										for (int x = 0; x < body_loop_count + 1; x++) {

											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")) {

												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {

												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												// System.out.println("d1:"+f.format(d1));
												// System.out.println("d2:"+f.format(d2));
												// System.out.println("time_count:"+(time_count-1));
												// System.out.println("time[time_count-1]:"+f.format(time[time_count-1]));
												// System.out.println("d1.getTime():"+d1.getTime());
												// System.out.println("d2.getTime():"+d2.getTime());
												// System.out.println("time[time_count-1].getTime():"+time[time_count-1].getTime());

												if (engage_main_body == 2) {

													if(d2 == null ||  time[0] == null ) {
														
														writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
																+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
																+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
																+ "Unknown" + "," + "Unknown" + "," + "Unknown" + ","
																+ Lot_id);
														
														first_head_information[i] = 1;
													
													} else {
													
														diff = d2.getTime() - time[time_count - 1].getTime();
														sec = diff / 1000;
													
														writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
																+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
																+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
																+ date_data + " " + f.format(d2) + "," + date_data + " "
																+ f.format(time[time_count - 1]) + "," + Math.abs(sec) + ","
																+ Lot_id);
													
														first_head_information[i] = 1;
													
													}


												} else {

													if(d2 == null ||  time[0] == null ) {
														
														writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
																+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
																+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
																+ "Unknown" + "," + "Unknown" + "," + "Unknown" + ","
																+ Lot_id);
														
														first_head_information[i] = 1;
														
																
													} else {

														diff = time[time_count - 2].getTime()
																- time[time_count - 1].getTime();
														sec = diff / 1000;

														writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
																+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
																+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
																+ date_data + " " + f.format(time[time_count - 2]) + ","
																+ date_data + " " + f.format(time[time_count - 1]) + ","
																+ Math.abs(sec) + "," + Lot_id);
														
														first_head_information[i] = 1;
														
													}
													

												}

											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												if (first_head_information[i] == 0) {

													writer.append("Unknown" + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + "Unknown" + "," + "Unknown" + ","
															+ "Unknown" + " " + "Unknown" + "," + "Unknown" + " "
															+ "Unknown" + "," + "Unknown" + "," + "Unknown");

													first_head_information[i] = 1;
												}

												if (body_data[x][4].equals("-1")) {

													if (body_data[x][5].equals("N/A")) {

														writer.append("," + body_data[x][6]);

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

														writer.append("," + body_data[x][7]);

													} else {

														writer.append("," + body_data[x][6]);

													}

												} else if (body_data[x][6].equals("N/A")) {

													writer.append("," + body_data[x][7]);

												} else if (body_data[x][3].equals("D1")
														|| body_data[x][3].equals("D2")) {

													writer.append("," + body_data[x][9]);

												} else {

													writer.append("," + body_data[x][8]);

												}

											}

										}

										writer.append("\n");

									}

								}

								System.out.println("NO HEAD J?");

								// Arrays.fill(body_data, "");
								// body_data = new String[172032][1024];

								for (int l = 0; l < body_loop_count + 1; l++) {

									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;

									}
								}

								for (int l = 0; l < site_counter.length; l++) {

									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (int i = 0; i < site_Hbin_counter.length; i++) {

									site_Hbin_counter[i] = null;

								}

								for (int i = 0; i < site_Sbin_counter.length; i++) {

									site_Sbin_counter[i] = null;

								}

								for (int i = 0; i < site_PF_counter.length; i++) {

									site_PF_counter[i] = null;

								}

								//isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;

							}

							rows = rows + 1;

						}

						System.out.println("NO HEAD L?");
						
						setTitle(dTXTffile[j] + ".csv saved..");
						
						noticeTXT.setText("");
						fileEmpty.setText("");
						fileEmpty.setText("   processing..........done");

						reader.close();
						writer.close();

					}

					// dispose(); // do not close for checking result.

				}

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

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
		new Txt2Csv();
	}
}
