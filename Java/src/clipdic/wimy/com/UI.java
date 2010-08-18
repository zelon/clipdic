package clipdic.wimy.com;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * Created on 2004. 3. 16.
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author zelon
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UI extends JFrame implements ActionListener
{
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JButton m_btUpdate = null;
	private javax.swing.JTextField m_tfInput = null;
	private javax.swing.JTextArea m_taDicResult = null;
	private javax.swing.JTextArea m_taStatus = null;
	private javax.swing.JPanel TopPanel = null;
	private javax.swing.JButton jButton = null;
	private Timer m_clipboardTimer = null;
	private javax.swing.JScrollPane jScrollPane = null;

	private java.awt.Font m_font = null;
	
	
	private ClipDicMain m_rhDicMain = null;
	
	public void SetInputTextBox(String strInput)
	{
		m_tfInput.setText(strInput);
	}
	
	public String GetInputTextBox() { return m_tfInput.getText(); }
	
	public void SetResultTextBox(String strResult)
	{
		m_taDicResult.setText(strResult);
		m_taDicResult.setCaretPosition(0);
	}
	
	/**
	 * This is the default constructor
	 */
	public UI() 
	{
		super();

		//m_font = new Font("����ü", Font.PLAIN, 12);
		
		initialize();
		
		
		m_clipboardTimer = new Timer(500, this);
		m_clipboardTimer.start();
		
		/// 화면에 꽉채우고 위치를 아래로 정하는 코드
		/*
		{
			GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle screenRect=ge.getMaximumWindowBounds();

			this.setSize(screenRect.width, screenRect.height / 5);
			this.setLocation(0, (screenRect.height - this.getHeight()));
		}
		*/
			
		this.setSize(300, 300);
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(567, 221);
		this.setContentPane(getJContentPane());
		this.setTitle("ZDic");
		//this.setFont(m_font);
		//this.m_tfInput.setFont(m_font);
		//this.m_taDicResult.setFont(m_font);
		//this.m_btUpdate.setFont(m_font);
		
		//ImageIcon icon = new ImageIcon("Dic.bmp", "DicIcon");
		
		//this.setIconImage(icon.getImage());
	}
	
	
	
	
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getTopPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes m_btUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getM_btUpdate() {
		if(m_btUpdate == null) {
			m_btUpdate = new javax.swing.JButton();
			m_btUpdate.setText("From Clipboard");
			m_btUpdate.setPreferredSize(new java.awt.Dimension(120,28));
			m_btUpdate.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					m_rhDicMain.ExecuteFromClipboard();
				}
			});
		}
		return m_btUpdate;
	}
	/**
	 * This method initializes m_tfInput
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getM_tfInput() {
		if(m_tfInput == null) {
			m_tfInput = new javax.swing.JTextField();
			m_tfInput.setText("");
			m_tfInput.setPreferredSize(new java.awt.Dimension(300,22));
			m_tfInput.addKeyListener(new java.awt.event.KeyListener() { 
				public void keyTyped(java.awt.event.KeyEvent e)
				{
					// ����Ű�� ���ؼ� �ڵ�8�� input ; �ϰ� ��.
					if ( e.getKeyChar() == '\n' )
					{
						   m_rhDicMain.SearchFromInput();
					}
				}
				public void keyPressed(java.awt.event.KeyEvent e) {} 
				public void keyReleased(java.awt.event.KeyEvent e) {} 
			});
		}
		return m_tfInput;
	}
	/**
	 * This method initializes m_taDicResult
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getM_taDicResult() {
		if(m_taDicResult == null) {
			m_taDicResult = new javax.swing.JTextArea();
			m_taDicResult.setLineWrap(true);
			m_taDicResult.setPreferredSize(new java.awt.Dimension(800,600));
			m_taDicResult.setRows(0);
			m_taDicResult.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,5));
		}
		return m_taDicResult;
	}
	
	private javax.swing.JTextArea getM_taStatus() {
		if(m_taStatus == null) {
			m_taStatus = new javax.swing.JTextArea();
			m_taStatus.setLineWrap(true);
			m_taStatus.setPreferredSize(new java.awt.Dimension(800,600));
			m_taStatus.setRows(0);
			m_taStatus.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,5));
			m_taStatus.setText("OK");
		}
		return m_taStatus;
	}
	
	/**
	 * This method initializes TopPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getTopPanel() {
		if(TopPanel == null) {
			TopPanel = new javax.swing.JPanel();
			TopPanel.setLayout(new java.awt.BorderLayout());
			
			TopPanel.add(getM_tfInput(), java.awt.BorderLayout.WEST);
			TopPanel.add(getJButton(), java.awt.BorderLayout.CENTER);
			TopPanel.add(getM_btUpdate(), java.awt.BorderLayout.EAST);
		}
		return TopPanel;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			//jButton.setFont(new Font("bakekmuk", Font.PLAIN, 12));
			jButton.setText("Search");
			jButton.setPreferredSize(new java.awt.Dimension(50,28));
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					m_rhDicMain.SearchFromInput();    
				}
			});
		}
		return jButton;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		m_rhDicMain.ExecuteFromClipboard();
	}
	
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.add(getM_taDicResult());
			jScrollPane.add(getM_taStatus());
			jScrollPane.setViewportView(getM_taDicResult());
			jScrollPane.setPreferredSize(new java.awt.Dimension(100,100));
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
