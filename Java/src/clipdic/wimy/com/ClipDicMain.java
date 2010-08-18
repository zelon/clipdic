package clipdic.wimy.com;

import java.awt.datatransfer.*;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import java.io.*;

public class ClipDicMain
{
	public static void main(String[] args)
	{
		try
		{
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
		}
		catch ( Exception e )
		{
		}
		
		ZUI aUI = new ZUI();
		aUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aUI.show();
	}
	
	private ZUI m_UI = null;
	private String m_strLastClipboard = "";
	
	public ClipDicMain(ZUI aUI)
	{
		/// ���۽ÿ� UI �� �޾ƿ�.
		m_UI = aUI;
	}
	
	public String GetHtmlSource(String strUrl)
	{
		String ret = "";
		URL url;
		try
		{
			url = new URL(strUrl);
			URLConnection con = url.openConnection();

			InputStream in = url.openStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String strLine;
			
			while (( strLine = br.readLine())!= null)
			{
				ret += strLine;
			}
		}
		catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IOException ex )
		{
			// TODO : Exception
		}
		
		return ret;		
		
	}

	String GetValidString(String strOriginal)
	{
		try
		{
			strOriginal = strOriginal.replaceAll("'", "");
			
			return URLEncoder.encode(strOriginal.trim(), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return strOriginal;
		}
	}

	String RemoveHtmlTag(String strOri)
	{
		/*
		Pattern pattern = Pattern.compile("\<.*\>");
		while(pattern.find())
		{
			String tag = strOri.substring(pattern.start(), pattern.end());
		}
		*/
		strOri = strOri.replaceAll("\\<!--.*?--\\>","");
		strOri = strOri.replaceAll("\\<.*?\\>","");
		strOri = strOri.replaceAll("��b�� ��------------------>", " ");
		
		strOri = strOri.replaceAll("&#12298", "<");
		strOri = strOri.replaceAll("&#12299", ">");
		strOri = strOri.replaceAll("&nbsp;", " ");
		strOri = strOri.replaceAll("&#12304", "[");
		strOri = strOri.replaceAll("&#12305", "]");
		strOri = strOri.replaceAll("&#237", "?");
		strOri = strOri.replaceAll("&#183", ".");
		strOri = strOri.replaceAll("&#58", ":");
		strOri = strOri.replaceAll("  ", "");
		strOri = strOri.replaceAll("\t", "");
		strOri = strOri.replaceAll("<!-- --", "");
		strOri = strOri.replaceAll("<!--", "");
		strOri = strOri.replaceAll("-->", "");
		strOri = strOri.replaceAll("�ܾ�� �˻���", "");
		strOri = strOri.replaceAll(" class=\"r01\"\\>", "");
		strOri = strOri.replaceAll("&userquery=", " ");
		strOri = strOri.replaceAll("", "");
		strOri = strOri.replaceAll("", "");

		return strOri;
		//return strOri.replaceAll("\<.*\>", "");
	}
	
	String GetNaverDic(String strWord)
	{
		strWord = GetValidString(strWord);
		String ret = "";
		String strHtml = GetHtmlSource("http://endic.naver.com/search.naver?query=" + strWord + "&mode=srch_ke");
		
		ret = strHtml;
		
		return ret;
	}
	
	String GetShellResult(String strWord)
	{
		System.out.printf(strWord);
		String ret = "";
		String s = "";
		try
		{
			Process oProcess = Runtime.getRuntime().exec("python getDic.py " + strWord);
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
			
			while ( (s =   stdOut.readLine() ) != null)
				ret += s;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		
	}
	
	String GetYahooDic(String strWord)
	{
		try
		{
			strWord = GetValidString(strWord);
			String ret = "";
			String strHtml = GetShellResult(strWord);

			/*
			return strHtml;
			int iStart = strHtml.indexOf("<!-- 검색결과 -->");
			
			if ( iStart == -1 ) return "Can't find start position";
			
			//int iEnd = strHtml.indexOf("�ܾ�Ǯ�� ��");
			int iEnd = strHtml.indexOf("<!--//검색결과-->");
			
			if ( iEnd == -1 ) return "Can't find start position";
		
			strHtml = strHtml.substring(iStart, iEnd);
			*/
		
			strHtml = RemoveHtmlTag(strHtml);
		
			ret = "[" + strWord + "]" + strHtml;
		
			return ret;
		}
		catch ( Exception ex )
		{
			return "Can't GetHtml Source for " + strWord + " error : " + ex.getMessage();
		}
	}

	public void ExecuteFromClipboard()
	{
		Clipboard clipboard = m_UI.getToolkit().getSystemClipboard();
		Transferable tf = clipboard.getContents(this);
					
		if ( tf != null )
		{
			try
			{
				String strResult = (String)tf.getTransferData(DataFlavor.stringFlavor);
				
				// ������ Ŭ������ �����̶� ��8�� �ƹ��ϵ� ���� �ʴ´�.
				if ( strResult.equals(m_strLastClipboard) )
				{
					// New Clipboard contents just return
					//System.out.println("������ Ŭ������� ������ ��=");
					return;
				}
				
				// Same clipboard contents go ahead.
				//System.out.println("������ Ŭ������� ������ �޶� ������");
				m_strLastClipboard = strResult;

				strResult = strResult.trim();
				m_UI.SetInputTextBox(strResult);
							
				if ( strResult.length() >= 1 )
				{
					strResult = strResult.toLowerCase();

					strResult = strResult.replaceAll("\n", "");
					strResult = strResult.replaceAll("  ", " ");

					strResult = GetYahooDic(strResult);
				}
							
				m_UI.SetResultTextBox(strResult);
							
			}
			catch (UnsupportedFlavorException e1)
			{
				if ( !m_strLastClipboard.equals("Can't read Clipboard") )
				{
					m_UI.SetResultTextBox("Can't read Clipboard");
					m_strLastClipboard = "Can't read Clipboard";
				}
			}
			catch (IOException e1)
			{
				if ( !m_strLastClipboard.equals("Can't read Clipboard") )
				{
					m_UI.SetResultTextBox("Can't read Clipboard");
					m_strLastClipboard = "Can't read Clipboard";
				}
			}
		}
	}

	public void SearchFromInput()
	{
		String strResult = GetYahooDic(m_UI.GetInputTextBox().toLowerCase());
		m_UI.SetResultTextBox(strResult);
	}
}
