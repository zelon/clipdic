package com.wimy.clipdic;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ClipDicMain
{
	private Browser m_browser = null;
	private final int timerInterval = 500; ///< 500 ms
	private Clipboard m_clipboard = null;
	private String m_strLastClipboard = "";
	
	public static void main(String[] args)
	{
		ClipDicMain main = new ClipDicMain();
		main.ShowBrowser();
	}

	public void ShowBrowser()
    {
        final Display display = new Display();
        
        final Shell shell = new Shell(display);//, SWT.ON_TOP | SWT.MAX | SWT.MIN | SWT.CLOSE);
        shell.setText("ClipDic");
        shell.setSize(544, 474);
        shell.setLayout(new FillLayout());
        
        URL fileStream = getClass().getClassLoader().getResource("com/wimy/clipdic/clipdic.png");
        
        if ( fileStream != null )
        {
            ImageData imageData;
			try
			{
				imageData = new ImageData(fileStream.openStream());
	            Image image = new Image(display, imageData);
	            shell.setImage(image);
			}
			catch (IOException e)
			{
	        	System.err.println("Cannot get clipdic.png");
			}
        }
        else
        {
        	System.err.println("Cannot get clipdic.png");
        }

        m_browser = new Browser(shell, SWT.NONE);
        searchWord("");

        shell.open();
		m_clipboard = new Clipboard(display);

		display.timerExec(timerInterval, new Runnable()
			{
				public void run()
				{
					//System.out.println("timer run");
					try
					{
						ExecuteFromClipboard();
					}
					catch ( NullPointerException e)
					{
						
					}
					
					display.timerExec(timerInterval, this);
				}
			}
		);
        
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        display.dispose();
    }

	private void ExecuteFromClipboard()
	{
		String strResult = (String)m_clipboard.getContents(TextTransfer.getInstance());

		strResult = strResult.trim().toLowerCase();

		if ( false == IsWordForDictionary(strResult) ) return;
		
		if ( strResult.equals(m_strLastClipboard) )	///< if same clipboard contents is not changed, skip
		{
			//System.out.println("same clipboard");
			return;
		}
		
		m_strLastClipboard = strResult;
		//System.out.println("new clipboard : " + m_strLastClipboard);

		searchWord(strResult);
	}

	boolean IsWordForDictionary(String word)
	{
		if ( word.startsWith("http://") ) return false;
		if ( word.length() <= 0 ) return false;
		
		return true;
	}
	
	private void searchWord(String strResult)
	{
		String url = "http://r.wimy.com/dic?q=" + strResult;
		//String url = "http://www.google.co.kr/dictionary?langpair=en|ko&hl=ko&aq=f&q=" + strResult;

		//System.out.println("new url : " + url);

		m_browser.setUrl(url);
	}
}
