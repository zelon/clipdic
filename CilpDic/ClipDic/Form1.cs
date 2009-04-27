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
            if (Clipboard.ContainsText())
            {
                string strCurrentText = Clipboard.GetText();
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
            return "http://endic.naver.com/small_search.nhn?query=" + strText + "&x=0&y=0";
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
    }
}
