using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace ClipDic
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private string m_strLastClipboard = "";
        private void OnTick(object sender, EventArgs e)
        {
			/// 클립보드에 텍스트가 들어있고, 현재창이 최소화된 상태가 아니면 동작한다.
            if (Clipboard.ContainsText() && this.WindowState != FormWindowState.Minimized)
            {
                string strCurrentText = Clipboard.GetText().Trim();

				/// url 은 검색하지 않는다.
				if (strCurrentText.StartsWith("http://")) return;

                if (m_strLastClipboard != strCurrentText)
                {
                    m_strLastClipboard = strCurrentText;

                    Navigate(strCurrentText);

                    /// 이미 리스트에 있는 것은 다시 넣지 않는다.
                    bool bExistDuplicate = false;
                    for (int i = 0; i < comboBox1.Items.Count; ++i)
                    {
                        if (strCurrentText == (string)comboBox1.Items[i])
                        {
                            bExistDuplicate = true;
                            break;
                        }
                    }

                    if (bExistDuplicate == false)
                    {
                        if (comboBox1.Items.Count > 3)
                        {
                            //comboBox1.Items.RemoveAt(comboBox1.Items.Count-1);
                            comboBox1.Items.RemoveAt(0);
                        }

                        comboBox1.Items.Add(strCurrentText);
                    }
                }
            }

        }

        private void Navigate(string strCurrentText)
        {
            try
            {
                webBrowser1.Stop();
                webBrowser1.Navigate(makeUrl(strCurrentText));
            }
            catch (System.Runtime.InteropServices.COMException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private string makeUrl(string strText)
        {
            return "http://r.wimy.com/dic?q=" + strText;
        }

        private void OnCheckedChangded(object sender, EventArgs e)
        {
            this.TopMost = m_cbAlwaysTop.Checked;
        }

        private void OnSlideScroll(object sender, EventArgs e)
        {
            this.Opacity = (double)(trackBar1.Value / 100.0);
        }

        private void OnSelectionChangeCommitted(object sender, EventArgs e)
        {
            Navigate((string)comboBox1.SelectedItem);
        }

        private void buttonHelp_Click(object sender, EventArgs e)
        {
            AboutDialog s = new AboutDialog();

            s.TopMost = this.TopMost;
            s.ShowDialog(this.Owner);
        }
    }
}
