namespace ClipDic
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다.
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
        /// </summary>
        private void InitializeComponent()
        {
			this.components = new System.ComponentModel.Container();
			this.splitContainer1 = new System.Windows.Forms.SplitContainer();
			this.trackBar1 = new System.Windows.Forms.TrackBar();
			this.m_cbAlwaysTop = new System.Windows.Forms.CheckBox();
			this.comboBox1 = new System.Windows.Forms.ComboBox();
			this.webBrowser1 = new System.Windows.Forms.WebBrowser();
			this.timer1 = new System.Windows.Forms.Timer(this.components);
			this.splitContainer1.Panel1.SuspendLayout();
			this.splitContainer1.Panel2.SuspendLayout();
			this.splitContainer1.SuspendLayout();
			((System.ComponentModel.ISupportInitialize)(this.trackBar1)).BeginInit();
			this.SuspendLayout();
			// 
			// splitContainer1
			// 
			this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.splitContainer1.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
			this.splitContainer1.Location = new System.Drawing.Point(0, 0);
			this.splitContainer1.Name = "splitContainer1";
			this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
			// 
			// splitContainer1.Panel1
			// 
			this.splitContainer1.Panel1.Controls.Add(this.trackBar1);
			this.splitContainer1.Panel1.Controls.Add(this.m_cbAlwaysTop);
			this.splitContainer1.Panel1.Controls.Add(this.comboBox1);
			// 
			// splitContainer1.Panel2
			// 
			this.splitContainer1.Panel2.Controls.Add(this.webBrowser1);
			this.splitContainer1.Size = new System.Drawing.Size(405, 503);
			this.splitContainer1.SplitterDistance = 25;
			this.splitContainer1.TabIndex = 0;
			// 
			// trackBar1
			// 
			this.trackBar1.Location = new System.Drawing.Point(194, 0);
			this.trackBar1.Maximum = 100;
			this.trackBar1.Minimum = 10;
			this.trackBar1.Name = "trackBar1";
			this.trackBar1.Size = new System.Drawing.Size(104, 45);
			this.trackBar1.TabIndex = 3;
			this.trackBar1.Value = 100;
			this.trackBar1.Scroll += new System.EventHandler(this.OnSlideScroll);
			// 
			// m_cbAlwaysTop
			// 
			this.m_cbAlwaysTop.AutoSize = true;
			this.m_cbAlwaysTop.Checked = true;
			this.m_cbAlwaysTop.CheckState = System.Windows.Forms.CheckState.Checked;
			this.m_cbAlwaysTop.Location = new System.Drawing.Point(314, 5);
			this.m_cbAlwaysTop.Name = "m_cbAlwaysTop";
			this.m_cbAlwaysTop.Size = new System.Drawing.Size(88, 16);
			this.m_cbAlwaysTop.TabIndex = 2;
			this.m_cbAlwaysTop.Text = "AlwaysTop";
			this.m_cbAlwaysTop.UseVisualStyleBackColor = true;
			this.m_cbAlwaysTop.CheckedChanged += new System.EventHandler(this.OnCheckedChangded);
			// 
			// comboBox1
			// 
			this.comboBox1.FormattingEnabled = true;
			this.comboBox1.Location = new System.Drawing.Point(3, 3);
			this.comboBox1.Name = "comboBox1";
			this.comboBox1.Size = new System.Drawing.Size(167, 20);
			this.comboBox1.TabIndex = 0;
			this.comboBox1.SelectionChangeCommitted += new System.EventHandler(this.OnSelectionChangeCommitted);
			// 
			// webBrowser1
			// 
			this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
			this.webBrowser1.Location = new System.Drawing.Point(0, 0);
			this.webBrowser1.MinimumSize = new System.Drawing.Size(20, 20);
			this.webBrowser1.Name = "webBrowser1";
			this.webBrowser1.ScrollBarsEnabled = false;
			this.webBrowser1.Size = new System.Drawing.Size(405, 474);
			this.webBrowser1.TabIndex = 1;
			this.webBrowser1.Url = new System.Uri("http://endic.naver.com/small_index.nhn", System.UriKind.Absolute);
			// 
			// timer1
			// 
			this.timer1.Enabled = true;
			this.timer1.Interval = 500;
			this.timer1.Tick += new System.EventHandler(this.OnTick);
			// 
			// Form1
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(405, 503);
			this.Controls.Add(this.splitContainer1);
			this.Name = "Form1";
			this.Text = "ClipDic by zelon";
			this.TopMost = true;
			this.splitContainer1.Panel1.ResumeLayout(false);
			this.splitContainer1.Panel1.PerformLayout();
			this.splitContainer1.Panel2.ResumeLayout(false);
			this.splitContainer1.ResumeLayout(false);
			((System.ComponentModel.ISupportInitialize)(this.trackBar1)).EndInit();
			this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.CheckBox m_cbAlwaysTop;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.WebBrowser webBrowser1;
        private System.Windows.Forms.TrackBar trackBar1;

    }
}

