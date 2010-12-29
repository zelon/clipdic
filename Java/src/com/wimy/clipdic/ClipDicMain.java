package com.wimy.clipdic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ClipDicMain
{
	private Browser m_browser = null;
	private final int timerInterval = 500; ///< 500 ms
	
	public static void main(String[] args)
	{
		ClipDicMain main = new ClipDicMain();
		main.ShowBrowser();
	}
    public void ShowBrowser()
    {
        final Display display = new Display();
        
        final Shell shell = new Shell(display);//, SWT.ON_TOP | SWT.MAX | SWT.MIN | SWT.CLOSE);
        shell.setSize(544, 474);
        shell.setLayout(new FillLayout());

        m_browser = new Browser(shell, SWT.NONE);
        m_browser.setUrl("http://www.google.com/");

        shell.open();
		m_clipboard = new Clipboard(display);

		display.timerExec(timerInterval, new Runnable()
			{
				public void run()
				{
					//System.out.println("timer run");
					ExecuteFromClipboard();
					
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

	private Clipboard m_clipboard = null;
	private String m_strLastClipboard = "";
	
	private void ExecuteFromClipboard()
	{
		String strResult = (String)m_clipboard.getContents(TextTransfer.getInstance());

		strResult = strResult.trim();
		strResult = strResult.toLowerCase();

		if ( strResult.length() <= 0 ) return;
		
		if ( strResult.equals(m_strLastClipboard) )	///< if same clipboard contents, skip
		{
			//System.out.println("same clipboard");
			return;
		}
		
		m_strLastClipboard = strResult;
		//System.out.println("new clipboard : " + m_strLastClipboard);

		searchWord(strResult);
	}

	private void searchWord(String strResult)
	{
		String url = "http://www.google.co.kr/dictionary?langpair=en|ko&q=" + strResult + "&hl=ko&aq=f";

		//System.out.println("new url : " + url);

		m_browser.setUrl(url);
	}
}
